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

    private val calculo = CalculoCaidaLibre()

    @Test
    fun alturaInicialSePublicaEnLaPropiedadObservable() {
        val gestor = GestorEstadoSimulacion()
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
        val gestor = GestorEstadoSimulacion()
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
        val gestor = GestorEstadoSimulacion()

        assertEquals(ConfiguracionFisica().alturaPorDefecto(), gestor.obtenerAlturaInicial())
        assertEquals(ConfiguracionFisica().velocidadPorDefecto(), gestor.obtenerVelocidadInicial())
        assertEquals(0.0, gestor.obtenerTiempoActual())
        assertEquals(ConfiguracionFisica().alturaPorDefecto(), gestor.obtenerAlturaActual())
        assertEquals(ConfiguracionFisica().velocidadPorDefecto(), gestor.obtenerVelocidadActual())
        assertFalse(gestor.estaEnEjecucion())
    }

    @Test
    fun establecerTiempoActualActualizaPropiedadesInstantaneasReactiamente() {
        val gestor = GestorEstadoSimulacion()
        // h0 = 100 m, v0 = 0 m/s, Tierra (g = 9.81)
        gestor.establecerAlturaInicial(100.0)
        gestor.establecerVelocidadInicial(0.0)

        // t = 1 s -> y = 100 - 0.5 * g * 1², v = g
        val g = Planeta.TIERRA.gravedad()
        gestor.establecerTiempoActual(1.0)

        assertEquals(1.0, gestor.obtenerTiempoActual(), 1e-6)
        assertEquals(100.0 - 0.5 * g, gestor.obtenerAlturaActual(), 1e-6)
        assertEquals(g, gestor.obtenerVelocidadActual(), 1e-6)
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
        // h0 = 4.9 m, v0 = 0, Tierra g = 9.81 -> t_caida ≈ 0.999 s
        gestor.establecerAlturaInicial(4.9)
        gestor.establecerVelocidadInicial(0.0)

        gestor.iniciarSimulacion()
        assertTrue(gestor.estaEnEjecucion())

        // Avanzar 0.5 segundos -> todavía en el aire
        val sigue1 = gestor.avanzarTiempo(0.5)
        assertTrue(sigue1)
        assertTrue(gestor.estaEnEjecucion())
        assertEquals(0.5, gestor.obtenerTiempoActual(), 1e-6)

        // Avanzar 0.6 segundos más -> supera el tiempo de caída -> llega al suelo
        val sigue2 = gestor.avanzarTiempo(0.6)
        assertFalse(sigue2)
        assertFalse(gestor.estaEnEjecucion())

        val g = Planeta.TIERRA.gravedad()
        val tiempoCaida = calculo.calcularTiempoCaida(4.9, 0.0, g)!!
        val velocidadImpacto = calculo.calcularVelocidadImpacto(4.9, 0.0, g)!!

        assertEquals(tiempoCaida, gestor.obtenerTiempoActual(), 1e-6)
        assertEquals(0.0, gestor.obtenerAlturaActual(), 1e-6)
        assertEquals(velocidadImpacto, gestor.obtenerVelocidadActual(), 1e-6)
        assertTrue(gestor.haLlegadoAlSuelo())
    }

    @Test
    fun iniciarSimulacionConAlturaCeroNoSeEjecuta() {
        val gestor = GestorEstadoSimulacion()
        gestor.establecerAlturaInicial(0.0)

        gestor.iniciarSimulacion()

        assertFalse(gestor.estaEnEjecucion())
        assertEquals(0.0, gestor.obtenerAlturaActual())
        assertTrue(gestor.haLlegadoAlSuelo())
    }

    @Test
    fun haLlegadoAlSueloEsFalsoDuranteVueloYVerdaderoAlFinalizar() {
        val gestor = GestorEstadoSimulacion()
        gestor.establecerAlturaInicial(100.0)
        gestor.establecerVelocidadInicial(0.0)

        // En reposo arriba
        assertFalse(gestor.haLlegadoAlSuelo())

        gestor.iniciarSimulacion()
        gestor.establecerTiempoActual(2.0)
        assertFalse(gestor.haLlegadoAlSuelo())

        // Calculamos tiempo de caída y avanzamos hasta el final
        val tCaida = calculo.calcularTiempoCaida(100.0, 0.0, Planeta.TIERRA.gravedad())!!
        gestor.establecerTiempoActual(tCaida)
        assertTrue(gestor.haLlegadoAlSuelo())
        assertEquals(0.0, gestor.obtenerAlturaActual())
        assertFalse(gestor.estaEnEjecucion())
    }
}