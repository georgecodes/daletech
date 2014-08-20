package com.elevenware.daletech.lessons.hello;

import com.elevenware.daletech.ActionDelegate;
import com.elevenware.daletech.ActionPlugin;

public class HelloWorldPlugin extends ActionPlugin {
    private String message;

    @ActionDelegate
    public void sayHello() {
        message = "Hello, world!";
    }

    public String getMessage() {
        return message;
    }
}
