package org.example

import kotlin.test.*

class ControladorSimulacionTest {
    /**
     * Pruebas unitarias de los controles de reproducción de [ControladorSimulacion]
     * (iniciar, pausar, alternar y reiniciar).
     *
     * Verifica las transiciones de estado del ciclo de vida de la simulación
     * interactuando con el contexto reactivo provisto por [NucleoSimulacion].
     */

    private val nucleo = NucleoSimulacion()

    /**
     * Al instanciar el controlador sobre un contexto recién creado, la
     * simulación debe encontrarse detenida.
     */
    @Test
    fun instanciarControlador() {
        val gestor = NucleoSimulacion().contexto()
        val controlador = ControladorSimulacion(gestor)
        assertNotNull(controlador)
        assertFalse(gestor.estaEnEjecucion())
    }

    /**
     * El comando [ControladorSimulacion.iniciar] debe activar el estado de
     * ejecución en el contexto cuando la altura inicial es positiva.
     */
    @Test
    fun iniciarActivaEjecucion() {
        val gestor = NucleoSimulacion().contexto()
        val controlador = ControladorSimulacion(gestor)
        controlador.iniciar()
        assertTrue(gestor.estaEnEjecucion())
    }

    /**
     * El comando [ControladorSimulacion.pausar] debe detener inmediatamente la
     * ejecución conservando el instante actual.
     */
    @Test
    fun pausarDetieneEjecucion() {
        val gestor = NucleoSimulacion().contexto()
        val controlador = ControladorSimulacion(gestor)
        controlador.iniciar()
        assertTrue(gestor.estaEnEjecucion())
        controlador.pausar()
        assertFalse(gestor.estaEnEjecucion())
    }

    /**
     * El comando [ControladorSimulacion.alternarEjecucion] debe conmutar entre
     * los estados en curso y pausado en llamadas sucesivas.
     */
    @Test
    fun alternarEjecucionConmutaEntreIniciarYPausar() {
        val gestor = NucleoSimulacion().contexto()
        val controlador = ControladorSimulacion(gestor)
        controlador.alternarEjecucion()
        assertTrue(gestor.estaEnEjecucion())
        controlador.alternarEjecucion()
        assertFalse(gestor.estaEnEjecucion())
    }

    /**
     * El comando [ControladorSimulacion.reiniciar] debe detener el temporizador
     * y restaurar t = 0, y = h₀ y v = v₀ tras haber avanzado varios fotogramas.
     */
    @Test
    fun reiniciarRestableceEstadoAValoresIniciales() {
        val gestor = nucleo.contexto()
        val controlador = ControladorSimulacion(gestor)
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
