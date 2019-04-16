package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        SqlHelper.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        LOG.info("Clear");
        SqlHelper.sqlExecute("DELETE FROM resume",
                ps -> {
                    ps.executeUpdate();
                    return null;
                });
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        SqlHelper.sqlExecute("INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                ps -> {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, resume.getFullName());
                    ps.executeUpdate();
                    return null;
                });
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        SqlHelper.sqlExecute("UPDATE resume SET full_name =? WHERE resume.uuid =?",
                ps -> {
                    ps.setString(1, resume.getFullName());
                    ps.setString(2, resume.getUuid());
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException("Resume " + resume.getUuid() + " not exist");
                    }
                    return null;
                });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return SqlHelper.sqlExecute("SELECT * FROM resume r WHERE r.uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    return new Resume(uuid, rs.getString("full_name"));
                });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SqlHelper.sqlExecute("DELETE FROM resume WHERE resume.uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    int res = ps.executeUpdate();
                    if (res == 0) {
                        throw new NotExistStorageException("Resume  not exist");
                    }
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted");
        List<Resume> sortedList = new ArrayList<>();
        return SqlHelper.sqlExecute("SELECT * FROM resume ORDER BY resume.uuid, resume.full_name",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        sortedList.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
                    }
                    return sortedList;
                });
    }

    @Override
    public int size() {
        LOG.info("GetSize");
        return SqlHelper.sqlExecute("SELECT COUNT (*) FROM resume",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new StorageException("Error count");
                    }
                    return rs.getInt(1);
                });
    }

}
