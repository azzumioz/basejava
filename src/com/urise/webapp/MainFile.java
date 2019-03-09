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
        //File dir = new File("c:/tmp");
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
        printDirectoryDeeply(dir, "");
    }

    public static void printDirectoryDeeply(File dir, String tab) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println(tab + "\t" + "dir: " + file.getName());
                    printDirectoryDeeply(file, tab + "\t");
                } else {
                    System.out.println(tab + "\t" + "file: " + file.getName());
                }
            }
        }
    }

}
