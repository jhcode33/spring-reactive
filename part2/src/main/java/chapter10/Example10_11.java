package chapter10;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;


/**
 * Schedulers.newSingle() 예
 *  - 호출할 때 마다 매번 하나의 쓰레드를 새로 생성한다.
 *
 */
@Slf4j
public class Example10_11 {
    public static void main(String[] args) throws InterruptedException {
        // Non-Blocking이기 때문에 실행 순서를 장담할 수 없다
        doTask("task1")
                .subscribe(data -> log.info("# onNext: {}", data));

        doTask("task2")
                .subscribe(data -> log.info("# onNext: {}", data));

        // main thread sleep -> 프로그램이 종료됨, 생성된 모든 thread가 종료되어서 실행이 안됨
        Thread.sleep(200L);
    }

    private static Flux<Integer> doTask(String taskName) {
        return Flux.fromArray(new Integer[] {1, 3, 5, 7})
                .publishOn(Schedulers.newSingle("new-single", true))
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("# {} doOnNext filter: {}", taskName, data))
                .map(data -> data * 10)
                .doOnNext(data -> log.info("# {} doOnNext map: {}", taskName, data));
    }
}
