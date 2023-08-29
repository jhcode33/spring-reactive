package com.itvillage;

import reactor.core.publisher.Flux;

public class Example2_5 {
    public static void main(String[] args) {
        Flux
                // 데이터를 변환하는 과정 전체를 sequence
                .just(1, 2, 3, 4, 5, 6) // datasource
                .filter(n -> n % 2 == 0)       // operator
                .map(n -> n * 2)
                .subscribe(System.out::println);
    }
}
