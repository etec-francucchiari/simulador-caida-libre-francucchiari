package org.example

import java.util.Random
import kotlin.test.*

class GeneradorEjerciciosTest {
    /**
     * Pruebas unitarias del generador de ejercicios cinemáticos aleatorios y
     * configurados ([GeneradorEjercicios]) para el Modo Avanzado.
     *
     * Verifica que los escenarios muestreados respeten las invariantes físicas
     * (alturas positivas, gravedades positivas, instantes intermedios previos
     * al impacto y resultados exactos acordes a [CalculoCaidaLibre]).
     */

    private val nucleo = NucleoSimulacion()

    /**
     * Ejecuta 200 generaciones consecutivas verificando que todos los ejercicios
     * tengan altura inicial positiva, gravedad positiva, valor esperado no
     * negativo y un instante de evaluación estrictamente menor al tiempo total
     * de caída cuando el objetivo es posición o velocidad.
     */
    @Test
    fun generarProduceEscenariosFisicamenteValidos() {
        val generador = GeneradorEjercicios(nucleo.catalogo(), nucleo.calculo(), Random(123))
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

    /**
     * El método `generarConfigurado` debe respetar exactamente el planeta,
     * la altura inicial, la velocidad inicial y el objetivo indicados, y
     * calcular el resultado verdadero con precisión de punto flotante.
     */
    @Test
    fun generarConfiguradoRespetaElEscenarioIndicado() {
        val generador = GeneradorEjercicios(nucleo.catalogo(), nucleo.calculo(), Random(1))
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

    /**
     * El enunciado textual generado por [EjercicioCaidaLibre.enunciado] debe
     * mencionar el nombre del entorno, la altura inicial formateada y la
     * magnitud física solicitada.
     */
    @Test
    fun enunciadoIncluyeElEscenarioYElObjetivo() {
        val generador = GeneradorEjercicios(nucleo.catalogo(), nucleo.calculo(), Random(2))
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
