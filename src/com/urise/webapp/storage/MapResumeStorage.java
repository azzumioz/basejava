package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    protected Map<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    protected void doSave(Resume resumeAsKey, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected void doUpdate(Resume resumeAsKey, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Resume resumeAsKey) {
        return resumeAsKey;
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    protected void doDelete(Resume resumeAsKey) {
        map.remove((resumeAsKey).getUuid());
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected boolean isExist(Resume resumeAsKey) {
        return resumeAsKey != null;
    }


}
