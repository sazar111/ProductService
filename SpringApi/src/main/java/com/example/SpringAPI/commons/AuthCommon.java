package com.example.SpringAPI.commons;

import com.example.SpringAPI.dtos.ValidateTokenResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthCommon {
    public boolean validateToken(String token){
        RestTemplate restTemplate = new RestTemplate();
        ValidateTokenResponseDto validateTokenResponseDto= restTemplate.postForObject("http://localhost:8081/user/validateToken/"+token,
                null,
                ValidateTokenResponseDto.class
                );
        if(validateTokenResponseDto.getEmail()!=null){
            return true;
        }
        return false;
    }
}
