package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    protected void doSave(Object searchKey, Resume resume) {
        if (size < STORAGE_LIMIT) {
            insertElement(resume, (Integer) searchKey);
            size++;
        } else {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
    }

    protected void doUpdate(Object searchKey, Resume resume) {
        storage[(Integer) searchKey] = resume;
    }

    protected Resume doGet(Object searchKey) {
        return storage[(Integer) searchKey];
    }

    protected void doDelete(Object searchKey) {
        fillDeletedElement((Integer) searchKey);
        storage[size - 1] = null;
        size--;
    }

    protected abstract List<Resume> doSort(List<Resume> list);

    protected abstract void insertElement(Resume resume, int index);

    protected abstract void fillDeletedElement(int index);

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return ((Integer) searchKey > -1);
    }
}
