package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size = 0;

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void save(Resume resume) {
        if (size == storage.length) {
            System.out.println("Массив полностью заполнен, удалите записи что бы освободить место.");
        } else {
            storage[size] = resume;
            size++;
        }
    }

    public void update(String uuid) {
        objectExists(uuid);
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                storage[i].setUuid(uuid + "_update");
            }
        }
    }

    public Resume get(String uuid) {
        objectExists(uuid);
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return storage[i];
            }
        }
        return null;
    }

    public void delete(String uuid) {
        objectExists(uuid);
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                System.arraycopy(storage, i + 1, storage, i, storage.length - 1 - i);
                size--;
            }
        }
    }

    public Resume objectExists(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                System.out.println(uuid + ": Объкт существует");
                return storage[i];
            }
        }
        System.out.println(uuid + ": Объект не существует");
        return null;
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
