package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size != storage.length) {
            if (!isResume(r.getUuid())) {
                storage[size] = r;
                size++;
            } else {
                System.out.println("Resume with uuid " + r.getUuid() + " already exist");
            }
        } else System.out.println("Resume not add. Storage is full");
    }

    public void update(Resume r) {
        isResume(r.getUuid());

    }

    public Resume get(String uuid) {
        if (isResume(uuid)) {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    return storage[i];
                }
            }
        } else {
            System.out.println("Resume with uuid " + uuid + " not found");
        }
        return null;
    }

    public void delete(String uuid) {
        if (isResume(uuid)) {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    storage[i] = storage[size - 1];
                    storage[size - 1] = null;
                    size--;
                }
            }
        } else System.out.println("Resume with uuid " + uuid + " not found");
    }

    private boolean isResume(String uuid) {
        boolean isResume = false;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                isResume = true;
            }
        }
        return isResume;
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
}
