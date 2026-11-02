package org.example

import kotlin.test.*

class CalculoCaidaLibreTrayectoriaTest {
    /**
     * Pruebas unitarias de posición instantánea y(t), velocidad instantánea v(t)
     * y muestreo discreto de la trayectoria completa en [CalculoCaidaLibre].
     *
     * Comprueba la evolución parabólica de la altura, el crecimiento lineal de
     * la rapidez hacia abajo, el truncamiento al impactar contra el suelo y la
     * monotonía de los puntos generados para los gráficos cinemáticos.
     */

    private val calculo = CalculoCaidaLibre()

    /**
     * En caída libre desde h₀ = 100 m en reposo con g = 9.8 m/s²:
     * - y(0) = 100.0 m
     * - y(1) = 100 - 4.9 = 95.1 m
     * - y(2) = 100 - 19.6 = 80.4 m
     */
    @Test
    fun posicionEnReposoEvolucionaCorrectamente() {
        val y0 = calculo.calcularPosicion(100.0, 0.0, 9.8, 0.0)
        val y1 = calculo.calcularPosicion(100.0, 0.0, 9.8, 1.0)
        val y2 = calculo.calcularPosicion(100.0, 0.0, 9.8, 2.0)
        assertEquals(100.0, y0!!, 1e-9)
        assertEquals(95.1, y1!!, 1e-9)
        assertEquals(80.4, y2!!, 1e-9)
    }

    /**
     * Una vez alcanzado el suelo (t ≥ t_caída), la posición no puede volverse
     * negativa: permanece clavada en y = 0.0 m.
     */
    @Test
    fun posicionAlImpactarYDespuesEsCero() {
        val yEnImpacto = calculo.calcularPosicion(4.9, 0.0, 9.8, 1.0)
        val yDespuesDeImpacto = calculo.calcularPosicion(4.9, 0.0, 9.8, 2.0)
        assertEquals(0.0, yEnImpacto!!, 1e-9)
        assertEquals(0.0, yDespuesDeImpacto!!, 1e-9)
    }

    /**
     * Durante el vuelo libre, la rapidez aumenta linealmente según v(t) = v₀ + g·t.
     */
    @Test
    fun velocidadAumentaLinealmenteConElTiempo() {
        val v0 = calculo.calcularVelocidad(100.0, 5.0, 9.8, 0.0)
        val v2 = calculo.calcularVelocidad(100.0, 5.0, 9.8, 2.0)
        assertEquals(5.0, v0!!, 1e-9)
        assertEquals(24.6, v2!!, 1e-9)
    }

    /**
     * Para instantes posteriores al impacto (t > t_caída), la velocidad reportada
     * se mantiene acotada en la velocidad de impacto.
     */
    @Test
    fun velocidadDespuesDelImpactoEsLaDeImpacto() {
        val vDespues = calculo.calcularVelocidad(4.9, 0.0, 9.8, 3.0)
        assertEquals(9.8, vDespues!!, 1e-9)
    }

    /**
     * Tiempos negativos o parámetros físicos inválidos deben producir `null`
     * tanto en posición como en velocidad.
     */
    @Test
    fun calcularPosicionYVelocidadConParametrosInvalidosDevuelveNulo() {
        assertNull(calculo.calcularPosicion(10.0, 0.0, 9.8, -1.0))
        assertNull(calculo.calcularPosicion(-5.0, 0.0, 9.8, 1.0))
        assertNull(calculo.calcularVelocidad(10.0, 0.0, 9.8, -0.5))
        assertNull(calculo.calcularVelocidad(10.0, -2.0, 9.8, 1.0))
    }

    /**
     * El muestreo de trayectoria debe incluir el instante inicial (t = 0, y = h₀)
     * y el instante final de impacto (t = t_caída, y = 0), manteniendo posición
     * monótonamente decreciente y velocidad monótonamente creciente.
     */
    @Test
    fun generarPuntosTrayectoriaContieneExtremosYValoresValidos() {
        val puntos = calculo.generarPuntosTrayectoria(4.9, 0.0, 9.8, cantidadPuntos = 11)
        assertEquals(11, puntos.size)
        val primerPunto = puntos.first()
        assertEquals(0.0, primerPunto.tiempo(), 1e-9)
        assertEquals(4.9, primerPunto.posicion(), 1e-9)
        assertEquals(0.0, primerPunto.velocidad(), 1e-9)
        val ultimoPunto = puntos.last()
        assertEquals(1.0, ultimoPunto.tiempo(), 1e-9)
        assertEquals(0.0, ultimoPunto.posicion(), 1e-9)
        assertEquals(9.8, ultimoPunto.velocidad(), 1e-9)
        for (i in 1 until puntos.size) {
            assertTrue(puntos[i].posicion() <= puntos[i - 1].posicion())
            assertTrue(puntos[i].velocidad() >= puntos[i - 1].velocidad())
        }
    }
}
