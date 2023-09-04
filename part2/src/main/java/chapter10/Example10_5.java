package chapter10;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;


/**
 * subscribeOn()과 publishOn()의 동작 과정 예
 *  - subscribeOn()과 publishOn()을 사용하지 않은 경우
 *      - Sequence의 Operator 체인에서 최초의 쓰레드는 subscribe()가
 *        호출되는 scope에 있는 쓰레드이다.
 */
@Slf4j
public class Example10_5 {
    public static void main(String[] args) {
        Flux
            .fromArray(new Integer[] {1, 3, 5, 7})
            // doOnNext()는 데이터 스트림의 요소를 수정하지 않고도
            // 해당 요소를 관찰하거나 디버깅하고, 메트릭 수집 및 기타 부작용을 수행하는 데 사용되는 연산자
            .doOnNext(data -> log.info("# doOnNext fromArray: {}", data))
            .filter(data -> data > 3)
            .doOnNext(data -> log.info("# doOnNext filter: {}", data))
            .map(data -> data * 10)
            .doOnNext(data -> log.info("# doOnNext map: {}", data))
            .subscribe(data -> log.info("# onNext: {}", data));
    }
}
