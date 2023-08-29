package chapter6;


import reactor.core.publisher.Mono;

/**
 * Mono 기본 개념 예제
 *  - 원본 데이터의 emit 없이 onComplete signal 만 emit 한다.
 */
public class Example6_2 {
    public static void main(String[] args) {
        // Mono는 데이터가 없을 수도 있다
        Mono
            .empty()
            .subscribe(
                    // onNext(), 데이터가 없으면 onNext()를 호출하지 않는다
                    none -> System.out.println("# emitted onNext signal"),

                    // onError(), 데이터 전송 중 오류가 아니기 때문에 Error도 호출하지 않는다
                    error -> System.out.println("# emitted onError signal"),

                    // onComplete(), 전송한 데이터가 없기 때문에 완료라고 뜬다
                    () -> System.out.println("# emitted onComplete signal")
            );
    }
}
