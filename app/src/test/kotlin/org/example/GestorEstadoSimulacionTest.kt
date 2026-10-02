package org.example

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Pruebas de la reactividad del [GestorEstadoSimulacion]:
 * - Actualización de parámetros iniciales (altura, velocidad, planeta).
 * - Reactividad de las propiedades instantáneas (tiempo, altura y velocidad actual).
 * - Avance y reinicio de la simulación.
 */
class GestorEstadoSimulacionTest {

    @Test
    fun alturaInicialSePublicaEnLaPropiedadObservable() {
        val gestor = GestorEstadoSimulacion()
        var ultimoValorNotificado = Double.NaN

        gestor.alturaInicialProperty.addListener { _, _, nuevoValor ->
            ultimoValorNotificado = nuevoValor.toDouble()
        }

        gestor.establecerAlturaInicial(245.5)

        assertEquals(245.5, gestor.obtenerAlturaInicial())
        assertEquals(245.5, ultimoValorNotificado)
    }

    @Test
    fun velocidadInicialSePublicaEnLaPropiedadObservable() {
        val gestor = GestorEstadoSimulacion()
        var ultimoValorNotificado = Double.NaN

        gestor.velocidadInicialProperty.addListener { _, _, nuevoValor ->
            ultimoValorNotificado = nuevoValor.toDouble()
        }

        gestor.establecerVelocidadInicial(12.25)

        assertEquals(12.25, gestor.obtenerVelocidadInicial())
        assertEquals(12.25, ultimoValorNotificado)
    }

    @Test
    fun losValoresPorDefectoSonLosParametrosIniciales() {
        val gestor = GestorEstadoSimulacion()

        assertEquals(ParametrosIniciales.ALTURA_DEFECTO, gestor.obtenerAlturaInicial())
        assertEquals(ParametrosIniciales.VELOCIDAD_DEFECTO, gestor.obtenerVelocidadInicial())
        assertEquals(0.0, gestor.obtenerTiempoActual())
        assertEquals(ParametrosIniciales.ALTURA_DEFECTO, gestor.obtenerAlturaActual())
        assertEquals(ParametrosIniciales.VELOCIDAD_DEFECTO, gestor.obtenerVelocidadActual())
        assertFalse(gestor.estaEnEjecucion())
    }

    @Test
    fun establecerTiempoActualActualizaPropiedadesInstantaneasReactiamente() {
        val gestor = GestorEstadoSimulacion()
        // h0 = 100 m, v0 = 0 m/s, Tierra (g = 9.8)
        gestor.establecerAlturaInicial(100.0)
        gestor.establecerVelocidadInicial(0.0)

        // t = 1 s -> y = 100 - 0.5 * 9.8 * 1 = 95.1 m, v = 9.8 m/s
        gestor.establecerTiempoActual(1.0)

        assertEquals(1.0, gestor.obtenerTiempoActual(), 1e-6)
        assertEquals(95.1, gestor.obtenerAlturaActual(), 1e-6)
        assertEquals(9.8, gestor.obtenerVelocidadActual(), 1e-6)
    }

    @Test
    fun cambiarParametrosInicialesReiniciaEstadoInstantaneo() {
        val gestor = GestorEstadoSimulacion()
        gestor.establecerTiempoActual(2.0)

        // Cambiar la altura inicial debe reiniciar el tiempo a 0 y actualizar la altura actual
        gestor.establecerAlturaInicial(200.0)

        assertEquals(0.0, gestor.obtenerTiempoActual())
        assertEquals(200.0, gestor.obtenerAlturaActual())
        assertEquals(0.0, gestor.obtenerVelocidadActual())
    }

    @Test
    fun cambiarPlanetaReiniciaEstadoInstantaneo() {
        val gestor = GestorEstadoSimulacion()
        gestor.establecerTiempoActual(1.5)

        gestor.establecerPlaneta(Planeta.LUNA)

        assertEquals(Planeta.LUNA, gestor.obtenerPlaneta())
        assertEquals(0.0, gestor.obtenerTiempoActual())
        assertEquals(gestor.obtenerAlturaInicial(), gestor.obtenerAlturaActual())
    }

    @Test
    fun avanzarTiempoDetieneLaSimulacionAlLlegarAlSuelo() {
        val gestor = GestorEstadoSimulacion()
        // h0 = 4.9 m, v0 = 0, g = 9.8 -> t_caida = 1.0 s
        gestor.establecerAlturaInicial(4.9)
        gestor.establecerVelocidadInicial(0.0)

        gestor.iniciarSimulacion()
        assertTrue(gestor.estaEnEjecucion())

        // Avanzar 0.5 segundos -> todavía en el aire
        val sigue1 = gestor.avanzarTiempo(0.5)
        assertTrue(sigue1)
        assertTrue(gestor.estaEnEjecucion())
        assertEquals(0.5, gestor.obtenerTiempoActual(), 1e-6)

        // Avanzar 0.6 segundos más -> total 1.1s > t_caida (1.0s) -> llega al suelo
        val sigue2 = gestor.avanzarTiempo(0.6)
        assertFalse(sigue2)
        assertFalse(gestor.estaEnEjecucion())
        assertEquals(1.0, gestor.obtenerTiempoActual(), 1e-6)
        assertEquals(0.0, gestor.obtenerAlturaActual(), 1e-6)
        assertEquals(9.8, gestor.obtenerVelocidadActual(), 1e-6)
    }
}