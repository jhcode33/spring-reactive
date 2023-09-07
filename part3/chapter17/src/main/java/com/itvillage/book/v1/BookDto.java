package com.itvillage.book.v1;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class BookDto {
    // controller와 service 계층 간의 데이터 전송을 위한 클래스
    @Getter
    public static class Post {
        private String titleKorean;
        private String titleEnglish;
        private String description;
        private String author;
        private String isbn;
        private String publishDate;
    }

    @Getter
    public static class Patch {
        @Setter
        private long bookId;
        private String titleKorean;
        private String titleEnglish;
        private String description;
        private String author;
        private String isbn;
        private String publishDate;
    }

    @Builder
    @Getter
    public static class Response {
        private long bookId;
        private String titleKorean;
        private String titleEnglish;
        private String description;
        private String author;
        private String isbn;
        private String publishDate;
    }
}
