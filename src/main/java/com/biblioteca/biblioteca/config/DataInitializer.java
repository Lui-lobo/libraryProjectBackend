package com.biblioteca.biblioteca.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Inicializando um usuário admin quando não haver um no banco
import com.biblioteca.biblioteca.repository.UserRepository;
import com.biblioteca.biblioteca.model.Administrator;

@Configuration
public class DataInitializer {

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            // Verifica se existe algum administrador no banco de dados
            if (userRepository.findByRole("Administrador").isEmpty()) {
                // Cria o administrador padrão
                Administrator admin = new Administrator(
                    "Admin",
                    "admin@biblioteca.com",
                    "admin123",
                    "Total"
                );

                userRepository.save(admin);
                System.out.println("Administrador padrão criado.");
            }
        };
    }
}
