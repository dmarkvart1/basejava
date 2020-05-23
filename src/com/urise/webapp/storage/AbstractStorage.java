package com.urise.webapp.storage;
import com.urise.webapp.model.Resume;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    protected abstract int indexOf(String uuid);

    protected List<Resume> arList = new ArrayList<>();

    public void clear() {
        arList.clear();
    }

    public void save(Resume resume) {
        arList.add(resume);
    }

    public void delete(String uuid) {
        arList.remove(new Resume(uuid));
    }

    public Resume[] getAll() {
        Resume[] arr = arList.toArray(new Resume[arList.size()]);
        return(arr);
    }

    public int size() {
        return arList.size();
    }
}
