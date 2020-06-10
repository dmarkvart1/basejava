package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MainCollections {
    private static final String UUID_1 = "UUID_1";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final String UUID_2 = "UUID_2";
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final String UUID_3 = "UUID_3";
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final String UUID_4 = "UUID_4";
    private static final Resume RESUME_4 = new Resume(UUID_4);

    public static void main(String[] args) {
        Collection<Resume> collection = new ArrayList<>();
        collection.add(RESUME_1);
        collection.add(RESUME_2);
        collection.add(RESUME_3);
        collection.add(RESUME_4);


//        for (Resume resume : collection) {
//            System.out.println(resume);
//            if (resume.getUuid() == UUID_1) {
////                collection.remove(resume);
//            }
//        }
        Iterator<Resume> iterator = collection.iterator();
        while (iterator.hasNext()) {
            Resume resume = iterator.next();
            System.out.println(resume);
            if (resume.getUuid() == UUID_1) {
                iterator.remove();
            }
        }
        System.out.println(collection.toString());

        Map<String, Resume> map = new HashMap<>();
        map.put(UUID_1, RESUME_1);
        map.put(UUID_2, RESUME_2);
        map.put(UUID_3, RESUME_3);
        map.put(UUID_4, RESUME_4);

//        for (String uuid : map.keySet()) {
//            System.out.println(map.get(uuid)); //не оптимальный вариант выборки данных. Так как первая операция получение резюме и вторая операция вывод информации по резюме
//        }
//        for (Map.Entry<String, Resume> entry : map.entrySet()) {
//            System.out.println(entry.getValue()); //оптимальный вариант
//        }
        List<Resume> resumes = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        resumes.remove(1);
            System.out.println(resumes);
    }
}
