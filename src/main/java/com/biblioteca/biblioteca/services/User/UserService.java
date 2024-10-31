package com.biblioteca.biblioteca.services.User;

// Importando classes especializadas de usuário
import com.biblioteca.biblioteca.model.Customer;
import com.biblioteca.biblioteca.model.Employee;
import com.biblioteca.biblioteca.model.User;
// Import de repositorio
import com.biblioteca.biblioteca.repository.UserRepository;
// Importando utilitários do springBoot
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// Importando utilitários do ajva
import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    
    public User createUser(User usuario) {
        if (emailAlreadyExists(usuario.getEmail())) {
            throw new IllegalArgumentException("E-mail já está em uso.");
        }
        return userRepository.save(usuario);
    }

    public Customer createCustomer(Customer cliente) {
        if (emailAlreadyExists(cliente.getEmail())) {
            throw new IllegalArgumentException("E-mail já está em uso.");
        }
        return userRepository.save(cliente);
    }

    public Employee createEmployee(Employee funcionario) {
        if (emailAlreadyExists(funcionario.getEmail())) {
            throw new IllegalArgumentException("E-mail já está em uso.");
        }
        return userRepository.save(funcionario);
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public boolean validateRole(Long id, String role) {
        User usuario = findUserById(id);
        return usuario != null && usuario.getRole().equals(role);
    }

    public boolean emailAlreadyExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
