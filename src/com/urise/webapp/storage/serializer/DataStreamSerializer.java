package com.urise.webapp.storage.serializer;

import com.urise.webapp.exception.StorageException;
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
            writeString(dos, resume.getUuid());
            writeString(dos, resume.getFullName());
            Map<ContactTypes, String> contacts = resume.getContacts();
            writeInt(dos, contacts.size());
            contacts.forEach((key, value) -> {
                writeString(dos, key.name());
                writeString(dos, value);
            });

            Map<SectionType, AbstractSection> sections = resume.getSections();
            writeInt(dos, sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                writeString(dos, entry.getKey().toString());
                switch (entry.getKey()) {
                    case OBJECTIVE:
                    case PERSONAL:
                        writeString(dos, entry.getValue().toString());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> items = ((ListSection) entry.getValue()).getItems();
                        writeWithExeption(dos, wl, items);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizations = ((OrganizationSection) entry.getValue()).getOrganizations();
                        writeInt(dos, organizations.size());
                        organizations.forEach(organization -> {
                            writeString(dos, organization.getHomePage().getName());
                            writeString(dos, writeMaybeNull(organization.getHomePage().getUrl()));
                            int sizePosition = (organization.getPositions().size());
                            writeInt(dos, sizePosition);
                            organization.getPositions().forEach(position -> {
                                writeString(dos, position.getStartDate().toString());
                                writeString(dos, position.getEndDate().toString());
                                writeString(dos, position.getTitle());
                                writeString(dos, writeMaybeNull(position.getDescription()));
                            });
                        });
                        break;
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume res = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                res.addContacts(ContactTypes.valueOf(dis.readUTF()), dis.readUTF());
            }
            int sizeSection = dis.readInt();
            for (int i = 0; i < sizeSection; i++) {
                String section = dis.readUTF();
                switch (section) {
                    case "OBJECTIVE":
                    case "PERSONAL":
                        res.addSections(SectionType.valueOf(section), new TextSection(dis.readUTF()));
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        List<String> listSection = new ArrayList<>();
                        int sizeListSection = dis.readInt();
                        for (int numSection = 0; numSection < sizeListSection; numSection++) {
                            listSection.add(dis.readUTF());
                        }
                        res.addSections(SectionType.valueOf(section), new ListSection(listSection));
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
                        res.addSections(SectionType.valueOf(section), new OrganizationSection(listOrganizations));
                        break;
                }
            }
            return res;
        }
    }

    private String writeMaybeNull(String value) {
        return value == null ? "" : value;
    }

    private String readMaybeNull(String value) {
        return value.equals("") ? null : value;
    }

    private void writeInt(DataOutputStream dos, int value) {
        try {
            dos.writeInt(value);
        } catch (IOException e) {
            throw new StorageException("Error in doWrite DataStreamSerializer", e);
        }
    }

    private void writeString(DataOutputStream dos, String value) {
        try {
            dos.writeUTF(value);
        } catch (IOException e) {
            throw new StorageException("Error in doWrite DataStreamSerializer", e);
        }
    }


    private <T> void writeWithExeption(DataOutputStream dos, wList consumer, List<T> list) throws IOException {
        consumer.write(dos, list);
    }

    @FunctionalInterface
    interface wList<T> {
        void write(DataOutputStream dos, List<T> list) throws IOException;
    }

    private wList wl = (dos, list) -> {
        dos.writeInt(list.size());
        for (Object o : list) {
            dos.writeUTF((String) o);
        }
    };

}
