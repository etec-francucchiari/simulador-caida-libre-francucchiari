package org.example

import java.util.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Pruebas del generador de opciones de fórmula (Etapa 1).
 */
class GeneradorOpcionesFormulaTest {

    private val calculo = CalculoCaidaLibre()
    private val generadorEjercicios =
        GeneradorEjercicios(CatalogoPlanetas(), calculo, Random(3))

    @Test
    fun generaTresOpcionesConUnaSolaCorrecta() {
        for (objetivo in ObjetivoEjercicio.entries) {
            val ejercicio = generadorEjercicios.generarConfigurado(
                Planeta.TIERRA,
                50.0,
                4.0,
                objetivo
            )

            val opciones = GeneradorOpcionesFormula(Random(5)).generar(ejercicio)

            assertEquals(3, opciones.size)
            assertEquals(1, opciones.count { it.esCorrecta() })
            assertEquals(
                ejercicio.formulaSimbolica(),
                opciones.first { it.esCorrecta() }.texto()
            )
            assertEquals(3, opciones.map { it.texto() }.toSet().size)
        }
    }

    @Test
    fun losDistractoresExplicanElErrorCometido() {
        val ejercicio = generadorEjercicios.generarConfigurado(
            Planeta.TIERRA,
            50.0,
            4.0,
            ObjetivoEjercicio.CALCULAR_POSICION
        )

        val distractores = GeneradorOpcionesFormula(Random(7))
            .generar(ejercicio)
            .filter { !it.esCorrecta() }

        assertEquals(2, distractores.size)
        assertTrue(distractores.all { it.retroalimentacion().isNotBlank() })
    }
}
