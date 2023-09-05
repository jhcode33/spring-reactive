package chapter14.operator_7_split;

import chapter14.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * split 예제
 *  - groupBy() Operator
 *      - emit되는 데이터를 key를 기준으로 그룹화 한 GroupedFlux를 리턴한다.
 *      - 그룹화 된 GroupedFlux로 그룹별 작업을 할 수 있다.
 *      - 저자 명으로 된 도서의 가격
 */
@Slf4j
public class Example14_59 {
    public static void main(String[] args) {
        // List의 요소를 순회하면서 Flux<Book>를 만든다
        Flux.fromIterable(SampleData.books)

                // 만들어진 Flux<Book>를 book.getAuthorName()을 key로 해서 묶고 하나의 Flux로 반환한다
                .groupBy(book -> book.getAuthorName())

                // flatMap은 전달된 데이터를 비동기적으로 변환하고 하나의 Flux를 생성한다
                // groupedFlux : Flux<GroupedFlux<String, Book>>
                .flatMap(groupedFlux ->
                    Mono
                        // Mono<String>
                        .just(groupedFlux.key())

                        // Mono<String> + Mono<Integer>
                        .zipWith(
                            // other Mono of zipWith : Mono<Integer>, 2개 생성, 동일한 작가가 쓴 책이 2개
                            groupedFlux
                                .doOnNext(book -> log.info("# who: {}", book.getAuthorName()))
                                //"Advance Java",         "Tom", "Tom-boy", 25000, 100
                                //"Getting started Java", "Tom", "Tom-boy", 32000, 230
                                .map(book ->
                                    (int)(book.getPrice() * book.getStockQuantity() * 0.1))
                                .doOnNext(data -> log.info("# doOnNext: {}", data))
                                // book의 총 수익을 하나로 합치기 위해 reduce
                                // Flux -> Mono로 만드는 과정, map은 Flux를 반환함
                                .reduce((y1, y2) -> y1 + y2),

                            // combinator of zipWith : authorName=Mono<String>, sumRoyalty=Mono<Integer>
                            // Mono<String> : 앞에서 전달된 Mono = .just(groupedFlux.key())
                            // Mono<Integer> : .zipWith 내부에서 생성한 other Mono = groupedFlux.map().reduce()
                            (authorName, sumRoyalty) ->
                                authorName + "'s royalty: " + sumRoyalty)
                )
                .subscribe(log::info);
    }
}