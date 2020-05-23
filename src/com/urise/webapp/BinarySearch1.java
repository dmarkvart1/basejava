package com.urise.webapp;

import java.util.Arrays;

public class BinarySearch1 {
    public static void main(String[] args) {
        int[] array1 = {1, 3, 1, 4, 6, 5, 7, 8, 9, 10, 11, 12, 16};
        int pos1 = Arrays.binarySearch(array1, 1);
        int pos2 = Arrays.binarySearch(array1, 2);
        int pos3 = Arrays.binarySearch(array1, 3);
        int pos4 = Arrays.binarySearch(array1, 4);
        int pos5 = Arrays.binarySearch(array1, 5);
        int pos6 = Arrays.binarySearch(array1, 6);
        int pos7 = Arrays.binarySearch(array1, 7);
        int pos8 = Arrays.binarySearch(array1, 8);
        int pos9 = Arrays.binarySearch(array1, 9);
        int pos10 = Arrays.binarySearch(array1, 10);
        int pos11 = Arrays.binarySearch(array1, 11);
        int pos12 = Arrays.binarySearch(array1, 12);
        int pos13 = Arrays.binarySearch(array1, 13);
//        for (int elem: array1){
//            System.out.println(elem);
//        }
        System.out.println(pos1);
        System.out.println(pos2);
        System.out.println(pos3);
        System.out.println(pos4);
        System.out.println(pos5);
        System.out.println(pos6);
        System.out.println(pos7);
        System.out.println(pos8);
        System.out.println(pos9);
        System.out.println(pos10);
        System.out.println(pos11);
        System.out.println(pos12);
        System.out.println(pos13);
    }
}