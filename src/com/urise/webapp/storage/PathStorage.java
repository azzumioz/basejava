package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.streamstorage.Strategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private Strategy strategy;

    protected PathStorage(String dir, Strategy strategy) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not Directory or is not writable");
        }
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
        List<Resume> listResume = listFiles().map(this::doGet).collect(Collectors.toList());
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
        listFiles().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return listFiles().collect(Collectors.toList()).size();
    }

    private Stream<Path> listFiles() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("List file read error", null);
        }
    }

}
