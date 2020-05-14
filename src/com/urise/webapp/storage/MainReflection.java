package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException {
        Resume resume = new Resume();
        Field field = resume.getClass().getDeclaredFields()[0];
        Class<?> fld = field.getDeclaringClass();
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(resume));
        System.out.println(fld.toString());
    }
}
