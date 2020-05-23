package com.urise.webapp.storage;
import com.urise.webapp.model.Resume;
import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractStorage implements Storage {
    private Collection<Resume> arList = new ArrayList<>();

    public void clear() {
        arList.clear();
    }

    public void save(Resume resume) {
        arList.add(resume);
    }

    public void delete(String uuid) {
        arList.remove(uuid);
    }

    public Resume[] getAll() {
        Resume[] arr = arList.toArray(new Resume[arList.size()]);
        return(arr);
    }

    public int size() {
        return arList.size();
    }
}
