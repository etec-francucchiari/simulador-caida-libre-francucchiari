package org.example

import java.util.Random
import kotlin.test.*

class GeneradorOpcionesRespuestaTest {
    /**
     * Pruebas unitarias del generador de opciones de respuesta numérica
     * ([GeneradorOpcionesRespuesta]) empleado en la Etapa 2 del Modo Avanzado.
     */

    private val generadorEjercicios = GeneradorEjercicios(Random(4))

    private fun crearGenerador(semilla: Long) = GeneradorOpcionesRespuesta(Random(semilla))

    @Test
    fun generaUnaRespuestaCorrectaYDistractoresDistintos() {
        val tierra = Planeta.entries.first()
        for (objetivo in ObjetivoEjercicio.entries) {
            val ejercicio = generadorEjercicios.generarConfigurado(tierra, 50.0, 4.0, objetivo)
            val opciones = crearGenerador(9).generar(ejercicio)
            assertEquals(3, opciones.size)
            assertEquals(1, opciones.count { it.esCorrecta() })
            assertEquals(ejercicio.valorCorrecto(), opciones.first { it.esCorrecta() }.valor(), 1e-6)
            assertEquals(3, opciones.map { it.valor() }.toSet().size)
        }
    }

    @Test
    fun losDistractoresDescribenErroresDeCalculo() {
        val ejercicio = generadorEjercicios.generarConfigurado(
            Planeta.entries.first(),
            50.0,
            4.0,
            ObjetivoEjercicio.CALCULAR_TIEMPO
        )
        val distractores = crearGenerador(2)
            .generar(ejercicio)
            .filter { !it.esCorrecta() }
        assertEquals(2, distractores.size)
        assertTrue(distractores.all { it.retroalimentacion().isNotBlank() })
    }
}
