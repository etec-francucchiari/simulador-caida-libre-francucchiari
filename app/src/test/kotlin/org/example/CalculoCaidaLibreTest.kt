package org.example

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

/**
 * Pruebas de las fórmulas físicas de [CalculoCaidaLibre] para la
 * caída libre: tiempo de caída y velocidad al impactar.
 */
class CalculoCaidaLibreTest {

    @Test
    fun tiempoDeCaidaDesdeReposoEsUnSegundo() {
        // h₀ = 4.9 m, v₀ = 0, g = 9.8 m/s²  →  t = √(2·h₀/g) = 1 s
        val tiempo = CalculoCaidaLibre.calcularTiempoCaida(4.9, 0.0, 9.8)
        assertEquals(1.0, tiempo!!, 1e-9)
    }

    @Test
    fun velocidadDeImpactoDesdeReposo() {
        // v = √(2·g·h₀) = √(2·9.8·4.9) = 9.8 m/s
        val velocidad = CalculoCaidaLibre.calcularVelocidadImpacto(4.9, 0.0, 9.8)
        assertEquals(9.8, velocidad!!, 1e-9)
    }

    @Test
    fun conAlturaCeroNoHayCaida() {
        // Si el cuerpo ya está en el suelo, el tiempo es 0 y la
        // velocidad de impacto coincide con la velocidad inicial.
        val tiempo = CalculoCaidaLibre.calcularTiempoCaida(0.0, 5.0, 9.8)
        val velocidad = CalculoCaidaLibre.calcularVelocidadImpacto(0.0, 5.0, 9.8)

        assertEquals(0.0, tiempo!!, 1e-9)
        assertEquals(5.0, velocidad!!, 1e-9)
    }

    @Test
    fun tiempoConVelocidadInicialPositiva() {
        // h₀ = 4.9, v₀ = 2.0, g = 9.8:
        // t = (−v₀ + √(v₀² + 2·g·h₀)) / g ≈ 0.81653 s
        val tiempo = CalculoCaidaLibre.calcularTiempoCaida(4.9, 2.0, 9.8)
        assertEquals(0.81653, tiempo!!, 1e-4)
    }

    @Test
    fun velocidadImpactoConVelocidadInicialPositiva() {
        // v = √(v₀² + 2·g·h₀) = √(4 + 96.04) = √(100.04) ≈ 10.002 m/s
        val velocidad = CalculoCaidaLibre.calcularVelocidadImpacto(4.9, 2.0, 9.8)
        assertEquals(10.002, velocidad!!, 1e-3)
    }

    @Test
    fun parametrosFisicamenteInvalidosDevuelvenNulo() {
        assertNull(CalculoCaidaLibre.calcularTiempoCaida(-1.0, 0.0, 9.8))
        assertNull(CalculoCaidaLibre.calcularTiempoCaida(4.9, -1.0, 9.8))
        assertNull(CalculoCaidaLibre.calcularTiempoCaida(4.9, 0.0, 0.0))
        assertNull(CalculoCaidaLibre.calcularVelocidadImpacto(-1.0, 0.0, 9.8))
    }
}