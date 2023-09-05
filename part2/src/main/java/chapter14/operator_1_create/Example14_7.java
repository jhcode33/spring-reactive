package chapter14.operator_1_create;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * delayElement 예제
 */
@Slf4j
public class Example14_7 {
    public static void main(String[] args) throws InterruptedException {
        log.info("# start: {}", LocalDateTime.now());
        Mono
            .just("Hello")
            // 지연
            .delayElement(Duration.ofSeconds(3))

            // sayDefault() 메소드는 파라미터로 전달되는 시점에서 평가(Eager Evaluation)
            // 따라서 sayDefault()가 먼저 수행된다
            .switchIfEmpty(sayDefault()) //Eager Evaluation

            // 실제 Mono가 비어있을 경우에만 호출되는 코드로 만들려면 아래와 같이 해야한다
//            .switchIfEmpty(Mono.defer(() -> sayDefault())) // Lazy Evaluation
            .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(3500);
    }

    private static Mono<String> sayDefault() {
        log.info("# Say Hi");
        return Mono.just("Hi");
    }
}
