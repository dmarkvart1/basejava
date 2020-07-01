package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume resume = new Resume("Name");
        Field field = resume.getClass().getDeclaredFields()[0];
        Class<? extends Resume> resumeClass = resume.getClass();
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(resume));
        System.out.println(resumeClass.toString());
        field.set(resume, "new_uuid");
        Method method = resumeClass.getMethod("toString");
        Object result = method.invoke(resume);
        System.out.println(result);
    }
}
