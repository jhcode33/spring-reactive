package chapter9;

import reactor.core.publisher.Flux;

public class GenerateHandle {

    public static String alphabet(int letterNumber) {
        if (letterNumber < 1 || letterNumber > 26) { // -1, 30
            return null;
        }
        // -1을 하는 이유는 알파벳의 인덱스가 0부터 시작하는 것이 아니라 1부터 시작하기 때문
        int letterIndexAscii = 'A' + letterNumber - 1;
        return "" + (char) letterIndexAscii;
    }

    // Flux<R> handle(BiConsumer<T, SynchronousSink<R>>);
    // BiConsumer의 functional method : void accept(T t, U u);
    public static void main(String[] args) {
        Flux<String> alphabet = Flux.just(-1, 30, 13, 9, 20)

                // 특정 조건에 부합하는 데이터만 emit할 때 대개 handle을 사용한다
                .handle((i, sink) -> {
                    String letter = alphabet(i);
                    if (letter != null)
                        sink.next(letter);
                });

        alphabet.subscribe(System.out::println);
    }
}
