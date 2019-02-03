package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.MapResumeStorage;
import com.urise.webapp.storage.Storage;

public class MainCollections {
    static final Storage ARRAY_STORAGE = new MapResumeStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1", "Name1");
        Resume r2 = new Resume("uuid2", "Name2");
        Resume r3 = new Resume("uuid3", "Name3");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        //System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getFullName()));
        System.out.println("Size: " + ARRAY_STORAGE.size());
        printAll();

        //List<Resume> list2 = ARRAY_STORAGE.getAllSorted();
        //list2.forEach(System.out::println);

        printAll();
        //ARRAY_STORAGE.delete(r3.getUuid());
        ARRAY_STORAGE.delete(r3.getFullName());
        printAll();
        ARRAY_STORAGE.update(r2);
        printAll();
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }

}
