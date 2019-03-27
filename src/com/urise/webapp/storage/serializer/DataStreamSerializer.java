package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactTypes, String> contacts = resume.getContacts();
            WriteMap<ContactTypes, String> contactWriteMap = (contactTypes, content) -> {
                dos.writeUTF(contactTypes.name());
                dos.writeUTF(content);
            };
            writeMapWithExeption(contacts, dos, contactWriteMap);
            Map<SectionType, AbstractSection> sections = resume.getSections();
            WriteMap<SectionType, AbstractSection> sectionWriteMap = (sectionType, section) -> {
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(section.toString());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        WriteList<String> writeList = dos::writeUTF;
                        writeListWithExeption(((ListSection) section).getItems(), dos, writeList);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        WriteList<Organization.Position> writePosition = position -> {
                            dos.writeUTF(position.getStartDate().toString());
                            dos.writeUTF(position.getEndDate().toString());
                            dos.writeUTF(position.getTitle());
                            dos.writeUTF(writeMaybeNull(position.getDescription()));
                        };
                        WriteList<Organization> writeOrganization = organization -> {
                            dos.writeUTF(organization.getHomePage().getName());
                            dos.writeUTF(writeMaybeNull(organization.getHomePage().getUrl()));
                            writeListWithExeption(organization.getPositions(), dos, writePosition);
                        };
                        writeListWithExeption(((OrganizationSection) section).getOrganizations(), dos, writeOrganization);
                        break;
                }

            };
            writeMapWithExeption(sections, dos, sectionWriteMap);
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

    private <T> void writeListWithExeption(List<T> list, DataOutputStream dos, WriteList <T> consumer) throws IOException {
        dos.writeInt(list.size());
        for (T t : list) {
            consumer.write(t);
        }
    }

    private <K, V> void writeMapWithExeption(Map<K, V> map, DataOutputStream dos, WriteMap <K, V> consumer) throws IOException {
        dos.writeInt(map.size());
        for (Map.Entry<K, V> entry : map.entrySet()) {
            consumer.write(entry.getKey(), entry.getValue());
        }
    }

    @FunctionalInterface
    interface WriteMap<K, V> {
        void write(K key, V value) throws IOException;
    }

    @FunctionalInterface
    interface WriteList<T> {
        void write(T value) throws IOException;
    }

}
