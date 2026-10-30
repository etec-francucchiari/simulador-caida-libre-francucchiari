package org.example

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue

/**
 * Gestor de estado reactivo del simulador de caída libre.
 *
 * Implementa los contratos [EstadoSimulacion] y [ComandosSimulacion] para que
 * las vistas dependan de abstracciones (DIP). Aloja la máquina de estados de
 * la simulación mediante el Patrón State ([EstadoEjecucion]) y delega las reglas
 * físicas de avance temporal en [MotorEstadoCaida] (SRP).
 *
 * Encapsulación estricta: todas las propiedades observables son campos
 * [private]; solo se exponen como [ObservableValue] de solo lectura mediante
 * métodos, además de los métodos de consulta y comando.
 *
 * @property configuracion valores por defecto y límites físicos.
 * @property motor motor de cálculo del estado instantáneo.
 * @property estadoInicial estado inicial de la máquina de estados.
 */
class GestorEstadoSimulacion(
    private val configuracion: ConfiguracionSimulacion,
    planetaInicial: EntornoGravitatorio,
    private val motor: MotorSimulacion,
    private val estadoInicial: EstadoEjecucion
) : ContextoSimulacion {

    private val planetaActivo = SimpleObjectProperty<EntornoGravitatorio>(planetaInicial)
    private val alturaInicial = SimpleDoubleProperty(configuracion.alturaPorDefecto())
    private val velocidadInicial = SimpleDoubleProperty(configuracion.velocidadPorDefecto())
    private val tiempoActual = SimpleDoubleProperty(0.0)
    private val alturaActual = SimpleDoubleProperty(configuracion.alturaPorDefecto())
    private val velocidadActual = SimpleDoubleProperty(configuracion.velocidadPorDefecto())
    private val enEjecucion = SimpleBooleanProperty(false)

    private var estadoActual: EstadoEjecucion = estadoInicial

    init {
        // Al modificar cualquier parámetro inicial o el planeta, se reinicia
        // el estado instantáneo a t = 0 con los nuevos valores de partida.
        alturaInicial.addListener { _, _, _ -> reiniciarSimulacion() }
        velocidadInicial.addListener { _, _, _ -> reiniciarSimulacion() }
        planetaActivo.addListener { _, _, _ -> reiniciarSimulacion() }
    }

    // ------------------------------------------------------------------
    // EstadoSimulacion: consultas
    // ------------------------------------------------------------------

    override fun obtenerPlaneta(): EntornoGravitatorio = planetaActivo.get()

    override fun obtenerAlturaInicial(): Double = alturaInicial.get()

    override fun obtenerVelocidadInicial(): Double = velocidadInicial.get()

    override fun obtenerTiempoActual(): Double = tiempoActual.get()

    override fun obtenerAlturaActual(): Double = alturaActual.get()

    override fun obtenerVelocidadActual(): Double = velocidadActual.get()

    override fun estaEnEjecucion(): Boolean = estadoActual.esActiva()

    override fun haLlegadoAlSuelo(): Boolean =
        motor.haLlegadoAlSuelo(
            obtenerAlturaInicial(),
            obtenerVelocidadInicial(),
            obtenerPlaneta().gravedad(),
            obtenerTiempoActual(),
            obtenerAlturaActual()
        )

    // ------------------------------------------------------------------
    // EstadoSimulacion: observables de solo lectura
    // ------------------------------------------------------------------

    override fun planetaObservable(): ObservableValue<EntornoGravitatorio> = planetaActivo

    override fun alturaInicialObservable(): ObservableValue<Number> = alturaInicial

    override fun velocidadInicialObservable(): ObservableValue<Number> = velocidadInicial

    override fun tiempoActualObservable(): ObservableValue<Number> = tiempoActual

    override fun alturaActualObservable(): ObservableValue<Number> = alturaActual

    override fun velocidadActualObservable(): ObservableValue<Number> = velocidadActual

    override fun enEjecucionObservable(): ObservableValue<Boolean> = enEjecucion

    override fun estadoEjecucion(): EstadoEjecucion = estadoActual

    // ------------------------------------------------------------------
    // ComandosSimulacion: aplicación de estados
    // ------------------------------------------------------------------

    override fun establecerPlaneta(planeta: EntornoGravitatorio) {
        planetaActivo.set(planeta)
    }

    override fun establecerAlturaInicial(altura: Double) {
        alturaInicial.set(altura)
    }

    override fun establecerVelocidadInicial(velocidad: Double) {
        velocidadInicial.set(velocidad)
    }

    override fun cambiarEstado(nuevoEstado: EstadoEjecucion): EstadoEjecucion {
        estadoActual = nuevoEstado
        enEjecucion.set(nuevoEstado.esActiva())
        return nuevoEstado
    }

    // ------------------------------------------------------------------
    // ComandosSimulacion: control de la máquina de estados
    // ------------------------------------------------------------------

    override fun iniciarSimulacion() {
        val nuevoEstado = estadoActual.iniciar()
        cambiarEstado(nuevoEstado)

        val tiempoCaida = motor.tiempoCaida(
            obtenerAlturaInicial(),
            obtenerVelocidadInicial(),
            obtenerPlaneta().gravedad()
        )

        if (haLlegadoAlSuelo() || obtenerTiempoActual() >= tiempoCaida) {
            reiniciarSimulacion()
        }

        if (obtenerAlturaInicial() <= 0.0) {
            return
        }

        // Aplica la transición de inicio sobre el estado vigente (reaplica el
        // reinicio previo y arranca la ejecución cuando procede).
        cambiarEstado(estadoActual.iniciar())
    }

    override fun pausarSimulacion() {
        cambiarEstado(estadoActual.pausar())
    }

    override fun reiniciarSimulacion() {
        cambiarEstado(estadoActual.reiniciar())
        tiempoActual.set(0.0)
        alturaActual.set(obtenerAlturaInicial())
        velocidadActual.set(obtenerVelocidadInicial())
    }

    override fun avanzarTiempo(deltaSegundos: Double): Boolean {
        if (deltaSegundos <= 0.0 || !estaEnEjecucion()) return false

        establecerTiempoActual(obtenerTiempoActual() + deltaSegundos)

        return estaEnEjecucion()
    }

    /**
     * Establece el tiempo instantáneo y recalcula altura y velocidad.
     */
    fun establecerTiempoActual(nuevoTiempo: Double) {
        val estadoInstantaneo = motor.estadoEn(
            obtenerAlturaInicial(),
            obtenerVelocidadInicial(),
            obtenerPlaneta().gravedad(),
            nuevoTiempo
        )

        tiempoActual.set(estadoInstantaneo.tiempo())
        alturaActual.set(estadoInstantaneo.altura())
        velocidadActual.set(estadoInstantaneo.velocidad())

        if (estadoInstantaneo.enSuelo()) {
            cambiarEstado(EstadoEjecucionReiniciado())
        }
    }
}
