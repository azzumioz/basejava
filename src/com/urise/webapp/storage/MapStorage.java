package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
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
    public void save(Resume r) {
        if (!storage.containsKey(r.getUuid())) {
            storage.put(r.getUuid(), r);
        } else {
            throw new ExistStorageException(r.getUuid());
        }
    }

    @Override
    public void update(Resume r) {
        if (!storage.containsKey(r.getUuid())) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            storage.replace(r.getUuid(), r);
        }
    }

    @Override
    public Resume get(String uuid) {
        int searchKey = getSearchKey(uuid);
        if (storage.containsKey(uuid)) {
            return storage.get(uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        if (storage.containsKey(uuid)) {
            storage.remove(uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    protected void doSave(Resume r) {

    }

    @Override
    protected void doSet(int searchKey, Resume r) {

    }

    @Override
    protected Resume doGet(int searchKey) {
        return storage.get(searchKey);
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    protected void doRemove(int searchKey) {

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
