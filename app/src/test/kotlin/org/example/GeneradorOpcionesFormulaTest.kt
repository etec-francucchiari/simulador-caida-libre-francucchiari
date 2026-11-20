package org.example

import java.util.Random
import kotlin.test.*

class GeneradorOpcionesFormulaTest {
    /**
     * Pruebas unitarias del generador de opciones de fórmula simbólica
     * ([GeneradorOpcionesFormula]) utilizado en la Etapa 1 del Modo Avanzado.
     */

    private val generadorEjercicios = GeneradorEjercicios(Random(3))

    private fun crearGenerador(semilla: Long) = GeneradorOpcionesFormula(Random(semilla))

    @Test
    fun generaTresOpcionesConUnaSolaCorrecta() {
        val tierra = Planeta.entries.first()
        for (objetivo in ObjetivoEjercicio.entries) {
            val ejercicio = generadorEjercicios.generarConfigurado(tierra, 50.0, 4.0, objetivo)
            val opciones = crearGenerador(5).generar(ejercicio)
            assertEquals(3, opciones.size)
            assertEquals(1, opciones.count { it.esCorrecta() })
            assertEquals(ejercicio.formulaSimbolica(), opciones.first { it.esCorrecta() }.texto())
            assertEquals(3, opciones.map { it.texto() }.toSet().size)
        }
    }

    @Test
    fun losDistractoresExplicanElErrorCometido() {
        val ejercicio = generadorEjercicios.generarConfigurado(
            Planeta.entries.first(),
            50.0,
            4.0,
            ObjetivoEjercicio.CALCULAR_POSICION
        )
        val distractores = crearGenerador(7)
            .generar(ejercicio)
            .filter { !it.esCorrecta() }
        assertEquals(2, distractores.size)
        assertTrue(distractores.all { it.retroalimentacion().isNotBlank() })
    }
}
