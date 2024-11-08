package com.biblioteca.biblioteca.services.bookReservation;

import com.biblioteca.biblioteca.model.BookReservation;
import com.biblioteca.biblioteca.model.BookReservationRequest;
import com.biblioteca.biblioteca.model.Loan;
import com.biblioteca.biblioteca.repository.BookReservationRepository;
import com.biblioteca.biblioteca.repository.BookReservationRequestRepository;
import com.biblioteca.biblioteca.repository.BookCollectionRepository;
import com.biblioteca.biblioteca.repository.LoanRepository;
import com.biblioteca.biblioteca.model.BookCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookReservationService {

    @Autowired
    private BookReservationRepository bookReservationRepository;

    @Autowired
    private BookReservationRequestRepository bookReservationRequestRepository;
    
    @Autowired
    private BookCollectionRepository bookCollectionRepository;

    @Autowired
    private LoanRepository loanRepository;

    public BookReservation approveReservation(Long requestId, Long employeeId) {
        BookReservationRequest request = bookReservationRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Solicitação não encontrada"));

        if (!"pendente".equals(request.getStatus())) {
            throw new IllegalStateException("Apenas solicitações pendentes podem ser aprovadas");
        }

        BookReservation reservation = new BookReservation(
                request.getCustomerId(),
                employeeId,
                request.getBookId(),
                "reservado",
                LocalDate.now(),
                LocalDate.now().plusWeeks(2) // Data de expiração padrão
        );

        request.setStatus("aprovado");
        bookReservationRequestRepository.save(request);

        return bookReservationRepository.save(reservation);
    }

    public List<BookReservation> findReservationsByStatus(String status) {
        return bookReservationRepository.findByStatus(status);
    }

    public void cancelReservation(Long reservationId) {
        BookReservation reservation = bookReservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada"));
        reservation.setStatus("cancelado");
        bookReservationRepository.save(reservation);

        // Devolve a cópia ao acervo
        BookCollection bookCollection = bookCollectionRepository.OptionalFindByBookId(reservation.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado no acervo"));
        bookCollection.addCopies(1);
        bookCollectionRepository.save(bookCollection);
    }

    public boolean convertReservationToLoan(Long reservationId, Long employeeId) {
        // Busca a reserva pelo ID
        BookReservation reservation = bookReservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada"));
        
        // Verifica se a reserva ainda está ativa
        if (!"reservado".equals(reservation.getStatus())) {
            throw new IllegalStateException("A reserva não está ativa e não pode ser convertida em empréstimo.");
        }
        
        // Verifica se há cópias disponíveis no acervo
        BookCollection bookCollection = bookCollectionRepository.OptionalFindByBookId(reservation.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado no acervo"));

        // Cria um novo empréstimo baseado na reserva
        Loan loan = new Loan();
        loan.setIdCliente(reservation.getCustomerId());
        loan.setIdFuncionario(employeeId);
        loan.setIdLivro(reservation.getBookId());
        loan.setStatus("ativo");
        loan.setDataAprovacao(LocalDate.now());
        loan.setDataDevolucaoEstimada(LocalDate.now().plusWeeks(2)); // Exemplo: empréstimo de 2 semanas

        // Salva o novo empréstimo
        loanRepository.save(loan);

        // Atualiza o status da reserva para "emprestado"
        reservation.setStatus("finalizado");
        bookReservationRepository.save(reservation);

        bookCollectionRepository.save(bookCollection);

        return true;  // Retorna true para indicar que a conversão foi bem-sucedida
    }
}
