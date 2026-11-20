package org.example

import kotlin.test.*

class ControladorSimulacionFotogramasTest {
    /**
     * Pruebas unitarias del bucle de fotogramas de [ControladorSimulacion]:
     * captura del primer pulso, integración del delta temporal, acotamiento ante
     * pausas del sistema operativo y detención automática al tocar el suelo.
     */

    private val calculo = crearNucleo().calculo()

    private fun crearNucleo() = NucleoSimulacion()

    @Test
    fun procesarPrimerFotogramaEstableceMarcaTemporalSinAvanzar() {
        val nucleo = crearNucleo()
        val gestor = nucleo.contexto()
        val controlador = nucleo.control()
        gestor.establecerAlturaInicial(50.0)
        gestor.establecerVelocidadInicial(0.0)
        controlador.iniciar()
        val sigue = controlador.procesarFrame(1_000_000_000L)
        assertTrue(sigue)
        assertEquals(0.0, gestor.obtenerTiempoActual())
        assertEquals(50.0, gestor.obtenerAlturaActual())
    }

    @Test
    fun procesarFotogramasConsecutivosAvanzaEstadoFisico() {
        val nucleo = crearNucleo()
        val gestor = nucleo.contexto()
        val controlador = nucleo.control()
        gestor.establecerAlturaInicial(100.0)
        gestor.establecerVelocidadInicial(0.0)
        controlador.iniciar()
        controlador.procesarFrame(1_000_000_000L)
        val sigue = controlador.procesarFrame(1_050_000_000L)
        assertTrue(sigue)
        assertEquals(0.05, gestor.obtenerTiempoActual(), 1e-6)
        val g = gestor.obtenerPlaneta().gravedad()
        assertEquals(100.0 - 0.5 * g * 0.05 * 0.05, gestor.obtenerAlturaActual(), 1e-5)
        assertEquals(g * 0.05, gestor.obtenerVelocidadActual(), 1e-5)
    }

    @Test
    fun procesarFotogramaConDeltaExcesivoSeLimitaAUmbralSeguro() {
        val nucleo = crearNucleo()
        val gestor = nucleo.contexto()
        val controlador = nucleo.control()
        controlador.iniciar()
        controlador.procesarFrame(1_000_000_000L)
        controlador.procesarFrame(3_000_000_000L)
        assertEquals(controlador.deltaMaximoSegundos(), gestor.obtenerTiempoActual(), 1e-6)
    }

    @Test
    fun procesarFotogramaDetieneSimulacionAlDetectarImpacto() {
        val nucleo = crearNucleo()
        val gestor = nucleo.contexto()
        val controlador = nucleo.control()
        gestor.establecerAlturaInicial(4.9)
        gestor.establecerVelocidadInicial(0.0)
        controlador.iniciar()
        controlador.procesarFrame(1_000_000_000L)
        var tiempoNano = 1_000_000_000L
        for (i in 1..9) {
            tiempoNano += 100_000_000L
            assertTrue(controlador.procesarFrame(tiempoNano))
        }
        val sigueFinal = controlador.procesarFrame(tiempoNano + 100_000_000L)
        val g = gestor.obtenerPlaneta().gravedad()
        assertFalse(sigueFinal)
        assertFalse(gestor.estaEnEjecucion())
        assertEquals(0.0, gestor.obtenerAlturaActual(), 1e-6)
        assertEquals(calculo.calcularTiempoCaida(4.9, 0.0, g)!!, gestor.obtenerTiempoActual(), 1e-6)
        assertEquals(calculo.calcularVelocidadImpacto(4.9, 0.0, g)!!, gestor.obtenerVelocidadActual(), 1e-6)
        assertTrue(gestor.haLlegadoAlSuelo())
    }

    @Test
    fun procesarFotogramaCuandoNoEstaEnEjecucionRetornaFalso() {
        val nucleo = crearNucleo()
        val gestor = nucleo.contexto()
        val controlador = nucleo.control()
        assertFalse(gestor.estaEnEjecucion())
        assertFalse(controlador.procesarFrame(1_000_000_000L))
    }

    @Test
    fun iniciarReiniciaAutomaticamenteSiYaEstabaEnElSuelo() {
        val nucleo = crearNucleo()
        val gestor = nucleo.contexto()
        val controlador = nucleo.control()
        gestor.establecerAlturaInicial(4.9)
        gestor.establecerVelocidadInicial(0.0)
        gestor.iniciarSimulacion()
        gestor.establecerTiempoActual(1.0)
        assertFalse(gestor.estaEnEjecucion())
        controlador.iniciar()
        assertTrue(gestor.estaEnEjecucion())
        assertEquals(0.0, gestor.obtenerTiempoActual())
        assertEquals(4.9, gestor.obtenerAlturaActual())
    }
}
