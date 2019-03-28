package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactTypes, String> contacts = resume.getContacts();
            Set<Map.Entry<ContactTypes, String>> entryContacts = contacts.entrySet();
            Write<Map.Entry<ContactTypes, String>> writeContacts = contact -> {
                dos.writeUTF(contact.getKey().name());
                dos.writeUTF(contact.getValue());
            };
            writeWithExeption(entryContacts, dos, writeContacts);

            Map<SectionType, AbstractSection> sections = resume.getSections();
            Set<Map.Entry<SectionType, AbstractSection>> entrySections = sections.entrySet();
            Write<Map.Entry<SectionType, AbstractSection>> writeSections = section -> {
                dos.writeUTF(section.getKey().name());
                switch (section.getKey()) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(section.getValue().toString());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        Write<String> writeList = dos::writeUTF;
                        writeWithExeption(((ListSection) section.getValue()).getItems(), dos, writeList);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        Write<Organization.Position> writePositions = position -> {
                            dos.writeUTF(position.getStartDate().toString());
                            dos.writeUTF(position.getEndDate().toString());
                            dos.writeUTF(position.getTitle());
                            dos.writeUTF(writeMaybeNull(position.getDescription()));
                        };
                        Write<Organization> writeOrganizations = organization -> {
                            dos.writeUTF(organization.getHomePage().getName());
                            dos.writeUTF(writeMaybeNull(organization.getHomePage().getUrl()));
                            writeWithExeption(organization.getPositions(), dos, writePositions);
                        };
                        writeWithExeption(((OrganizationSection) section.getValue()).getOrganizations(), dos, writeOrganizations);
                        break;
                }
            };
            writeWithExeption(entrySections, dos, writeSections);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContacts(ContactTypes.valueOf(dis.readUTF()), dis.readUTF());
            }
            int sizeSection = dis.readInt();
            for (int i = 0; i < sizeSection; i++) {
                String section = dis.readUTF();
                switch (section) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                        resume.addSections(SectionType.valueOf(section), new TextSection(dis.readUTF()));
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        List<String> listSection = new ArrayList<>();
                        int sizeListSection = dis.readInt();
                        for (int numSection = 0; numSection < sizeListSection; numSection++) {
                            listSection.add(dis.readUTF());
                        }
                        resume.addSections(SectionType.valueOf(section), new ListSection(listSection));
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        List<Organization> listOrganizations = new ArrayList<>();
                        int sizeOrganizationSection = dis.readInt();
                        for (int numOrganization = 0; numOrganization < sizeOrganizationSection; numOrganization++) {
                            String name = dis.readUTF();
                            String url = readMaybeNull(dis.readUTF());
                            List<Organization.Position> listPositions = new ArrayList<>();
                            int sizePosition = dis.readInt();
                            for (int numPosition = 0; numPosition < sizePosition; numPosition++) {
                                listPositions.add(new Organization.Position(LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()), dis.readUTF(), readMaybeNull(dis.readUTF())));
                            }
                            listOrganizations.add(new Organization(new Link(name, url), listPositions));
                        }
                        resume.addSections(SectionType.valueOf(section), new OrganizationSection(listOrganizations));
                        break;
                }
            }
            return resume;
        }
    }

    private String writeMaybeNull(String value) {
        return value == null ? "" : value;
    }

    private String readMaybeNull(String value) {
        return value.equals("") ? null : value;
    }

    private <T> void writeWithExeption(Collection<T> collection, DataOutputStream dos, Write<T> consumer) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            consumer.write(t);
        }
    }

    @FunctionalInterface
    interface Write<T> {
        void write(T value) throws IOException;
    }

}
