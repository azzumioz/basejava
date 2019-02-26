package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractStorageTest {

    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1, "Name1");
        RESUME_2 = new Resume(UUID_2, "Name2");
        RESUME_3 = new Resume(UUID_3, "Name3");
        RESUME_4 = new Resume(UUID_4, "Name4");

        RESUME_1.addContacts(ContactTypes.EMAIL, "ee@mail.ru");
        RESUME_1.addContacts(ContactTypes.PHONE, "88001223535");
        RESUME_1.addSections(SectionType.OBJECTIVE, new TextSection("Objective1"));
        RESUME_1.addSections(SectionType.PERSONAL, new TextSection("Personal"));
        RESUME_1.addSections(SectionType.ACHIEVEMENT, new ListSection("Achievement1", "Achievement2", "Achievement3"));
        RESUME_1.addSections(SectionType.QUALIFICATIONS, new ListSection("Java", "SQL", "JS"));
        RESUME_1.addSections(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Org1", "hhtp://org1.com",
                                new Organization.Position(2002, Month.OCTOBER, "position1", "content1"),
                                new Organization.Position(2003, Month.SEPTEMBER, 2004, Month.OCTOBER,"position2", "content2"))));
        RESUME_1.addSections(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Edu1", "hhtp://edu1.com",
                                new Organization.Position(2004, Month.OCTOBER,  2005, Month.OCTOBER,"position1", null),
                                new Organization.Position(2005, Month.SEPTEMBER,  2006, Month.OCTOBER,"position2", "content2")),
                        new Organization("Edu2", "http://edu2.com")));
        RESUME_2.addContacts(ContactTypes.SKYPE, "skype1");
        RESUME_2.addContacts(ContactTypes.PHONE, "1222232");
        RESUME_2.addSections(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Org2", "hhtp://org2.com",
                                new Organization.Position(2011, Month.SEPTEMBER, "position2", "content2"))));
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

    @Test
    public void update() {
        Resume RESUME_5 = new Resume(UUID_1, "new Name");
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
    public void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(Arrays.asList(RESUME_1, RESUME_2, RESUME_3), list);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    private void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }

}