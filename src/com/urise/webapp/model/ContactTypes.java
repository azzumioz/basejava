package com.urise.webapp.model;

public enum ContactTypes {
    TELEPHONE("Тел:"),
    SKYPE("Skype:"),
    EMAIL("Почта:"),
    LINKEDIN("Профиль"),
    GITHUB("Профиль"),
    STACKOVERFLOW("Профиль");

    private String title;

    ContactTypes(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
