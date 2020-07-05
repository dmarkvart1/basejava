package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
    private Map<String, Resume> mapUuidStorage = new HashMap<>();

    @Override
    protected void delResume(Object uuid) {
        mapUuidStorage.remove((String) uuid);
    }

    @Override
    protected boolean isExist(Object uuid) {
        return mapUuidStorage.containsKey((String) uuid);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void saveResume(Resume resume, Object index) {
        mapUuidStorage.put((String) index, resume);
    }

    @Override
    protected void updateElement(Object uuid, Resume resume) {
        mapUuidStorage.put((String) uuid, resume);
    }

    @Override
    protected Resume getElement(Object uuid) {
        return mapUuidStorage.get((String) uuid);
    }

    @Override
    public void clear() {
        mapUuidStorage.clear();
    }

    @Override
    public List<Resume> doCopyAll() {
        return new ArrayList<>(mapUuidStorage.values());
    }

    @Override

    public int size() {
        return mapUuidStorage.size();
    }
}
