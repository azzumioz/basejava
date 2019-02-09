package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    protected void doSave(Integer searchKey, Resume resume) {
        if (size < STORAGE_LIMIT) {
            insertElement(resume, (Integer) searchKey);
            size++;
        } else {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
    }

    protected void doUpdate(Integer searchKey, Resume resume) {
        storage[searchKey] = resume;
    }

    protected Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    protected void doDelete(Integer searchKey) {
        fillDeletedElement(searchKey);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void insertElement(Resume resume, int index);

    protected abstract void fillDeletedElement(int index);

    @Override
    protected List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

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
    protected boolean isExist(Integer searchKey) {
        return (searchKey > -1);
    }

}
