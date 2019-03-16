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
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactTypes, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                switch (entry.getKey()) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(entry.getKey().toString());
                        dos.writeUTF(entry.getValue().toString());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        dos.writeUTF(entry.getKey().toString());
                        int sizeList = (((ListSection) entry.getValue()).getItems().size());
                        dos.writeInt(sizeList);
                        for (int i = 0; i < sizeList; i++) {
                            dos.writeUTF(((ListSection) entry.getValue()).getItems().get(i));
                        }
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        dos.writeUTF(entry.getKey().toString());
                        int sizeOrganizationSection = (((OrganizationSection) entry.getValue()).getOrganizations().size());
                        dos.writeInt(sizeOrganizationSection);
                        for (int numSection = 0; numSection < sizeOrganizationSection; numSection++) {
                            Organization organization = ((OrganizationSection) entry.getValue()).getOrganizations().get(numSection);
                            dos.writeUTF(organization.getHomePage().getName());
                            dos.writeUTF(writeMaybeNull(organization.getHomePage().getUrl()));
                            int sizePosition = ((OrganizationSection) entry.getValue()).getOrganizations().get(numSection).getPositions().size();
                            dos.writeInt(sizePosition);
                            for (int numPosition = 0; numPosition < sizePosition; numPosition++) {
                                Organization.Position position = organization.getPositions().get(numPosition);
                                dos.writeUTF(position.getStartDate().toString());
                                dos.writeUTF(position.getEndDate().toString());
                                dos.writeUTF(position.getTitle());
                                dos.writeUTF(writeMaybeNull(position.getDescription()));
                            }
                        }
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

}
