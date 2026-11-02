package org.example

import java.util.Random
import kotlin.test.*

class GeneradorOpcionesRespuestaTest {
    /**
     * Pruebas unitarias del generador de opciones de respuesta numérica
     * ([GeneradorOpcionesRespuesta]) empleado en la Etapa 2 del Modo Avanzado.
     *
     * Verifica la unicidad de los valores propuestos, la presencia exacta de la
     * respuesta verdadera y la existencia de diagnósticos de error en cada
     * distractor numérico.
     */

    private val generadorEjercicios = GeneradorEjercicios(CatalogoPlanetas(), CalculoCaidaLibre(), Random(4))

    /**
     * Para cualquiera de los tres objetivos de aprendizaje, se deben generar
     * tres opciones numéricas distintas entre sí, donde exactamente una sea
     * correcta y coincida con `ejercicio.valorCorrecto()`.
     */
    @Test
    fun generaUnaRespuestaCorrectaYDistractoresDistintos() {
        val tierra = Planeta.entries.first()
        for (objetivo in ObjetivoEjercicio.entries) {
            val ejercicio = generadorEjercicios.generarConfigurado(tierra, 50.0, 4.0, objetivo)
            val opciones = GeneradorOpcionesRespuesta(Random(9)).generar(ejercicio)
            assertEquals(3, opciones.size)
            assertEquals(1, opciones.count { it.esCorrecta() })
            assertEquals(ejercicio.valorCorrecto(), opciones.first { it.esCorrecta() }.valor(), 1e-6)
            assertEquals(3, opciones.map { it.valor() }.toSet().size)
        }
    }

    /**
     * Cada distractor numérico debe explicar el error de cálculo habitual que
     * conduce a ese resultado (por ejemplo, omitir la raíz cuadrada o el factor ½).
     */
    @Test
    fun losDistractoresDescribenErroresDeCalculo() {
        val ejercicio = generadorEjercicios.generarConfigurado(
            Planeta.entries.first(),
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
