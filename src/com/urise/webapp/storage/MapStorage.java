package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void doSave(int searchKey, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void doSet(int searchKey, Resume r) {
        storage.replace(r.getUuid(), r);
    }

    @Override
    public Resume get(String uuid) {
        int searchKey = getSearchKey(uuid);
        if (searchKey > -1) {
            return doGet(uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    protected Resume doGet(int searchKey) {
        return null;
    }

    private Resume doGet(String uuid) {
        return storage.get(uuid);
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public void delete(String uuid) {
        int searchKey = getSearchKey(uuid);
        if (searchKey > -1) {
            doRemove(uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    protected void doRemove(int searchKey) {
    }

    private void doRemove(String uuid) {
        storage.remove(uuid);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected int getSearchKey(String uuid) {
        return storage.containsKey(uuid) ? 1 : -1;
    }

}
