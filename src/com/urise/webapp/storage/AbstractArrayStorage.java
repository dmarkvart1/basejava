package com.urise.webapp.storage;

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

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void saveResume(Resume resume, int index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        saveElement(resume, index);
        size++;
    }

    protected abstract void saveElement(Resume resume, int index);

    @Override
    protected void updateElement(int index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected Resume getElement(int index) {
        return storage[index];
    }

    @Override
    protected void delResume(int index) {
        delElement(index);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void delElement(int index);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int size() {
        return size;
    }
}
