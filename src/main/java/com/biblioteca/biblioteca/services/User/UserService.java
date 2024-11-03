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
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    
    public User createUser(User usuario) {
        if (emailAlreadyExists(usuario.getEmail())) {
            throw new IllegalArgumentException("E-mail já está em uso.");
        }
        usuario.setActive(true);
        return userRepository.save(usuario);
    }

    public Customer createCustomer(Customer cliente) {
        if (emailAlreadyExists(cliente.getEmail())) {
            throw new IllegalArgumentException("E-mail já está em uso.");
        }
        cliente.setActive(true);
        return userRepository.save(cliente);
    }

    public Employee createEmployee(Employee funcionario) {
        if (emailAlreadyExists(funcionario.getEmail())) {
            throw new IllegalArgumentException("E-mail já está em uso.");
        }
        funcionario.setActive(true);
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

    // Método para atualizar as informações do usuário
    public Customer updateCustomer(Long id, Customer customerDetails) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent() && optionalUser.get() instanceof Customer) {
            Customer existingCustomer = (Customer) optionalUser.get();

            // Verifica se o e-mail já está em uso por outro usuário
            if (!existingCustomer.getEmail().equals(customerDetails.getEmail()) &&
                emailAlreadyExists(customerDetails.getEmail())) {
                throw new IllegalArgumentException("E-mail já está em uso por outro usuário.");
            }

            existingCustomer.setName(customerDetails.getName());
            existingCustomer.setEmail(customerDetails.getEmail());
            existingCustomer.setAddress(customerDetails.getAddress());
            existingCustomer.setPassword(customerDetails.getPassword());

            return userRepository.save(existingCustomer);
        } else {
            throw new IllegalArgumentException("Usuário não encontrado ou não é um Cliente.");
        }
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent() && optionalUser.get() instanceof Employee) {
            Employee existingEmployee = (Employee) optionalUser.get();

            // Verifica se o e-mail já está em uso por outro usuário
            if (!existingEmployee.getEmail().equals(employeeDetails.getEmail()) &&
                emailAlreadyExists(employeeDetails.getEmail())) {
                throw new IllegalArgumentException("E-mail já está em uso por outro usuário.");
            }

            existingEmployee.setName(employeeDetails.getName());
            existingEmployee.setEmail(employeeDetails.getEmail());
            existingEmployee.setProfessionalPosition(employeeDetails.getProfessionalPosition());
            existingEmployee.setPassword(employeeDetails.getPassword());

            return userRepository.save(existingEmployee);
        } else {
            throw new IllegalArgumentException("Usuário não encontrado ou não é um Funcionário.");
        }
    }

    // Método para ativar ou inativar o usuário com base no campo active
    public User toggleUserActiveStatus(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setActive(!user.getActive());
            return userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Usuário com ID " + id + " não encontrado.");
        }
    }

    // Busca usuários a partir de seus dados
    public List<User> findUsersByName(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }

    public List<User> findUsersByEmail(String email) {
        return userRepository.findByEmailWithOptional(email)
                .map(List::of) // Cria uma lista com o usuário, se encontrado
                .orElse(List.of()); // Retorna uma lista vazia, se não encontrado
    }
}
