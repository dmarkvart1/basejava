package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {


    @Override
    protected void differAction(Resume resume) {
        int index = indexOf(resume.getUuid());
        if (index < 0) {
            index = -(index + 1);
            if (storage[index] == null) {
                storage[index] = resume;
                System.out.println("Объект сохранен:" + resume.getUuid());
                size++;
            } else {
                System.out.println("Запись невозможна!");
            }
        }
    }

    @Override
    protected int indexOf(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
