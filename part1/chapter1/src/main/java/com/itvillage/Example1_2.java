package com.itvillage;

import java.util.Arrays;
import java.util.List;

public class Example1_2 {
    public static void main(String[] args) {
        // stream으로 처리하면 병렬 처리 가능
        List<Integer> numbers = Arrays.asList(1, 3, 21, 10, 8, 11); // datasource(처리해야할 데이터)
        // numbers.parallelStream() 병렬 스트림, 파티셔닝이 이루어짐. 기본적으로 단일 스트림
        int sum = numbers.stream()

                // 중간 연산
                .filter(number -> number > 6 && (number % 2 != 0))
                .mapToInt(number -> number)

                // 종단 연산
                .sum();

        System.out.println("합계: " + sum);
    }
}
