package com.elevenware.daletech;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author George McIntosh <george@elevenware.com>
 * @version 1.0
 * @see 20th August 2014
 *
 * A class which parses a Groovy script, passing an arbitrary object - typically a
 * {@link com.elevenware.daletech.ConfigurableGroovyDelegate} as a delegate, and allowing
 * client code access to that delegate object post-parsing.
 */
public class DelegatingGroovyScriptParser {

    private final Object delegate;

    public DelegatingGroovyScriptParser(Object delegate) {
        this.delegate = delegate;
    }

    /**
     * Parses the script from the provided {@link java.io.Reader}
     * @param reader
     */
    public void parse(Reader reader) {
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getCanonicalName());
        GroovyShell sh = new GroovyShell(getClass().getClassLoader(),new Binding(),cc);
        DelegatingScript script = null;
        script = (DelegatingScript)sh.parse(reader);
        script.setDelegate(delegate);
        script.run();
    }

    /**
     * Parses the script from the named classpath resource
     * @param path
     */
    public void parseFromClasspath(String path) {
        parse(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(path)));
    }

    /**
     * Returns the script delegate
     * @return
     */
    public Object getDelegate() {
        return delegate;
    }


}
