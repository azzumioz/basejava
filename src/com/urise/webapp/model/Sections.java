package com.urise.webapp.model;

import java.util.EnumMap;

public class Sections {
    private EnumMap<SectionType, Object> enumMap = new EnumMap<>(SectionType.class);

    public void set(SectionType sectionType, Object object) {
        enumMap.put(sectionType, object);
    }

    public Object get() {
        return enumMap;
    }

    @Override
    public String toString() {
        String result = "";
        for (SectionType type : SectionType.values()) {
            result += type.getTitle() + "\n" + enumMap.get(type) + "\n";
        }
        return result;
    }
}
