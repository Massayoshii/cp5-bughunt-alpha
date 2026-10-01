package br.com.fiap.petfiap.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Testes unitarios do model: sem banco, sem Spring (Aula 15).
public class BanhoTest {

    private Banho banhoDoRex() {
        return new Banho(1, "Rex", "PEQUENO", "Ana", LocalDateTime.of(2026, 10, 1, 10, 0));
    }

    @Test
    public void deveAcumular20PontosDeFidelidade() {
        // Act
        int pontos = banhoDoRex().calcularPontosFidelidade();

        // Assert
        assertEquals(20, pontos);
    }

    @Test
    public void deveDurar45Minutos() {
        // Act
        int duracao = banhoDoRex().getDuracaoMinutos();

        // Assert
        assertEquals(45, duracao);
    }

    @Test
    public void deveCalcularPrecoDeAcordoComOporte(){
        // Arrange
        Banho banhoPequeno = new Banho(
                1, "Rex", "PEQUENO", "Ana",
                LocalDateTime.of(2026, 10, 1, 10, 0));

        Banho banhoMedio = new Banho(
                2, "Thor", "MEDIO", "Bruno",
                LocalDateTime.of(2026, 10, 1, 11, 0));

        Banho banhoGrande = new Banho(
                3, "Luna", "GRANDE", "Carlos",
                LocalDateTime.of(2026, 10, 1, 12, 0));

        // Act
        double precoPequeno = banhoPequeno.calcularPreco();
        double precoMedio = banhoMedio.calcularPreco();
        double precoGrande = banhoGrande.calcularPreco();

        // Assert
        assertEquals(60.0, precoPequeno);
        assertEquals(80.0, precoMedio);
        assertEquals(100.0, precoGrande);
    }
}
