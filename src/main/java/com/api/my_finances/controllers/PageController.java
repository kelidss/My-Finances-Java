package com.api.my_finances.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/")
    public String index() {
        // Verificar o token JWT ou autenticação
        return "index"; // O Spring Boot automaticamente procurará por "index.html" na pasta src/main/resources/static
    }

    @RequestMapping("/login")
    public String login() {
        // Verificar o token JWT ou autenticação
        return "login"; // O Spring Boot automaticamente procurará por "login.html" na pasta src/main/resources/static
    }
}
