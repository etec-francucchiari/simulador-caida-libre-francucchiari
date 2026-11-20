package org.example

import kotlin.test.*

class ControladorSimulacionTest {
    /**
     * Pruebas unitarias de los controles de reproducción de [ControladorSimulacion]
     * (iniciar, pausar, alternar y reiniciar).
     */

    private val nucleo = crearNucleo()

    private fun crearNucleo() = NucleoSimulacion()

    @Test
    fun instanciarControlador() {
        val actual = crearNucleo()
        val gestor = actual.contexto()
        val controlador = actual.control()
        assertNotNull(controlador)
        assertFalse(gestor.estaEnEjecucion())
    }

    @Test
    fun iniciarActivaEjecucion() {
        val actual = crearNucleo()
        val gestor = actual.contexto()
        val controlador = actual.control()
        controlador.iniciar()
        assertTrue(gestor.estaEnEjecucion())
    }

    @Test
    fun pausarDetieneEjecucion() {
        val actual = crearNucleo()
        val gestor = actual.contexto()
        val controlador = actual.control()
        controlador.iniciar()
        assertTrue(gestor.estaEnEjecucion())
        controlador.pausar()
        assertFalse(gestor.estaEnEjecucion())
    }

    @Test
    fun alternarEjecucionConmutaEntreIniciarYPausar() {
        val actual = crearNucleo()
        val gestor = actual.contexto()
        val controlador = actual.control()
        controlador.alternarEjecucion()
        assertTrue(gestor.estaEnEjecucion())
        controlador.alternarEjecucion()
        assertFalse(gestor.estaEnEjecucion())
    }

    @Test
    fun reiniciarRestableceEstadoAValoresIniciales() {
        val gestor = nucleo.contexto()
        val controlador = nucleo.control()
        gestor.establecerAlturaInicial(100.0)
        gestor.establecerVelocidadInicial(10.0)
        controlador.iniciar()
        controlador.procesarFrame(1_000_000_000L)
        controlador.procesarFrame(1_500_000_000L)
        assertTrue(gestor.obtenerTiempoActual() > 0.0)
        controlador.reiniciar()
        assertFalse(gestor.estaEnEjecucion())
        assertEquals(0.0, gestor.obtenerTiempoActual())
        assertEquals(100.0, gestor.obtenerAlturaActual())
        assertEquals(10.0, gestor.obtenerVelocidadActual())
    }
}
