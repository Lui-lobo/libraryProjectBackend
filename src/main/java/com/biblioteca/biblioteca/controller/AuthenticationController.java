package com.biblioteca.biblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.biblioteca.model.User;
import com.biblioteca.biblioteca.services.Auth.AuthService;

@RestController()
@RequestMapping("/api/auth")
public class AuthenticationController {
     @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        try {
            User usuario = authService.login(email, password);
            return ResponseEntity.ok(usuario); // Retorna o usuário autenticado
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body("Credenciais invalidas"); // Credenciais inválidas
        }
    }
}
