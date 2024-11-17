package com.biblioteca.biblioteca.services.loanService;

import com.biblioteca.biblioteca.model.BookCollection;
import com.biblioteca.biblioteca.model.Loan;
import com.biblioteca.biblioteca.repository.LoanRepository;
import com.biblioteca.biblioteca.repository.BookCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookCollectionRepository bookCollectionRepository;

    // Buscar empréstimos pelo status
    public List<Loan> findLoansByStatus(String status) {
        return loanRepository.findByStatus(status);
    }

    // Buscar empréstimos pelo ID do cliente
    public List<Loan> findLoansByClientId(Long clientId) {
        return loanRepository.findByIdCliente(clientId);
    }

    // Buscar empréstimos pelo ID do livro
    public List<Loan> findLoansByBookId(Long bookId) {
        return loanRepository.findByIdLivro(bookId);
    }

    // Buscar empréstimo por ID
    public Optional<Loan> findLoanById(Long loanId) {
        return loanRepository.findById(loanId);
    }

    // Buscar empréstimos pelo ID do livro
    public List<Loan> findLoansByIdAndReturnInList(Long bookId) {
        return loanRepository.findByIdAndReturnInList(bookId);
    }

    // Devolução de livro
    public String returnBook(Long loanId) {
        Optional<Loan> loanOpt = loanRepository.findById(loanId);
        
        if (loanOpt.isEmpty()) {
            return "Empréstimo não encontrado.";
        }

        Loan loan = loanOpt.get();

        if (!loan.getStatus().equals("ativo")) {
            return "Este empréstimo não pode ser devolvido, pois não está ativo.";
        }

        // Atualizar status e data de devolução
        loan.setStatus("devolvido");
        loan.setDataDevolucaoEstimada(LocalDate.now());
        loanRepository.save(loan);

        // Atualizar quantidade disponível no acervo
        Optional<BookCollection> bookCollectionOpt = bookCollectionRepository.OptionalFindByBookId(loan.getIdLivro());

        if (bookCollectionOpt.isPresent()) {
            BookCollection bookCollection = bookCollectionOpt.get();
            bookCollection.addCopies(1);
            bookCollectionRepository.save(bookCollection);
        } else {
            return "Acervo para o livro não encontrado.";
        }

        return "Livro devolvido com sucesso.";
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAllLoans();
    }

    public List<Loan> getLoansByCustomerId(Long customerId) {
        return loanRepository.findByCustomerId(customerId);
    }

    public List<Loan> getLoansByCustomerIdAndStatus(Long customerId, String status) {
        return loanRepository.findByCustomerIdAndStatus(customerId, status);
    }
}
