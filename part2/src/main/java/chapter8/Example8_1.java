package chapter8;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

/**
 * doOnXXXX 예제
 *  - doOnXXXX() Operator의 호출 시점을 알 수 있다.
 */
@Slf4j
public class Example8_1 {
    public static void main(String[] args) {
        Flux.range(1, 5)
            .doOnRequest(data -> log.info("# doOnRequest: {}", data))
                /*
                    A simple base class for a Subscriber implementation
                    that lets the user perform a request(long) and cancel() on it directly. -> subscription
                 */
            .subscribe(new BaseSubscriber<Integer>() {

                // 1번만 호출
                @Override
                protected void hookOnSubscribe(Subscription subscription) {
                    request(1);
                }

                @SneakyThrows
                @Override
                protected void hookOnNext(Integer value) {
                    Thread.sleep(2000L);
                    log.info("# hookOnNext: {}", value);
                    // You can call request(long) here to further request data from the source
                    // 직접적으로 request를 호출할 수 있기 때문에 데이터의 양을 조절할 수 있음
                    // backpressure
                    request(1);
                }
            });
    }
}

