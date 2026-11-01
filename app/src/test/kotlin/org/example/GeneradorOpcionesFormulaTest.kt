package org.example

import java.util.Random
import kotlin.test.*

class GeneradorOpcionesFormulaTest {
    /**
     * Pruebas unitarias del generador de opciones de fórmula simbólica
     * ([GeneradorOpcionesFormula]) utilizado en la Etapa 1 del Modo Avanzado.
     *
     * Comprueba que para todos los objetivos ([ObjetivoEjercicio]) se generen
     * tres fórmulas distintas con una única opción verdadera y dos distractores
     * pedagógicos con retroalimentación explicativa.
     */

    private val generadorEjercicios = GeneradorEjercicios(CatalogoPlanetas(), CalculoCaidaLibre(), Random(3))

    /**
     * Para cada tipo de objetivo (tiempo, velocidad y posición), la lista de
     * opciones debe tener longitud 3, contener una sola fórmula correcta que
     * coincida con `ejercicio.formulaSimbolica()` y no repetir textos.
     */
    @Test
    fun generaTresOpcionesConUnaSolaCorrecta() {
        val tierra = Planeta.entries.first()
        for (objetivo in ObjetivoEjercicio.entries) {
            val ejercicio = generadorEjercicios.generarConfigurado(tierra, 50.0, 4.0, objetivo)
            val opciones = GeneradorOpcionesFormula(Random(5)).generar(ejercicio)
            assertEquals(3, opciones.size)
            assertEquals(1, opciones.count { it.esCorrecta() })
            assertEquals(ejercicio.formulaSimbolica(), opciones.first { it.esCorrecta() }.texto())
            assertEquals(3, opciones.map { it.texto() }.toSet().size)
        }
    }

    /**
     * Los dos distractores generados deben incluir un mensaje no vacío que
     * explique al estudiante por qué la expresión elegida es físicamente
     * incorrecta.
     */
    @Test
    fun losDistractoresExplicanElErrorCometido() {
        val ejercicio = generadorEjercicios.generarConfigurado(
            Planeta.entries.first(),
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
