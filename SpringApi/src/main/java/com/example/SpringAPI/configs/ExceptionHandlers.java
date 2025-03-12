package com.example.SpringAPI.configs;

import com.example.SpringAPI.Exceptions.ProductNotFoundException;
import com.example.SpringAPI.Exceptions.UnauthorizedException;
import com.example.SpringAPI.dtos.ExceptionMessageDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity handleArithmeticException(ArithmeticException e) {
        ExceptionMessageDto exceptionMessageDto = new ExceptionMessageDto();
        exceptionMessageDto.setMessage(e.getMessage());
        exceptionMessageDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        ResponseEntity<ExceptionMessageDto> responseEntity= new ResponseEntity( exceptionMessageDto,HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity handleNullPointerException(NullPointerException e) {
        ExceptionMessageDto exceptionMessageDto = new ExceptionMessageDto();
        exceptionMessageDto.setMessage(e.getMessage());
        exceptionMessageDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionMessageDto);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity handleProductNotFoundException(ProductNotFoundException e) {
        ExceptionMessageDto exceptionMessageDto = new ExceptionMessageDto();
        exceptionMessageDto.setMessage("No Product found for the id "+e.getId());
        exceptionMessageDto.setStatusCode(HttpStatus.NOT_FOUND.toString());
        ResponseEntity<ExceptionMessageDto> responseEntity= new ResponseEntity(exceptionMessageDto,HttpStatus.NOT_FOUND);
        return responseEntity;
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity handleUnauthorizedException(UnauthorizedException e) {
        ExceptionMessageDto exceptionMessageDto = new ExceptionMessageDto();
        exceptionMessageDto.setMessage("User is not authorized to perform this action or the token in invalid"+e.getMessage());
        exceptionMessageDto.setStatusCode(HttpStatus.UNAUTHORIZED.toString());
        ResponseEntity<ExceptionMessageDto> responseEntity= new ResponseEntity(exceptionMessageDto,HttpStatus.UNAUTHORIZED);
        return responseEntity;
    }

}
