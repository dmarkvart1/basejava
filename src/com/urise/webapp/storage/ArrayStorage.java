package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void save(Resume r) {
        storage[size] = r;
        size++;
    }

    public void update(String uuid) {
        objectExists(uuid);
        for (int i = 0; i < size; i++) {
            if (uuid == storage[i].getUuid()) {
                storage[i].setUuid(uuid + "_update");
            }
        }
    }

    public Resume get(String uuid) {
        objectExists(uuid);
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid() == uuid)
                return storage[i];
        }
        return null;
    }

    public void delete(String uuid) {
        objectExists(uuid);
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid() == uuid) {
                System.arraycopy(storage, i + 1, storage, i, storage.length - 1 - i);
                size--;
            }
        }
    }

    public boolean objectExists(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid() == uuid) {
                System.out.println(uuid + ": Объкт существует");
                return true;
            }
        }
        System.out.println(uuid + ": Объект не существует");
        return false;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        for (int i = 0; i < size; i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    public int size() {
        return size;
    }
}
