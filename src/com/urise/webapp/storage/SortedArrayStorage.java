package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class SortedArrayStorage extends AbstractArrayStorage {

    protected static final Comparator<Resume> RESUME_COMPARATOR_UUID = Comparator.comparing(Resume::getUuid);

    @Override
    protected List<Resume> getAll() {
        List<Resume> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(storage[i]);
        }
        return list;
    }

    @Override
    protected Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "temp");
        return (Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR_UUID));
    }

    @Override
    protected void insertElement(Resume resume, int index) {
        int insertIndex = -index - 1;
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
        storage[insertIndex] = resume;
    }

    @Override
    protected void fillDeletedElement(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

}
