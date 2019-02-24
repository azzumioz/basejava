package com.urise.webapp.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static List<File> getListFiles(List<File> files, File dir) {
        if (files == null)
            files = new ArrayList<>();
        if (!dir.isDirectory()) {
            files.add(dir);
            return files;
        }
        for (File file : dir.listFiles())
            getListFiles(files, file);
        return files;
    }
}
