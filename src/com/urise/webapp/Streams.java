package com.urise.webapp;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.sun.javafx.fxml.expression.Expression.divide;

public class Streams {

    public static void main(String[] args) {
        int[] arr = {3, 2, 1, 5, 4};
        System.out.println(minValue(arr));
//        minValue(arr);
        List<Integer> list = Arrays.asList(9, 8, 1, 6, 2, 1, 4, 3, 2, 1, 2, 1, 3, 9, 2, 1);
        System.out.println(oddOrEven(list));

        System.out.println();
//        List<Integer> list = Arrays.asList(1, 2, 3, 4, 3, 2, 1, 5, 6, 3, 7, 5);
//        list.stream()
//                .distinct()
//                .forEach(x -> System.out.print(x));

//       int[] values(){
//            IntStream.of(values)
//                    .distinct()
//                    .sorted()
//                    .forEach(System.out::print);
//            return 0;
//        }

//        int sum = Arrays.stream(values)
//                .reduce((left, right) -> left + right).getAsInt();
//
//        List<Integer> oddOrEven (List < Integer > integers) {
//            Optional<Integer> sum1 = integers.stream().reduce((acc, x) -> acc + x)
//        }

//        Optional  <Integer> sum1 = integers.stream().reduce((acc, x) -> acc + x);
//        integers.stream().filter(x -> (x % 2) == (sum1 % 2)).distinct().collect(Collectors.toList());

//        long sum2 = integers.stream().reduce(0, (u, v) -> u + v);
//        System.out.println(integers.stream().filter(x -> (x % 2)==(sum2 % 2)).distinct().collect(Collectors.toList()));
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        System.out.println();
        int sum = integers.stream().reduce((acc, x) -> acc + x).get();
        return integers.stream().filter(x -> (x % 2) == (sum % 2)).collect(Collectors.toList());
    }

    static int minValue(int[] values) {
        return IntStream.of(values).distinct().sorted().reduce(0,(i, i1) -> 10*i+i1);
    }
}

