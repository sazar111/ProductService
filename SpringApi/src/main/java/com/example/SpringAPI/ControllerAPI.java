package com.example.SpringAPI;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample")
public class ControllerAPI {

    @GetMapping("/getHello/{name}")
    public String greet(@PathVariable("name") String name){
        return "Hello "+ name;
    }

    @GetMapping("getBye")
    public String bye(){
        return "Bye World";
    }
}
