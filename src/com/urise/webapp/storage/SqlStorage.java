package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        LOG.info("Clear");
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, resume.getFullName());
                        ps.execute();
                    }
                    insertContact(resume, conn);
                    insertSection(resume, conn);
                    return null;
                }
        );
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name =? WHERE uuid =?")) {
                        ps.setString(1, resume.getFullName());
                        ps.setString(2, resume.getUuid());
                        if (ps.executeUpdate() == 0) {
                            throw new NotExistStorageException("Resume " + resume.getUuid() + " not exist");
                        }
                    }
                    deleteContacts(resume, conn);
                    deleteSections(resume, conn);
                    insertContact(resume, conn);
                    insertSection(resume, conn);
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.execute("" +
                        "SELECT * FROM resume r " +
                        "  LEFT JOIN contact c " +
                        "    ON r.uuid = c.resume_uuid " +
                        "  LEFT JOIN section s " +
                        "   ON r.uuid = s.resume_uuid" +
                        " WHERE r.uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        addContact(r, rs);
                        addSection(r, rs);
                    } while (rs.next());
                    return r;
                });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.execute("DELETE FROM resume WHERE resume.uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException("Resume  not exist");
                    }
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted");
        Map<String, Resume> mapResume = new LinkedHashMap<>();
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement psResume = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid");
                         PreparedStatement psContact = conn.prepareStatement("SELECT * FROM contact");
                         PreparedStatement psSection = conn.prepareStatement("SELECT * FROM section")
                    ) {
                        ResultSet rsResume = psResume.executeQuery();
                        ResultSet rsContact = psContact.executeQuery();
                        ResultSet rsSection = psSection.executeQuery();
                        while (rsResume.next()) {
                            String uuid = rsResume.getString("uuid");
                            mapResume.put(uuid, new Resume(uuid, rsResume.getString("full_name")));
                        }
                        while (rsContact.next()) {
                            addContact(mapResume.get(rsContact.getString("resume_uuid")), rsContact);
                        }
                        while (rsSection.next()) {
                            addSection(mapResume.get(rsSection.getString("resume_uuid")), rsSection);
                        }
                    }
                    return null;
                }
        );
        return new ArrayList<>(mapResume.values());
    }

    @Override
    public int size() {
        LOG.info("GetSize");
        return sqlHelper.execute("SELECT COUNT (*) FROM resume",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    return rs.next() ? rs.getInt(1) : 0;
                });
    }

    private void insertContact(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactTypes, String> contact : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, contact.getKey().name());
                ps.setString(3, contact.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContact(Resume resume, ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            resume.addContacts(ContactTypes.valueOf(rs.getString("type")), value);
        }
    }

    private void deleteContacts(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid =?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

    private void insertSection(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type_section, value_section) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> section : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, section.getKey().name());
                switch (section.getKey()) {
                    case OBJECTIVE:
                    case PERSONAL:
                        ps.setObject(3, section.getValue().toString());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ps.setObject(3, String.join("\n", ((ListSection) section.getValue()).getItems()));
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addSection(Resume resume, ResultSet rs) throws SQLException {
        String value = rs.getString("value_section");
        if (value != null) {
            SectionType type = SectionType.valueOf(rs.getString("type_section"));
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    resume.addSections(type, new TextSection(value));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    List<String> listSections = new ArrayList<>();
                    Collections.addAll(listSections, value.split("\\n"));
                    resume.addSections(type, new ListSection(listSections));
            }
        }
    }

    private void deleteSections(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section WHERE resume_uuid =?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

}
