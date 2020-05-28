package com.urise.webapp.storage;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int indexElement(Resume resume) {
        return Arrays.binarySearch(storage, 0, size, resume);
    }
}
