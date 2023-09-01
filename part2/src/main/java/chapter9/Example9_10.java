package chapter9;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import static reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST;

/**
 * Sinks.Many 예제
 *  - replay()를 사용하여 이미 emit된 데이터 중에서 특정 개수의 최신 데이터만 전달하는 예제
 */
@Slf4j
public class Example9_10 {
    public static void main(String[] args) {
        /* limit() 메서드는 지정된 특성을 가진 Sinks.Many의 동작을 구성하는 데 사용됩니다.
            여기서 메서드의 각 부분이 설명 내용과 어떤 의미를 가지는지 살펴보겠습니다:

            Multicast(멀티캐스트): 이 특성은 Sinks.Many가 여러 개의 구독자에게 신호를 브로드캐스트할 수 있는 능력을 나타냅니다.
            Without Subscriber(구독자 없이): 이것은 구독자가 없을 때에도 싱크가 일정한 수의 요소(최대 historySize까지)를 기억하고 저장할 수 있다는 것을 의미합니다. 이것은 데이터의 이력을 유지하고, 활성 구독자가 없어도 데이터가 즉시 삭제되지 않도록 보장합니다.
            Backpressure(백프레셔): 싱크는 각 구독자의 다운스트림 요구를 존중합니다. 다시 말해, 구독자가 데이터를 요청하는 속도를 존중하며, 구독자가 데이터를 처리할 수 있는 속도를 초과하여 데이터를 제공하지 않습니다.
            Replaying(재생): 새 구독자가 싱크에 참여할 때, 이전에 푸시된 요소 중 일부(최대 historySize까지)를 재생할 수 있음을 의미합니다. 이렇게 하면 늦게 참여하는 구독자도 일부 이전 데이터를 수신할 수 있습니다.

            limit(historySize): 이 메서드는 재생될 수 있는 최대 요소 수를 설정합니다.
            historySize 매개변수는 엄격히 양수여야 합니다.
            이 매개변수는 싱크가 새로운 구독자에게 재생하고 기억할 수 있는 이전 요소의 수를 결정합니다.
            예를 들어, limit(10)을 사용하면 싱크가 새로운 구독자가 참여할 때 기억하고
            최대 10개의 이전 요소를 재생할 수 있음을 의미합니다. 이 한계를 초과하는 이전 요소는 삭제됩니다.

            만약 이력 크기를 제한하는 효과를 얻고 싶다면,
            Duration 기반의 limit(Duration.ZERO)를 사용하여 이력 요소를 재생하지 않도록 설정할 수 있습니다.

            요약하면, limit() 메서드는 새로운 구독자에게 재생될 수 있는 이전 요소의 최대 수를 제어하며, 메모리를 효율적으로 관리하기 위해 이전 요소가 삭제되도록 보장합니다.
         */

        // replaying: up to historySize elements pushed to this sink are replayed to new subscribers.
        // Older elements are discarded.
        // 최신 2개의 요소만 재생(replay)되도록 구성한다
        Sinks.Many<Integer> replaySink = Sinks.many().replay().limit(2);
        Flux<Integer> fluxView = replaySink.asFlux();

        replaySink.emitNext(1, FAIL_FAST);
        replaySink.emitNext(2, FAIL_FAST);
        replaySink.emitNext(3, FAIL_FAST);

        // 최대 2의 데이터만 가질 수 있기 때문에 새로운 데이터가 emit되면 오래된 데이터는 drop한다
        // 새로운 구독자가 생기면 새로운 데이터에 대해서 다시 emit한다?
        fluxView.subscribe(data -> log.info("# Subscriber1: {}", data));

        // Subscriber1은 이미 구독 중이며 fluxView로부터 스트림을 관찰하고 있으므로 새로 발행된 값 4도 수신
        replaySink.emitNext(4, FAIL_FAST);

        fluxView.subscribe(data -> log.info("# Subscriber2: {}", data));
    }
}
