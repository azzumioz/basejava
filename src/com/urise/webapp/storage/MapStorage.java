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
    protected void doSave(String searchKey, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void doUpdate(String searchKey, Resume r) {
        storage.replace(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(String searchKey) {
        return storage.get(searchKey);
    }


    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }


    @Override
    protected void doRemove(String searchKey) {
        storage.remove(searchKey);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected String getSearchKey(String uuid) {
        return storage.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected boolean isFind(String searchKey) {
        return (searchKey != null);
    }

}
