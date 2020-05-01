package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected int indexOf(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                System.out.println("Объкт существует:" + uuid);
                return i;
            }
        }
        System.out.println("Объект не существует:" + uuid);
        return -1;
    }

    @Override
    protected void saveElement(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    protected void delElement(int index) {
        storage[index] = storage[size - 1];
    }

}
