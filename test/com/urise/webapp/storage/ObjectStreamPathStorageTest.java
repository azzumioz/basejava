package com.urise.webapp.storage;

import com.urise.webapp.storage.streamstorage.ObjectStreamStorage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new ObjectStreamStorage()));
    }

}