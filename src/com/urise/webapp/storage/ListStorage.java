package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> arList = new ArrayList<>();

    public void update(Resume resume) {
        int index = arList.indexOf(resume);
        arList.add(index, resume);
    }

    public Resume get(String uuid) {
        int index = arList.indexOf(uuid);
        return arList.get(index);
    }
}
