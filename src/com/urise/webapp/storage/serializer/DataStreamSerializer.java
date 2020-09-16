package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import com.urise.webapp.model.Organization.Position;

import java.io.*;
import java.time.YearMonth;
import java.util.*;
import static java.lang.String.*;


public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream streamWriter = new DataOutputStream(outputStream)) {
            streamWriter.writeUTF(resume.getUuid());
            streamWriter.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            writeDate(streamWriter, contacts.entrySet(), entry -> {
                streamWriter.writeUTF(entry.getKey().name());
                streamWriter.writeUTF(entry.getValue());
            });

            Map<SectionType, AbstractSection> sections = resume.getSections();
            writeDate(streamWriter, sections.entrySet(), entry -> {
                SectionType nameSection = SectionType.valueOf(entry.getKey().name());
                streamWriter.writeUTF(valueOf(nameSection));
                switch (nameSection) {
                    case OBJECTIVE:
                    case PERSONAL:
                        streamWriter.writeUTF(valueOf(entry.getValue()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListStringSection stringSection = (ListStringSection) entry.getValue();
                        writeDate(streamWriter, stringSection.getLists(), section -> {
                            streamWriter.writeUTF(valueOf(section.intern()));
                        });
                        break;

                    default:
                        OrganizationSection organizationSectionList = ((OrganizationSection) entry.getValue());
                        List<Organization> organizationList = organizationSectionList.getOrganizationList();
                        if (organizationList.get(0).getWebSite().nameOrganisation == null) {
                            streamWriter.writeUTF("null");
                        } else {
                            streamWriter.writeUTF(valueOf(organizationList.get(0).getWebSite().nameOrganisation));
                        }
                        streamWriter.writeUTF(valueOf(organizationList.get(0).getWebSite().url));
                        streamWriter.writeInt(organizationList.get(0).getPositions().size());

//                        writeDate(streamWriter, organizationList.get(0).getPositions(), positions -> {
//                            streamWriter.writeUTF(valueOf(positions.getFrom()));
//                            streamWriter.writeUTF(valueOf(positions.getTo()));
//                            streamWriter.writeUTF(valueOf(positions.getTitle()));
//                        });

                        for (Position positions : organizationList.get(0).getPositions()) {
                            streamWriter.writeUTF(valueOf(positions.getFrom()));
                            streamWriter.writeUTF(valueOf(positions.getTo()));
                            streamWriter.writeUTF(valueOf(positions.getTitle()));
                            if (positions.getDescription() == null) {
                                streamWriter.writeUTF("null");
                            } else {
                                streamWriter.writeUTF(valueOf(positions.getDescription()));
                            }
                        }
                }
            });
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
                        List<Position> positions = new ArrayList<>();
                        String nameOrganisation = streamReader.readUTF();
                        String url = streamReader.readUTF();
                        if (url.equals("null")) {
                            url = null;
                        }
                        int listFromTo = streamReader.readInt();
                        WebSite webSite = new WebSite(url, nameOrganisation);
                        for (int j = 0; j < listFromTo; j++) {
                            YearMonth from = YearMonth.parse(streamReader.readUTF());
                            YearMonth to = YearMonth.parse(streamReader.readUTF());
                            String title = streamReader.readUTF();
                            String description = streamReader.readUTF();
                            if (description.equals("null")) {
                                description = null;
                            }
                            positions.add(new Position(from, to, title, description));
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

    private interface WriteWithException<T> {
        void write(T elem) throws IOException;
    }

    private <T> void writeDate(DataOutputStream dos, Collection<T> collection, WriteWithException<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T elem : collection) {
            writer.write(elem);
        }
    }
}