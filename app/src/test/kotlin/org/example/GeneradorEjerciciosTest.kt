package org.example

import java.util.Random
import kotlin.test.*

class GeneradorEjerciciosTest {
    /**
     * Pruebas unitarias del generador de ejercicios cinemáticos aleatorios y
     * configurados ([GeneradorEjercicios]) para el Modo Avanzado.
     */

    private val nucleo = NucleoSimulacion()

    private fun crearGenerador(semilla: Long) =
        GeneradorEjercicios(Random(semilla))

    @Test
    fun generarProduceEscenariosFisicamenteValidos() {
        val generador = crearGenerador(123)
        repeat(200) {
            val ejercicio = generador.generar()
            assertTrue(ejercicio.alturaInicial() > 0.0)
            assertTrue(ejercicio.gravedad() > 0.0)
            assertTrue(ejercicio.valorCorrecto() >= 0.0)
            if (ejercicio.objetivo().esTiempo() || ejercicio.objetivo().esVelocidad()) {
                assertTrue(ejercicio.velocidadInicial() > 0.0)
            }
            if (!ejercicio.objetivo().esTiempo()) {
                assertTrue(ejercicio.tiempoEvaluacion() < ejercicio.tiempoTotalCaida())
            }
        }
    }

    @Test
    fun generarConfiguradoRespetaElEscenarioIndicado() {
        val generador = crearGenerador(1)
        val ejercicio = generador.generarConfigurado(
            Planeta.MARTE,
            50.0,
            0.0,
            ObjetivoEjercicio.CALCULAR_TIEMPO
        )
        assertEquals(Planeta.MARTE, ejercicio.entorno())
        assertEquals(50.0, ejercicio.alturaInicial())
        assertEquals(0.0, ejercicio.velocidadInicial())
        assertEquals(ObjetivoEjercicio.CALCULAR_TIEMPO, ejercicio.objetivo())
        val esperado = nucleo.calculo().calcularTiempoCaida(50.0, 0.0, Planeta.MARTE.gravedad())!!
        assertEquals(esperado, ejercicio.valorCorrecto(), 1e-9)
    }

    @Test
    fun enunciadoIncluyeElEscenarioYElObjetivo() {
        val generador = crearGenerador(2)
        val ejercicio = generador.generarConfigurado(
            Planeta.TIERRA,
            100.0,
            0.0,
            ObjetivoEjercicio.CALCULAR_VELOCIDAD
        )
        val enunciado = ejercicio.enunciado()
        assertTrue(enunciado.contains("Tierra"))
        assertTrue(enunciado.contains("100.00"))
        assertTrue(enunciado.contains("velocidad"))
    }
}
