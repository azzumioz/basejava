package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Strategy {

    void doWrite(OutputStream os, Resume resume) throws IOException;

    Resume doRead(InputStream is) throws IOException;
}
