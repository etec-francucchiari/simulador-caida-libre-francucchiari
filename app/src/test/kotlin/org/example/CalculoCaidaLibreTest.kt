package org.example

import kotlin.test.*

class CalculoCaidaLibreTest {
    /**
     * Pruebas unitarias de las fórmulas cinemáticas de [CalculoCaidaLibre]
     * enfocadas en el cálculo del tiempo total de caída y la velocidad de impacto.
     *
     * Verifica la resolución de la ecuación de segundo grado:
     *   0.5 · g · t² + v₀ · t - h₀ = 0
     * tanto en caída desde el reposo (v₀ = 0) como con impulso inicial hacia
     * abajo (v₀ > 0), además del rechazo de parámetros físicamente inválidos.
     */

    private val calculo = CalculoCaidaLibre()

    /**
     * Caso clásico: desde h₀ = 4.9 m en reposo con g = 9.8 m/s²,
     * t = √(2 · 4.9 / 9.8) = 1.0 s exacto.
     */
    @Test
    fun tiempoDeCaidaDesdeReposoEsUnSegundo() {
        val tiempo = calculo.calcularTiempoCaida(4.9, 0.0, 9.8)
        assertEquals(1.0, tiempo!!, 1e-9)
    }

    /**
     * Con los mismos datos del caso anterior, la velocidad al tocar el suelo
     * debe cumplir v = g · t = 9.8 m/s.
     */
    @Test
    fun velocidadDeImpactoDesdeReposo() {
        val velocidad = calculo.calcularVelocidadImpacto(4.9, 0.0, 9.8)
        assertEquals(9.8, velocidad!!, 1e-9)
    }

    /**
     * Si el cuerpo ya parte desde el nivel del suelo (h₀ = 0), el tiempo de
     * caída es nulo y la velocidad de impacto coincide con la velocidad inicial.
     */
    @Test
    fun conAlturaCeroNoHayCaida() {
        val tiempo = calculo.calcularTiempoCaida(0.0, 5.0, 9.8)
        val velocidad = calculo.calcularVelocidadImpacto(0.0, 5.0, 9.8)
        assertEquals(0.0, tiempo!!, 1e-9)
        assertEquals(5.0, velocidad!!, 1e-9)
    }

    /**
     * Con velocidad inicial positiva hacia abajo (v₀ = 2.0 m/s, h₀ = 4.9 m,
     * g = 9.8 m/s²), el tiempo de vuelo disminuye respecto al caso en reposo.
     */
    @Test
    fun tiempoConVelocidadInicialPositiva() {
        val tiempo = calculo.calcularTiempoCaida(4.9, 2.0, 9.8)
        assertEquals(0.81653, tiempo!!, 1e-4)
    }

    /**
     * Por conservación de la energía mecánica: v² = v₀² + 2 · g · h₀,
     * para h₀ = 4.9 m, v₀ = 2.0 m/s y g = 9.8 m/s², v ≈ 10.002 m/s.
     */
    @Test
    fun velocidadImpactoConVelocidadInicialPositiva() {
        val velocidad = calculo.calcularVelocidadImpacto(4.9, 2.0, 9.8)
        assertEquals(10.002, velocidad!!, 1e-3)
    }

    /**
     * Alturas negativas, velocidades iniciales negativas o gravedades no
     * positivas deben rechazarse devolviendo `null`.
     */
    @Test
    fun parametrosFisicamenteInvalidosDevuelvenNulo() {
        assertNull(calculo.calcularTiempoCaida(-1.0, 0.0, 9.8))
        assertNull(calculo.calcularTiempoCaida(4.9, -1.0, 9.8))
        assertNull(calculo.calcularTiempoCaida(4.9, 0.0, 0.0))
        assertNull(calculo.calcularVelocidadImpacto(-1.0, 0.0, 9.8))
    }
}
