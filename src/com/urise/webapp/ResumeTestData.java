package com.urise.webapp;

import com.urise.webapp.model.*;
import com.urise.webapp.model.Resume;
import java.time.Month;
import java.time.YearMonth;


public class ResumeTestData {



    public static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
//        resume.addContact(ContactType.EMAIL, "moyemail@mail.ru");
//        resume.addContact(ContactType.PHONE, "111324134134111");
//        resume.addSection(SectionType.OBJECTIVE, new TextContentSection("Programmer"));
//        resume.addSection(SectionType.PERSONAL, new TextContentSection("AboutMe1"));
//        resume.addSection(SectionType.ACHIEVEMENT, new ListStringSection("Достижение1", "Achivment2", "Achivment3", "Achivment4"));
//        resume.addSection(SectionType.QUALIFICATIONS, new ListStringSection("JavaScript", "HTML5", "CSS", "SQL"));
//        resume.addSection(SectionType.EXPERIENCE,
//                new OrganizationSection(
//                        new Organization("http://www.nat.ru", "NewAgeTechnologies",
//                                new Organization.Position(date1, date2, "position1", "content1"),
//                                new Organization.Position(date3, date6, "position2", "content2"))));
//
//        resume.addSection(SectionType.EDUCATION,
//                new OrganizationSection(
//                        new Organization("http://www.yandex.ru", "Yandex",
//                                new Organization.Position(date1, date5, "Student", "content1"),
//                                new Organization.Position(date3, date4, "Aspirant", "content2"))));
        return resume;
    }
}
