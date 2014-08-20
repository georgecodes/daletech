package com.elevenware.daletech.lessons.language;

import com.elevenware.daletech.ActionDelegate;
import com.elevenware.daletech.ActionPlugin;
import groovy.lang.Closure;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BeansPlugin extends ActionPlugin {

    private Map<String, LittleBeanDefinition> beans;
    private LittleBeanDefinition current;

    public  BeansPlugin() {
        beans = new HashMap<>();
    }

    @ActionDelegate
    public void beans(Closure closure) {
        closure.setDelegate(this);
        closure.call();
    }

    @ActionDelegate
    public void bean(Map args, String name) {
        String className = (String) args.get("class");
        LittleBeanDefinition beanDefinition = new LittleBeanDefinition(name, className);
        beans.put(name, beanDefinition);
        current = beanDefinition;
    }

    @ActionDelegate
    public void bean(Map args, String name, Closure closure) {
        String className = (String) args.get("class");
        LittleBeanDefinition beanDefinition = new LittleBeanDefinition(name, className);
        beans.put(name, beanDefinition);
        current = beanDefinition;
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }

    @ActionDelegate
    public void ref(String name) {
        current.addConstructorRef(new LittleBeanDefinition.LittleRef(name));
    }

    public Collection<LittleBeanDefinition> getBeans() {
        return beans.values();
    }
}
