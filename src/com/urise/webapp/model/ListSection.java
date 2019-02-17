package com.urise.webapp.model;

import java.util.List;

public class ListSection extends Sections {
    private List<String> list;

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
}
