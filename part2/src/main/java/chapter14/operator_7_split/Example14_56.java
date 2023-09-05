package chapter14.operator_7_split;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * split 예제
 *  - bufferTimeout(maxSize, maxTime) Operator
 *      - Upstream에서 emit되는 첫 번째 데이터부터 maxSize 숫자 만큼의 데이터 또는 maxTime 내에 emit된 데이터를 List 버퍼로 한번에 emit한다.
 *      - maxSize나 maxTime에서 먼저 조건에 부합할때까지 emit된 데이터를 List 버퍼로 emit한다.
 *      - 마지막 버퍼가 포함하는 데이터는 maxSize보다 작거나 같다.
 */
@Slf4j
public class Example14_56 {
    public static void main(String[] args) {
        Flux
            .range(1, 20)
            .map(num -> {
                try {
                    if (num < 10) {
                        // 10보다 작은 수는 100 millisecond 만큼 지연된다
                        Thread.sleep(100L);
                    } else {
                        // 10 이상의 수는 300 millisecond 만큼 지연된다
                        Thread.sleep(300L);
                    }
                } catch (InterruptedException e) {}
                return num;
            })
            .bufferTimeout(3, Duration.ofMillis(400L))
            .elapsed()
            .subscribe(buffer -> log.info("# onNext: {}, # time: {}", buffer.getT2(), buffer.getT1()));
    }
}