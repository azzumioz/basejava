package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new HashMap<>();

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
    public void clear() {
        storage.clear();
    }

    @Override
    protected void doSave(Object searchKey, Resume r) {
        storage.put(r.getFullName(), r);
    }

    @Override
    protected void doUpdate(Object searchKey, Resume r) {
        storage.replace(r.getFullName(), r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get(searchKey);
    }


    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedList = new ArrayList(storage.values());
        Collections.sort(sortedList, RESUME_COMPARATOR_FULL_NAME);
        return sortedList;
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove(searchKey);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Object getSearchKey(String fullName) {
        return fullName;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        boolean temp = storage.containsKey(searchKey);
        return storage.containsKey(searchKey);
    }


}
