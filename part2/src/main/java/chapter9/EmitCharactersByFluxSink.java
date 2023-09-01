package chapter9;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class EmitCharactersByFluxSink {

    public static void main(String[] args) {
        CharacterGenerator characterGenerator = new CharacterGenerator();

        // a b c -> Flux<Character> -> List<Character>, bloking
        List<Character> sequence1 = characterGenerator.generateCharacters().take(3).collectList().block();

        // a b -> Flux<Character> -> List<Character>, bloking
        // block() mehtod return T, T = Mono<List<Character> 의 List<Character>이다.
        List<Character> sequence2 = characterGenerator.generateCharacters().take(2).collectList().block();

        log.info("데이터 생성");
        log.info("sequence1: {}", sequence1);
        log.info("sequence2: {}", sequence2);


        CharacterCreator characterCreator = new CharacterCreator();

        // 각각의 Thread에서 CharacterCreator의 accept() 메소드 실행
        // 데이터를 CharacterCreator 객체의 consumer 변수가 List<Character> 데이터를 받음
        Thread producerThread1 = new Thread(
                // parameter target
                // Runnable interface의 run method의 body
                () -> characterCreator.consumer.accept(sequence1));

        Thread producerThread2 = new Thread(() -> characterCreator.consumer.accept(sequence2));

        log.info("데이터 구독");
        List<Character> consolidated = new ArrayList<>();

        // 원래 구독은 Flux 메소드를 구현한 객체를 구독하는데
        // createCharacterSequence() 메소드로 반환된 Flux 객체를 구독함
        // onNext()의 구현으로 Method reference, List의 add 메소드를 활용
        characterCreator.createCharacterSequence().subscribe(consolidated::add);

        try {
            producerThread1.start();
            producerThread2.start();
            producerThread1.join();
            producerThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("consolidated: {}", consolidated);

    }
}
