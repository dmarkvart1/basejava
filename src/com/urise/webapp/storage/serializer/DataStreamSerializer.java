package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream streamWriter = new DataOutputStream(outputStream)) {
            streamWriter.writeUTF(resume.getUuid());
            streamWriter.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            streamWriter.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                streamWriter.writeUTF(entry.getKey().name());
                streamWriter.writeUTF(entry.getValue());
            }

            Map<SectionType, AbstractSection> sectionType = resume.getSections();
            streamWriter.writeInt(sectionType.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sectionType.entrySet()) {
                int question = request(entry.getKey().name());

                streamWriter.writeUTF(entry.getKey().name());
                if (question == 1) {
                    streamWriter.writeUTF(String.valueOf(entry.getValue()));
                    continue;
                }
                if (question == 2) {
                    streamWriter.writeUTF(entry.getKey().name());
                    ListStringSection stringSection = (ListStringSection) entry.getValue();
                    streamWriter.writeInt(stringSection.getLists().size());

                    for (String section : stringSection.getLists()) {
                        streamWriter.writeUTF(String.valueOf(section));
                    }
                    continue;
                } else {
                    OrganizationSection organizationList = ((OrganizationSection) entry.getValue());
                    List<Organization> list = organizationList.getOrganizationList();
                    streamWriter.writeUTF(String.valueOf(list.get(0).getWebSite().nameOrganisation));
                    streamWriter.writeUTF(String.valueOf(list.get(0).getWebSite().url));

                    streamWriter.writeInt(list.get(0).getPositions().size());
                    for (Organization.Position positions : list.get(0).getPositions()) {
                        streamWriter.writeUTF(String.valueOf(positions.getFrom()));
                        streamWriter.writeUTF(String.valueOf(positions.getTo()));
                        streamWriter.writeUTF(String.valueOf(positions.getTitle()));
                        streamWriter.writeUTF(String.valueOf(positions.getDescription()));
                    }
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream streamReader = new DataInputStream(inputStream)) {
            String uuid = streamReader.readUTF();
            String fullName = streamReader.readUTF();


            Resume resume = new Resume(uuid, fullName);
            int size = streamReader.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(streamReader.readUTF()),
                        streamReader.readUTF());
            }
            int size2 = streamReader.readInt();
            for (int i = 0; i < size2; i++) {

                SectionType sectionType = SectionType.valueOf(streamReader.readUTF());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        TextContentSection contentSection1 = new TextContentSection(streamReader.readUTF());
                        resume.addSection(sectionType, contentSection1);
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListStringSection contentSection2 = new ListStringSection(streamReader.readUTF());
                        int size3 = streamReader.readInt();
                        List<String> list = new ArrayList<>();
                        for (int q = 0; q < size3; q++) {
                            list.add(streamReader.readUTF());
                        }
                        contentSection2.setLists(list);
                        resume.addSection(sectionType, contentSection2);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        OrganizationSection organizationSection = new OrganizationSection();

                        List<Organization> list2 = new ArrayList<>();
                        List<Organization.Position> positions = new ArrayList<>();
                        String nameOrganisation = streamReader.readUTF();
                        String url = streamReader.readUTF();
                        int size4 = streamReader.readInt();
                        WebSite webSite = new WebSite(url, nameOrganisation);
                        for (int j = 0; j < size4; j++) {
                            YearMonth from = YearMonth.parse(streamReader.readUTF());
                            YearMonth to = YearMonth.parse(streamReader.readUTF());
                            String title = streamReader.readUTF();
                            String description = streamReader.readUTF();
                            positions.add(new Organization.Position(from, to, title, description));
                        }
                        Organization organization = new Organization(webSite, positions);
                        list2.add(organization);
                        organizationSection.setOrganizationList(list2);
                        resume.addSection(sectionType, organizationSection);
                        break;
                }
            }
            return resume;
        }
    }

    public static int request(String type) {
        int typeRequest = 0;
        switch (type) {
            case "OBJECTIVE":
            case "PERSONAL":
                typeRequest = 1;
                break;
            case "ACHIEVEMENT":
            case "QUALIFICATIONS":
                typeRequest = 2;
                break;
            default:
                typeRequest = 3;
                break;
        }
        return typeRequest;
    }
}