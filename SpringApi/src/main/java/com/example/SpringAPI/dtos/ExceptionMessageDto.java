package com.example.SpringAPI.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionMessageDto {
    String statusCode;
    String message;
}
