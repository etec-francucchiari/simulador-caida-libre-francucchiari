package org.example

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue

/**
 * Gestor de estado reactivo del simulador de caída libre.
 *
 * Implementa los contratos [EstadoSimulacion] y [ComandosSimulacion] para que
 * las vistas dependan de abstracciones (DIP).
 *
 * Encapsulación estricta: todas las propiedades observables son campos
 * [private]; solo se exponen como [ObservableValue] de solo lectura mediante
 * métodos, además de los métodos de consulta y comando.
 *
 * @property configuracion valores por defecto y límites físicos.
 * @property calculo servicio de fórmulas físicas inyectado.
 */
class GestorEstadoSimulacion(
    private val configuracion: ConfiguracionFisica = ConfiguracionFisica(),
    planetaInicial: EntornoGravitatorio = Planeta.TIERRA,
    private val calculo: CalculoCaidaLibre = CalculoCaidaLibre()
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

    private val umbralPrecisionTiempo: Double = 1e-6
    private val umbralAlturaSuelo: Double = 1e-5

    /**
     * Determina si el cuerpo se encuentra detenido en el suelo.
     */
    override fun haLlegadoAlSuelo(): Boolean {
        val h0 = obtenerAlturaInicial()
        val v0 = obtenerVelocidadInicial()
        val g = obtenerPlaneta().gravedad()
        val tiempoCaida = calculo.calcularTiempoCaida(h0, v0, g) ?: 0.0
        return obtenerAlturaActual() <= umbralAlturaSuelo ||
                (tiempoCaida > 0.0 && (tiempoCaida - obtenerTiempoActual()) <= umbralPrecisionTiempo)
    }

    override fun iniciarSimulacion() {
        val h0 = obtenerAlturaInicial()
        val v0 = obtenerVelocidadInicial()
        val g = obtenerPlaneta().gravedad()
        val tiempoCaida = calculo.calcularTiempoCaida(h0, v0, g) ?: 0.0

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
        val h0 = obtenerAlturaInicial()
        val v0 = obtenerVelocidadInicial()
        val g = obtenerPlaneta().gravedad()
        val tiempoCaida = calculo.calcularTiempoCaida(h0, v0, g) ?: 0.0

        val tiempoLimitado = nuevoTiempo.coerceIn(0.0, tiempoCaida)
        val tiempoEfectivo =
            if (tiempoCaida > 0.0 && (tiempoCaida - tiempoLimitado) <= umbralPrecisionTiempo) {
                tiempoCaida
            } else {
                tiempoLimitado
            }
        tiempoActual.set(tiempoEfectivo)

        val posicion = calculo.calcularPosicion(h0, v0, g, tiempoEfectivo) ?: 0.0
        val velocidad = calculo.calcularVelocidad(h0, v0, g, tiempoEfectivo) ?: 0.0

        if (posicion <= umbralAlturaSuelo || tiempoEfectivo >= tiempoCaida) {
            alturaActual.set(0.0)
            val velocidadImpacto = calculo.calcularVelocidadImpacto(h0, v0, g) ?: velocidad
            velocidadActual.set(velocidadImpacto)
            enEjecucion.set(false)
        } else {
            alturaActual.set(posicion)
            velocidadActual.set(velocidad)
        }
    }

    override fun avanzarTiempo(deltaSegundos: Double): Boolean {
        if (!estaEnEjecucion() || deltaSegundos <= 0.0) return false

        val tiempoDestino = obtenerTiempoActual() + deltaSegundos
        establecerTiempoActual(tiempoDestino)

        if (obtenerAlturaActual() <= 0.0 || haLlegadoAlSuelo()) {
            enEjecucion.set(false)
        }

        return estaEnEjecucion()
    }
}