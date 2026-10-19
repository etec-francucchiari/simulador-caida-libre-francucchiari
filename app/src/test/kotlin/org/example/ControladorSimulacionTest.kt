package org.example

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Pruebas unitarias para [ControladorSimulacion] (Issue #5):
 * - Bucle de animación dinámico (cálculo de delta, procesamiento de fotogramas).
 * - Controles de reproducción (iniciar, pausar, alternar, reiniciar).
 * - Limitación de delta por seguridad ante retrasos.
 * - Detección de colisión / detención automática cuando el objeto llega al suelo (y <= 0).
 * - Reinicio automático al iniciar una simulación ya finalizada.
 */
class ControladorSimulacionTest {

    private val calculo = CalculoCaidaLibre()

    @Test
    fun instanciarControlador() {
        val gestor = GestorEstadoSimulacion(ConfiguracionFisica(), Planeta.TIERRA, MotorEstadoCaida(CalculoCaidaLibre()))
        val controlador = ControladorSimulacion(gestor)
        assertNotNull(controlador)
        assertFalse(gestor.estaEnEjecucion())
    }

    @Test
    fun iniciarActivaEjecucion() {
        val gestor = GestorEstadoSimulacion(ConfiguracionFisica(), Planeta.TIERRA, MotorEstadoCaida(CalculoCaidaLibre()))
        val controlador = ControladorSimulacion(gestor)

        controlador.iniciar()
        assertTrue(gestor.estaEnEjecucion())
    }

    @Test
    fun pausarDetieneEjecucion() {
        val gestor = GestorEstadoSimulacion(ConfiguracionFisica(), Planeta.TIERRA, MotorEstadoCaida(CalculoCaidaLibre()))
        val controlador = ControladorSimulacion(gestor)

        controlador.iniciar()
        assertTrue(gestor.estaEnEjecucion())

        controlador.pausar()
        assertFalse(gestor.estaEnEjecucion())
    }

    @Test
    fun alternarEjecucionConmutaEntreIniciarYPausar() {
        val gestor = GestorEstadoSimulacion(ConfiguracionFisica(), Planeta.TIERRA, MotorEstadoCaida(CalculoCaidaLibre()))
        val controlador = ControladorSimulacion(gestor)

        // Estado inicial: detenido -> alternar debe iniciar
        controlador.alternarEjecucion()
        assertTrue(gestor.estaEnEjecucion())

        // Estado activo -> alternar debe pausar
        controlador.alternarEjecucion()
        assertFalse(gestor.estaEnEjecucion())
    }

    @Test
    fun reiniciarRestableceEstadoAValoresIniciales() {
        val gestor = GestorEstadoSimulacion(ConfiguracionFisica(), Planeta.TIERRA, MotorEstadoCaida(CalculoCaidaLibre()))
        val controlador = ControladorSimulacion(gestor)

        gestor.establecerAlturaInicial(100.0)
        gestor.establecerVelocidadInicial(10.0)

        controlador.iniciar()
        // Simulamos avance en el tiempo
        controlador.procesarFrame(1_000_000_000L)
        controlador.procesarFrame(1_500_000_000L)

        assertTrue(gestor.obtenerTiempoActual() > 0.0)

        controlador.reiniciar()

        assertFalse(gestor.estaEnEjecucion())
        assertEquals(0.0, gestor.obtenerTiempoActual())
        assertEquals(100.0, gestor.obtenerAlturaActual())
        assertEquals(10.0, gestor.obtenerVelocidadActual())
    }

    @Test
    fun procesarPrimerFotogramaEstableceMarcaTemporalSinAvanzar() {
        val gestor = GestorEstadoSimulacion(ConfiguracionFisica(), Planeta.TIERRA, MotorEstadoCaida(CalculoCaidaLibre()))
        val controlador = ControladorSimulacion(gestor)

        gestor.establecerAlturaInicial(50.0)
        gestor.establecerVelocidadInicial(0.0)
        controlador.iniciar()

        // El primer fotograma sólo inicializa el timestamp base
        val sigue = controlador.procesarFrame(1_000_000_000L)
        assertTrue(sigue)
        assertEquals(0.0, gestor.obtenerTiempoActual())
        assertEquals(50.0, gestor.obtenerAlturaActual())
    }

    @Test
    fun procesarFotogramasConsecutivosAvanzaEstadoFisico() {
        val gestor = GestorEstadoSimulacion(ConfiguracionFisica(), Planeta.TIERRA, MotorEstadoCaida(CalculoCaidaLibre()))
        val controlador = ControladorSimulacion(gestor)

        // h0 = 100 m, v0 = 0 m/s, Tierra g = 9.81 m/s²
        gestor.establecerAlturaInicial(100.0)
        gestor.establecerVelocidadInicial(0.0)
        gestor.establecerPlaneta(Planeta.TIERRA)

        controlador.iniciar()

        // Primer frame en t = 1.0 s (marca base)
        controlador.procesarFrame(1_000_000_000L)

        // Segundo frame en t = 1.05 s (delta = 0.05 s = 50 ms)
        val sigue = controlador.procesarFrame(1_050_000_000L)
        assertTrue(sigue)
        assertEquals(0.05, gestor.obtenerTiempoActual(), 1e-6)

        // Posición teórica: 100 - 0.5 * g * 0.05²
        val g = Planeta.TIERRA.gravedad()
        assertEquals(100.0 - 0.5 * g * 0.05 * 0.05, gestor.obtenerAlturaActual(), 1e-5)
        // Velocidad teórica: g * 0.05
        assertEquals(g * 0.05, gestor.obtenerVelocidadActual(), 1e-5)
    }

    @Test
    fun procesarFotogramaConDeltaExcesivoSeLimitaAUmbralSeguro() {
        val gestor = GestorEstadoSimulacion(ConfiguracionFisica(), Planeta.TIERRA, MotorEstadoCaida(CalculoCaidaLibre()))
        val controlador = ControladorSimulacion(gestor)

        controlador.iniciar()

        // Base en 1 segundo
        controlador.procesarFrame(1_000_000_000L)

        // Salto excesivo de 2 segundos (2_000_000_000 ns de diferencia)
        controlador.procesarFrame(3_000_000_000L)

        // Debe haberse acotado a DELTA_MAXIMO_SEGUNDOS (0.1 s)
        assertEquals(
            controlador.deltaMaximoSegundos(),
            gestor.obtenerTiempoActual(),
            1e-6
        )
    }

    @Test
    fun procesarFotogramaDetieneSimulacionAlDetectarImpacto() {
        val gestor = GestorEstadoSimulacion(ConfiguracionFisica(), Planeta.TIERRA, MotorEstadoCaida(CalculoCaidaLibre()))
        val controlador = ControladorSimulacion(gestor)

        // h0 = 4.9 m, v0 = 0 m/s, Tierra g = 9.81 m/s² -> t_caida ≈ 0.999 s
        gestor.establecerAlturaInicial(4.9)
        gestor.establecerVelocidadInicial(0.0)

        controlador.iniciar()

        // Primer frame
        controlador.procesarFrame(1_000_000_000L)

        // Simulamos 10 pasos de 0.1 s (total 1.0 s)
        var tiempoNano = 1_000_000_000L
        for (i in 1..9) {
            tiempoNano += 100_000_000L // +0.1s
            val sigue = controlador.procesarFrame(tiempoNano)
            assertTrue(sigue, "Debería continuar en el aire en el paso $i")
            assertTrue(gestor.estaEnEjecucion())
        }

        // Paso 10: alcanza el tiempo de caída -> colisión con el suelo
        tiempoNano += 100_000_000L
        val sigueFinal = controlador.procesarFrame(tiempoNano)

        val g = Planeta.TIERRA.gravedad()
        val tiempoCaida = calculo.calcularTiempoCaida(4.9, 0.0, g)!!
        val velocidadImpacto = calculo.calcularVelocidadImpacto(4.9, 0.0, g)!!

        assertFalse(sigueFinal, "Debe finalizar al tocar el suelo")
        assertFalse(gestor.estaEnEjecucion(), "La simulación debe detenerse automáticamente")
        assertEquals(0.0, gestor.obtenerAlturaActual(), 1e-6)
        assertEquals(tiempoCaida, gestor.obtenerTiempoActual(), 1e-6)
        assertEquals(velocidadImpacto, gestor.obtenerVelocidadActual(), 1e-6)
        assertTrue(gestor.haLlegadoAlSuelo())
    }

    @Test
    fun procesarFotogramaCuandoNoEstaEnEjecucionRetornaFalso() {
        val gestor = GestorEstadoSimulacion(ConfiguracionFisica(), Planeta.TIERRA, MotorEstadoCaida(CalculoCaidaLibre()))
        val controlador = ControladorSimulacion(gestor)

        assertFalse(gestor.estaEnEjecucion())
        val resultado = controlador.procesarFrame(1_000_000_000L)
        assertFalse(resultado)
    }

    @Test
    fun iniciarReiniciaAutomaticamenteSiYaEstabaEnElSuelo() {
        val gestor = GestorEstadoSimulacion(ConfiguracionFisica(), Planeta.TIERRA, MotorEstadoCaida(CalculoCaidaLibre()))
        val controlador = ControladorSimulacion(gestor)

        // h0 = 4.9 m, g = 9.8 -> t_caida = 1.0 s
        gestor.establecerAlturaInicial(4.9)
        gestor.establecerVelocidadInicial(0.0)

        // Avanzamos hasta el impacto en el suelo
        gestor.iniciarSimulacion()
        gestor.establecerTiempoActual(1.0)
        assertFalse(gestor.estaEnEjecucion())
        assertEquals(0.0, gestor.obtenerAlturaActual())

        // Iniciar nuevamente debe reiniciar y comenzar a correr
        controlador.iniciar()
        assertTrue(gestor.estaEnEjecucion())
        assertEquals(0.0, gestor.obtenerTiempoActual())
        assertEquals(4.9, gestor.obtenerAlturaActual())
    }
}
