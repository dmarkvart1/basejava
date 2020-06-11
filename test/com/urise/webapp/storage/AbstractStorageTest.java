package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static com.urise.webapp.storage.SortedArrayStorage.RESUME_COMPARATOR;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "UUID_1";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final String UUID_2 = "UUID_2";
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final String UUID_3 = "UUID_3";
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final String UUID_4 = "UUID_4";
    private static final Resume RESUME_4 = new Resume(UUID_4);


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
        assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertEquals(RESUME_4, storage.get(UUID_4));
        assertSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void update() {
        storage.update(new Resume(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("NotExist"));
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_3);
        storage.get(UUID_3);
        assertSize(2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void getAll() {
        Resume[] actualResumes = {RESUME_1, RESUME_2, RESUME_3};
        Resume[] expectedResumes = storage.getAll();
        Arrays.sort(expectedResumes, RESUME_COMPARATOR);
        assertArrayEquals(actualResumes, expectedResumes);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}