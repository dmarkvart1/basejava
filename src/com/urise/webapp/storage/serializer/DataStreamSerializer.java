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
            Map<SectionType, AbstractSection> sectionType = resume.getSections();
            streamWriter.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                streamWriter.writeUTF(entry.getKey().name());
                streamWriter.writeUTF(entry.getValue());
            }
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
            String[] myArray = {"PERSONAL", "OBJECTIVE", "ACHIEVEMENT", "QUALIFICATIONS", "EXPERIENCE", "EDUCATION"};
            int size2 = streamReader.readInt();
            for (int i = 0; i < size2; i++) {
//                SectionRequest sectionRequest = new SectionRequest() {
//                    public SectionType sectionRequest(SectionType type) {
//                        if(type.equals("PERSONAL")) {
//                        }
//                        return null;
//                    }
//                };
                SectionType sectionType = SectionType.valueOf(streamReader.readUTF());
                if (sectionType.name().equals(myArray[0]) || (sectionType.name().equals(myArray[1]))) {
                    TextContentSection contentSection = new TextContentSection(streamReader.readUTF());
                    resume.addSection(sectionType, contentSection);
                    continue;
                }
                if (sectionType.name().equals(myArray[2]) || (sectionType.name().equals(myArray[3]))) {
                    ListStringSection contentSection = new ListStringSection(streamReader.readUTF());
                    resume.addSection(sectionType, contentSection);
                    continue;
                } if (sectionType.name().equals(myArray[4]) || (sectionType.name().equals(myArray[5]))){
                    OrganizationSection contentSection = new OrganizationSection(streamReader.readUTF());
                    resume.addSection(sectionType, contentSection);
                }
            }
            return  resume;
        }

    }
}

//interface SectionRequest {
//     default SectionType sectionRequest() {
//         return null;
//     }
//}

