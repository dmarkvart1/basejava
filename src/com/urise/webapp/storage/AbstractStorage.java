package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    protected abstract SK getSearchKey(String uuid);
    protected abstract void saveResume(Resume resume, SK searchKey);
    protected abstract void updateElement(SK searchKey, Resume resume);
    protected abstract Resume getElement(SK searchKey);
    protected abstract void delResume(SK searchKey);
    protected abstract boolean isExist(SK searchKey);
    protected abstract List<Resume> doCopyAll();

    @Override
    public void save(Resume resume) {
        SK searchKey = isExistElement(resume.getUuid());
        saveResume(resume, searchKey);
        System.out.println("Объект сохранен:" + resume.getUuid());
    }

    @Override
    public void update(Resume resume) {
        SK searchKey = isNotExistElement(resume.getUuid());
        updateElement(searchKey, resume);
        System.out.println("Объект обновлен:" + resume.getUuid());
    }

    @Override
    public Resume get(String uuid) {
        SK searchKey = isNotExistElement(uuid);
        System.out.println("Объект получен:" + uuid);
        return getElement(searchKey);
    }

    @Override
    public void delete(String uuid) {
        SK searchKey = isNotExistElement(uuid);
        delResume(searchKey);
        System.out.println("Объект удален:" + uuid);
    }

    private SK isNotExistElement(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK isExistElement(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = doCopyAll();
        Collections.sort(list);
        return list;
    }
}
