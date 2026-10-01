package br.com.fiap.petfiap.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(AtendimentoNaoEncontradoException.class)
    public ResponseEntity<Void> handleAtendimentoNaoEncontradoException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(HorarioOcupadoException.class)
    public ResponseEntity<Void> handleHorarioOcupadoException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(StatusInvalidoException.class)
    public ResponseEntity<Void> handleStatusInvalidoException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}