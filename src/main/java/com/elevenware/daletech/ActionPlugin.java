package com.elevenware.daletech;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ActionPlugin {

    private List<InvocationDescriptor> descriptors;
    private List<String> names;

    public ActionPlugin() {
        this.descriptors = new ArrayList<>();
        this.names = new ArrayList<>();
        for (Method method : getClass().getDeclaredMethods()) {
            if (method.getAnnotation(ActionDelegate.class) != null) {
                InvocationDescriptor descriptor = new InvocationDescriptor();
                descriptor.setMethod(method);
                descriptor.setName(method.getName());
                descriptor.setTypes(new TypesMatcher(method.getParameterTypes()));
                descriptors.add(descriptor);
                names.add(method.getName());
            }
        }
    }

    public <T> T invoke(String target, Object...args) {
        Method method = findMethod(target, args);
        try {
            return (T) method.invoke(this, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Method findMethod(String target, Object[] args) {
        for (InvocationDescriptor descriptor : descriptors) {
            if (descriptor.matchesName(target) && descriptor.matchesArgs(args)) {
                return descriptor.getMethod();
            }
        }
        throw new RuntimeException("Cannot find a way to invoke (" + target + ")");
    }

    public boolean canHandle(String target) {
        return names.contains(target);
    }

    private class InvocationDescriptor {
        private Method method;
        private String name;
        private TypesMatcher types;

        public boolean matchesName(String target) {
            return name.equals(target);
        }

        public boolean matchesArgs(Object[] args) {
            return types.matchesObjectsInOrder(args);
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setTypes(TypesMatcher types) {
            this.types = types;
        }
    }
}
