package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.Assert;

public class AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Override
    protected void doTestUpdate(Resume resume, String uuid) {
        Assert.assertSame(resume, storage.get(uuid));
    }

}