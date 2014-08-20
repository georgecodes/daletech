package com.elevenware.daletech.lessons.hello;

import com.elevenware.daletech.ConfigurableGroovyDelegate;
import com.elevenware.daletech.DelegatingGroovyScriptParser;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestHelloWorld {

    @Test
    public void helloWorld() {

        ConfigurableGroovyDelegate delegate = new ConfigurableGroovyDelegate();
        HelloWorldPlugin hello = new HelloWorldPlugin();
        delegate.registerPlugin(hello);
        DelegatingGroovyScriptParser parser = new DelegatingGroovyScriptParser(delegate);

        parser.parseFromClasspath("hello.groovy");

        assertEquals("Hello, world!", hello.getMessage());


    }

}
