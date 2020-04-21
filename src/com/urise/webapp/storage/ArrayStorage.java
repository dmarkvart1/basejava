package com.urise.webapp.storage;
import com.urise.webapp.model.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }


    public void save(Resume resume) {
        if (size == storage.length) {
            System.out.println("Массив полностью заполнен, удалите записи что бы освободить место.");
        } else {
            int index = objectExists(resume.getUuid());
            if (index == -1) {
                storage[size] = resume;
                System.out.println("Объект сохранен:" + resume.getUuid());
                size++;
            } else {
                System.out.println("Запись невозможна!");
            }
        }
    }

    public void update(Resume resume) {
        int index = objectExists(resume.getUuid());
        if (index == -1) {
        } else {
            storage[index] = resume;
            System.out.println("Объект обновлен:" + resume.getUuid());
        }
    }

    public Resume get(String uuid) {
        int index = objectExists(uuid);
        if (index == -1) {
        } else {
            System.out.println("Объект получен:" + storage[index]);
            return storage[index];
        }
        return null;
    }

    public void delete(String uuid) {
        int index = objectExists(uuid);
        if (index == -1) {
        } else {
            System.arraycopy(storage, index + 1, storage, index, 2);
            System.out.println("Объект удален:" + uuid);
            size--;
        }
    }

    public int objectExists(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                System.out.println("Объкт существует:" + uuid);
                return i;
            }
        }
        System.out.println("Объект не существует:" + uuid);
        return -1;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes;
        resumes = Arrays.copyOfRange(storage, size, size);
        return resumes;
    }

    public int size() {
        return size;
    }
}
