package com.elevenware.daletech;

import java.util.ArrayList;
import java.util.List;

public class InvocationRecordingPlugin extends ActionPlugin {

    private List<String> invocations;

    public InvocationRecordingPlugin() {
        this.invocations = new ArrayList<>();
    }

    @ActionDelegate
    public void record(String message) {
        this.invocations.add(message);
    }

    public List<String> getInvocations() {
        return invocations;
    }
}
