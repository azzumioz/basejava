package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume r) {
        int searchKey = getSearchKey(r.getUuid());
        if (searchKey > -1) {
            throw new ExistStorageException(r.getUuid());
        } else {
            doSave(r);
        }
    }

    @Override
    public void update(Resume r) {
        int searchKey = getSearchKey(r.getUuid());
        if (searchKey > -1) {
            doSet(searchKey, r);
        } else {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        int searchKey = getSearchKey(uuid);
        if (searchKey > -1) {
            return doGet(searchKey);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        int searchKey = getSearchKey(uuid);
        if (searchKey > -1) {
            doRemove(searchKey);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract void doSave(Resume r);

    protected abstract void doSet(int searchKey, Resume r);

    protected abstract Resume doGet(int searchKey);

    protected abstract void doRemove(int searchKey);

    protected abstract int getSearchKey(String uuid);
}
