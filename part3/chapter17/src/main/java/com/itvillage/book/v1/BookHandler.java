package com.itvillage.book.v1;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@Component("bookHandlerV1")
public class BookHandler {
    private final BookMapper mapper;

    public BookHandler(BookMapper mapper) {
        this.mapper = mapper;
    }

    // ServerRequest : headers & body of HTTP Request
    public Mono<ServerResponse> createBook(ServerRequest request) {
        return
                // request body를 통해서 Mono 생성
                request.bodyToMono(BookDto.Post.class)

                .map(post -> mapper.bookPostToBook(post))
                .flatMap(book ->
                        ServerResponse
                                .created(URI.create("/v1/books/" + book.getBookId())) // "/v1/books/{bookId}
                                .build()); // create Mono<ServerResponse>
    }

    public Mono<ServerResponse> getBook(ServerRequest request) {
        // pathVariable : 동일한 key의 value를 반환
        long bookId = Long.valueOf(request.pathVariable("book-id"));
        Book book =
                new Book(bookId,
                        "Java 고급",
                        "Advanced Java",
                        "Kevin",
                        "111-11-1111-111-1",
                        "Java 중급 프로그래밍 마스터",
                        "2022-03-22",
                        LocalDateTime.now(),
                        LocalDateTime.now());
        return ServerResponse
                            .ok()
                            .bodyValue(mapper.bookToResponse(book))
                            // Mono의 데이터가 비어있을 경우 -> ServerResponse 404 생성
                            .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateBook(ServerRequest request) {
        final long bookId = Long.valueOf(request.pathVariable("book-id"));
        return request
                .bodyToMono(BookDto.Patch.class)
                .map(patch -> {
                    patch.setBookId(bookId);
                    return mapper.bookPatchToBook(patch);
                })
                .flatMap(book -> ServerResponse.ok()
                        // bodyValue : return Mono<ServerResponse>
                        .bodyValue(mapper.bookToResponse(book)));
    }

    public Mono<ServerResponse> getBooks(ServerRequest request) {
        List<Book> books = List.of(
                new Book(1L,
                        "Java 고급",
                        "Advanced Java",
                        "Kevin",
                        "111-11-1111-111-1",
                        "Java 중급 프로그래밍 마스터",
                        "2022-03-22",
                        LocalDateTime.now(),
                        LocalDateTime.now()),
                new Book(2L,
                        "Kotlin 고급",
                        "Advanced Kotlin",
                        "Kevin",
                        "222-22-2222-222-2",
                        "Kotlin 중급 프로그래밍 마스터",
                        "2022-05-22",
                        LocalDateTime.now(),
                        LocalDateTime.now())
        );
        return ServerResponse
                .ok()
                .bodyValue(mapper.booksToResponse(books));
    }
}
