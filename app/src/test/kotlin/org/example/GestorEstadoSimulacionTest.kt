package org.example

import kotlin.test.*

class GestorEstadoSimulacionTest {
    /**
     * Pruebas unitarias de las propiedades observables de parámetros iniciales e
     * indicadores instantáneos en [GestorEstadoSimulacion].
     */

    private val configuracion = nuevoNucleo().configuracion()

    private fun nuevoNucleo() = NucleoSimulacion()

    @Test
    fun alturaInicialSePublicaEnLaPropiedadObservable() {
        val gestor = nuevoNucleo().contexto()
        var ultimoValorNotificado = Double.NaN
        gestor.alturaInicialObservable().addListener { _, _, nuevoValor ->
            ultimoValorNotificado = nuevoValor.toDouble()
        }
        gestor.establecerAlturaInicial(245.5)
        assertEquals(245.5, gestor.obtenerAlturaInicial())
        assertEquals(245.5, ultimoValorNotificado)
    }

    @Test
    fun velocidadInicialSePublicaEnLaPropiedadObservable() {
        val gestor = nuevoNucleo().contexto()
        var ultimoValorNotificado = Double.NaN
        gestor.velocidadInicialObservable().addListener { _, _, nuevoValor ->
            ultimoValorNotificado = nuevoValor.toDouble()
        }
        gestor.establecerVelocidadInicial(12.25)
        assertEquals(12.25, gestor.obtenerVelocidadInicial())
        assertEquals(12.25, ultimoValorNotificado)
    }

    @Test
    fun losValoresPorDefectoSonLosParametrosIniciales() {
        val gestor = nuevoNucleo().contexto()
        assertEquals(configuracion.alturaPorDefecto(), gestor.obtenerAlturaInicial())
        assertEquals(configuracion.velocidadPorDefecto(), gestor.obtenerVelocidadInicial())
        assertEquals(0.0, gestor.obtenerTiempoActual())
        assertEquals(configuracion.alturaPorDefecto(), gestor.obtenerAlturaActual())
        assertEquals(configuracion.velocidadPorDefecto(), gestor.obtenerVelocidadActual())
        assertFalse(gestor.estaEnEjecucion())
    }

    @Test
    fun establecerTiempoActualActualizaPropiedadesInstantaneasReactivamente() {
        val gestor = nuevoNucleo().contexto()
        gestor.establecerAlturaInicial(100.0)
        gestor.establecerVelocidadInicial(0.0)
        val g = gestor.obtenerPlaneta().gravedad()
        gestor.establecerTiempoActual(1.0)
        assertEquals(1.0, gestor.obtenerTiempoActual(), 1e-6)
        assertEquals(100.0 - 0.5 * g, gestor.obtenerAlturaActual(), 1e-6)
        assertEquals(g, gestor.obtenerVelocidadActual(), 1e-6)
    }

    @Test
    fun cambiarParametrosOPlanetaReiniciaEstadoInstantaneo() {
        val gestor = nuevoNucleo().contexto()
        gestor.establecerTiempoActual(2.0)
        gestor.establecerAlturaInicial(200.0)
        assertEquals(0.0, gestor.obtenerTiempoActual())
        assertEquals(200.0, gestor.obtenerAlturaActual())
        gestor.establecerTiempoActual(1.5)
        gestor.establecerPlaneta(Planeta.LUNA)
        assertEquals(Planeta.LUNA, gestor.obtenerPlaneta())
        assertEquals(0.0, gestor.obtenerTiempoActual())
    }
}
