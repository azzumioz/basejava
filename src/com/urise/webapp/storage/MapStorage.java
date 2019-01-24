package com.urise.webapp.storage;

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
    protected void doSave(Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void doSet(Resume r) {
        storage.replace(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(String uuid) {
        return storage.get(uuid);
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    protected void doRemove(String uuid) {
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
