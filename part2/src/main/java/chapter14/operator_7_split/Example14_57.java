package chapter14.operator_7_split;

import chapter14.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * split 예제
 *  - groupBy(keyMapper) Operator
 *      - emit되는 데이터를 key를 기준으로 그룹화 한 GroupedFlux를 리턴한다.
 *      - 그룹화 된 GroupedFlux로 그룹별 작업을 할 수 있다.
 */
@Slf4j
public class Example14_57 {
    public static void main(String[] args) {
        Flux.fromIterable(SampleData.books)
                // data의 AuthorName을 key로 하여 동일한 key를 가진 데이터를 group으로 묶는다
                .groupBy(book -> book.getAuthorName())
                .flatMap(groupedFlux ->
                        groupedFlux
                                .map(book -> book.getBookName() +
                                        "(" + book.getAuthorName() + ")")
                                // Flux<map>으로 반환된 값을 list로 모아서 전달한다
                                .collectList()
                )
                .subscribe(bookByAuthor ->
                        log.info("# book by author: {}", bookByAuthor));
    }
}