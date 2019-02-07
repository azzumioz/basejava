package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    protected static final Comparator<Resume> RESUME_COMPARATOR_FULL_NAME = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    protected abstract void doSave(Object searchKey, Resume resume);

    protected abstract void doUpdate(Object searchKey, Resume resume);

    protected abstract Resume doGet(Object searchKey);

    protected abstract List<Resume> getAll();

    protected abstract void doDelete(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExist(Object searchKey);

    @Override
    public void save(Resume resume) {
        Object searchKey = getNotExistedSearchKey(resume.getUuid());
        doSave(searchKey, resume);
    }

    @Override
    public void update(Resume resume) {
        Object searchKey = getExistedSearchKey(resume.getUuid());
        doUpdate(searchKey, resume);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistedSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedList = getAll();
        sortedList.sort(RESUME_COMPARATOR_FULL_NAME);
        return sortedList;
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getExistedSearchKey(uuid);
        doDelete(searchKey);
    }

    protected Object getExistedSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected Object getNotExistedSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

}
