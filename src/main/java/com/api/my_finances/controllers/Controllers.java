package com.api.my_finances.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controllers {
    @GetMapping("/api/transacoes")
    public ResponseEntity<String> getData() {
        // Simula a obtenção de dados
        String data = "Dados de exemplo";
        return ResponseEntity.ok(data);
    }

    @PostMapping("/api/transacoes")
    public ResponseEntity<String> postData(@RequestBody String data) {
        // Simula o envio de dados
        return ResponseEntity.ok("Dados recebidos: " + data);
    }
}
