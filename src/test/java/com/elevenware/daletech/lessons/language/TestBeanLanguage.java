package com.elevenware.daletech.lessons.language;

import com.elevenware.daletech.ConfigurableGroovyDelegate;
import com.elevenware.daletech.DelegatingGroovyScriptParser;
import com.elevenware.daletech.ExtensibleBindingContext;
import com.elevenware.daletech.lessons.language.beans.HelloServiceImpl;
import com.elevenware.daletech.lessons.language.beans.MessageFactory;
import com.elevenware.daletech.lessons.language.beans.MessageFactoryImpl;
import org.junit.Test;

import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class TestBeanLanguage {

    @Test
    public void parseLanguage() {

        ConfigurableGroovyDelegate delegate = new ConfigurableGroovyDelegate();
        BeansPlugin plugin = new BeansPlugin();
        delegate.registerPlugin(plugin);
        DelegatingGroovyScriptParser parser = new DelegatingGroovyScriptParser(delegate);

        parser.parseFromClasspath("beans.groovy");

        Collection<LittleBeanDefinition> beans = plugin.getBeans();

        assertEquals(2, beans.size());

        LittleBeanDefinition hello = null;
        LittleBeanDefinition messages = null;
        for(LittleBeanDefinition defintion: beans) {
            if(defintion.getName().equals("helloService")) {
                hello = defintion;
            }
            if(defintion.getName().equals("messageFactory")) {
                messages = defintion;
            }
        }

        assertEquals(HelloServiceImpl.class.getCanonicalName(), hello.getClassName());
        assertEquals(MessageFactoryImpl.class.getCanonicalName(), messages.getClassName());

        List<LittleBeanDefinition.LittleRef> refs = messages.getConstructorRefs();
        assertEquals(1, refs.size());

        assertEquals(hello.getName(), refs.get(0).getName());

    }

}
