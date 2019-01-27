package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void doSave(int searchKey, Resume r) {
        storage.add(r);
    }

    @Override
    protected void doSet(int searchKey, Resume r) {
        storage.set(searchKey, r);
    }

    @Override
    protected Resume doGet(int searchKey) {
        return storage.get(searchKey);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    protected void doRemove(int searchKey) {
        storage.remove(searchKey);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected int getSearchKey(String uuid) {
        Iterator<Resume> iterator = storage.iterator();
        for (int i = 0; i < size(); i++) {
            Resume nextResume = iterator.next();
            if (nextResume.getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
