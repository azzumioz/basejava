package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public class OverflowArrayStorageTest extends AbstractStorageTest {

    protected OverflowArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        getStorage().clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                getStorage().save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail("Error: storage overflow before limit");
        }
        getStorage().save(new Resume());
    }
}
