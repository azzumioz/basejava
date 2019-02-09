package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage<Integer> {
    private List<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    protected void doSave(Integer searchKey, Resume resume) {
        list.add(resume);
    }

    @Override
    protected void doUpdate(Integer searchKey, Resume resume) {
        list.set(searchKey, resume);
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return list.get(searchKey);
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(list);
    }

    @Override
    protected void doDelete(Integer searchKey) {
        list.remove(searchKey.intValue());
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
    protected boolean isExist(Integer searchKey) {
        return (searchKey > -1);
    }
}
