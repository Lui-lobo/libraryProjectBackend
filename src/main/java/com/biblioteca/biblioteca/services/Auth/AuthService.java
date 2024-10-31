package com.biblioteca.biblioteca.services.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca.model.User;
import com.biblioteca.biblioteca.repository.UserRepository;

@Service
public class AuthService {
       @Autowired
    private UserRepository userRepository;

    public User login(String email, String senha) {
        User usuario = userRepository.findByEmail(email);

        if (usuario == null || !senha.equals(usuario.getPassword().trim())) {
            throw new IllegalArgumentException("E-mail ou senha incorretos");
        }

        return usuario;
    }
}
