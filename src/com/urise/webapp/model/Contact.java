package com.urise.webapp.model;

import java.util.EnumMap;

public class Contact {

    private EnumMap<ContactTypes, String> enumMap;

    public Contact(EnumMap<ContactTypes, String> enumMap) {
        this.enumMap = enumMap;
    }

    public Object get() {
        return enumMap;
    }

    public void set(ContactTypes contacts, String object) {
        enumMap.put(contacts, object);
    }

    @Override
    public String toString() {
        String result = "";
        for (ContactTypes type : ContactTypes.values()) {
            result += type.getTitle() + " " + enumMap.get(type) + "\n";
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        return enumMap.equals(contact.enumMap);
    }

    @Override
    public int hashCode() {
        return enumMap.hashCode();
    }
}
