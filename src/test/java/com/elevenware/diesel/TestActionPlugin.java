package com.elevenware.diesel;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestActionPlugin {

    @Test
    public void configuresSelf() {

        String message = "this is the message in lowercase";
        ActionPlugin plugin = new SimpleContextPlugin();
        Foo foo = plugin.invoke("foo", message);

        assertNotNull(foo);

        assertEquals(message.toUpperCase(), foo.getText());

    }

    @Test
    public void answersToNames() {

        ActionPlugin plugin = new SimpleContextPlugin();

        assertTrue(plugin.canHandle("foo"));
        assertTrue(plugin.canHandle("bar"));

    }

}


