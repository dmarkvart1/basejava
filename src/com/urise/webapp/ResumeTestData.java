package com.urise.webapp;

import com.urise.webapp.model.*;
import com.urise.webapp.model.Resume;

import java.time.Month;
import java.time.YearMonth;


public class ResumeTestData {

    private static final YearMonth date1 = YearMonth.of(2010, Month.FEBRUARY);
    private static final YearMonth date2 = YearMonth.of(2011, Month.MARCH);
    private static final YearMonth date3 = YearMonth.of(2012, Month.AUGUST);
    private static final YearMonth date4 = YearMonth.of(2013, Month.APRIL);
    private static final YearMonth date5 = YearMonth.of(2014, Month.JULY);
    private static final YearMonth date6 = YearMonth.of(2015, Month.SEPTEMBER);

    public static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.addContact(ContactType.EMAIL, "moyemail@mail.ru");
        resume.addContact(ContactType.PHONE, "111324134134111");
        resume.addSection(SectionType.OBJECTIVE, new TextContentSection("Programmer"));
        resume.addSection(SectionType.PERSONAL, new TextContentSection("AboutMe1"));
        resume.addSection(SectionType.ACHIEVEMENT, new ListStringSection("Достижение1", "Achivment2", "Achivment3", "Achivment4"));
        resume.addSection(SectionType.QUALIFICATIONS, new ListStringSection("JavaScript", "HTML5", "CSS", "SQL"));

        resume.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("http://www.nat.ru", "NewAgeTechnologies",
                                new Organization.Position(date1, date2, "position1", "content1"),
                                new Organization.Position(date3, date6, "position2", "content2"))));

        resume.addSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("http://www.yandex.ru", "Yandex",
                                new Organization.Position(date1, date5, "Student", "content1"),
                                new Organization.Position(date3, date4, "Aspirant", "content2"))));
        return resume;
    }
}