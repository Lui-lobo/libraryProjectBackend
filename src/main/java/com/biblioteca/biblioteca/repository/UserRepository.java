package com.biblioteca.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.biblioteca.biblioteca.model.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    // Verifica se já existe um usuário com o e-mail fornecido
    boolean existsByEmail(String email);

    List<User> findByRole(String role);
}
