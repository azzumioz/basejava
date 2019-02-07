package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage {
    private List<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        list.add(resume);
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        list.set((Integer) searchKey, resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return list.get((int) searchKey);
    }

    @Override
    protected List<Resume> getAll() {
        return list;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedList = new ArrayList<>(list);
        sortedList.sort(RESUME_COMPARATOR_FULL_NAME);
        return sortedList;
    }

    @Override
    protected void doDelete(Object searchKey) {
        list.remove((int) searchKey);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return ((Integer) (searchKey) > -1);
    }
}
