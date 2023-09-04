package chapter10;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * publishOn() 기본 예제
 *  - Operator 체인에서 Downstream Operator의 실행을 위한 쓰레드를 지정한다.
 */
@Slf4j
public class Example10_2 {
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[] {1, 3, 5, 7})

                // main Thread
                // hook method
                .doOnNext(data -> log.info("# doOnNext: {}", data))

                // main Thread : 1회
                .doOnSubscribe(subscription -> log.info("# doOnSubscribe"))

                // onNext, onComplete, onError를 실행할 때
                // thread를 생성하여 코드들이 해당 thread에서 실행되도록 한다
                .publishOn(Schedulers.parallel())
                .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(500L);
    }
}
