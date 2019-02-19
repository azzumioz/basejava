package com.urise.webapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExperienceSection extends Section {
    private List<Experience> list = new ArrayList<>();

    public List<Experience> get() {
        return list;
    }

    public void set(String name, String link, String position, String description, LocalDate fromDate, LocalDate toDate) {
        this.list.add(new Experience(name, link, position, description, fromDate, toDate));
    }

    private class Experience {
        private String name;
        private String link;
        private String position;
        private String description;
        private LocalDate fromDate;
        private LocalDate toDate;

        public Experience(String name, String link, String position, String description, LocalDate fromDate, LocalDate toDate) {
            this.name = name;
            this.link = link;
            this.position = position;
            this.description = description;
            this.fromDate = fromDate;
            this.toDate = toDate;
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExperienceSection that = (ExperienceSection) o;

        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }
}


