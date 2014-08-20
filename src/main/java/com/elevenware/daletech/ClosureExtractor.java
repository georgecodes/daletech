package com.elevenware.daletech;

import groovy.lang.Closure;

import java.util.HashMap;
import java.util.Map;

/**
 * @author George McIntosh <george@elevenware.com>
 * @version 1.0
 * @since 20th August 2014
 *
 * ClosureExtractor - can be used for extracting closures from a script. Useful in testing closure delegates
 *
 */
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
