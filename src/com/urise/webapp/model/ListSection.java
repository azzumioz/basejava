package com.urise.webapp.model;

import java.util.List;

public class ListSection extends Section {
    private List<String> list;

    public ListSection(List<String> list) {
        this.list = list;
    }

    public void set(List<String> list) {
        this.list = list;
    }

    public List<String> get() {
        return list;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            result += list.get(i) + "\n";
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }
}
