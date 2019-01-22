package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
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
    public void save(Resume r) {
        if (size < STORAGE_LIMIT) {
            int searchKey = getSearchKey(r.getUuid());
            if (searchKey > -1) {
                throw new ExistStorageException(r.getUuid());
            } else {
                insertElement(r, searchKey);
                size++;
            }
        } else {
            throw new StorageException("Storage overflow", r.getUuid());
        }
    }

    @Override
    public void update(Resume r) {
        int searchKey = getSearchKey(r.getUuid());
        if (searchKey > -1) {
            storage[searchKey] = r;
        } else {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        int searchKey = getSearchKey(uuid);
        if (searchKey < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage[searchKey];
    }

    @Override
    public void delete(String uuid) {
        int searchKey = getSearchKey(uuid);
        if (searchKey > -1) {
            fillDeletedElement(searchKey);
            storage[size - 1] = null;
            size--;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public int size() {
        return size;
    }

    protected abstract int getSearchKey(String uuid);

    protected abstract void insertElement(Resume r, int index);

    protected abstract void fillDeletedElement(int index);
}
