package com.urise.webapp.storage;

import static org.junit.Assert.*;

public class ArrayStorageTest extends AbstractArrayStorageTest {
    private Storage storage = new SortedArrayStorage();

    public ArrayStorageTest() {
        super(new ArrayStorage());
    }
}