package com.urise.webapp.storage;

import com.urise.webapp.storage.streamstorage.ObjectStreamStorage;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {

    public ObjectStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamStorage()));
    }

}