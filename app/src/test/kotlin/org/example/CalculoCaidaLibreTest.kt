package org.example

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Pruebas de las fórmulas físicas de [CalculoCaidaLibre] para la
 * caída libre: tiempo de caída, velocidad de impacto, posición instantánea,
 * velocidad instantánea y generación de puntos de trayectoria.
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

    @Test
    fun posicionEnReposoEvolucionaCorrectamente() {
        // h₀ = 100 m, v₀ = 0 m/s, g = 9.8 m/s²
        // t = 0  -> y = 100 m
        // t = 1  -> y = 100 - 0.5 * 9.8 * 1 = 95.1 m
        // t = 2  -> y = 100 - 0.5 * 9.8 * 4 = 80.4 m
        val y0 = CalculoCaidaLibre.calcularPosicion(100.0, 0.0, 9.8, 0.0)
        val y1 = CalculoCaidaLibre.calcularPosicion(100.0, 0.0, 9.8, 1.0)
        val y2 = CalculoCaidaLibre.calcularPosicion(100.0, 0.0, 9.8, 2.0)

        assertEquals(100.0, y0!!, 1e-9)
        assertEquals(95.1, y1!!, 1e-9)
        assertEquals(80.4, y2!!, 1e-9)
    }

    @Test
    fun posicionAlImpactarYDespuesEsCero() {
        // h₀ = 4.9 m, v₀ = 0, g = 9.8 -> t_caida = 1.0 s
        val yEnImpacto = CalculoCaidaLibre.calcularPosicion(4.9, 0.0, 9.8, 1.0)
        val yDespuesDeImpacto = CalculoCaidaLibre.calcularPosicion(4.9, 0.0, 9.8, 2.0)

        assertEquals(0.0, yEnImpacto!!, 1e-9)
        assertEquals(0.0, yDespuesDeImpacto!!, 1e-9)
    }

    @Test
    fun velocidadAumentaLinealmenteConElTiempo() {
        // h₀ = 100 m, v₀ = 5 m/s, g = 9.8 m/s²
        // t = 0 -> v = 5 m/s
        // t = 2 -> v = 5 + 9.8 * 2 = 24.6 m/s
        val v0 = CalculoCaidaLibre.calcularVelocidad(100.0, 5.0, 9.8, 0.0)
        val v2 = CalculoCaidaLibre.calcularVelocidad(100.0, 5.0, 9.8, 2.0)

        assertEquals(5.0, v0!!, 1e-9)
        assertEquals(24.6, v2!!, 1e-9)
    }

    @Test
    fun velocidadDespuesDelImpactoEsLaDeImpacto() {
        // h₀ = 4.9 m, v₀ = 0, g = 9.8 -> v_impacto = 9.8 m/s
        val vDespues = CalculoCaidaLibre.calcularVelocidad(4.9, 0.0, 9.8, 3.0)
        assertEquals(9.8, vDespues!!, 1e-9)
    }

    @Test
    fun calcularPosicionYVelocidadConParametrosInvalidosDevuelveNulo() {
        assertNull(CalculoCaidaLibre.calcularPosicion(10.0, 0.0, 9.8, -1.0))
        assertNull(CalculoCaidaLibre.calcularPosicion(-5.0, 0.0, 9.8, 1.0))
        assertNull(CalculoCaidaLibre.calcularVelocidad(10.0, 0.0, 9.8, -0.5))
        assertNull(CalculoCaidaLibre.calcularVelocidad(10.0, -2.0, 9.8, 1.0))
    }

    @Test
    fun generarPuntosTrayectoriaContieneExtremosYValoresValidos() {
        val h0 = 4.9
        val v0 = 0.0
        val g = 9.8
        val puntos = CalculoCaidaLibre.generarPuntosTrayectoria(h0, v0, g, cantidadPuntos = 11)

        assertEquals(11, puntos.size)

        val primerPunto = puntos.first()
        assertEquals(0.0, primerPunto.tiempo, 1e-9)
        assertEquals(h0, primerPunto.posicion, 1e-9)
        assertEquals(v0, primerPunto.velocidad, 1e-9)

        val ultimoPunto = puntos.last()
        assertEquals(1.0, ultimoPunto.tiempo, 1e-9)
        assertEquals(0.0, ultimoPunto.posicion, 1e-9)
        assertEquals(9.8, ultimoPunto.velocidad, 1e-9)

        // Verificar que la posición es monótona decreciente
        for (i in 1 until puntos.size) {
            assertTrue(puntos[i].posicion <= puntos[i - 1].posicion)
            assertTrue(puntos[i].velocidad >= puntos[i - 1].velocidad)
        }
    }
}