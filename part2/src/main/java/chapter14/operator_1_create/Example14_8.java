package chapter14.operator_1_create;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * using 예제
 */
@Slf4j
public class Example14_8 {
    public static void main(String[] args) {
        Path path = Paths.get("C:\\Users\\jhcode33\\ProjectAll\\intellij\\Spring-Reactive\\part2\\src\\main\\java\\chapter14\\operator_1_create\\using_example.txt");

        Flux
            .using(() -> Files.lines(path), // resource create
                    Flux::fromStream,       // resource use
                    Stream::close)          // resource close
            .subscribe(log::info);
    }
}
