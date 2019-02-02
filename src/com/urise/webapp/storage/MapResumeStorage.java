package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class MapResumeStorage extends MapStorage {

    @Override
    public void save(Resume r) {
        Object searchKey = getNotExistedSearchKey(r.getFullName());
        doSave(searchKey, r);
    }

    @Override
    public void update(Resume r) {
        Object searchKey = getExistedSearchKey(r.getFullName());
        doUpdate(searchKey, r);
    }

    @Override
    protected void doSave(Object searchKey, Resume r) {
        storage.put(r.getFullName(), r);
    }

    @Override
    protected void doUpdate(Object searchKey, Resume r) {
        storage.replace(r.getFullName(), r);
    }

}
