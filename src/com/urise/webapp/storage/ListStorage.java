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
    protected void doSave(Resume r) {
        storage.add(r);
    }

    @Override
    protected void doSet(Resume r) {
        storage.set(getSearchKey(r.getUuid()), r);
    }

    @Override
    protected Resume doGet(String uuid) {
        return storage.get(getSearchKey(uuid));
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    protected void doRemove(String uuid) {
        storage.remove(getSearchKey(uuid));
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected int getSearchKey(String uuid) {
        Resume searchResume = new Resume(uuid);
        Iterator<Resume> iter = storage.iterator();
        for (int i = 0; i < size(); i++) {
            Resume nextResume = iter.next();
            if (nextResume.compareTo(searchResume) == 0) {
                return i;
            }
        }
        return -1;
    }
}
