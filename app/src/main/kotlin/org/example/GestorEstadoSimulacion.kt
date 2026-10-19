package org.example

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue

/**
 * Gestor de estado reactivo del simulador de caída libre.
 *
 * Implementa los contratos [EstadoSimulacion] y [ComandosSimulacion] para que
 * las vistas dependan de abstracciones (DIP). Delega las reglas físicas de
 * avance temporal en [MotorEstadoCaida] (SRP).
 *
 * Encapsulación estricta: todas las propiedades observables son campos
 * [private]; solo se exponen como [ObservableValue] de solo lectura mediante
 * métodos, además de los métodos de consulta y comando.
 *
 * @property configuracion valores por defecto y límites físicos.
 * @property motor motor de cálculo del estado instantáneo.
 */
class GestorEstadoSimulacion(
    private val configuracion: ConfiguracionSimulacion,
    planetaInicial: EntornoGravitatorio,
    private val motor: MotorSimulacion
) : ContextoSimulacion {

    private val planetaActivo = SimpleObjectProperty<EntornoGravitatorio>(planetaInicial)
    private val alturaInicial = SimpleDoubleProperty(configuracion.alturaPorDefecto())
    private val velocidadInicial = SimpleDoubleProperty(configuracion.velocidadPorDefecto())
    private val tiempoActual = SimpleDoubleProperty(0.0)
    private val alturaActual = SimpleDoubleProperty(configuracion.alturaPorDefecto())
    private val velocidadActual = SimpleDoubleProperty(configuracion.velocidadPorDefecto())
    private val enEjecucion = SimpleBooleanProperty(false)

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

    override fun estaEnEjecucion(): Boolean = enEjecucion.get()

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

    // ------------------------------------------------------------------
    // ComandosSimulacion
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

    override fun haLlegadoAlSuelo(): Boolean =
        motor.haLlegadoAlSuelo(
            obtenerAlturaInicial(),
            obtenerVelocidadInicial(),
            obtenerPlaneta().gravedad(),
            obtenerTiempoActual(),
            obtenerAlturaActual()
        )

    override fun iniciarSimulacion() {
        val tiempoCaida = motor.tiempoCaida(
            obtenerAlturaInicial(),
            obtenerVelocidadInicial(),
            obtenerPlaneta().gravedad()
        )

        if (haLlegadoAlSuelo() || obtenerTiempoActual() >= tiempoCaida) {
            reiniciarSimulacion()
        }

        if (obtenerAlturaInicial() <= 0.0) {
            enEjecucion.set(false)
            return
        }

        enEjecucion.set(true)
    }

    override fun pausarSimulacion() {
        enEjecucion.set(false)
    }

    override fun reiniciarSimulacion() {
        enEjecucion.set(false)
        tiempoActual.set(0.0)
        alturaActual.set(obtenerAlturaInicial())
        velocidadActual.set(obtenerVelocidadInicial())
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
            enEjecucion.set(false)
        }
    }

    override fun avanzarTiempo(deltaSegundos: Double): Boolean {
        if (!estaEnEjecucion() || deltaSegundos <= 0.0) return false

        establecerTiempoActual(obtenerTiempoActual() + deltaSegundos)

        if (obtenerAlturaActual() <= 0.0 || haLlegadoAlSuelo()) {
            enEjecucion.set(false)
        }

        return estaEnEjecucion()
    }
}
