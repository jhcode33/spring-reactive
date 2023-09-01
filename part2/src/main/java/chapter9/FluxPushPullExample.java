package chapter9;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class FluxPushPullExample {

    // 메시지 리스너 인터페이스 정의
    interface MyMessageListener<T> {
        void onMessage(List<T> messages);
    }

    // 가상의 메시지 프로세서
    static class MyMessageProcessor {
        private MyMessageListener<String> listener;

        public void register(MyMessageListener<String> listener) {
            this.listener = listener;
        }

        // 메시지의 시뮬레이션 메서드
        public List<String> getHistory(long n) {
            return IntStream.range(0, (int) n)
                    .mapToObj(i -> "History-" + i)
                    .collect(Collectors.toList());
        }

        // 실제 메시지 발송 메서드 (예시)
        public void dispatchMessages() {
            listener.onMessage(List.of("msg1", "msg2", "msg3"));
        }
    }

    public static void main(String[] args) {
        MyMessageProcessor myMessageProcessor = new MyMessageProcessor();

        // push
        Flux<String> bridge = Flux.create(sink -> {
            // register MyMessageListener
            myMessageProcessor.register(messages -> {
                log.info("called onMessage");

                for (String s : messages) {
                    // push
                    sink.next(s); // The remaining messages that arrive asynchronously later are also delivered.
                }
            });

            // pull : 데이터를 요청함
            sink.onRequest(n -> { // 'n' is request method of Subscription to pull : 데이터를 요청함
                if (n == Long.MAX_VALUE) {
                    log.warn("Unlimited request detected. Limiting to 10 messages.");
                    n = 10; // or another reasonable limit
                }

                /** Polling : Computer Science 용어
                 *  - 컴퓨터나 제어 장치가 외부 장치가 준비 상태나 상태를 확인할 때까지 기다리는 프로세스를 말한다
                 *    클라이언트 프로그램(MessageProcessor)이 동기 활동으로 외부 장치의 상태(MessageListener)를
                 *    적극적으로 확인하는 것을 의미
                 *  - 여기서 poll도 동일한 의미로 쓰인 것으로 보인다
                 */
                // call
                List<String> messages = myMessageProcessor.getHistory(n); // Poll for messages when requests are made.
                for (String s : messages) {
                    log.info("onRequest: message = {}", s);
                    // push
                    sink.next(s); // If messages are available immediately, push them to the sink.
                }
            });
        });

        bridge.subscribe(
                data -> System.out.println("Received: " + data),
                err -> System.err.println("Error: " + err.getMessage()),
                () -> System.out.println("Stream completed")
        );

        // 메시지 발송 시뮬레이션
        // 비동기로 처리할 수 없음, push & call 방식 중 하나만 사용 가능
//        myMessageProcessor.dispatchMessages();
    }
}
