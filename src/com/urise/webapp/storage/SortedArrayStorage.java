package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void insertResume(int index, Resume r) {
        System.arraycopy(storage, (-index - 1), storage, -index, size - (-index - 1));
        storage[-index - 1] = r;
        size++;
    }

    @Override
    protected void deleteResume(int index, String uuid) {
        for (int i = index; i < size - 1; i++) {
            storage[i] = storage[i + 1];
        }
        storage[size] = null;
        size--;
    }
}
