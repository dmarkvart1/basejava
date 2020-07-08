package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected Integer getSearchKey(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid, "dummy"), RESUME_COMPARATOR);
    }

    @Override
    protected void delElement(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

    @Override
    protected void saveElement(Resume resume, Integer index) {
        int indexSave = -(index + 1);
        System.arraycopy(storage, indexSave, storage, indexSave + 1, size - indexSave);
        storage[indexSave] = resume;
    }
}
