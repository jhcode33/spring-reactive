package com.itvillage;

import com.itvillage.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalTime;

/**
 * 본사 API 서버에 도서 정보를 요청하는 검색용 클라이언트 PC 역할을 한다.
 */
@Slf4j
@SpringBootApplication
public class SpringReactiveHeadOfficeApplication {
	private URI baseUri = UriComponentsBuilder.newInstance().scheme("http")
			.host("localhost")
			.port(6060)
			.path("/v1/books")
			.build()
			.encode()
			.toUri();
	public static void main(String[] args) {
		System.setProperty("reactor.netty.ioWorkerCount", "1");
		SpringApplication.run(SpringReactiveHeadOfficeApplication.class, args);
	}

	@Bean
	public CommandLineRunner run() {
		return (String... args) -> {
			log.info("# 요청 시작 시간: {}", LocalTime.now());

			for (int i = 1; i <= 5; i++) {
				int a = i;
				this.getBook(i)
					.subscribe(
							book -> {
								// 전달 받은 도서를 처리.
								log.info("{}: book name: {}",
										LocalTime.now(), book.getName());

							}
					);
				// 요청한 작업이 완료되지 않아도 다른 작업 수행 가능 -> Non-blocking
				log.info("# 요청 도착 시간: {}", LocalTime.now());

				// 병렬 요청 보내기
				// 비동기이기 때문에 작업의 순서가 보장되지 않음, 병렬 처리 가능
				// 요청했던 작업들이 5초가 지나면 거의 동시 다시 돌아옴
				this.getBook(i)
						.subscribe(
								//subscriber
								book -> {
									log.info("{}: book name: {}",
											LocalTime.now(), book.getName());
								}
						);
				}
		};
	}

	private Mono<Book> getBook(long bookId) {
		URI getBooksUri = UriComponentsBuilder.fromUri(baseUri)
				.path("/{book-id}")
				.build()
				.expand(bookId)
				.encode()
				.toUri(); // http://localhost:6060/v1/books/{book-id}

		return WebClient.create()
				.get()
				.uri(getBooksUri)
				.retrieve()
				.bodyToMono(Book.class);
	}
}
