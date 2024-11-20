package com.biblioteca.biblioteca.services.bookReservationRequest;

import com.biblioteca.biblioteca.model.BookReservationRequest;
import com.biblioteca.biblioteca.model.BookCollection;
import com.biblioteca.biblioteca.model.BookReservation;
import com.biblioteca.biblioteca.repository.BookReservationRequestRepository;
import com.biblioteca.biblioteca.repository.BookCollectionRepository;
import com.biblioteca.biblioteca.repository.BookReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Collections;

@Service
public class BookReservationRequestService {

    @Autowired
    private BookReservationRequestRepository bookReservationRequestRepository;

    @Autowired
    private BookCollectionRepository bookCollectionRepository;

    @Autowired
    private BookReservationRepository bookReservationRepository;

    public void cancelReservationRequest(Long reservationRequestId) {
        // Busca a solicitação de reserva pelo ID
        BookReservationRequest reservationRequest = bookReservationRequestRepository.findById(reservationRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Solicitação de reserva não encontrada"));
    
        // Atualiza o status para "cancelado"
        reservationRequest.setStatus("cancelado");
        bookReservationRequestRepository.save(reservationRequest);
    
        // Retorna a cópia ao acervo
        BookCollection bookCollection = bookCollectionRepository.OptionalFindByBookId(reservationRequest.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado no acervo"));
        bookCollection.addCopies(1);
        bookCollectionRepository.save(bookCollection);
    }

    public BookReservationRequest createReservationRequest(Long customerId, Long bookId) {
        BookCollection bookCollection = bookCollectionRepository.OptionalFindByBookId(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado no acervo"));

        if (bookCollection.getAvailableCopies() <= 0) {
            throw new IllegalStateException("Nenhuma cópia disponível para reserva");
        }

        BookReservationRequest request = new BookReservationRequest(customerId, bookId, "pendente", LocalDate.now());
        bookCollection.removeCopies(1);
        bookCollectionRepository.save(bookCollection);

        return bookReservationRequestRepository.save(request);
    }

    public List<BookReservationRequest> findRequestsByStatus(String status) {
        return bookReservationRequestRepository.findByStatus(status);
    }

    public Boolean updateRequestStatus(Long requestId, String status, Long approverId) {
        BookReservationRequest request = bookReservationRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Solicitação não encontrada"));

        if (!"pendente".equals(request.getStatus())) {
            throw new IllegalStateException("Apenas solicitações pendentes podem ser aprovadas");
        }

        request.setStatus(status);

        if ("reprovado".equals(status)) {
            // Retorna o livro ao acervo em caso de reprovação
            BookCollection bookCollection = bookCollectionRepository.OptionalFindByBookId(request.getBookId())
                    .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado no acervo"));
            bookCollection.addCopies(1);
            bookCollectionRepository.save(bookCollection);
        } else if ("aprovado".equals(status)) {
            // Cria uma nova reserva de livro se a solicitação for aprovada
            BookReservation reservation = new BookReservation();
            reservation.setCustomerId(request.getCustomerId());
            reservation.setEmployeeId(approverId);  // Quem aprovou
            reservation.setBookId(request.getBookId());
            reservation.setStatus("reservado");
            reservation.setDataAprovacao(LocalDate.now());
            reservation.setDataExpiracao(LocalDate.now().plusWeeks(1)); // Exemplo: reserva válida por 1 semana

            // Salva a nova reserva
            bookReservationRepository.save(reservation);
        }

        // Salva a atualização da solicitação
        bookReservationRequestRepository.save(request);

        return true;
    }

    public List<BookReservationRequest> findAllReservationRequests() {
        return bookReservationRequestRepository.findAll();
    }

    public List<BookReservationRequest> findRequestsByCustomerId(Long customerId) {
        return bookReservationRequestRepository.findByCustomerId(customerId);
    }

    public List<BookReservationRequest> findRequestsByBookId(Long bookId) {
        return bookReservationRequestRepository.findByBookId(bookId);
    }

    public List<BookReservationRequest> findReservationRequestById(Long id) {
        return bookReservationRequestRepository.findById(id)
                .map(Collections::singletonList)
                .orElse(Collections.emptyList());
    }
}
