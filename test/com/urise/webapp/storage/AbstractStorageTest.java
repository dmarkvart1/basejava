package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.ResumeTestData;
import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }
    protected static final File STORAGE_DIR = Config.get().getStorageDir();
    protected Storage storage;

    private  Resume RESUME_1 = ResumeTestData.createResume("4e0e8d60-2c29-41aa-959c-c12e9fe1d5b0", "Ivan Petrovich");
    private  Resume RESUME_2 = ResumeTestData.createResume("4a2a895c-a662-421e-aed1-8e698e561c03", "Alexandr Alexandrov");
    private  Resume RESUME_3 = ResumeTestData.createResume("5f3cbb03-3024-479e-b297-7b93d3fddb1b", "Fedor Pavlovich");
    private  Resume RESUME_4 = ResumeTestData.createResume("2f3cbb03-3024-479e-b297-7b93d3fddb1c", "Vadim Nikolaevich");

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
        assertEquals(RESUME_4, storage.get("2f3cbb03-3024-479e-b297-7b93d3fddb1c"));
        assertSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void update() {
        storage.update(new Resume("4e0e8d60-2c29-41aa-959c-c12e9fe1d5b0", "NewName"));
        System.out.println("Object is update: " + storage.get(RESUME_1.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("NotExist", "NotExistName"));
    }

    @Test
    public void get() {
        assertGet(RESUME_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete("4e0e8d60-2c29-41aa-959c-c12e9fe1d5b0");
        storage.get("4e0e8d60-2c29-41aa-959c-c12e9fe1d5b0");
        assertSize(0);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void getAllSorted() {
        List<Resume> expectedResumes = Arrays.asList(RESUME_2, RESUME_3, RESUME_1);
        List<Resume> actualResumes = storage.getAllSorted();
        assertEquals(expectedResumes, actualResumes);
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