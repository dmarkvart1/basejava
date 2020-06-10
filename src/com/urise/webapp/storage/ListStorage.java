package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> listStorage = new ArrayList<>();

    @Override
    protected Object getSearchKey(String uuid) {
        return listStorage.indexOf(new Resume(uuid));
    }

    @Override
    public void clear() {
        listStorage.clear();
    }

    @Override
    protected void saveResume(Resume resume, Object searchKey) {
        listStorage.add(resume);
    }

    @Override
    protected void updateElement(Object searchKey, Resume resume) {
        listStorage.set((Integer) searchKey, resume);
    }

    @Override
    protected Resume getElement(Object searchKey) {
        Resume resume = listStorage.get((Integer) searchKey);
        return resume;
    }

    @Override
    protected void delResume(Object searchKey) {
        listStorage.remove((int) searchKey);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
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