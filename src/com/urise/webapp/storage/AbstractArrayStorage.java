package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index > -1) {
            return storage[index];
        }
        System.out.println("Resume " + uuid + " not found");
        return null;
    }

    public void save(Resume r) {
        if (size < STORAGE_LIMIT) {
            int index = getIndex(r.getUuid());
            if (index <= -1) {
                insertResume(index, r);
            } else {
                System.out.println("Resume " + r.getUuid() + " already exist");
            }
        } else {
            System.out.println("Resume not add. Storage is full");
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index > -1) {
            deleteResume(index, uuid);
            return;
        }
        System.out.println("Resume " + uuid + " not found");
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index <= -1) {
            System.out.println("Resume not found");
        } else {
            storage[index] = r;
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insertResume(int index, Resume r);

    protected abstract void deleteResume(int index, String uuid);
}
