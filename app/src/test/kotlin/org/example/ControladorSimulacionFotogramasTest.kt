package org.example

import kotlin.test.*

class ControladorSimulacionFotogramasTest {
    /**
     * Pruebas unitarias del bucle de fotogramas de [ControladorSimulacion]:
     * captura del primer pulso, integración del delta temporal, acotamiento ante
     * pausas del sistema operativo y detención automática al tocar el suelo.
     */

    private val calculo = CalculoCaidaLibre()

    /**
     * El primer pulso recibido tras iniciar solo registra la marca temporal de
     * referencia (`ultimoTiempoNano`) sin avanzar el tiempo físico.
     */
    @Test
    fun procesarPrimerFotogramaEstableceMarcaTemporalSinAvanzar() {
        val gestor = NucleoSimulacion().contexto()
        val controlador = ControladorSimulacion(gestor)
        gestor.establecerAlturaInicial(50.0)
        gestor.establecerVelocidadInicial(0.0)
        controlador.iniciar()
        val sigue = controlador.procesarFrame(1_000_000_000L)
        assertTrue(sigue)
        assertEquals(0.0, gestor.obtenerTiempoActual())
        assertEquals(50.0, gestor.obtenerAlturaActual())
    }

    /**
     * Dos pulsos separados por 50 ms (50_000_000 ns) deben hacer avanzar el
     * tiempo en 0.05 s y recalcular posición y velocidad instantáneas.
     */
    @Test
    fun procesarFotogramasConsecutivosAvanzaEstadoFisico() {
        val gestor = NucleoSimulacion().contexto()
        val controlador = ControladorSimulacion(gestor)
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

    /**
     * Si entre dos fotogramas transcurre un lapso anormalmente grande (por
     * ejemplo, 2 s al arrastrar la ventana), el incremento se limita a
     * [ControladorSimulacion.deltaMaximoSegundos].
     */
    @Test
    fun procesarFotogramaConDeltaExcesivoSeLimitaAUmbralSeguro() {
        val gestor = NucleoSimulacion().contexto()
        val controlador = ControladorSimulacion(gestor)
        controlador.iniciar()
        controlador.procesarFrame(1_000_000_000L)
        controlador.procesarFrame(3_000_000_000L)
        assertEquals(controlador.deltaMaximoSegundos(), gestor.obtenerTiempoActual(), 1e-6)
    }

    /**
     * Al acumular suficientes fotogramas para alcanzar el suelo, el controlador
     * detiene la simulación y clava el estado final en el instante de impacto.
     */
    @Test
    fun procesarFotogramaDetieneSimulacionAlDetectarImpacto() {
        val gestor = NucleoSimulacion().contexto()
        val controlador = ControladorSimulacion(gestor)
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

    /**
     * Si el contexto no está en ejecución, `procesarFrame` no modifica el estado
     * y devuelve `false`.
     */
    @Test
    fun procesarFotogramaCuandoNoEstaEnEjecucionRetornaFalso() {
        val gestor = NucleoSimulacion().contexto()
        val controlador = ControladorSimulacion(gestor)
        assertFalse(gestor.estaEnEjecucion())
        assertFalse(controlador.procesarFrame(1_000_000_000L))
    }

    /**
     * Si el objeto ya había llegado al suelo y el usuario vuelve a pulsar
     * Iniciar, la simulación se reinicia automáticamente desde t = 0.
     */
    @Test
    fun iniciarReiniciaAutomaticamenteSiYaEstabaEnElSuelo() {
        val gestor = NucleoSimulacion().contexto()
        val controlador = ControladorSimulacion(gestor)
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
