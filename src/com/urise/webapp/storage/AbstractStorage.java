package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume r) {
        Object searchKey = getSearchKey(r.getUuid());
        if (isFind(searchKey)) {
            throw new ExistStorageException(r.getUuid());
        } else {
            doSave(searchKey, r);
        }
    }

    @Override
    public void update(Resume r) {
        Object searchKey = getSearchKey(r.getUuid());
        if (isFind(searchKey)) {
            doUpdate(searchKey, r);
        } else {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isFind(searchKey)) {
            return doGet(searchKey);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isFind(searchKey)) {
            doRemove(searchKey);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract void doSave(Object searchKey, Resume r);

    protected abstract void doUpdate(Object searchKey, Resume r);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doRemove(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isFind(Object searchKey);
}
