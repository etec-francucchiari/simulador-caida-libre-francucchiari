package org.example

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Pruebas unitarias para el servicio [ExplicadorFisicaPasoAPaso].
 *
 * Verifica la deducción, sustitución dinámica y cálculo aritmético paso a paso
 * de las fórmulas cinemáticas del MRUV requeridas en el Modo Principiante:
 * - Posición: y(t) = y₀ + v₀·t - ½·g·t²
 * - Velocidad: v(t) = v₀ - g·t
 * - Tiempo de caída esperado: t_final = √(2·y₀ / g)
 */
class ExplicadorFisicaPasoAPasoTest {

    private val explicador = ExplicadorFisicaPasoAPaso(CalculoCaidaLibre())

    @Test
    fun explicarPosicionEnInicioDesdeReposo() {
        // h0 = 100 m, v0 = 0 m/s, g = 9.8 m/s², t = 0.0 s
        val paso = explicador.explicarPosicion(100.0, 0.0, 9.8, 0.0)

        assertEquals("y(t) = y₀ + v₀·t - ½·g·t²", paso.formulaGeneral())
        assertTrue(paso.formulaConValores().contains("100.00"))
        assertTrue(paso.formulaConValores().contains("9.80"))
        assertTrue(paso.sustitucionInstante().contains("y(0.00)"))
        assertTrue(paso.resultadoFinal().contains("100.00 m"))
    }

    @Test
    fun explicarPosicionEnInstanteIntermedioCalculaCorrectamente() {
        // h0 = 100 m, v0 = 0 m/s, g = 9.8 m/s², t = 2.0 s
        // y(2) = 100 - 0.5 * 9.8 * 4 = 100 - 19.6 = 80.4 m
        val paso = explicador.explicarPosicion(100.0, 0.0, 9.8, 2.0)

        assertTrue(paso.sustitucionInstante().contains("y(2.00)"))
        assertTrue(paso.evaluacionIntermedia().contains("19.60"))
        assertTrue(paso.resultadoFinal().contains("80.40 m"))
    }

    @Test
    fun explicarPosicionConVelocidadInicialHaciaAbajo() {
        // h0 = 50 m, v0 = 10 m/s, g = 9.8 m/s², t = 1.0 s
        // y(1) = 50 + (-10*1) - (0.5 * 9.8 * 1) = 50 - 10 - 4.9 = 35.1 m
        val paso = explicador.explicarPosicion(50.0, 10.0, 9.8, 1.0)

        assertTrue(paso.formulaConValores().contains("-10.00"))
        assertTrue(paso.evaluacionIntermedia().contains("-10.00"))
        assertTrue(paso.evaluacionIntermedia().contains("4.90"))
        assertTrue(paso.resultadoFinal().contains("35.10 m"))
    }

    @Test
    fun explicarPosicionAlColisionarConElSueloIndicaImpacto() {
        // h0 = 4.9 m, v0 = 0 m/s, g = 9.8 m/s² -> t_caida = 1.0 s
        // En t = 1.5 s (después del impacto):
        val paso = explicador.explicarPosicion(4.9, 0.0, 9.8, 1.5)

        assertTrue(paso.resultadoFinal().contains("0.00 m"))
        assertTrue(paso.resultadoFinal().contains("Impacto en el suelo"))
        assertTrue(paso.aclaracionDidactica().contains("alcanzado el suelo"))
    }

    @Test
    fun explicarVelocidadEnInicioDesdeReposoEsCero() {
        // v0 = 0 m/s, g = 9.8 m/s², t = 0.0 s
        val paso = explicador.explicarVelocidad(100.0, 0.0, 9.8, 0.0)

        assertEquals("v(t) = v₀ - g·t", paso.formulaGeneral())
        assertTrue(paso.formulaConValores().contains("v(t) = 0.00 - (9.80)·t"))
        assertTrue(paso.resultadoFinal().contains("0.00 m/s"))
    }

    @Test
    fun explicarVelocidadEnInstanteIntermedioAumentaLinealmente() {
        // v0 = 0 m/s, g = 9.8 m/s², t = 1.5 s -> v = -14.7 m/s, rapidez = 14.7 m/s
        val paso = explicador.explicarVelocidad(100.0, 0.0, 9.8, 1.5)

        assertTrue(paso.sustitucionInstante().contains("v(1.50)"))
        assertTrue(paso.evaluacionIntermedia().contains("14.70"))
        assertTrue(paso.resultadoFinal().contains("-14.70 m/s"))
        assertTrue(paso.resultadoFinal().contains("|v| = 14.70 m/s"))
    }

    @Test
    fun explicarVelocidadConVelocidadInicial() {
        // v0 = 5 m/s, g = 9.8 m/s², t = 1.0 s -> v = -5 - 9.8 = -14.8 m/s
        val paso = explicador.explicarVelocidad(100.0, 5.0, 9.8, 1.0)

        assertTrue(paso.formulaConValores().contains("-5.00"))
        assertTrue(paso.resultadoFinal().contains("-14.80 m/s"))
        assertTrue(paso.resultadoFinal().contains("|v| = 14.80 m/s"))
    }

    @Test
    fun explicarVelocidadAlImpactarMuestraVelocidadDeImpacto() {
        // h0 = 4.9 m, v0 = 0 m/s, g = 9.8 m/s² -> v_impacto = sqrt(2 * 9.8 * 4.9) = 9.8 m/s
        val paso = explicador.explicarVelocidad(4.9, 0.0, 9.8, 2.0)

        assertTrue(paso.resultadoFinal().contains("9.80 m/s"))
        assertTrue(paso.aclaracionDidactica().contains("impactar contra el suelo"))
    }

    @Test
    fun explicarTiempoCaidaParaCaidaDesdeReposo() {
        // h0 = 4.9 m, v0 = 0 m/s, g = 9.8 m/s² -> t = sqrt(2 * 4.9 / 9.8) = sqrt(1) = 1.0 s
        val paso = explicador.explicarTiempoCaida(4.9, 0.0, 9.8)

        assertEquals("t_final = √(2·y₀ / g)", paso.formulaGeneral())
        assertTrue(paso.formulaConValores().contains("√(2 · 4.90 / 9.80)"))
        assertTrue(paso.resultadoFinal().contains("1.00 s"))
    }

    @Test
    fun explicarTiempoCaidaEnDiferentesPlanetas() {
        // h0 = 100 m, v0 = 0 m/s
        // Tierra: g = 9.8 -> t = sqrt(200 / 9.8) = sqrt(20.408) ≈ 4.52 s
        val pasoTierra = explicador.explicarTiempoCaida(100.0, 0.0, Planeta.TIERRA.gravedad())
        assertTrue(pasoTierra.resultadoFinal().contains("4.52 s"))

        // Luna: g = 1.62 -> t = sqrt(200 / 1.62) = sqrt(123.456) ≈ 11.11 s
        val pasoLuna = explicador.explicarTiempoCaida(100.0, 0.0, Planeta.LUNA.gravedad())
        assertTrue(pasoLuna.resultadoFinal().contains("11.11 s"))

        // Júpiter: g = 24.79 -> t = sqrt(200 / 24.79) = sqrt(8.067) ≈ 2.84 s
        val pasoJupiter = explicador.explicarTiempoCaida(100.0, 0.0, Planeta.JUPITER.gravedad())
        assertTrue(pasoJupiter.resultadoFinal().contains("2.84 s"))
    }

    @Test
    fun explicarTiempoCaidaConVelocidadInicialMuestraAclaracion() {
        // h0 = 4.9 m, v0 = 2.0 m/s, g = 9.8 m/s² -> t_caida ≈ 0.82 s
        val paso = explicador.explicarTiempoCaida(4.9, 2.0, 9.8)

        assertTrue(paso.resultadoFinal().contains("0.82 s"))
        assertTrue(paso.aclaracionDidactica().contains("ecuación cuadrática"))
    }
}
