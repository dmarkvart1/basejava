package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> listStorage = new ArrayList<>();

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < listStorage.size(); i++) {
            if (listStorage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
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
        return listStorage.get((Integer) searchKey);
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
    public List<Resume> doCopyAll() {
        return new ArrayList<>(listStorage);
    }

    @Override
    public int size() {
        return listStorage.size();
    }
}