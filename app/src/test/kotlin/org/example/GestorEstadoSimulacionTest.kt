package org.example

import kotlin.test.*

class GestorEstadoSimulacionTest {
    /**
     * Pruebas unitarias de las propiedades observables de parámetros iniciales e
     * indicadores instantáneos en [GestorEstadoSimulacion].
     *
     * Verifica la propagación reactiva de cambios hacia los listeners de la UI y
     * el reinicio automático del estado instantáneo al modificar el escenario.
     */

    private val configuracion = ConfiguracionFisica()

    /**
     * Modificar la altura inicial debe notificar inmediatamente a los
     * suscriptores de `alturaInicialObservable()`.
     */
    @Test
    fun alturaInicialSePublicaEnLaPropiedadObservable() {
        val gestor = NucleoSimulacion().contexto()
        var ultimoValorNotificado = Double.NaN
        gestor.alturaInicialObservable().addListener { _, _, nuevoValor ->
            ultimoValorNotificado = nuevoValor.toDouble()
        }
        gestor.establecerAlturaInicial(245.5)
        assertEquals(245.5, gestor.obtenerAlturaInicial())
        assertEquals(245.5, ultimoValorNotificado)
    }

    /**
     * Modificar la velocidad inicial debe notificar inmediatamente a los
     * suscriptores de `velocidadInicialObservable()`.
     */
    @Test
    fun velocidadInicialSePublicaEnLaPropiedadObservable() {
        val gestor = NucleoSimulacion().contexto()
        var ultimoValorNotificado = Double.NaN
        gestor.velocidadInicialObservable().addListener { _, _, nuevoValor ->
            ultimoValorNotificado = nuevoValor.toDouble()
        }
        gestor.establecerVelocidadInicial(12.25)
        assertEquals(12.25, gestor.obtenerVelocidadInicial())
        assertEquals(12.25, ultimoValorNotificado)
    }

    /**
     * Al crearse el gestor, tanto los parámetros iniciales como las magnitudes
     * instantáneas reflejan los valores por defecto de [ConfiguracionFisica].
     */
    @Test
    fun losValoresPorDefectoSonLosParametrosIniciales() {
        val gestor = NucleoSimulacion().contexto()
        assertEquals(configuracion.alturaPorDefecto(), gestor.obtenerAlturaInicial())
        assertEquals(configuracion.velocidadPorDefecto(), gestor.obtenerVelocidadInicial())
        assertEquals(0.0, gestor.obtenerTiempoActual())
        assertEquals(configuracion.alturaPorDefecto(), gestor.obtenerAlturaActual())
        assertEquals(configuracion.velocidadPorDefecto(), gestor.obtenerVelocidadActual())
        assertFalse(gestor.estaEnEjecucion())
    }

    /**
     * Establecer manualmente un tiempo `t` actualiza de forma coherente la
     * altura y(t) y la velocidad v(t) según la gravedad del planeta activo.
     */
    @Test
    fun establecerTiempoActualActualizaPropiedadesInstantaneasReactivamente() {
        val gestor = NucleoSimulacion().contexto()
        gestor.establecerAlturaInicial(100.0)
        gestor.establecerVelocidadInicial(0.0)
        val g = gestor.obtenerPlaneta().gravedad()
        gestor.establecerTiempoActual(1.0)
        assertEquals(1.0, gestor.obtenerTiempoActual(), 1e-6)
        assertEquals(100.0 - 0.5 * g, gestor.obtenerAlturaActual(), 1e-6)
        assertEquals(g, gestor.obtenerVelocidadActual(), 1e-6)
    }

    /**
     * Cambiar la altura inicial, la velocidad inicial o el entorno gravitatorio
     * debe reiniciar automáticamente el tiempo transcurrido a t = 0 s.
     */
    @Test
    fun cambiarParametrosOPlanetaReiniciaEstadoInstantaneo() {
        val gestor = NucleoSimulacion().contexto()
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
