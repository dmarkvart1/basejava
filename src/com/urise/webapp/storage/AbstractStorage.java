package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    private final static Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract SK getSearchKey(String uuid);

    protected abstract void saveResume(Resume resume, SK searchKey);

    protected abstract void updateElement(SK searchKey, Resume resume);

    protected abstract Resume getElement(SK searchKey);

    protected abstract void delResume(SK searchKey);

    protected abstract boolean isExist(SK searchKey);

    protected abstract List<Resume> doCopyAll();

    @Override
    public void save(Resume resume) {
        LOG.info("Save" + resume);
        SK searchKey = getSearchKeyIfNotExist(resume.getUuid());
        saveResume(resume, searchKey);
        System.out.println("Объект сохранен:" + resume.getUuid());
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update" + resume);
        SK searchKey = getSearchKeyIfExist(resume.getUuid());
        updateElement(searchKey, resume);
        System.out.println("Объект обновлен:" + resume.getUuid());
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get" + uuid);
        SK searchKey = getSearchKeyIfExist(uuid);
        System.out.println("Объект получен:" + uuid);
        return getElement(searchKey);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete" + uuid);
        SK searchKey = getSearchKeyIfExist(uuid);
        delResume(searchKey);
        System.out.println("Объект удален:" + uuid);
    }

    private SK getSearchKeyIfExist(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getSearchKeyIfNotExist(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted");
        List<Resume> list = doCopyAll();
        Collections.sort(list);
        return list;
    }
}
