package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract SK getSearchKey(String uuid);

    protected abstract void doSave(SK searchKey, Resume resume);

    protected abstract void doUpdate(SK searchKey, Resume resume);

    protected abstract Resume doGet(SK searchKey);

    protected abstract List<Resume> doCopyAll();

    protected abstract void doDelete(SK searchKey);

    protected abstract boolean isExist(SK searchKey);

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        SK searchKey = getNotExistedSearchKey(resume.getUuid());
        doSave(searchKey, resume);
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        SK searchKey = getExistedSearchKey(resume.getUuid());
        doUpdate(searchKey, resume);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK searchKey = getExistedSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> sortedList = doCopyAll();
        Collections.sort(sortedList);
        return sortedList;
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK searchKey = getExistedSearchKey(uuid);
        doDelete(searchKey);
    }

    private SK getExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

}
