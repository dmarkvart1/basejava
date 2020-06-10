package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract Object getSearchKey(String uuid);
    protected abstract void saveResume(Resume resume, Object searchKey);
    protected abstract void updateElement(Object searchKey, Resume resume);
    protected abstract Resume getElement(Object searchKey);
    protected abstract void delResume(Object searchKey);
    protected abstract boolean isExist(Object searchKey);

    @Override
    public void save(Resume resume) {
        Object searchKey = existElement(resume.getUuid());
        saveResume(resume, searchKey);
        System.out.println("Объект сохранен:" + resume.getUuid());
    }

    @Override
    public void update(Resume resume) {
        Object searchKey = notExistElement(resume.getUuid());
        updateElement(searchKey, resume);
        System.out.println("Объект обновлен:" + resume.getUuid());
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = notExistElement(uuid);
        System.out.println("Объект получен:" + uuid);
        return getElement(searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = notExistElement(uuid);
        delResume(searchKey);
        System.out.println("Объект удален:" + uuid);
    }

    private Object notExistElement(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object existElement(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}
