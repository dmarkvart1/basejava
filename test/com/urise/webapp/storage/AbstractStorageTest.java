package com.urise.webapp.storage;

import com.urise.webapp.ResumeTestData;
import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.Month;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("D:\\test\\basejava\\storage");
    protected Storage storage;

    private static final String UUID_1 = "1001";
    private static final String UUID_2 = "1002";
    private static final String UUID_3 = "1003";
    private static final String UUID_4 = "1004";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    private static final YearMonth date1 = YearMonth.of(2010, Month.FEBRUARY);
    private static final YearMonth date2 = YearMonth.of(2011, Month.MARCH);
    private static final YearMonth date3 = YearMonth.of(2012, Month.AUGUST);
    private static final YearMonth date4 = YearMonth.of(2013, Month.APRIL);
    private static final YearMonth date5 = YearMonth.of(2014, Month.JULY);
    private static final YearMonth date6 = YearMonth.of(2015, Month.SEPTEMBER);

    static {
        RESUME_1 = new Resume(UUID_1, "Ivan Petrovich");
        RESUME_2 = new Resume(UUID_2, "Alexandr Alexandrov");
        RESUME_3 = new Resume(UUID_3, "Fedor Pavlovich");
        RESUME_4 = new Resume(UUID_4, "Vadim Nikolaevich");

        RESUME_1.addContact(ContactType.EMAIL, "moyemail@mail.ru");
        RESUME_1.addContact(ContactType.PHONE, "111324134134111");
        RESUME_1.addSection(SectionType.OBJECTIVE, new TextContentSection("Programmer"));
        RESUME_1.addSection(SectionType.PERSONAL, new TextContentSection("AboutMe1"));
        RESUME_1.addSection(SectionType.ACHIEVEMENT, new ListStringSection("Достижение1", "Achivment2", "Achivment3", "Achivment4"));
        RESUME_1.addSection(SectionType.QUALIFICATIONS, new ListStringSection("JavaScript", "HTML5", "CSS", "SQL"));
        RESUME_1.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("http://www.nat.ru", "NewAgeTechnologies",
                                new Organization.Position(date1, date2, "position1", "content1"),
                                new Organization.Position(date3, date6, "position2", "content2"))));

        RESUME_1.addSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("http://www.yandex.ru", "Yandex",
                                new Organization.Position(date1, date5, "Student", "content1"),
                                new Organization.Position(date3, date4, "Aspirant", "content2"))));
    }

    public AbstractStorageTest(Storage storage) {
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
        assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertEquals(RESUME_4, storage.get("1004"));
        assertSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void update() {
        storage.update(new Resume("1001", "NewName"));
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
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete("1001");
        storage.get("1001");
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