package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STOTAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STOTAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = indexOf(uuid);
        if (index != -1) {
            System.out.println("Объект получен:" + storage[index]);
            return storage[index];
        }
        return null;
    }

    protected abstract int indexOf(String uuid);
}
