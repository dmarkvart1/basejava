package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
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
            System.out.println("Массив полностью заполнен, удалите записи что бы освободить место.");
        } else {
            differAction(resume);
        }
    }
    protected abstract void differAction(Resume resume);

    public void update(Resume resume) {
        int index = indexOf(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
            System.out.println("Объект обновлен:" + resume.getUuid());
        }
    }

    public Resume get(String uuid) {
        int index = indexOf(uuid);
        if (index >= 0) {
            System.out.println("Объект получен:" + storage[index]);
            return storage[index];
        }
        return null;
    }

    public void delete(String uuid) {
        int index = indexOf(uuid);
        if (index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, size - index - 1);
            System.out.println("Объект удален:" + uuid);
            size--;
        }
    }

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
