package com.biblioteca.biblioteca.services.loanRequest;

import com.biblioteca.biblioteca.model.BookCollection;
import com.biblioteca.biblioteca.model.Loan;
import com.biblioteca.biblioteca.model.LoanRequest;
import com.biblioteca.biblioteca.repository.LoanRequestRepository;
import com.biblioteca.biblioteca.repository.BookCollectionRepository;
import com.biblioteca.biblioteca.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoanRequestService {

    @Autowired
    private LoanRequestRepository loanRequestRepository;
    
    @Autowired
    private BookCollectionRepository bookCollectionRepository;

    @Autowired
    private LoanRepository loanRepository;

    public LoanRequest createLoanRequest(Long idCliente, Long idBook) {
            // Buscar o acervo pelo ID do livro
        Optional<BookCollection> bookCollectionOptional = bookCollectionRepository.OptionalFindByBookId(idBook);
        
        if (bookCollectionOptional.isPresent()) {
            BookCollection bookCollection = bookCollectionOptional.get();

            // Verificar se há exemplares disponíveis no acervo
            if (bookCollection.getAvailableCopies() > 0) {
                // Reduzir o número de cópias disponíveis
                bookCollection.removeCopies(1);
                bookCollectionRepository.save(bookCollection);

                // Criar a solicitação de empréstimo
                LoanRequest loanRequest = new LoanRequest(idCliente, idBook);
                return loanRequestRepository.save(loanRequest);
            } else {
                throw new IllegalArgumentException("Livro não disponível para empréstimo.");
            }
        } else {
            throw new IllegalArgumentException("Acervo não encontrado para o livro especificado.");
        }
    }

    public List<LoanRequest> listLoanRequests() {
        return loanRequestRepository.findAll();
    }

    public List<LoanRequest> findLoanRequestsByClientId(Long idCliente) {
        return loanRequestRepository.findByIdCliente(idCliente);
    }

    public LoanRequest updateLoanRequest(Long loanRequestId, Long idFuncionario, String newStatus) {
        Optional<LoanRequest> optionalLoanRequest = loanRequestRepository.findById(loanRequestId);

        if (optionalLoanRequest.isPresent()) {
            LoanRequest loanRequest = optionalLoanRequest.get();
            BookCollection bookCollection = bookCollectionRepository
                .OptionalFindByBookId(loanRequest.getIdBook())
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado no acervo"));

            // Atualizar o status da solicitação de empréstimo
            loanRequest.setStatus(newStatus);
            loanRequest.setIdFuncionario(idFuncionario);
            loanRequestRepository.save(loanRequest);

            // Se o status for "aprovado", cria um novo empréstimo (sem alterar a quantidade de livros)
            if ("aprovado".equalsIgnoreCase(newStatus)) {
                Loan loan = new Loan(
                    loanRequest.getIdCliente(),
                    loanRequest.getIdFuncionario(),
                    loanRequest.getIdBook(),
                    "ativo",
                    LocalDate.now(),
                    LocalDate.now().plusWeeks(2) // Exemplo: devolução estimada para 2 semanas depois
                );
                loanRepository.save(loan);

            // Se o status for "reprovado", adiciona o livro de volta ao estoque
            } else if ("reprovado".equalsIgnoreCase(newStatus)) {
                bookCollection.addCopies(1); // Adiciona uma cópia de volta ao estoque
                bookCollectionRepository.save(bookCollection);
            }

            return loanRequest;
        } else {
            throw new IllegalArgumentException("Solicitação de empréstimo não encontrada.");
        }
    }

    public List<LoanRequest> getLoanRequestsByStatus(String status) {
        return loanRequestRepository.findByStatus(status);
    }

    public LoanRequest findLoanRequestById(Long loanRequestId) {
        return loanRequestRepository.findById(loanRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Requisição de empréstimo não encontrada"));
    }
}
