package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;


/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected int indexElement(Resume resume) {
        for (int i = 0; i < size; i++) {
            if (resume.getUuid().equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void delElement(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected void saveElement(Resume resume, int index) {
        storage[size] = resume;
    }
}