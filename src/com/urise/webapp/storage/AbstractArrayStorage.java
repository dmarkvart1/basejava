package com.urise.webapp.storage;

import com.urise.webapp.exeption.StorageException;
import com.urise.webapp.model.Resume;
import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected int size = 0;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void saveResume(Resume resume, Object searchKey) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        saveElement(resume, (Integer) searchKey);
        size++;
    }

    protected abstract void saveElement(Resume resume, Integer searchKey);

    @Override
    protected void updateElement(Object searchKey, Resume resume) {
        storage[(Integer) searchKey] = resume;
    }

    @Override
    protected Resume getElement(Object searchKey) {
        return storage[(Integer) searchKey];
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    @Override
    protected void delResume(Object searchKey) {
        delElement((Integer) searchKey);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void delElement(int index);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    public int size() {
        return size;
    }
}
