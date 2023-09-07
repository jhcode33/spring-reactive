package com.itvillage.book.v2;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component("bookValidatorV2")
public class BookValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return BookDto.Post.class.isAssignableFrom(clazz);
    }

    // 유효성 검사 메소드
    @Override
    public void validate(Object target, Errors errors) {
        BookDto.Post post = (BookDto.Post) target;

        //  @NotNull  org.springframework.validation.Errors errors,
        //  String field,      // 검사 대상 필드의 이름, 값이 비어있거나, 공백 문자로만 구성되면 error를 발생시킴
        //  String errorCode
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "titleKorean", "field.required");

        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors, "titleEnglish", "field.required");
    }
}
