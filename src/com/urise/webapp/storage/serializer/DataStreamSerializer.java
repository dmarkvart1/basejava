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

            Map<SectionType, AbstractSection> sections = resume.getSections();
            streamWriter.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                SectionType nameSection = SectionType.valueOf(entry.getKey().name());
                streamWriter.writeUTF(String.valueOf(nameSection));
                switch (nameSection) {
                    case OBJECTIVE:
                    case PERSONAL:
                        streamWriter.writeUTF(String.valueOf(entry.getValue()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListStringSection stringSection = (ListStringSection) entry.getValue();
                        streamWriter.writeInt(stringSection.getLists().size());

                        for (String section : stringSection.getLists()) {
                            streamWriter.writeUTF(String.valueOf(section));
                        }
                        break;

                    default:
                        OrganizationSection organizationSectionList = ((OrganizationSection) entry.getValue());
                        List<Organization> organizationList = organizationSectionList.getOrganizationList();
                        if (organizationList.get(0).getWebSite().nameOrganisation == null) {
                            streamWriter.writeUTF("null");
                        } else {
                            streamWriter.writeUTF(String.valueOf(organizationList.get(0).getWebSite().nameOrganisation));
                        }
                        streamWriter.writeUTF(String.valueOf(organizationList.get(0).getWebSite().url));

                        streamWriter.writeInt(organizationList.get(0).getPositions().size());
                        for (Organization.Position positions : organizationList.get(0).getPositions()) {
                            streamWriter.writeUTF(String.valueOf(positions.getFrom()));
                            streamWriter.writeUTF(String.valueOf(positions.getTo()));
                            streamWriter.writeUTF(String.valueOf(positions.getTitle()));

                            if (positions.getDescription() == null) {
                                streamWriter.writeUTF("null");
                            } else {
                                streamWriter.writeUTF(String.valueOf(positions.getDescription()));
                            }

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
            int contactsSize = streamReader.readInt();
            for (int i = 0; i < contactsSize; i++) {
                resume.addContact(ContactType.valueOf(streamReader.readUTF()),
                        streamReader.readUTF());
            }
            int sectionsSize = streamReader.readInt();
            for (int i = 0; i < sectionsSize; i++) {

                SectionType sections = SectionType.valueOf(streamReader.readUTF());
                switch (sections) {
                    case OBJECTIVE:
                    case PERSONAL:
                        TextContentSection textSection = new TextContentSection(streamReader.readUTF());
                        resume.addSection(sections, textSection);
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        int listSize = streamReader.readInt();
                        List<String> list = new ArrayList<>();
                        for (int q = 0; q < listSize; q++) {
                            list.add(streamReader.readUTF());
                        }
                        resume.addSection(sections, new ListStringSection(list));
                        break;
                    default:
                        OrganizationSection organizationSection = new OrganizationSection();

                        List<Organization> organizations = new ArrayList<>();
                        List<Organization.Position> positions = new ArrayList<>();
                        String nameOrganisation = streamReader.readUTF();
                        String url = streamReader.readUTF();
                        if(url.equals("null")){
                            url = null;
                        }
                        int listFromTo = streamReader.readInt();
                        WebSite webSite = new WebSite(url, nameOrganisation);
                        for (int j = 0; j < listFromTo; j++) {
                            YearMonth from = YearMonth.parse(streamReader.readUTF());
                            YearMonth to = YearMonth.parse(streamReader.readUTF());
                            String title = streamReader.readUTF();
                            String description = streamReader.readUTF();
                            if (description.equals("null")){
                                description = null;
                            }
                            positions.add(new Organization.Position(from, to, title, description));
                        }
                        Organization organization = new Organization(webSite, positions);
                        organizations.add(organization);
                        organizationSection.setOrganizationList(organizations);
                        resume.addSection(sections, organizationSection);
                        break;
                }
            }
            return resume;
        }
    }
}