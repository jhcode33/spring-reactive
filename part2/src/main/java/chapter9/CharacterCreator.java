package chapter9;

import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Consumer;

public class CharacterCreator {
    public Consumer<List<Character>> consumer; // consumer -> captured variable

    public Flux<Character> createCharacterSequence() {
        // multi-thread, async
        return Flux.create(sink -> CharacterCreator.this.consumer =
                                            // accept() method body
                                            // items = sequence1 and sequence2
                                            items -> items.forEach(sink::next));
    }
}