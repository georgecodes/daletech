package com.elevenware.daletech;

public class InvocationCountingActionPlugin extends ActionPlugin {

    private int invocations = 0;

    public int getInvocations() {
        return invocations;
    }

    @ActionDelegate
    public void doSomething() {
        invocations++;
    }

}
