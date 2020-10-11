package com.urise.webapp.sql;

import com.urise.webapp.model.ContactType;

public class Contact {
   private ContactType type;
   private String value;
   private String uuid;

    public ContactType getType() {
        return type;
    }

    public void setType(ContactType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}

