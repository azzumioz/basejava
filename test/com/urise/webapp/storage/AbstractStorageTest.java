package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractStorageTest {
    public Storage getStorage() {
        return storage;
    }

    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1);
        RESUME_2 = new Resume(UUID_2);
        RESUME_3 = new Resume(UUID_3);
        RESUME_4 = new Resume(UUID_4);
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
        RESUME_1.setFullName("Name1");
        RESUME_2.setFullName("Name2");
        RESUME_3.setFullName("Name2");
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

//    @Test(expected = StorageException.class)
//    public void saveOverflow() {
//        storage.clear();
//        try {
//            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
//                storage.save(new Resume());
//            }
//        } catch (StorageException e) {
//            Assert.fail("Error: storage overflow before limit");
//        }
//        storage.save(new Resume());
//    }

    @Test
    public void update() {
        Resume RESUME_5 = new Resume(UUID_1);
        storage.update(RESUME_5);
        Assert.assertSame(RESUME_5, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_4);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test
    public void getAll() {
        Resume[] arrayExist = storage.getAll();
        Resume[] arrayTemp = new Resume[]{RESUME_1, RESUME_2, RESUME_3};
        Arrays.sort(arrayExist);
        Assert.assertEquals(3, arrayExist.length);
        Assert.assertArrayEquals(arrayTemp, arrayExist);
    }

    @Test
    public void getAllSorted() {
        List<Resume> arrayExist = storage.getAllSorted();
        List<Resume> arrayTemp = new ArrayList<>();
        arrayTemp.add(RESUME_1);
        arrayTemp.add(RESUME_2);
        arrayTemp.add(RESUME_3);
        Assert.assertEquals(3, arrayExist.size());
        Assert.assertEquals(arrayExist, arrayTemp);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    private void assertGet(Resume r) {
        Assert.assertEquals(r, storage.get(r.getUuid()));
    }

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }

}