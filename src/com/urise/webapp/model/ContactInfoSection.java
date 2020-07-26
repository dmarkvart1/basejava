package com.urise.webapp.model;

import java.util.Objects;

public enum  ContactInfoSection {
    ADDRESS("Адрес"),
    PHONE("Телефон"),
    MOBILE("Мобильный"),
    EMAIL("Эл.почта"),
    SKYPE("Skype"),
    SITE("Домашняя страница");

    private String title;

    ContactInfoSection(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
