package com.urise.webapp;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;


public class MainFile extends SimpleFileVisitor<Path> {
//    public FileVisitResult visitFile(Path path,
//                                     BasicFileAttributes fileAttributes) {
//        System.out.println(" " + path.getFileName());
//        return FileVisitResult.CONTINUE;
//    }
//
//    public FileVisitResult preVisitDirectory(Path path,
//                                             BasicFileAttributes fileAttributes) {
//        System.out.println(" " + path.getFileName().toString().toUpperCase());
//        return FileVisitResult.CONTINUE;
//    }
//}
//
//class ReadCatalog {
//    public static void main(String[] args) {
//        Path filePath = Paths.get("D:\\test\\basejava\\src");
//        try {
//            Files.walkFileTree(filePath, new MainFile());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}

    private static List<File> files = new ArrayList<>();


    public static void main(String[] args) throws IOException {
//        String filePath = "./.gitignore";
        getSubFiles(files, new File("D:\\test\\basejava\\src"));
        StringBuilder sb = new StringBuilder();
        for (Object file : files.toArray()) {

            if (((File) file).isDirectory()) {
                System.out.println(sb.append(" ") + ((File) file).getName().toUpperCase());
            } else {
                System.out.println(((File) file).getName());
            }
        }


// Если возникла какая-нибудь ошибка при доступе к файлу,
// то выводим эту ошибку.
// Если вы не переопределите этот метод, и возникнет
// ошибка, то бросится исключение IOException

//        File file = new File(filePath);
//        try {
//            System.out.println(file.getCanonicalPath());
//        } catch (IOException e) {
//            throw new RuntimeException("Error:", e);
//        }
//
//        File dir = new File("D:\\test\\basejava");
//        System.out.println(dir.isDirectory());
//        String[] list = dir.list();
//        if (list != null) {
//            for (String name : list) {
//                System.out.println(name);
//            }
//        }
//        try (FileInputStream fis = new FileInputStream(filePath)) {
//            System.out.println(fis.read());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

       private static void getSubFiles (List < File > source, File parent){
            List<File> dir = new ArrayList<>();
            if (!source.contains(parent)) {
                source.add(parent);
            }
            File[] listFiles = parent.listFiles();
            if (listFiles == null) {
                return;
            }
            for (File file : listFiles) {
                if (file.isDirectory()) {
                    dir.add(file);
                } else {
                    if (!source.contains(file)) {
                        source.add(file);
                    }
                }
            }
            for (File file : dir) {
                getSubFiles(source, file);
            }
        }
    }





