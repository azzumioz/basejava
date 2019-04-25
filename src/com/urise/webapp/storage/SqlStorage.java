package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactTypes;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
                    setContact(resume, conn);
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
                    try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid =?")) {
                        ps.setString(1, resume.getUuid());
                        ps.executeUpdate();
                    }
                    setContact(resume, conn);
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
                        " WHERE r.uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        String typeString = rs.getString("type");
                        if (typeString != null) {
                            String value = rs.getString("value");
                            ContactTypes type = ContactTypes.valueOf(typeString);
                            r.addContacts(type, value);
                        }
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
        List<Resume> resumeList = new ArrayList<>();
        List<String> listUUID = new ArrayList<>();
        sqlHelper.execute("SELECT * FROM resume r LEFT JOIN contact c ON r.uuid = c.resume_uuid ORDER BY r.full_name, r.uuid",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    Resume r = new Resume();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid");
                        String type = rs.getString("type");
                        String value = rs.getString("value");
                        if (listUUID.contains(uuid)) {
                            r.addContacts(ContactTypes.valueOf(type), value);
                        } else {
                            r = new Resume(uuid, rs.getString("full_name"));
                            r.addContacts(ContactTypes.valueOf(type), value);
                            resumeList.add(r);
                            listUUID.add(uuid);
                        }
                    }
                    return null;
                });
        return resumeList;
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

    private void setContact(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactTypes, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

}
