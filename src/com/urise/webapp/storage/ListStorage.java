package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> listStorage = new ArrayList<>();

    @Override
    protected int indexElement(Resume index) {
        return listStorage.indexOf(index);
    }

    @Override
    public void clear() {
        listStorage.clear();
    }


    @Override
    protected void saveElement(Resume resume, int index) {
        listStorage.add(resume);
    }

    @Override
    protected void updateElement(int index, Resume resume) {
        listStorage.set(index, resume);
    }

    @Override
    protected Resume getElement(int index) {
        return listStorage.get(index);
    }

    @Override
    protected void delElement(int index) {
        listStorage.remove(index);
    }

    @Override
    public Resume[] getAll() {
        return (listStorage.toArray(new Resume[listStorage.size()]));
    }

    @Override
    public int size() {
        return listStorage.size();
    }
}