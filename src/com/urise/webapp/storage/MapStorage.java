package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> mapStorage = new HashMap<>();

    @Override
    protected void delResume(Object searchKey) {
        mapStorage.remove(searchKey.toString());
    }

    @Override
    protected boolean isExist(Object searchKey) {
        Resume resume= (Resume) searchKey;
        return mapStorage.containsValue(resume);
    }

    @Override
    protected Object indexElement(String uuid) {
        return mapStorage.get(uuid);
    }

    @Override
    protected void saveResume(Resume resume, Object index) {
        mapStorage.put(resume.getUuid(), resume);
    }

    @Override
    protected void updateElement(Object searchKey, Resume resume) {
        mapStorage.replace(searchKey.toString(), resume);
    }

    @Override
    protected Resume getElement(Object ob) {
        return mapStorage.get(ob.toString());
    }

    @Override
    public void clear() {
        mapStorage.clear();
    }

    @Override
    public Resume[] getAll() {
        return mapStorage.values().toArray(new Resume[mapStorage.size()]);
    }

    @Override
    public int size() {
        return mapStorage.size();
    }
}
