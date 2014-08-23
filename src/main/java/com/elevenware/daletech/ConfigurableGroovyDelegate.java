package com.elevenware.daletech;

import groovy.lang.GroovyObjectSupport;

import java.util.*;

/**
 * @author George McIntosh <george@elevenware.com>
 * @version 1.0
 * @since 20th August 2014
 *
 * This class is useful as a delegate to parsed scripts, allowing for method calls
 * to be intercepted and delegated to configured plugins. This is also useful
 * when using this class as a delegate to a captured closure.
 *
 *
 */
public class ConfigurableGroovyDelegate extends GroovyObjectSupport  {

    private List<ActionPlugin> plugins;
    private Object entity;

    public ConfigurableGroovyDelegate() {
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
                process(plugin);
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

    protected void process(ActionPlugin plugin) {

    }

}
