package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract int indexElement(Resume resume);

    @Override
    public void save(Resume resume) {
        int index = existElement(resume.getUuid());
        saveResume(resume, index);
        System.out.println("Объект сохранен:" + resume.getUuid());
    }

    protected abstract void saveResume(Resume resume, int index);

    @Override
    public void update(Resume resume) {
        int index = notExistElement(resume.getUuid());
        updateElement(index, resume);
        System.out.println("Объект обновлен:" + resume.getUuid());
    }

    protected abstract void updateElement(int index, Resume resume);

    @Override
    public Resume get(String uuid) {
        int index = notExistElement(uuid);
        System.out.println("Объект получен:" + uuid);
        return getElement(index);
    }

    protected abstract Resume getElement(int index);

    @Override
    public void delete(String uuid) {
        int index = notExistElement(uuid);
        delResume(index);
        System.out.println("Объект удален:" + uuid);
    }

    protected abstract void delResume(int index);

    private int existElement(String uuid) {
        int index = indexElement(new Resume(uuid));
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    private int notExistElement(String uuid) {
        int index = indexElement(new Resume(uuid));
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }
}
