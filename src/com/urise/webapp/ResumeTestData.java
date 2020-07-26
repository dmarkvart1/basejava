package com.urise.webapp;

import com.urise.webapp.model.*;
import com.urise.webapp.storage.ListStorage;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.model.Resume;

import java.time.Month;
import java.time.YearMonth;


public class ResumeTestData {

    private static final String UUID_1 = "UUID_1";
    private static final String UUID_2 = "UUID_2";
    private static final String UUID_3 = "UUID_3";

    private static final Resume RESUME_1 = new Resume(UUID_1, "Name1");
    private static final Resume RESUME_2 = new Resume(UUID_2, "Name2");
    private static final Resume RESUME_3 = new Resume(UUID_3, "Name3");
    private static final YearMonth date1 = YearMonth.of(2010, Month.FEBRUARY);
    private static final YearMonth date2 = YearMonth.of(2011, Month.MARCH);
    private static final YearMonth date3 = YearMonth.of(2012, Month.AUGUST);
    private static final YearMonth date4 = YearMonth.of(2013, Month.APRIL);

    static {
        RESUME_1.addContact(ContactInfoSection.EMAIL, "moyemail@mail.ru");
        RESUME_1.addContact(ContactInfoSection.PHONE, "111324134134111");
        RESUME_1.addSection(SectionType.OBJECTIVE, new TextContentSection("Programming"));
        RESUME_1.addSection(SectionType.PERSONAL, new TextContentSection("AboutMe"));
        RESUME_1.addSection(SectionType.ACHIEVEMENT, new ListStringSection("Achivment1", "Achivment2", "Achivment3", "Achivment4"));
        RESUME_1.addSection(SectionType.QUALIFICATIONS, new ListStringSection("JavaScript", "HTML5", "CSS", "SQL"));
        RESUME_1.addSection(SectionType.EXPERIENCE, new ExperienceEducationSection("http://www.mail.ru",
                "Organization11", date1, date2, "InnoForce", "DescriptionInfo"));
        RESUME_1.addSection(SectionType.EDUCATION, new ExperienceEducationSection("http://www.yandex.ru", "Politech",
                        date3, date4, "aspirant", "StudentInfo"));

        RESUME_2.addContact(ContactInfoSection.EMAIL, "moyemail@mail.ru");
        RESUME_2.addContact(ContactInfoSection.PHONE, "111324134134111");
        RESUME_2.addSection(SectionType.OBJECTIVE, new TextContentSection("Programming"));
        RESUME_2.addSection(SectionType.PERSONAL, new TextContentSection("AboutMe"));
        RESUME_2.addSection(SectionType.ACHIEVEMENT, new ListStringSection("Achivment1", "Achivment2", "Achivment3", "Achivment4"));
        RESUME_2.addSection(SectionType.QUALIFICATIONS, new ListStringSection("JavaScript", "HTML5", "CSS", "SQL"));
        RESUME_2.addSection(SectionType.EXPERIENCE, new ExperienceEducationSection("http://www.mail.ru",
                "Organization11", date1, date2, "InnoForce", "DescriptionInfo"));
        RESUME_2.addSection(SectionType.EDUCATION, new ExperienceEducationSection("http://www.yandex.ru", "Politech",
                        date3, date4, "aspirant", "StudentInfo"));

        RESUME_3.addContact(ContactInfoSection.EMAIL, "moyemail@mail.ru");
        RESUME_3.addContact(ContactInfoSection.PHONE, "111324134134111");
        RESUME_3.addSection(SectionType.OBJECTIVE, new TextContentSection("Programming"));
        RESUME_3.addSection(SectionType.PERSONAL, new TextContentSection("AboutMe"));
        RESUME_3.addSection(SectionType.ACHIEVEMENT, new ListStringSection("Achivment1", "Achivment2", "Achivment3", "Achivment4"));
        RESUME_3.addSection(SectionType.QUALIFICATIONS, new ListStringSection("JavaScript", "HTML5", "CSS", "SQL"));
        RESUME_3.addSection(SectionType.EXPERIENCE, new ExperienceEducationSection("http://www.mail.ru",
                "Organization11", date1, date2, "InnoForce", "DescriptionInfo"));
        RESUME_3.addSection(SectionType.EDUCATION, new ExperienceEducationSection("http://www.yandex.ru", "Politech",
                date3, date4, "aspirant", "StudentInfo"));
    }

    public static void main(String[] args) {
        Storage storage = new ListStorage();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }
}
