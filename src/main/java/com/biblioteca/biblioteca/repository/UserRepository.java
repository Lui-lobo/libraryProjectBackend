package com.biblioteca.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biblioteca.biblioteca.model.User;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    // Verifica se já existe um usuário com o e-mail fornecido
    boolean existsByEmail(String email);

    List<User> findByRole(String role);

    List<User> findByNameContainingIgnoreCase(String name);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmailWithOptional(@Param("email") String email);
}
