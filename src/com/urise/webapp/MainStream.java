package com.urise.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MainStream {
    public static void main(String[] args) {
        MainStream mainStream = new MainStream();
        int[] array = {1, 2, 3, 3, 2, 3};
        System.out.println(mainStream.minValue(array));
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        System.out.println(mainStream.oddOrEven(list));
    }

    private int minValue(int[] values) {
        return IntStream.of(values).distinct().sorted().reduce(0, (a, b) -> 10 * a + b);
    }

    private List<Integer> oddOrEven(List<Integer> integers) {
        Stream<Integer> stream = integers.stream();
        if (stream.reduce(0, Integer::sum) % 2 == 0) {
            return stream.filter(x -> x % 2 != 0).collect(Collectors.toList());
        }
        return  stream.filter(x -> x % 2 == 0).collect(Collectors.toList());
    }

}