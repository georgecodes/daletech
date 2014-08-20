package com.elevenware.daletech.lessons.language.beans;

import java.util.HashMap;
import java.util.Map;

public class MessageFactoryImpl implements MessageFactory {

    private Map<String, String> data;

    public MessageFactoryImpl() {
        data = new HashMap<>();
    }

    @Override
    public String find(String key) {
        return data.get(key);
    }

    @Override
    public void store(String key, String value) {
        data.put(key, value);
    }
}
