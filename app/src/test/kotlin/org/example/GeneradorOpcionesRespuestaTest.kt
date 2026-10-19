package org.example

import java.util.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Pruebas del generador de opciones de respuesta (Etapa 2).
 */
class GeneradorOpcionesRespuestaTest {

    private val calculo = CalculoCaidaLibre()
    private val generadorEjercicios =
        GeneradorEjercicios(CatalogoPlanetas(), calculo, Random(4))

    @Test
    fun generaUnaRespuestaCorrectaYDistractoresDistintos() {
        for (objetivo in ObjetivoEjercicio.entries) {
            val ejercicio = generadorEjercicios.generarConfigurado(
                Planeta.TIERRA,
                50.0,
                4.0,
                objetivo
            )

            val opciones = GeneradorOpcionesRespuesta(Random(9)).generar(ejercicio)

            assertEquals(3, opciones.size)
            assertEquals(1, opciones.count { it.esCorrecta() })
            assertEquals(
                ejercicio.valorCorrecto(),
                opciones.first { it.esCorrecta() }.valor(),
                1e-6
            )
            assertEquals(3, opciones.map { it.valor() }.toSet().size)
        }
    }

    @Test
    fun losDistractoresDescribenErroresDeCalculo() {
        val ejercicio = generadorEjercicios.generarConfigurado(
            Planeta.TIERRA,
            50.0,
            4.0,
            ObjetivoEjercicio.CALCULAR_TIEMPO
        )

        val distractores = GeneradorOpcionesRespuesta(Random(2))
            .generar(ejercicio)
            .filter { !it.esCorrecta() }

        assertEquals(2, distractores.size)
        assertTrue(distractores.all { it.retroalimentacion().isNotBlank() })
    }
}
