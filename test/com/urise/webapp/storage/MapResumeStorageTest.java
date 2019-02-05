package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.Assert;

public class MapResumeStorageTest extends AbstractStorageTest {

    public MapResumeStorageTest() {
        super(new MapResumeStorage());
    }

    @Override
    protected void doTestUpdate(Resume resume, String uuid) {
        Assert.assertSame(resume, storage.get(resume.getFullName()));
    }
}