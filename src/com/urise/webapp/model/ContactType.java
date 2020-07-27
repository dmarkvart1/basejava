package com.urise.webapp.model;

public enum ContactType {
    ADDRESS("Адрес"),
    PHONE("Телефон"),
    MOBILE("Мобильный"),
    EMAIL("Эл.почта"),
    SKYPE("Skype"),
    SITE("Домашняя страница");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
