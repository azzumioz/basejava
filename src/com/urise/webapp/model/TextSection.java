package com.urise.webapp.model;

public class TextSection extends Section {
    private String parameter;

    public TextSection(String parameter) {
        this.parameter = parameter;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TextSection that = (TextSection) o;

        return parameter.equals(that.parameter);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + parameter.hashCode();
        return result;
    }
}
