package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.exeption.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;


    protected abstract int indexOf(String uuid);

    public void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            int index = indexOf(resume.getUuid());
            if (index < 0) {
                saveElement(resume, index);
                System.out.println("Объект сохранен:" + resume.getUuid());
                size++;
            } else {
                throw new ExistStorageException(resume.getUuid());
            }
        }
    }

    protected abstract void saveElement(Resume resume, int index);

    public void update(Resume resume) {
        int index = indexOf(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
            System.out.println("Объект обновлен:" + resume.getUuid());
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    public Resume get(String uuid) {
        int index = indexOf(uuid);
        if (index >= 0) {
            System.out.println("Объект получен:" + storage[index]);
            return storage[index];
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public void delete(String uuid) {
        int index = indexOf(uuid);
        if (index >= 0) {
            delElement(index);
            System.out.println("Объект удален:" + uuid);
            storage[size - 1] = null;
            size--;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract void delElement(int index);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
