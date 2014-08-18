package com.elevenware.daletech;

import groovy.lang.GroovyObjectSupport;

import java.util.*;

public class ConfigurableGroovyDelegate extends GroovyObjectSupport  {

    private Map<String, String> parameters;
    private List<ActionPlugin> plugins;
    private Object entity;

    public ConfigurableGroovyDelegate() {
        this.parameters = new HashMap<>();
        this.plugins = new ArrayList<>();
    }

    @Override
    public Object invokeMethod(String name, Object args) {
        Object[] realArgs;
        if(args.getClass().isArray()) {
            realArgs = (Object[]) args;
        } else {
            realArgs = new Object[] { args };
        }
        for(ActionPlugin plugin: plugins) {
            if(plugin != null && plugin.canHandle(name)) {
                return plugin.invoke(name, realArgs);
            }
        }
        return null;
    }

    public Object methodMissing(String name, Object args) {
        return invokeMethod(name, args);
    }

    public void registerPlugin(ActionPlugin plugin) {
        plugins.add(plugin);
    }


    public void registerPlugins(Collection<ActionPlugin> plugins) {
        for(ActionPlugin plugin:plugins) {
            registerPlugin(plugin);
        }
    }

}
