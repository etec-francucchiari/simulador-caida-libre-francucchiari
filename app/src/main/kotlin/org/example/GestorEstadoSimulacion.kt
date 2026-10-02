package org.example

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty

/**
 * Gestor de estado reactivo del simulador de caída libre.
 *
 * Centraliza tanto la configuración inicial (planeta, altura y velocidad inicial)
 * como las variables de estado dinámicas instantáneas durante la simulación
 * (tiempo transcurrido, altura actual y velocidad actual).
 *
 * Expone propiedades JavaFX observables para que la interfaz gráfica y los
 * componentes de visualización gráfica se actualicen automáticamente ante
 * cualquier cambio de estado.
 *
 * Principios aplicados:
 * - **Encapsulamiento**: el estado interno es privado; se accede mediante getters
 *   y propiedades observables.
 * - **Reactividad**: los componentes visuales observan las propiedades sin polling.
 * - **Desacoplamiento**: la lógica de cálculo físico se delega a [CalculoCaidaLibre].
 */
class GestorEstadoSimulacion {

    /**
     * Propiedad observable con el planeta activo de la simulación.
     */
    val planetaActivoProperty: SimpleObjectProperty<Planeta> =
        SimpleObjectProperty(Planeta.TIERRA)

    /**
     * Propiedad observable con la altura inicial (h₀) en metros.
     */
    val alturaInicialProperty: SimpleDoubleProperty =
        SimpleDoubleProperty(ParametrosIniciales.ALTURA_DEFECTO)

    /**
     * Propiedad observable con la velocidad inicial (v₀) en m/s.
     */
    val velocidadInicialProperty: SimpleDoubleProperty =
        SimpleDoubleProperty(ParametrosIniciales.VELOCIDAD_DEFECTO)

    /**
     * Propiedad observable con el tiempo transcurrido (t) en segundos.
     */
    val tiempoActualProperty: SimpleDoubleProperty =
        SimpleDoubleProperty(0.0)

    /**
     * Propiedad observable con la altura instantánea y(t) en metros.
     */
    val alturaActualProperty: SimpleDoubleProperty =
        SimpleDoubleProperty(ParametrosIniciales.ALTURA_DEFECTO)

    /**
     * Propiedad observable con la velocidad instantánea v(t) en m/s.
     */
    val velocidadActualProperty: SimpleDoubleProperty =
        SimpleDoubleProperty(ParametrosIniciales.VELOCIDAD_DEFECTO)

    /**
     * Propiedad observable que indica si la simulación está actualmente en ejecución.
     */
    val enEjecucionProperty: SimpleBooleanProperty =
        SimpleBooleanProperty(false)

    init {
        // Al modificar cualquier parámetro inicial o el planeta, se reinicia
        // el estado instantáneo a t = 0 con los nuevos valores de partida.
        alturaInicialProperty.addListener { _, _, _ -> reiniciarSimulacion() }
        velocidadInicialProperty.addListener { _, _, _ -> reiniciarSimulacion() }
        planetaActivoProperty.addListener { _, _, _ -> reiniciarSimulacion() }
    }

    /**
     * Obtiene el planeta actualmente seleccionado.
     */
    fun obtenerPlaneta(): Planeta = planetaActivoProperty.get()

    /**
     * Establece el planeta activo de la simulación.
     */
    fun establecerPlaneta(planeta: Planeta) {
        planetaActivoProperty.set(planeta)
    }

    /**
     * Obtiene la altura inicial configurada en metros.
     */
    fun obtenerAlturaInicial(): Double = alturaInicialProperty.get()

    /**
     * Establece la altura inicial de la simulación.
     */
    fun establecerAlturaInicial(altura: Double) {
        alturaInicialProperty.set(altura)
    }

    /**
     * Obtiene la velocidad inicial configurada en m/s.
     */
    fun obtenerVelocidadInicial(): Double = velocidadInicialProperty.get()

    /**
     * Establece la velocidad inicial de la simulación.
     */
    fun establecerVelocidadInicial(velocidad: Double) {
        velocidadInicialProperty.set(velocidad)
    }

    /**
     * Obtiene el tiempo transcurrido actual en segundos.
     */
    fun obtenerTiempoActual(): Double = tiempoActualProperty.get()

    /**
     * Obtiene la altura actual sobre el suelo en metros.
     */
    fun obtenerAlturaActual(): Double = alturaActualProperty.get()

    /**
     * Obtiene la velocidad actual del cuerpo en m/s.
     */
    fun obtenerVelocidadActual(): Double = velocidadActualProperty.get()

    /**
     * Indica si la simulación se encuentra corriendo.
     */
    fun estaEnEjecucion(): Boolean = enEjecucionProperty.get()

    companion object {
        private const val UMBRAL_PRECISION_TIEMPO: Double = 1e-6
        private const val UMBRAL_ALTURA_SUELO: Double = 1e-5
    }

    /**
     * Determina si el cuerpo se encuentra actualmente detenido en el suelo
     * (altura y <= 0 o tiempo transcurrido alcanzando el tiempo total de caída).
     */
    fun haLlegadoAlSuelo(): Boolean {
        val h0 = obtenerAlturaInicial()
        val v0 = obtenerVelocidadInicial()
        val g = obtenerPlaneta().gravedad
        val tiempoCaida = CalculoCaidaLibre.calcularTiempoCaida(h0, v0, g) ?: 0.0
        return obtenerAlturaActual() <= UMBRAL_ALTURA_SUELO ||
                (tiempoCaida > 0.0 && (tiempoCaida - obtenerTiempoActual()) <= UMBRAL_PRECISION_TIEMPO)
    }

    /**
     * Inicia o reanuda la simulación en tiempo real.
     * Si el cuerpo ya se encuentra en el suelo, se reinicia primero automáticamente.
     */
    fun iniciarSimulacion() {
        val h0 = obtenerAlturaInicial()
        val v0 = obtenerVelocidadInicial()
        val g = obtenerPlaneta().gravedad
        val tiempoCaida = CalculoCaidaLibre.calcularTiempoCaida(h0, v0, g) ?: 0.0

        if (haLlegadoAlSuelo() || obtenerTiempoActual() >= tiempoCaida) {
            reiniciarSimulacion()
        }

        if (obtenerAlturaInicial() <= 0.0) {
            enEjecucionProperty.set(false)
            return
        }

        enEjecucionProperty.set(true)
    }

    /**
     * Pausa la ejecución de la simulación.
     */
    fun pausarSimulacion() {
        enEjecucionProperty.set(false)
    }

    /**
     * Reinicia el tiempo y los valores instantáneos a las condiciones de inicio.
     */
    fun reiniciarSimulacion() {
        enEjecucionProperty.set(false)
        tiempoActualProperty.set(0.0)
        alturaActualProperty.set(obtenerAlturaInicial())
        velocidadActualProperty.set(obtenerVelocidadInicial())
    }

    /**
     * Establece manualmente el tiempo instantáneo de simulación y recalcula
     * de forma reactiva la altura y velocidad asociadas.
     *
     * Detecta la colisión o detención cuando el objeto llega al suelo (y <= 0)
     * y finaliza la simulación en dicho instante.
     *
     * @param nuevoTiempo tiempo en segundos (t ≥ 0).
     */
    fun establecerTiempoActual(nuevoTiempo: Double) {
        val h0 = obtenerAlturaInicial()
        val v0 = obtenerVelocidadInicial()
        val g = obtenerPlaneta().gravedad
        val tiempoCaida = CalculoCaidaLibre.calcularTiempoCaida(h0, v0, g) ?: 0.0

        val tiempoLimitado = nuevoTiempo.coerceIn(0.0, tiempoCaida)
        val tiempoEfectivo = if (tiempoCaida > 0.0 && (tiempoCaida - tiempoLimitado) <= UMBRAL_PRECISION_TIEMPO) {
            tiempoCaida
        } else {
            tiempoLimitado
        }
        tiempoActualProperty.set(tiempoEfectivo)

        val posicion = CalculoCaidaLibre.calcularPosicion(h0, v0, g, tiempoEfectivo) ?: 0.0
        val velocidad = CalculoCaidaLibre.calcularVelocidad(h0, v0, g, tiempoEfectivo) ?: 0.0

        if (posicion <= UMBRAL_ALTURA_SUELO || tiempoEfectivo >= tiempoCaida) {
            alturaActualProperty.set(0.0)
            val velocidadImpacto = CalculoCaidaLibre.calcularVelocidadImpacto(h0, v0, g) ?: velocidad
            velocidadActualProperty.set(velocidadImpacto)
            enEjecucionProperty.set(false)
        } else {
            alturaActualProperty.set(posicion)
            velocidadActualProperty.set(velocidad)
        }
    }

    /**
     * Avanza el tiempo transcurrido en un diferencial deltaSegundos.
     *
     * @param deltaSegundos tiempo transcurrido en el frame en segundos.
     * @return `true` si la simulación continúa en el aire, o `false` si ha tocado el suelo (y <= 0).
     */
    fun avanzarTiempo(deltaSegundos: Double): Boolean {
        if (!estaEnEjecucion() || deltaSegundos <= 0.0) return false

        val tiempoDestino = obtenerTiempoActual() + deltaSegundos
        establecerTiempoActual(tiempoDestino)

        if (obtenerAlturaActual() <= 0.0 || haLlegadoAlSuelo()) {
            enEjecucionProperty.set(false)
        }

        return estaEnEjecucion()
    }
}