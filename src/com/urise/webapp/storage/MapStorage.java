package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> mapStorage = new HashMap<>();

    @Override
    protected void delResume(Object uuid) {
        mapStorage.remove((String) uuid);
    }

    @Override
    protected boolean isExist(Object uuid) {
        return mapStorage.containsKey((String) uuid);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void saveResume(Resume resume, Object index) {
        mapStorage.put((String) index, resume);
    }

    @Override
    protected void updateElement(Object uuid, Resume resume) {
        mapStorage.put((String) uuid, resume);
    }

    @Override
    protected Resume getElement(Object uuid) {
        return mapStorage.get((String) uuid);
    }

    @Override
    public void clear() {
        mapStorage.clear();
    }

    @Override
    public List<Resume> doCopyAll() {
        return new ArrayList<>(mapStorage.values());
    }

    @Override

    public int size() {
        return mapStorage.size();
    }
}
