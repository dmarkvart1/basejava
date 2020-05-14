package com.urise.webapp.storage;

import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.exeption.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private static Storage storage;
    private static Resume resume;

    public AbstractArrayStorageTest(Storage storage)  {
        this.storage = storage;
    }

    private static final String UUID_1 = "UUID_1";
    private static final String UUID_2 = "UUID_2";
    private static final String UUID_3 = "UUID_3";
    private static final String UUID_4 = "UUID_4";


    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
        storage.save(new Resume(UUID_4));
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test(expected = StorageException.class)
    public void save() {
            for (int i = 0; i < 10000; i++) {
                resume = new Resume();
                storage.save(resume);
                if (i == 100) {
                    fail("заваливаем тест...");
                }
            }
        }

    @Test
    public void update() {
        storage.update(new Resume(UUID_4));
    }

    @Test
    public void get() {
        assertEquals("UUID_4", storage.get("UUID_4").getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void delete() {
        storage.delete("UUID_4");
    }

    @Test
    public void getAll() {
        assertEquals(4, storage.getAll().length);
    }

    @Test
    public void size() {
        assertEquals(4, storage.size());
    }
}