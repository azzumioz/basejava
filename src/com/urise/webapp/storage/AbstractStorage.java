package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume r) {
        String searchKey = getSearchKey(r.getUuid());
        if (isFind(searchKey)) {
            throw new ExistStorageException(r.getUuid());
        } else {
            doSave(searchKey, r);
        }
    }

    @Override
    public void update(Resume r) {
        String searchKey = getSearchKey(r.getUuid());
        if (isFind(searchKey)) {
            doUpdate(searchKey, r);
        } else {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        String searchKey = getSearchKey(uuid);
        if (isFind(searchKey)) {
            return doGet(searchKey);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        String searchKey = getSearchKey(uuid);
        if (isFind(searchKey)) {
            doRemove(searchKey);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract void doSave(String searchKey, Resume r);

    protected abstract void doUpdate(String searchKey, Resume r);

    protected abstract Resume doGet(String searchKey);

    protected abstract void doRemove(String searchKey);

    protected abstract String getSearchKey(String uuid);

    protected abstract boolean isFind(String searchKey);
}
