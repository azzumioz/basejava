package com.urise.webapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DateSection extends Sections {
    private List<Job> list = new ArrayList<>();

    public List<Job> get() {
        return list;
    }

    public void set(String name, String position, String description, LocalDate fromDate, LocalDate toDate) {
        this.list.add(new Job(name, position, description, fromDate, toDate));
    }

    @Override
    public String toString() {
        String result = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        for (int i = 0; i < list.size(); i++) {
            result += list.get(i).name + "\n" +
                    list.get(i).fromDate.format(formatter) + " - " +
                    list.get(i).toDate.format(formatter) + " " +
                    list.get(i).position + "\n" +
                    list.get(i).description + "\n";
        }
        return result;
    }

    private class Job {
        private String name;
        private String position;
        private String description;
        private LocalDate fromDate;
        private LocalDate toDate;

        public Job(String name, String position, String description, LocalDate fromDate, LocalDate toDate) {
            this.name = name;
            this.position = position;
            this.description = description;
            this.fromDate = fromDate;
            this.toDate = toDate;
        }
    }
}


