package com.elevenware.daletech.lessons.language;

import java.util.ArrayList;
import java.util.List;

public class LittleBeanDefinition {

    private final String name;
    private String className;
    private List<LittleRef> constructorRefs;

    public LittleBeanDefinition(String name, String className) {
        this.name = name;
        this.className = className;
        this.constructorRefs = new ArrayList<>();
    }

    public void addConstructorRef(LittleRef ref) {
        this.constructorRefs.add(ref);
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public List<LittleRef> getConstructorRefs() {
        return constructorRefs;
    }

    public static class LittleRef {
        private final String name;

        public LittleRef(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
