package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;


/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object indexElement(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid == storage[i].getUuid()) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void delElement(Integer index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected void saveElement(Resume resume, Integer index) {
        storage[size] = resume;
    }
}