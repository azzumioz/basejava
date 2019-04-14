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
    private String sqlString;
    private Map<Integer, String> parameters;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        LOG.info("Clear");
        sqlString = "DELETE FROM resume";
        parameters = new HashMap<>();
        sqlExecute((ABlockOfCode<Integer>) ps -> {
            ps.executeUpdate();
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        sqlString = "INSERT INTO resume (uuid, full_name) VALUES (?,?)";
        parameters = new HashMap<>();
        parameters.put(1, resume.getUuid());
        parameters.put(2, resume.getFullName());
        sqlExecute((ABlockOfCode<Integer>) ps -> {
            try {
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new ExistStorageException("Resime " + resume.getUuid() + " already exist");
            }
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        sqlString = "UPDATE resume SET full_name =? WHERE resume.uuid =?";
        parameters = new HashMap<>();
        parameters.put(1, resume.getFullName());
        parameters.put(2, resume.getUuid());
        sqlExecute((ABlockOfCode<Integer>) ps -> {
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException("Resume " + resume.getUuid() + " not exist");
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        sqlString = "SELECT * FROM resume r WHERE r.uuid =?";
        parameters = new HashMap<>();
        parameters.put(1, uuid);
        return sqlExecute(ps -> {
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
        sqlString = "DELETE FROM resume WHERE resume.uuid =?";
        parameters = new HashMap<>();
        parameters.put(1, uuid);
        sqlExecute((ABlockOfCode<Integer>) ps -> {
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
        sqlString = "SELECT * FROM resume ORDER BY resume.uuid, resume.full_name";
        parameters = new HashMap<>();
        List<Resume> sortedList = new ArrayList<>();
        return sqlExecute(ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sortedList.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }
            return sortedList;
        });
    }

    @Override
    public int size() {
        sqlString = "SELECT COUNT (*) FROM resume";
        parameters = new HashMap<>();
        return sqlExecute(ps -> {
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new StorageException("Error count");
            }
            return rs.getInt(1);
        });
    }

    public interface ABlockOfCode<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }

    private <T> T sqlExecute(ABlockOfCode<T> aBlockOfCode) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlString)) {
            if (parameters != null) {
                for (Integer key : parameters.keySet()) {
                    ps.setString(key, parameters.get(key));
                }
            }
            return aBlockOfCode.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

}
