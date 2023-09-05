package chapter13;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;

/**
 * StepVerifier 활용 예제
 */
public class ExampleTest13_5 {
    @Test
    public void takeNumberTest() {
        Flux<Integer> source = Flux.range(0, 1000); // 0~999
        StepVerifier
                .create(GeneralTestExample.takeNumber(source, 500), // 0~499
                        StepVerifierOptions.create().scenarioName("Verify from 0 to 499"))
                .expectSubscription()
                .expectNext(0)
                .expectNextCount(498)

                // 0 ~ 499까지의 수밖에 없다
                .expectNext(500)
                .expectComplete()
                .verify();
    }
}
