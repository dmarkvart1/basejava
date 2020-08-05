package com.urise.webapp.storage;

import com.urise.webapp.exeption.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        }
        if (!directory.canRead() && !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void saveResume(Resume resume, File file) {
        try {
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error: ", file.getName(), e);
        }
    }

    protected abstract void doWrite(Resume resume, File file) throws IOException;

    @Override
    protected void updateElement(File file, Resume resume) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error: ", file.getName(), e);
        }
    }

    @Override
    protected Resume getElement(File file) {
        return new Resume(String.valueOf(file));
    }

    @Override
    protected void delResume(File file) {
        file.delete();

    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> files = new ArrayList<>();
        for (Object file : files.toArray()) {
            if (((File) file).isFile()) {
                files.add((Resume) file);
            }
        }
        return new ArrayList<>(files);
    }

    @Override
    public void clear() {
        for (File delFile : new File(String.valueOf(directory)).listFiles())
            if (delFile.isFile()) delFile.delete();
    }

    @Override
    public int size() {
        File[] arrFiles = directory.listFiles();
        List<File> lst = Arrays.asList(arrFiles);
        return lst.size();
    }
}
