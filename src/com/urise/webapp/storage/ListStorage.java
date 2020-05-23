package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ListStorage extends AbstractStorage {

    public void update(Resume resume) {
        int index = arList.indexOf(resume);
        arList.set(index, resume);
    }

    public Resume get(String uuid) {
        return arList.get(indexOf(uuid));
    }

    @Override
    protected int indexOf(String uuid) {
        Resume searchKey = new Resume(uuid);
        int index = arList.indexOf(searchKey);
        return index;
    }
}
