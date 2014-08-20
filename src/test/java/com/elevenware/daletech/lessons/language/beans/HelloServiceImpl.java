package com.elevenware.daletech.lessons.language.beans;

public class HelloServiceImpl implements HelloService {

    private final MessageFactory factory;

    public HelloServiceImpl(MessageFactory factory) {
        this.factory = factory;
    }

    @Override
    public String getMessage(String key) {
        return factory.find(key);
    }
}
