package com.elevenware.daletech;


import java.util.List;

public class SimpleContextPlugin extends ActionPlugin {

    @ActionDelegate
    public Foo foo(String text) {
        return new Foo(text.toUpperCase());
    }

    @ActionDelegate
    public Foo bar(String text, List list) {
        return new Foo(text.toUpperCase());
    }

}
