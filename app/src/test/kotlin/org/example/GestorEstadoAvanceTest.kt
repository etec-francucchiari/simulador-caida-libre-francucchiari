package org.example

import kotlin.test.*

class GestorEstadoAvanceTest {
    /**
     * Pruebas unitarias del avance temporal discreto (`avanzarTiempo`) y de la
     * detección de llegada al suelo (`haLlegadoAlSuelo`) en [GestorEstadoSimulacion].
     */

    private val calculo = nuevoNucleo().calculo()

    private fun nuevoNucleo() = NucleoSimulacion()

    @Test
    fun avanzarTiempoDetieneLaSimulacionAlLlegarAlSuelo() {
        val gestor = nuevoNucleo().contexto()
        gestor.establecerAlturaInicial(4.9)
        gestor.establecerVelocidadInicial(0.0)
        gestor.iniciarSimulacion()
        assertTrue(gestor.estaEnEjecucion())
        assertTrue(gestor.avanzarTiempo(0.5))
        assertEquals(0.5, gestor.obtenerTiempoActual(), 1e-6)
        assertFalse(gestor.avanzarTiempo(0.6))
        assertFalse(gestor.estaEnEjecucion())
        val g = gestor.obtenerPlaneta().gravedad()
        assertEquals(calculo.calcularTiempoCaida(4.9, 0.0, g)!!, gestor.obtenerTiempoActual(), 1e-6)
        assertEquals(0.0, gestor.obtenerAlturaActual(), 1e-6)
        assertEquals(calculo.calcularVelocidadImpacto(4.9, 0.0, g)!!, gestor.obtenerVelocidadActual(), 1e-6)
        assertTrue(gestor.haLlegadoAlSuelo())
    }

    @Test
    fun iniciarSimulacionConAlturaCeroNoSeEjecuta() {
        val gestor = nuevoNucleo().contexto()
        gestor.establecerAlturaInicial(0.0)
        gestor.iniciarSimulacion()
        assertFalse(gestor.estaEnEjecucion())
        assertEquals(0.0, gestor.obtenerAlturaActual())
        assertTrue(gestor.haLlegadoAlSuelo())
    }

    @Test
    fun haLlegadoAlSueloEsFalsoDuranteVueloYVerdaderoAlFinalizar() {
        val gestor = nuevoNucleo().contexto()
        gestor.establecerAlturaInicial(100.0)
        gestor.establecerVelocidadInicial(0.0)
        assertFalse(gestor.haLlegadoAlSuelo())
        gestor.iniciarSimulacion()
        gestor.establecerTiempoActual(2.0)
        assertFalse(gestor.haLlegadoAlSuelo())
        val tCaida = calculo.calcularTiempoCaida(100.0, 0.0, gestor.obtenerPlaneta().gravedad())!!
        gestor.establecerTiempoActual(tCaida)
        assertTrue(gestor.haLlegadoAlSuelo())
        assertEquals(0.0, gestor.obtenerAlturaActual())
        assertFalse(gestor.estaEnEjecucion())
    }
}
