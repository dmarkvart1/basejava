package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {
    private Map<String, Resume> mapUuidStorage = new HashMap<>();

    @Override
    protected void delResume(String uuid) {
        mapUuidStorage.remove(uuid);
    }

    @Override
    protected boolean isExist(String uuid) {
        return mapUuidStorage.containsKey(uuid);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void saveResume(Resume resume, String uuid) {
        mapUuidStorage.put(uuid, resume);
    }

    @Override
    protected void updateElement(String uuid, Resume resume) {
        mapUuidStorage.put(uuid, resume);
    }

    @Override
    protected Resume getElement(String uuid) {
        return mapUuidStorage.get(uuid);
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
