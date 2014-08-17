package com.elevenware.diesel;

import groovy.lang.Closure;

import java.util.HashMap;
import java.util.Map;

public class ClosureExtractor {
    private Map<String, Closure> closures;

    public ClosureExtractor() {
        closures = new HashMap<>();
    }

    public Object register(String name, Closure closure) {
        this.closures.put(name, closure);
        return null;
    }

    public Closure getClosure(String name) {
        return closures.get(name);
    }
}
