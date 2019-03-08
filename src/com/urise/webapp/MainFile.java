package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {

    public static void main(String[] args) {
        String filePath = ".\\.gitignore";
        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
        File dir = new File("./src/com/urise/webapp");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : dir.list()) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        printDirectoryDeeply(dir);
    }

    public static void printDirectoryDeeply(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                int count = file.getAbsolutePath().split("\\\\").length - 10;
                if (file.isFile()) {
                    for (int i = 0; i < count; i++) {
                        System.out.print("\t");
                    }
                    System.out.println("files: " + file.getName());
                } else {
                    for (int i = 0; i < count; i++) {
                        System.out.print("\t");
                    }
                    System.out.println("dir: " + file.getName());
                    printDirectoryDeeply(file);
                }
            }
        }
    }

}
