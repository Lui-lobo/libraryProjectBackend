package com.biblioteca.biblioteca;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class biblioteca {
    
    @RequestMapping("/biblioteca")
    public String hello() {
        return "Hello world";
    }

}
