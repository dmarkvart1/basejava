package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
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
    protected void saveResume(Resume resume, Integer searchKey) {
        listStorage.add(resume);
    }

    @Override
    protected void updateElement(Integer searchKey, Resume resume) {
        listStorage.set(searchKey, resume);
    }

    @Override
    protected Resume getElement(Integer searchKey) {
        return listStorage.get(searchKey);
    }

    @Override
    protected void delResume(Integer searchKey) {
        listStorage.remove(searchKey.intValue());
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
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