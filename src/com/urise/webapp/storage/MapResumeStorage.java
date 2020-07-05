package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    private Map<String, Resume> mapResumeStorage = new HashMap<>();

    @Override
    protected void delResume(Object resume) {
        mapResumeStorage.remove(((Resume) resume).getUuid());
    }

    @Override
    protected boolean isExist(Object resume) {
        return resume != null;
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return mapResumeStorage.get(uuid);
    }

    @Override
    protected void saveResume(Resume resume, Object res) {
        mapResumeStorage.put(resume.getUuid(), resume);
    }

    @Override
    protected void updateElement(Object res, Resume resume) {
        mapResumeStorage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getElement(Object resume) {
        return (Resume) resume;
    }

    @Override
    public void clear() {
        mapResumeStorage.clear();
    }

    @Override
    public List<Resume> doCopyAll() {
        return new ArrayList<>(mapResumeStorage.values());
    }

    @Override

    public int size() {
        return mapResumeStorage.size();
    }
}
