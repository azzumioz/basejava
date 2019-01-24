package com.urise.webapp.storage;

import org.junit.Test;

public class ListStorageTest extends AbstractArrayStorageTest {

    public ListStorageTest() {
        super(new ListStorage());
    }

    @Test
    public void saveOverflow() {
    }
}