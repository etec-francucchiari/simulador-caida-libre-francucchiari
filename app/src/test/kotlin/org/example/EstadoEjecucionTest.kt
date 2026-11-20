package org.example

import kotlin.test.*

class EstadoEjecucionTest {
    /**
     * Pruebas unitarias de la máquina de estados del ciclo de vida de la
     * simulación (Patrón State).
     */

    private val nucleo = NucleoSimulacion()

    @Test
    fun estadoEjecutandosePausaAlAlternar() {
        val estado = nucleo.contexto().estadoEjecucion().iniciar()
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
        val reiniciado = nucleo.contexto().estadoEjecucion().reiniciar()
        assertFalse(reiniciado.esActiva())
        assertEquals("Reiniciado", reiniciado.nombre())
        assertFalse(reiniciado.debeAvanzar(0.05))
        val iniciado = reiniciado.iniciar()
        assertTrue(iniciado.esActiva())
        assertTrue(iniciado.debeAvanzar(0.05))
        val alternado = reiniciado.alternar()
        assertTrue(alternado.esActiva())
        assertTrue(alternado.debeAvanzar(0.05))
        assertTrue(reiniciado.reiniciar().iniciar().esActiva())
    }

    @Test
    fun estadoEnModoEjercicioPermitePausaYReanudaEnLaMismaFase() {
        val estado = EstadoEjecucionEnModoEjercicio(nucleo.desafio())
        assertFalse(estado.esActiva())
        val ejecutando = estado.iniciar()
        assertTrue(ejecutando.esActiva())
        assertTrue(ejecutando.debeAvanzar(0.05))
        val pausado = ejecutando.pausar()
        assertFalse(pausado.esActiva())
        assertFalse(pausado.debeAvanzar(0.05))
        val reanudado = pausado.iniciar()
        assertTrue(reanudado.esActiva())
        assertTrue(reanudado.debeAvanzar(0.05))
    }
}
