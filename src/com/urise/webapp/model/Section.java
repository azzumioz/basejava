package com.urise.webapp.model;

import java.util.EnumMap;

public class Section {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Section section = (Section) o;

        return enumMap.equals(section.enumMap);
    }

    @Override
    public int hashCode() {
        return enumMap.hashCode();
    }
}
