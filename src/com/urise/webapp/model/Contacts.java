package com.urise.webapp.model;

import java.util.EnumMap;

public class Contacts {

    public void set(ContactTypes contacts, Object object) {
        enumMap.put(contacts, object);
    }

    public Object get() {
        return enumMap;
    }

    private EnumMap<ContactTypes, Object> enumMap = new EnumMap<>(ContactTypes.class);

    @Override
    public String toString() {
        String result = "";
        for (ContactTypes type : ContactTypes.values()) {
            result += type.getTitle() + " " + enumMap.get(type) + "\n";
        }
        return result;
    }
}
