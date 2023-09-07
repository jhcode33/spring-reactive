package com.itvillage.book.v4;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.reactive.function.server.RouterFunction;

import javax.validation.Validator;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static reactor.core.publisher.Mono.just;

@Configuration("bookRouterV4")
public class BookRouter {
    @Bean
    public RouterFunction<?> routeBookV4(BookHandler handler) {
        return route()
                // Builder POST(String pattern, HandlerFunction<ServerResponse> handlerFunction);
                // Mono<T> handle(ServerRequest request); <- defined by HandlerFunction interface
                // handler::createBook have implemented for handle method
                .POST("/v4/books", handler::createBook)
                .PATCH("/v4/books/{book-id}", handler::updateBook)
                .GET("/v4/books", handler::getBooks)
                .GET("/v4/books/{book-id}", handler::getBook)
                .build();
    }

    @Bean
    public RouterFunction<?> helloRuterFunction() {
        return route(GET("/hello"),
                request -> ok().body(just("Hello World!"), String.class))
                .andRoute(GET("/bye"),
                        request -> ok().body(just("See ya!"), String.class));
    }

    @Bean
    public Validator javaxValidator() {
        return new LocalValidatorFactoryBean();
    }
}
