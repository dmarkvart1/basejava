package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public abstract void clear();

    protected int indexOf(String uuid) {
        Resume searchKey = new Resume(uuid);
        int index = indexElement(searchKey);
        if (index >= 0) {
            System.out.println("Объкт существует:" + uuid);
            return index;
        } else {
            System.out.println("Объкт не существует:" + uuid);
        }
        return index;
    }

    protected abstract int indexElement(Resume resume);

    @Override
    public void save(Resume resume) {
        int index = indexOf(resume.getUuid());
        if (index < 0) {
            saveElement(resume, index);
            System.out.println("Объект сохранен:" + resume.getUuid());
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    protected abstract void saveElement(Resume resume, int index);

    @Override
    public void update(Resume resume) {
        int index = indexOf(resume.getUuid());
        if (index >= 0) {
            updateElement(index, resume);
            System.out.println("Объект обновлен:" + resume.getUuid());
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    protected abstract void updateElement(int index, Resume resume);

    @Override
    public Resume get(String uuid) {
        int index = indexOf(uuid);
        if (index >= 0) {
            System.out.println("Объект получен:" + uuid);
            return getElement(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }
    protected abstract Resume getElement(int index);

    @Override
    public void delete(String uuid) {
        int index = indexOf(uuid);
        if (index >= 0) {
            delElement(index);
            System.out.println("Объект удален:" + uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract void delElement(int index);
}
