package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
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
                streamWriter.writeUTF(entry.getKey().name());
                streamWriter.writeUTF(String.valueOf(entry.getValue()));
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
                        resume.addSection(sectionType, contentSection2);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        OrganizationSection contentSection3 = new OrganizationSection(streamReader.readUTF());
                        resume.addSection(sectionType, contentSection3);
                        break;
                }
            }
            return resume;
        }
    }
}

//interface SectionRequest {
//     default SectionType sectionRequest() {
//         return null;
//     }
//}