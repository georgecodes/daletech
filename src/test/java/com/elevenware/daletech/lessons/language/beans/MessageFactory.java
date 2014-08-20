package com.elevenware.daletech.lessons.language.beans;

public interface MessageFactory {
    String find(String key);
    void store(String key, String value);
}
