package com.urise.webapp.model;

public enum ContactTypes {
    PHONE("Тел."),
    MOBIKE("Мобильный"),
    HOME_PHONE("Домашний тел."),
    SKYPE("Skype"),
    EMAIL("Почта"),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль StackOverflow"),
    HOME_PAGE("Домашняя страница");

    private final String title;

    ContactTypes(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
