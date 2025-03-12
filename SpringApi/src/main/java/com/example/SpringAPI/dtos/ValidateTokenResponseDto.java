package com.example.SpringAPI.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ValidateTokenResponseDto {
    Date createdDate;
    String value;
    Date expiryDate;
    String email;
}
