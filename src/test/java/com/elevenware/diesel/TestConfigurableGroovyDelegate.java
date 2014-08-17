package com.elevenware.diesel;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.assertEquals;

public class TestConfigurableGroovyDelegate {

    @Test
    public void canAddPlugins() throws FileNotFoundException {

        InvocationCountingActionPlugin plugin1 = new InvocationCountingActionPlugin();
        InvocationRecordingPlugin plugin2 = new InvocationRecordingPlugin();
        ConfigurableGroovyDelegate delegate = new ConfigurableGroovyDelegate();
        delegate.registerPlugin(plugin1);
        delegate.registerPlugin(plugin2);

        DelegatingGroovyScriptParser parser = new DelegatingGroovyScriptParser(delegate);
        parser.parse(new FileReader("src/test/resources/pluggable.groovy"));

        assertEquals(1, plugin1.getInvocations());
        assertEquals(1, plugin2.getInvocations().size());
        assertEquals("This is a message to you", plugin2.getInvocations().get(0));

    }

}
