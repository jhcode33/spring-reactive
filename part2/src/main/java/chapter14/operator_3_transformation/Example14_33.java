package chapter14.operator_3_transformation;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * merge 예제
 */
@Slf4j
public class Example14_33 {
    public static void main(String[] args) throws InterruptedException {
        Flux
            // 하나의 datastream으로 data가 통합되어 emit된다
            .merge(
                    Flux.just(1, 2, 3, 4).delayElements(Duration.ofMillis(300L)),
                    Flux.just(5, 6, 7).delayElements(Duration.ofMillis(500L))
            )
            .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(2000L);
    }
}