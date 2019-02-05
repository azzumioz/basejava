package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        storage.put(resume.getFullName(), resume);
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        storage.replace(resume.getFullName(), resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected List<Resume> doSort(List<Resume> list) {
        List<Resume> sortedList = new ArrayList(storage.values());
        sortedList.sort(RESUME_COMPARATOR_FULL_NAME);
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
        return storage.containsKey(searchKey);
    }


}
