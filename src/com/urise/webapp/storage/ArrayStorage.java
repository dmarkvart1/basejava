package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage implements Storage {

    @Override
    protected void differAction(Resume resume) {
        int index = indexOf(resume.getUuid());
        if (index < 0) {
            storage[size] = resume;
            System.out.println("Объект сохранен:" + resume.getUuid());
            size++;
        } else {
            System.out.println("Запись невозможна!");
        }
    }

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

}
