package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.Assert;

public class MapUuidStorageTest extends AbstractStorageTest {

    public MapUuidStorageTest() {
        super(new MapUuidStorage());
    }

    @Override
    protected void doTestUpdate(Resume resume, String uuid) {
        Assert.assertSame(resume, storage.get(uuid));
    }
}