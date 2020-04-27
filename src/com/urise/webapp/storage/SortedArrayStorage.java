package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {


    @Override
    public void save(Resume resume) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Массив полностью заполнен, удалите записи что бы освободить место.");
        } else {
            int index = indexOf(resume.getUuid());
            if (index < 0) {
                index = -(index+1);
                if (storage[index] == null) {
                    storage[index] = resume;
                    System.out.println("Объект сохранен:" + resume.getUuid());
                    size++;
                }
            } else {
                System.out.println("Запись невозможна!");
            }
        }
    }

    @Override
    public void update(Resume resume) {
        int index = indexOf(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
            System.out.println("Объект обновлен:" + resume.getUuid());
        }
    }

    @Override
    protected int indexOf(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    public void delete(String uuid) {
        int index = indexOf(uuid);
        if (index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, size - index - 1);
            System.out.println("Объект удален:" + uuid);
            size--;
        }
    }
}
