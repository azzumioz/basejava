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
    protected void doSave(String searchKey, Resume r) {
        storage.add(r);
    }

    @Override
    protected void doUpdate(String searchKey, Resume r) {
        storage.set(Integer.parseInt(searchKey), r);
    }

    @Override
    protected Resume doGet(String searchKey) {
        return storage.get(Integer.parseInt(searchKey));
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    protected void doRemove(String searchKey) {
        storage.remove(Integer.parseInt(searchKey));
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected String getSearchKey(String uuid) {
        Iterator<Resume> iterator = storage.iterator();
        for (int i = 0; i < size(); i++) {
            Resume nextResume = iterator.next();
            if (nextResume.getUuid().equals(uuid)) {
                return String.valueOf(i);
            }
        }
        return String.valueOf(-1);
    }

    @Override
    protected boolean isFind(String searchKey) {
        return (Integer.parseInt(searchKey) > -1);
    }
}
