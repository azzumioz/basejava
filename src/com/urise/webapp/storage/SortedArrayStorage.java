package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    public void save(Resume r) {
        if (size < STORAGE_LIMIT) {
            int index = -1 * getIndex(r.getUuid()) - 1;
            Resume[] tempResume = new Resume[size + 1];
            if (index >= 0) {
                tempResume = getAll();
                storage[index] = r;
                size++;
                for (int i = index + 1; i < size; i++) {
                    storage[i] = tempResume[i - 1];
                }
            } else {
                System.out.println("Resume " + r.getUuid() + " already exist");
            }
        } else {
            System.out.println("Resume not add. Storage is full");
        }
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index <= -1) {
            System.out.println("Resume not found");
        } else {
            storage[index] = r;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index > -1) {
            for (int i = index; i < size - 1; i++) {
                storage[i] = storage[i + 1];
            }
            size--;
            return;
        }
        System.out.println("Resume " + uuid + " not found");
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
