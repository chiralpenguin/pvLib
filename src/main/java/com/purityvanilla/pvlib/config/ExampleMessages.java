package com.purityvanilla.pvlib.config;

import net.kyori.adventure.text.Component;

public class ExampleMessages extends Messages {
    public ExampleMessages(String filepath) {
        super(filepath);
    }

    public Component exampleMessage() {
        return getMessage("exampleMessage");
    }

    public Component exampleRules() {
        return getMessage("exampleRules");
    }
}
