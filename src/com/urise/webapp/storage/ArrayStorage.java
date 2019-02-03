package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.List;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertElement(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    protected void fillDeletedElement(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected List<Resume> doSort(List<Resume> list) {
        list.sort(RESUME_COMPARATOR_FULL_NAME);
        return list;
    }

}
