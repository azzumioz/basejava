package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {
    protected Map<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    protected void doSave(String uuid, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected void doUpdate(String uuid, Resume resume) {
        map.put(resume.getUuid(), resume);

    }

    @Override
    protected Resume doGet(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    protected void doDelete(String uuid) {
        map.remove(uuid);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String uuid) {
        return map.containsKey(uuid);
    }

}
