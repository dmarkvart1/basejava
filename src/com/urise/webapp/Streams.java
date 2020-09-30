package com.urise.webapp;


import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Streams {
    public static void main(String[] args) {
        int[] arr = {1, 2, 1, 4, 3, 2, 1, 2, 1, 3, 2, 1};
        minValue(arr);

        List<Integer> list = Arrays.asList(1, 2, 1, 4, 3, 2, 1, 2, 1, 3, 2, 2);
        oddOrEven(list);

//        List<Integer> list = Arrays.asList(1, 2, 3, 4, 3, 2, 1, 5, 6, 3, 7, 5);
//        list.stream()
//                .distinct()
//                .forEach(x -> System.out.print(x));
    }

    private static void minValue(int[] values) {
        IntStream.of(values)
                .distinct()
                .sorted()
                .forEach(System.out::print);
        System.out.println();
    }

    private static void oddOrEven(List<Integer> integers) {
        Optional<Integer> sum1 = integers.stream().reduce((acc, x) -> acc + x);
        System.out.println(sum1);
//        integers.stream().filter(x -> (x % 2) == (sum1 % 2)).distinct().collect(Collectors.toList());

        long sum2 = integers.stream().reduce(0, (u, v) -> u + v);
        System.out.println(integers.stream().filter(x -> (x % 2)==(sum2 % 2)).distinct().collect(Collectors.toList()));

    }

}
