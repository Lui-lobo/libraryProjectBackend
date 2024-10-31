package com.biblioteca.biblioteca.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
// Utilitários do springBoot
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.biblioteca.biblioteca.model.Customer;
import com.biblioteca.biblioteca.model.Employee;
import com.biblioteca.biblioteca.model.User;
// Importando serviços
import com.biblioteca.biblioteca.services.User.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Administradores podem criar funcionários
    @PostMapping("/admin/createEmployee")
    public ResponseEntity<?> createEmployee(
            @RequestParam Long adminId,
            @RequestBody Employee funcionario) {

        // Verifica se o usuário que está tentando criar é um Administrador
        if (userService.validateRole(adminId, "Administrador")) {
            try {
                Employee novoFuncionario = userService.createEmployee(funcionario);
                return ResponseEntity.ok(novoFuncionario);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(400).body(e.getMessage());
            }
        } else {
            return ResponseEntity.status(403).body("Função insuficiente para está ação"); // 403 Forbidden se não for
                                                                                          // Administrador
        }
    }

    @PostMapping("/createCustomer")
    public ResponseEntity<?> createCustomer(
            @RequestParam Long userId, // ID do usuário que está criando o cliente (pode ser Funcionario ou
                                       // Administrador)
            @RequestBody Customer cliente) {

        // Verifica se o usuário que está tentando criar é um Administrador ou
        // Funcionário
        if (userService.validateRole(userId, "Funcionario") || userService.validateRole(userId, "Administrador")) {
            try {
                Customer novoCliente = userService.createCustomer(cliente);
                return ResponseEntity.ok(novoCliente);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(400).body(e.getMessage());
            }
        } else {
            return ResponseEntity.status(403).body("Função insuficiente para está ação"); // 403 Forbidden se não for
                                                                                          // autorizado
        }
    }

    @PostMapping("/public/createCustomer")
    public ResponseEntity<?> publicCreateCustomer(@RequestBody Customer cliente) {
        // Ao registrar, sempre atribuímos a role "Cliente"
        try {
            cliente.setRole("Cliente");
            Customer novoCliente = userService.createCustomer(cliente);
            return ResponseEntity.ok(novoCliente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }

    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> listarUsuarios() {
        List<User> usuarios = userService.listUsers();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/findUserById")
    public ResponseEntity<User> searchUserById(@RequestParam Long id) {
        User usuario = userService.findUserById(id);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

    // O delete deve futuramente se preocupar com a integridade das outras tabelas
    // Pois apagar um funcionário tem danos colaterais nos emprestimos aprovados por
    // ele
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser(@RequestParam Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/validateRole")
    public ResponseEntity<Boolean> validateRole(@RequestParam Long id, @RequestParam String role) {
        boolean isValid = userService.validateRole(id, role);
        return ResponseEntity.ok(isValid);
    }
}
