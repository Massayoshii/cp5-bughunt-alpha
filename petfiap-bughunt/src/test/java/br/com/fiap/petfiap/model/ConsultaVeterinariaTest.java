package br.com.fiap.petfiap.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Testes unitarios do model: sem banco, sem Spring (Aula 15).
public class ConsultaVeterinariaTest {

    private ConsultaVeterinaria consultaDaMimi() {
        return new ConsultaVeterinaria(1, "Mimi", "MEDIO", "Bruno", LocalDateTime.of(2026, 10, 1, 14, 0));
    }

    @Test
    public void deveAcumular50PontosDeFidelidade() {
        // Act
        int pontos = consultaDaMimi().calcularPontosFidelidade();

        // Assert
        assertEquals(50, pontos);
    }

    @Test
    public void deveDurar30Minutos() {
        // Act
        int duracao = consultaDaMimi().getDuracaoMinutos();

        // Assert
        assertEquals(30, duracao);
    }

    @Test
    public void deveCustar150ReaisIndependentementeDoPorte() {
        // Arrange
        ConsultaVeterinaria consultaPequena = new ConsultaVeterinaria(
                1, "Mimi", "PEQUENO", "Bruno",
                LocalDateTime.of(2026, 10, 1, 14, 0));

        ConsultaVeterinaria consultaGrande = new ConsultaVeterinaria(
                2, "Thor", "GRANDE", "Carlos",
                LocalDateTime.of(2026, 10, 1, 15, 0));

        // Act
        double precoPequena = consultaPequena.calcularPreco();
        double precoGrande = consultaGrande.calcularPreco();

        // Assert
        assertEquals(150.0, precoPequena, 0.001);
        assertEquals(150.0, precoGrande, 0.001);
    }
}
