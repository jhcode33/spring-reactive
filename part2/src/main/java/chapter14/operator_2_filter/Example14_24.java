package chapter14.operator_2_filter;

import chapter14.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * takeUntil 예제
 */
@Slf4j
public class Example14_24 {
    public static void main(String[] args) {
        Flux
            .fromIterable(SampleData.btcTopPricesPerYear)
            .takeUntil(tuple -> tuple.getT2() > 20_000_000)
            .subscribe(tuple -> log.info("# onNext: {}, {}",
                                            tuple.getT1(), tuple.getT2()));
    }
}
