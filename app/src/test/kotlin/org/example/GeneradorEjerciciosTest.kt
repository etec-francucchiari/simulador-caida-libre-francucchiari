package org.example

import java.util.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Pruebas del generador de ejercicios del Modo Avanzado.
 */
class GeneradorEjerciciosTest {

    private val catalogo = CatalogoPlanetas()
    private val calculo = CalculoCaidaLibre()

    @Test
    fun generarProduceEscenariosFisicamenteValidos() {
        val generador = GeneradorEjercicios(catalogo, calculo, Random(123))

        repeat(200) {
            val ejercicio = generador.generar()

            assertTrue(ejercicio.alturaInicial() > 0.0)
            assertTrue(ejercicio.gravedad() > 0.0)
            assertTrue(ejercicio.valorCorrecto() >= 0.0)

            if (ejercicio.objetivo().esTiempo() || ejercicio.objetivo().esVelocidad()) {
                assertTrue(
                    ejercicio.velocidadInicial() > 0.0,
                    "v₀ debe ser > 0 para el objetivo ${ejercicio.objetivo()}"
                )
            }
            if (!ejercicio.objetivo().esTiempo()) {
                assertTrue(
                    ejercicio.tiempoEvaluacion() < ejercicio.tiempoTotalCaida(),
                    "El instante evaluado debe ser anterior al impacto"
                )
            }
        }
    }

    @Test
    fun generarConfiguradoRespetaElEscenarioIndicado() {
        val generador = GeneradorEjercicios(catalogo, calculo, Random(1))

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

        val esperado = calculo.calcularTiempoCaida(50.0, 0.0, Planeta.MARTE.gravedad())!!
        assertEquals(esperado, ejercicio.valorCorrecto(), 1e-9)
    }

    @Test
    fun enunciadoIncluyeElEscenarioYElObjetivo() {
        val generador = GeneradorEjercicios(catalogo, calculo, Random(2))
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
