package com.urise.webapp.model;

public class TextSection extends Sections {
    private String parameter;

    public void set(String parameter) {
        this.parameter = parameter;
    }

    public String get() {
        return parameter;
    }

    @Override
    public String toString() {
        return get() + "\n";
    }
}
