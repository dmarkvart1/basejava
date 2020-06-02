package com.urise.webapp.storage;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int indexElement(Resume resume) {
        return Arrays.binarySearch(storage, 0, size, resume);
    }

    @Override
    protected void delElement(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

    @Override
    protected void saveElement(Resume resume, int index) {
        int indexSave = -(index + 1);
        System.arraycopy(storage, indexSave, storage, indexSave + 1, size - indexSave);
        storage[indexSave] = resume;
    }
}
