package org.example

import javafx.beans.value.ObservableValue

class GestorEstadoSimulacion(
    parametros: MemoriaParametrosSimulacion,
    instantanea: MemoriaInstantaneaSimulacion,
    motor: MotorSimulacion,
    estadoInicial: EstadoEjecucion = instantanea.crearEstadoInicial()
) : ContextoSimulacion {
    constructor(
        instantanea: MemoriaInstantaneaSimulacion,
        planetaInicial: EntornoGravitatorio,
        motor: MotorSimulacion
    ) : this(
        parametros = instantanea.crearMemoriaParametros(planetaInicial),
        instantanea = instantanea,
        motor = motor
    )

    /**
     * Gestor de estado reactivo del simulador de caída libre.
     *
     * Implementa [ContextoSimulacion] ([EstadoSimulacion] y [ComandosSimulacion])
     * para que las vistas dependan de abstracciones (DIP). Coordina la máquina de
     * estados ([EstadoEjecucion]) y delega las reglas físicas en [MotorSimulacion].
     */

    private val contextoInterno = Pair(
        Triple(parametros, instantanea, motor),
        arrayOf(estadoInicial)
    )

    init {
        vincularParametros()
    }

    private fun vincularParametros() {
        contextoInterno.first.first.vincularReinicio { reiniciarSimulacion() }
    }

    override fun obtenerPlaneta() = contextoInterno.first.first.obtenerPlaneta()

    override fun obtenerAlturaInicial(): Double = contextoInterno.first.first.obtenerAlturaInicial()

    override fun obtenerVelocidadInicial(): Double = contextoInterno.first.first.obtenerVelocidadInicial()

    override fun obtenerTiempoActual(): Double = contextoInterno.first.second.obtenerTiempoActual()

    override fun obtenerAlturaActual(): Double = contextoInterno.first.second.obtenerAlturaActual()

    override fun obtenerVelocidadActual(): Double = contextoInterno.first.second.obtenerVelocidadActual()

    override fun estaEnEjecucion(): Boolean = contextoInterno.second[0].esActiva()

    override fun haLlegadoAlSuelo(): Boolean =
        contextoInterno.first.third.haLlegadoAlSuelo(tiempoTotalCaida(), obtenerTiempoActual(), obtenerAlturaActual())

    private fun tiempoTotalCaida(): Double =
        contextoInterno.first.third.tiempoCaida(obtenerAlturaInicial(), obtenerVelocidadInicial(), obtenerPlaneta().gravedad())

    override fun planetaObservable(): ObservableValue<EntornoGravitatorio> = contextoInterno.first.first.planetaObservable()

    override fun alturaInicialObservable(): ObservableValue<Number> = contextoInterno.first.first.alturaInicialObservable()

    override fun velocidadInicialObservable(): ObservableValue<Number> = contextoInterno.first.first.velocidadInicialObservable()

    override fun tiempoActualObservable(): ObservableValue<Number> = contextoInterno.first.second.tiempoActualObservable()

    override fun alturaActualObservable(): ObservableValue<Number> = contextoInterno.first.second.alturaActualObservable()

    override fun velocidadActualObservable(): ObservableValue<Number> = contextoInterno.first.second.velocidadActualObservable()

    override fun enEjecucionObservable(): ObservableValue<Boolean> = contextoInterno.first.second.enEjecucionObservable()

    override fun estadoEjecucion() = contextoInterno.second[0]

    override fun establecerPlaneta(planeta: EntornoGravitatorio) = contextoInterno.first.first.establecerPlaneta(planeta)

    override fun establecerAlturaInicial(altura: Double) = contextoInterno.first.first.establecerAlturaInicial(altura)

    override fun establecerVelocidadInicial(velocidad: Double) = contextoInterno.first.first.establecerVelocidadInicial(velocidad)

    override fun cambiarEstado(nuevoEstado: EstadoEjecucion): EstadoEjecucion {
        contextoInterno.second[0] = nuevoEstado
        contextoInterno.first.second.actualizarEjecucion(nuevoEstado.esActiva())
        return nuevoEstado
    }

    override fun iniciarSimulacion() {
        cambiarEstado(contextoInterno.second[0].iniciar())
        if (haLlegadoAlSuelo() || obtenerTiempoActual() >= tiempoTotalCaida()) {
            reiniciarSimulacion()
        }
        if (obtenerAlturaInicial() > 0.0) {
            cambiarEstado(contextoInterno.second[0].iniciar())
        }
    }

    override fun pausarSimulacion() {
        cambiarEstado(contextoInterno.second[0].pausar())
    }

    override fun reiniciarSimulacion() {
        val reiniciado = contextoInterno.second[0].reiniciar()
        contextoInterno.second[0] = reiniciado
        contextoInterno.first.second.reiniciar(obtenerAlturaInicial(), obtenerVelocidadInicial(), reiniciado.esActiva())
    }

    override fun avanzarTiempo(deltaSegundos: Double): Boolean {
        if (deltaSegundos <= 0.0 || !contextoInterno.second[0].esActiva()) return false
        establecerTiempoActual(contextoInterno.first.second.obtenerTiempoActual() + deltaSegundos)
        return contextoInterno.second[0].esActiva()
    }

    /** Establece el tiempo instantáneo y recalcula altura y velocidad. */
    fun establecerTiempoActual(nuevoTiempo: Double) {
        val calculado = contextoInterno.first.third.estadoEn(obtenerAlturaInicial(), obtenerVelocidadInicial(), obtenerPlaneta().gravedad(), nuevoTiempo)
        contextoInterno.first.second.aplicarEstado(calculado)
        if (calculado.enSuelo()) {
            cambiarEstado(contextoInterno.second[0].reiniciar())
        }
    }
}
