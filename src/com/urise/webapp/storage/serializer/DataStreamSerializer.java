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
            writeWithException(streamWriter, contacts.entrySet(), entry -> {
                streamWriter.writeUTF(entry.getKey().name());
                streamWriter.writeUTF(entry.getValue());
            });

            Map<SectionType, AbstractSection> sections = resume.getSections();
            writeWithException(streamWriter, sections.entrySet(), entry -> {
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
                        writeWithException(streamWriter, stringSection.getLists(), section ->
                                streamWriter.writeUTF((section.intern())));
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

                        writeWithException(streamWriter, organizationList.get(0).getPositions(), positions -> {
                            streamWriter.writeUTF(valueOf(positions.getFrom()));
                            streamWriter.writeUTF(valueOf(positions.getTo()));
                            streamWriter.writeUTF(valueOf(positions.getTitle()));
                            if (positions.getDescription() == null) {
                                streamWriter.writeUTF("null");
                            } else {
                                streamWriter.writeUTF(valueOf(positions.getDescription()));
                            }
                });
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
            readElement(streamReader, () -> resume.addContact(ContactType.valueOf(streamReader.readUTF()),
                    streamReader.readUTF()));

            readElement(streamReader, () -> {
                SectionType sections = SectionType.valueOf(streamReader.readUTF());
                switch (sections) {
                    case OBJECTIVE:
                    case PERSONAL:
                        TextContentSection textSection = new TextContentSection(streamReader.readUTF());
                        resume.addSection(sections, textSection);
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> list = new ArrayList<>();
                        readElement(streamReader, () -> list.add(streamReader.readUTF()));
                        resume.addSection(sections, new ListStringSection(list));
                        break;
                    default:
                        OrganizationSection organizationSection = new OrganizationSection();

                        List<Organization> organizations = new ArrayList<>();
                        List<Position> positions = new ArrayList<>();
                        String nameOrganisation = streamReader.readUTF();
                        String url = streamReader.readUTF();
                        url = url.equals("null") ? null : url;
                        WebSite webSite = new WebSite(url, nameOrganisation);
                        readElement(streamReader, () -> {
                            YearMonth from = YearMonth.parse(streamReader.readUTF());
                            YearMonth to = YearMonth.parse(streamReader.readUTF());
                            String title = streamReader.readUTF();
                            String description = streamReader.readUTF();
                            description = !description.equals("null") ? description : null;
                            positions.add(new Position(from, to, title, description));
                        });
                        Organization organization = new Organization(webSite, positions);
                        organizations.add(organization);
                        organizationSection.setOrganizationList(organizations);
                        resume.addSection(sections, organizationSection);
                        break;
                }
            });
            return resume;
        }
    }

    private <T> void writeWithException(DataOutputStream dos, Collection<T> collection, Writer<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T elem : collection) {
            writer.write(elem);
        }
    }

    private interface Writer<T> {
        void write(T elem) throws IOException;
    }

    private interface Read {
        void reading() throws IOException;
    }

    private void readElement(DataInputStream streamReader, Read element) throws IOException {
        int size = streamReader.readInt();
        for (int i = 0; i < size; i++) {
            element.reading();
        }
    }
}