package com.itvillage.book.v1;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

// MapStruct를 사용해 자동으로 매필 구현체를 생성하는 인터페이스
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        implementationName = "bookMapperV1")
public interface BookMapper {
    Book bookPostToBook(BookDto.Post requestBody);
    Book bookPatchToBook(BookDto.Patch requestBody);
    BookDto.Response bookToResponse(Book book);
    List<BookDto.Response> booksToResponse(List<Book> books);
}
