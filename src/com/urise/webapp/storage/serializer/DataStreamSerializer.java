package com.urise.webapp.storage.serializer;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
                dos.writeUTF(entry.getKey().name());
                switch (entry.getValue().getClass().getSimpleName()) {
                    case "TextSection":
                        dos.writeUTF(entry.getValue().getClass().getName());
                        dos.writeUTF(entry.getValue().toString());
                        break;
                    case "ListSection":
                        dos.writeUTF(entry.getValue().getClass().getName());
                        int sizeList = (((ListSection) entry.getValue()).getItems().size());
                        dos.writeInt(sizeList);
                        for (int i = 0; i < sizeList; i++) {
                            dos.writeUTF(((ListSection) entry.getValue()).getItems().get(i));
                        }
                        break;
                    case "OrganizationSection":
                        dos.writeUTF(entry.getValue().getClass().getName());
                        int sizeOrganizationSection = (((OrganizationSection) entry.getValue()).getOrganizations().size());
                        dos.writeInt(sizeOrganizationSection);
                        for (int numSection = 0; numSection < sizeOrganizationSection; numSection++) {
                            dos.writeUTF(((OrganizationSection) entry.getValue()).getOrganizations().get(numSection).getHomePage().getName());
                            dos.writeUTF(((OrganizationSection) entry.getValue()).getOrganizations().get(numSection).getHomePage().getUrl());
                            int sizePosition = ((OrganizationSection) entry.getValue()).getOrganizations().get(numSection).getPositions().size();
                            dos.writeInt(sizePosition);
                            for (int numPosition = 0; numPosition < sizePosition; numPosition++) {
                                dos.writeUTF(((OrganizationSection) entry.getValue()).getOrganizations().get(numSection).getPositions().get(numPosition).getStartDate().toString());
                                dos.writeUTF(((OrganizationSection) entry.getValue()).getOrganizations().get(numSection).getPositions().get(numPosition).getEndDate().toString());
                                dos.writeUTF(((OrganizationSection) entry.getValue()).getOrganizations().get(numSection).getPositions().get(numPosition).getTitle());
                                dos.writeUTF(checkNull(((OrganizationSection) entry.getValue()).getOrganizations().get(numSection).getPositions().get(numPosition).getDescription()));
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
                try {
                    switch (Objects.requireNonNull(Class.forName(dis.readUTF())).newInstance().getClass().getSimpleName()) {
                        case "TextSection":
                            res.addSections(SectionType.valueOf(section), new TextSection(dis.readUTF()));
                            break;
                        case "ListSection":
                            List<String> listSection = new ArrayList<>();
                            int sizeListSection = dis.readInt();
                            for (int numSection = 0; numSection < sizeListSection; numSection++) {
                                listSection.add(dis.readUTF());
                            }
                            res.addSections(SectionType.valueOf(section), new ListSection(listSection));
                            break;
                        case "OrganizationSection":
                            List<Organization> listOrganizations = new ArrayList<>();
                            int sizeOrganizationSection = dis.readInt();
                            for (int numOrganization = 0; numOrganization < sizeOrganizationSection; numOrganization++) {
                                String name = dis.readUTF();
                                String url = dis.readUTF();
                                List<Organization.Position> listPositions = new ArrayList<>();
                                int sizePosition = dis.readInt();
                                for (int numPosition = 0; numPosition < sizePosition; numPosition++) {
                                    listPositions.add(new Organization.Position(LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()), dis.readUTF(), getNull(dis.readUTF())));
                                }
                                listOrganizations.add(new Organization(new Link(name, url), listPositions));
                            }
                            res.addSections(SectionType.valueOf(section), new OrganizationSection(listOrganizations));
                            break;
                    }
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    throw new StorageException("Error in DataStreamSerializer", e);
                }
            }
            return res;
        }
    }

    private String checkNull(String value) {
        return value == null ? "NULL" : value;
    }

    private String getNull(String value) {
        return value.equals("NULL") ? null : value;
    }

}
