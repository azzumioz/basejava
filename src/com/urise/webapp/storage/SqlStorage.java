package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private final ConnectionFactory connectionFactory;
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
    private Map<Integer, String> parameters;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        LOG.info("Clear");
        doExecuteUpdate("DELETE FROM resume", new HashMap<>());
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            if (ps.executeUpdate() == 0)
                throw new ExistStorageException("Resime " + resume.getUuid() + " already exist");
        } catch (SQLException e) {
            throw new ExistStorageException("Resime " + resume.getUuid() + " already exist");
        }
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        parameters = new HashMap<>();
        parameters.put(1, resume.getFullName());
        parameters.put(2, resume.getUuid());
        if (doExecuteUpdate("UPDATE resume SET full_name =? WHERE resume.uuid =?", parameters) == 0) {
            throw new NotExistStorageException("Resume " + resume.getUuid() + " not exist");
        }
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r WHERE r.uuid =?")) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        parameters = new HashMap<>();
        parameters.put(1, uuid);
        if (doExecuteUpdate("DELETE FROM resume WHERE resume.uuid =?", parameters) == 0) {
            throw new NotExistStorageException("Resume  not exist");
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted");
        List<Resume> sortedList = new ArrayList<>();
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY resume.uuid, resume.full_name")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sortedList.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }
            return sortedList;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public int size() {
        LOG.info("GetSize");
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT COUNT (*) FROM resume")) {
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new StorageException("Error count");
            }
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new StorageException(e);
        }

    }

    private int doExecuteUpdate(String sqlString, Map<Integer, String> parameters) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlString)) {
            if (parameters != null) {
                for (Integer key : parameters.keySet()) {
                    ps.setString(key, parameters.get(key));
                }
            }
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new StorageException(e);
        }

    }

}
