package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public int size() {
        return size;
    }

    protected void doSave(int searchKey, Resume r) {
        if (size < STORAGE_LIMIT) {
            insertElement(r, searchKey);
            size++;
        } else {
            throw new StorageException("Storage overflow", r.getUuid());
        }
    }

    protected void doSet(int searchKey, Resume r) {
        storage[searchKey] = r;
    }

    protected Resume doGet(int searchKey) {
        return storage[searchKey];
    }

    protected void doRemove(int searchKey) {
        fillDeletedElement(searchKey);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void insertElement(Resume r, int index);

    protected abstract void fillDeletedElement(int index);
}
