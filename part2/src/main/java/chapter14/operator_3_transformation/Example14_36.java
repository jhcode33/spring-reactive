package chapter14.operator_3_transformation;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * zip 예제
 */
@Slf4j
public class Example14_36 {
    public static void main(String[] args) throws InterruptedException {
        Flux
            // Tuple2 데이터 타입의 Flux를 생성할 때 각각의 Flux의 요소를 combinator 진행해서 생성함
            .zip(
                    Flux.just(1, 2, 3).delayElements(Duration.ofMillis(300L)),
                    Flux.just(4, 5, 6).delayElements(Duration.ofMillis(500L)),
                    (n1, n2) -> n1 * n2
            )
            .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(2500L);
    }
}