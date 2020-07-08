package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private Map<String, Resume> mapResumeStorage = new HashMap<>();

    @Override
    protected void delResume(Resume resume) {
        mapResumeStorage.remove((resume).getUuid());
    }

    @Override
    protected boolean isExist(Resume resume) {
        return resume != null;
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return mapResumeStorage.get(uuid);
    }

    @Override
    protected void saveResume(Resume resume, Resume res) {
        mapResumeStorage.put(resume.getUuid(), resume);
    }

    @Override
    protected void updateElement(Resume res, Resume resume) {
        mapResumeStorage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getElement(Resume resume) {
        return resume;
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
