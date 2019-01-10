package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size = 0;

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void save(Resume r) {
        if (size != storage.length) {
            Integer uuidResume = isResume(r.getUuid());
            if (uuidResume == null) {
                storage[size] = r;
                size++;
            } else {
                System.out.println("Resume with uuid " + storage[uuidResume] + " already exist");
            }
        } else {
            System.out.println("Resume not add. Storage is full");
        }
    }

    public void update(Resume r) {
        Integer uuidResume = isResume(r.getUuid());
        if (uuidResume == null) {
            System.out.println("Resume not found");
        } else {
            System.out.println("Resume with uuid " + storage[uuidResume] + " was update");
        }
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        System.out.println("Resume with uuid " + uuid + " not found");
        return null;
    }

    public void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                size--;
                return;
            }
        }
        System.out.println("Resume with uuid " + uuid + " not found");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    private Integer isResume(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

}
