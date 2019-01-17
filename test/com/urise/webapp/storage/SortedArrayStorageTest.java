package com.urise.webapp.storage;

import static org.junit.Assert.*;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    private Storage storage = new ArrayStorage();

    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }
}