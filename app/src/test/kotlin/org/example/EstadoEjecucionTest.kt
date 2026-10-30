package org.example

import java.util.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Pruebas de la máquina de estados de la simulación (Issue #8: Patrón State).
 *
 * Verifica las transiciones entre los estados [EstadoEjecucionEnCurso],
 * [EstadoEjecucionPausado], [EstadoEjecucionReiniciado] y
 * [EstadoEjecucionEnModoEjercicio], así como la decisión de avance del
 * estado activo.
 */
class EstadoEjecucionTest {

    @Test
    fun estadoEjecutandosePausaAlAlternar() {
        val estado = EstadoEjecucionEnCurso()

        assertTrue(estado.esActiva())
        assertEquals("Ejecutándose", estado.nombre())
        assertTrue(estado.debeAvanzar(0.05))
        assertFalse(estado.debeAvanzar(0.0))

        val pausado = estado.alternar()

        assertFalse(pausado.esActiva())
        assertFalse(pausado.debeAvanzar(0.05))

        val reanudado = pausado.alternar()
        assertTrue(reanudado.esActiva())
        assertTrue(reanudado.debeAvanzar(0.05))
    }

    @Test
    fun estadoReiniciadoIniciaLaEjecucion() {
        val reiniciado = EstadoEjecucionReiniciado()

        assertFalse(reiniciado.esActiva())
        assertEquals("Reiniciado", reiniciado.nombre())
        assertFalse(reiniciado.debeAvanzar(0.05))

        val iniciado = reiniciado.iniciar()
        assertTrue(iniciado.esActiva())
        assertTrue(iniciado.debeAvanzar(0.05))

        val alternado = reiniciado.alternar()
        assertTrue(alternado.esActiva())
        assertTrue(alternado.debeAvanzar(0.05))

        val trasReiniciar = reiniciado.reiniciar().iniciar()
        assertTrue(trasReiniciar.esActiva())
    }

    @Test
    fun estadoEnModoEjercicioPermitePausaYReanudaEnLaMismaFase() {
        val desafio = ControladorDesafio(
            GeneradorEjercicios(CatalogoPlanetas(), CalculoCaidaLibre(), java.util.Random(7)),
            GeneradorOpcionesFormula(),
            GeneradorOpcionesRespuesta()
        )
        val estado = EstadoEjecucionEnModoEjercicio(desafio)

        // Al entrar al modo ejercicio la simulación inicia en reposo.
        assertFalse(estado.esActiva())

        val ejecutando = estado.iniciar()
        assertTrue(ejecutando.esActiva())
        assertTrue(ejecutando.debeAvanzar(0.05))

        val pausado = ejecutando.pausar()
        assertFalse(pausado.esActiva())
        assertFalse(pausado.debeAvanzar(0.05))

        // La reanudación sigue dentro del Modo Ejercicio.
        val reanudado = pausado.iniciar()
        assertTrue(reanudado.esActiva())
        assertTrue(reanudado.debeAvanzar(0.05))
    }
}
