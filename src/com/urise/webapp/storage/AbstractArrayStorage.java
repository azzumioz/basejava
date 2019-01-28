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

    protected void doSave(String searchKey, Resume r) {
        if (size < STORAGE_LIMIT) {
            insertElement(r, Integer.parseInt(searchKey));
            size++;
        } else {
            throw new StorageException("Storage overflow", r.getUuid());
        }
    }

    protected void doUpdate(String searchKey, Resume r) {
        storage[Integer.parseInt(searchKey)] = r;
    }

    protected Resume doGet(String searchKey) {
        return storage[Integer.parseInt(searchKey)];
    }

    protected void doRemove(String searchKey) {
        fillDeletedElement(Integer.parseInt(searchKey));
        storage[size - 1] = null;
        size--;
    }

    protected abstract void insertElement(Resume r, int index);

    protected abstract void fillDeletedElement(int index);

    @Override
    protected boolean isFind(String searchKey) {
        return (Integer.parseInt(searchKey) > -1);
    }
}
