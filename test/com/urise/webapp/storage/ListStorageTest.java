package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.Assert;

public class ListStorageTest extends AbstractStorageTest {

    public ListStorageTest() {
        super(new ListStorage());
    }

    @Override
    protected void doTestUpdate(Resume resume, String uuid) {
        Assert.assertSame(resume, storage.get(uuid));
    }
}