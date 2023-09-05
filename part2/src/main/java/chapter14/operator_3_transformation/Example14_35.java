package chapter14.operator_3_transformation;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * zip 예제
 */
@Slf4j
public class Example14_35 {
    public static void main(String[] args) throws InterruptedException {
        Flux
            // 두 개의 flux를 merge해서 Tuple2 데이터 타입의 Flux를 생성함
            .zip(
                    Flux.just(1, 2, 3).delayElements(Duration.ofMillis(300L)),
                    Flux.just(4, 5, 6).delayElements(Duration.ofMillis(500L))
            )
            .subscribe(tuple2 -> log.info("# onNext: {}", tuple2));

        Thread.sleep(2500L);
    }
}