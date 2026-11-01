package org.example

import javafx.beans.value.ObservableValue

class GestorEstadoSimulacion(
    private val parametros: MemoriaParametrosSimulacion,
    private val instantanea: MemoriaInstantaneaSimulacion,
    private val motor: MotorSimulacion,
    estadoInicial: EstadoEjecucion
) : ContextoSimulacion {
    /**
     * Gestor de estado reactivo del simulador de caída libre.
     *
     * Implementa [ContextoSimulacion] ([EstadoSimulacion] y [ComandosSimulacion])
     * para que las vistas dependan de abstracciones (DIP). Coordina la máquina de
     * estados ([EstadoEjecucion]) y delega las reglas físicas en [MotorSimulacion].
     */

    private var estadoActual: EstadoEjecucion = estadoInicial

    init {
        vincularParametros()
    }

    private fun vincularParametros() {
        parametros.vincularReinicio { reiniciarSimulacion() }
    }

    override fun obtenerPlaneta(): EntornoGravitatorio = parametros.obtenerPlaneta()

    override fun obtenerAlturaInicial(): Double = parametros.obtenerAlturaInicial()

    override fun obtenerVelocidadInicial(): Double = parametros.obtenerVelocidadInicial()

    override fun obtenerTiempoActual(): Double = instantanea.obtenerTiempoActual()

    override fun obtenerAlturaActual(): Double = instantanea.obtenerAlturaActual()

    override fun obtenerVelocidadActual(): Double = instantanea.obtenerVelocidadActual()

    override fun estaEnEjecucion(): Boolean = estadoActual.esActiva()

    override fun haLlegadoAlSuelo(): Boolean =
        motor.haLlegadoAlSuelo(tiempoTotalCaida(), obtenerTiempoActual(), obtenerAlturaActual())

    private fun tiempoTotalCaida(): Double =
        motor.tiempoCaida(obtenerAlturaInicial(), obtenerVelocidadInicial(), obtenerPlaneta().gravedad())

    override fun planetaObservable(): ObservableValue<EntornoGravitatorio> = parametros.planetaObservable()

    override fun alturaInicialObservable(): ObservableValue<Number> = parametros.alturaInicialObservable()

    override fun velocidadInicialObservable(): ObservableValue<Number> = parametros.velocidadInicialObservable()

    override fun tiempoActualObservable(): ObservableValue<Number> = instantanea.tiempoActualObservable()

    override fun alturaActualObservable(): ObservableValue<Number> = instantanea.alturaActualObservable()

    override fun velocidadActualObservable(): ObservableValue<Number> = instantanea.velocidadActualObservable()

    override fun enEjecucionObservable(): ObservableValue<Boolean> = instantanea.enEjecucionObservable()

    override fun estadoEjecucion(): EstadoEjecucion = estadoActual

    override fun establecerPlaneta(planeta: EntornoGravitatorio) = parametros.establecerPlaneta(planeta)

    override fun establecerAlturaInicial(altura: Double) = parametros.establecerAlturaInicial(altura)

    override fun establecerVelocidadInicial(velocidad: Double) = parametros.establecerVelocidadInicial(velocidad)

    override fun cambiarEstado(nuevoEstado: EstadoEjecucion): EstadoEjecucion {
        estadoActual = nuevoEstado
        instantanea.actualizarEjecucion(nuevoEstado.esActiva())
        return nuevoEstado
    }

    override fun iniciarSimulacion() {
        cambiarEstado(estadoActual.iniciar())
        if (haLlegadoAlSuelo() || obtenerTiempoActual() >= tiempoTotalCaida()) {
            reiniciarSimulacion()
        }
        if (obtenerAlturaInicial() > 0.0) {
            cambiarEstado(estadoActual.iniciar())
        }
    }

    override fun pausarSimulacion() {
        cambiarEstado(estadoActual.pausar())
    }

    override fun reiniciarSimulacion() {
        estadoActual = estadoActual.reiniciar()
        instantanea.reiniciar(parametros.obtenerAlturaInicial(), parametros.obtenerVelocidadInicial(), estadoActual.esActiva())
    }

    override fun avanzarTiempo(deltaSegundos: Double): Boolean {
        if (deltaSegundos <= 0.0 || !estadoActual.esActiva()) return false
        establecerTiempoActual(instantanea.obtenerTiempoActual() + deltaSegundos)
        return estadoActual.esActiva()
    }

    /** Establece el tiempo instantáneo y recalcula altura y velocidad. */
    fun establecerTiempoActual(nuevoTiempo: Double) {
        val calculado = motor.estadoEn(parametros.obtenerAlturaInicial(), parametros.obtenerVelocidadInicial(), parametros.obtenerPlaneta().gravedad(), nuevoTiempo)
        instantanea.aplicarEstado(calculado)
        if (calculado.enSuelo()) {
            cambiarEstado(estadoActual.reiniciar())
        }
    }
}
