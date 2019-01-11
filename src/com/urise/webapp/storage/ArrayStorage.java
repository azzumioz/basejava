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
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size < storage.length) {
            int uuidResume = getIndex(r.getUuid());
            if (uuidResume == -1) {
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
        int uuidResume = getIndex(r.getUuid());
        if (uuidResume == -1) {
            System.out.println("Resume not found");
        } else {
            storage[uuidResume] = r;
        }
    }

    public Resume get(String uuid) {
        int uuidResume = getIndex(uuid);
        if (uuidResume != -1) {
            return storage[uuidResume];
        }
        System.out.println("Resume with uuid " + uuid + " not found");
        return null;
    }

    public void delete(String uuid) {
        int uuidResume = getIndex(uuid);
        if (uuidResume != -1) {
            storage[uuidResume] = storage[size - 1];
            storage[size - 1] = null;
            size--;
            return;
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

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

}
