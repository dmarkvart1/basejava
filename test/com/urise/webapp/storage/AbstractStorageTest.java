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

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    private static final String UUID1 = "4e0e8d60-2c29-41aa-959c-c12e9fe1d5b0";
    private static final String UUID2 = "4a2a895c-a662-421e-aed1-8e698e561c03";
    private static final String UUID3 = "5f3cbb03-3024-479e-b297-7b93d3fddb1b";
    private static final String UUID4 = "2f3cbb03-3024-479e-b297-7b93d3fddb1c";

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    protected static final File STORAGE_DIR = Config.get().getStorageDir();
    protected Storage storage;

    private Resume RESUME_1 = new Resume(UUID1, "Ivan Petrovich");
    private Resume RESUME_2 = ResumeTestData.createResume(UUID2, "Alexandr Alexandrov");
    private Resume RESUME_3 = ResumeTestData.createResume(UUID3, "Fedor Pavlovich");
    private Resume RESUME_4 = ResumeTestData.createResume(UUID4, "Vadim Nikolaevich");

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
        assertEquals(RESUME_4, storage.get(UUID4));
        assertSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void update() {
        Resume updated = new Resume(UUID2, "NewName");
        updated.addContact(ContactType.EMAIL, "my@mail.my");
        updated.addContact(ContactType.PHONE, "54656664645");
        updated.addSection(SectionType.OBJECTIVE, new TextContentSection("NewProgrammer"));
        updated.addSection(SectionType.PERSONAL, new TextContentSection("NewAboutMe"));
        updated.addSection(SectionType.ACHIEVEMENT, new ListStringSection("NewДостижение1", "NewAchivment2", "NEwAchivment3", "NEwAchivment4"));
        updated.addSection(SectionType.QUALIFICATIONS, new ListStringSection("NewJavaScript", "NewHTML5", "NEwCSS", "NEwSQL"));
        storage.update(updated);
        assertGet(updated);
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
        storage.delete(UUID1);
        storage.get(UUID1);
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