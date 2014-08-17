package com.elevenware.diesel;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.Reader;

public class DelegatingGroovyScriptParser {

    private final Object delegate;

    public DelegatingGroovyScriptParser(Object delegate) {
        this.delegate = delegate;
    }

    public void parse(Reader reader) {
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getCanonicalName());
        GroovyShell sh = new GroovyShell(getClass().getClassLoader(),new Binding(),cc);
        DelegatingScript script = null;
        script = (DelegatingScript)sh.parse(reader);
        script.setDelegate(delegate);
        script.run();
    }

    public Object getDelegate() {
        return delegate;
    }
}
