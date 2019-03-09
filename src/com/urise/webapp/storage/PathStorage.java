package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private Strategy strategy;

    protected PathStorage(String dir, Strategy strategy) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not Directory or is not writable");
        }
        this.directory = directory;
        this.strategy = strategy;
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toString() + File.separator + uuid);
    }

    @Override
    protected void doSave(Path file, Resume resume) {
        try {
            Files.createFile(file);
        } catch (IOException e) {
            throw new StorageException("Could not create file" + file.getFileName(), null, e);
        }
        doUpdate(file, resume);
    }

    @Override
    protected void doUpdate(Path file, Resume resume) {
        try {
            strategy.doWrite(new BufferedOutputStream(new FileOutputStream(file.toString())), resume);
        } catch (IOException e) {
            throw new StorageException("Write error" + file.getFileName(), null, e);
        }
    }

    @Override
    protected Resume doGet(Path file) {
        try {
            return strategy.doRead(new BufferedInputStream(new FileInputStream(file.toString())));
        } catch (IOException e) {
            throw new StorageException("Read error" + file.getFileName(), null, e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> listResume = null;
        try {
            List<Path> listFiles = Files.list(directory).collect(Collectors.toList());
            for (Path file : listFiles) {
                listResume.add(doGet(file));
            }
        } catch (IOException e) {
            throw new StorageException("Read error", null, e);
        }
        return listResume;
    }

    @Override
    protected void doDelete(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new StorageException("Delete file error", null);
        }
    }

    @Override
    protected boolean isExist(Path file) {
        return Files.exists(file);
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Delete Path error", null);
        }
    }

    @Override
    public int size() {
        try {
            return Files.list(directory).collect(Collectors.toList()).size();
        } catch (IOException e) {
            throw new StorageException("Error size", null);
        }
    }

}
