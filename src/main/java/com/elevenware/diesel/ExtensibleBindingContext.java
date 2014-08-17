package com.elevenware.diesel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ExtensibleBindingContext extends ConfigurableGroovyDelegate implements Map {

    private Map bindingDelegate;
    private Map<String, String> parameters;
    private Object entity;

    public ExtensibleBindingContext() {
        this.bindingDelegate = new HashMap();
        this.parameters = new HashMap<>();
    }

    @Override
    public int size() {
        return bindingDelegate.size();
    }

    @Override
    public boolean isEmpty() {
        return bindingDelegate.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return bindingDelegate.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return bindingDelegate.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return bindingDelegate.get(key);
    }

    @Override
    public Object put(Object key, Object value) {
        return bindingDelegate.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return bindingDelegate.remove(key);
    }

    @Override
    public void putAll(Map m) {
        bindingDelegate.putAll(m);
    }

    @Override
    public void clear() {
        bindingDelegate.clear();
    }

    @Override
    public Set keySet() {
        return bindingDelegate.keySet();
    }

    @Override
    public Collection values() {
        return bindingDelegate.values();
    }

    @Override
    public Set<Entry> entrySet() {
        return bindingDelegate.entrySet();
    }

    public void setParameter(String key, String value) {
        parameters.put(key, value);
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    public void setParameters(Map params) {
        parameters.putAll(params);
    }
}