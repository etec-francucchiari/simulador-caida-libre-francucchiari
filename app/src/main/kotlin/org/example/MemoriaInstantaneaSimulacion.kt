package org.example

import javafx.beans.property.*
import javafx.beans.value.ObservableValue

class MemoriaInstantaneaSimulacion(
    configuracion: ConfiguracionSimulacion
) {
    /**
     * Encapsula las propiedades reactivas del estado instantáneo de la simulación:
     * tiempo transcurrido, altura actual, velocidad actual e indicador de ejecución.
     *
     * Trabaja en conjunto con [MemoriaParametrosSimulacion] dentro de
     * [GestorEstadoSimulacion] para separar el estado cinemático transitorio de los
     * parámetros iniciales de configuración (SRP / alta cohesión LCOM).
     *
     * Todas las propiedades reactivas son privadas y se exponen únicamente como
     * vistas inmutables [ObservableValue] o lecturas puntuales.
     */

    private val tiempoActual = SimpleDoubleProperty(0.0)
    private val alturaActual = SimpleDoubleProperty(configuracion.alturaPorDefecto())
    private val velocidadActual = SimpleDoubleProperty(configuracion.velocidadPorDefecto())
    private val enEjecucion = SimpleBooleanProperty(false)

    /**
     * Aplica de manera atómica los valores cinemáticos de un [EstadoInstantaneo]
     * calculado por el motor físico a las propiedades observables de la interfaz.
     *
     * @param estado instantánea física con tiempo, altura y velocidad.
     */
    fun aplicarEstado(estado: EstadoInstantaneo) {
        tiempoActual.set(estado.tiempo())
        alturaActual.set(estado.altura())
        velocidadActual.set(estado.velocidad())
    }

    /**
     * Restablece el estado instantáneo al instante inicial t = 0 s con la altura y
     * velocidad iniciales indicadas.
     *
     * @param alturaInicial altura en t = 0 (m).
     * @param velocidadInicial velocidad en t = 0 (m/s).
     * @param activa indica si la simulación queda en estado de ejecución activa.
     */
    fun reiniciar(alturaInicial: Double, velocidadInicial: Double, activa: Boolean) {
        tiempoActual.set(0.0)
        alturaActual.set(alturaInicial)
        velocidadActual.set(velocidadInicial)
        enEjecucion.set(activa)
    }

    /**
     * Actualiza el indicador observable que señala si el bucle de animación está activo.
     *
     * @param activa `true` cuando el estado de ejecución permite avanzar fotogramas.
     */
    fun actualizarEjecucion(activa: Boolean) {
        enEjecucion.set(activa)
    }

    /**
     * Devuelve el tiempo transcurrido actual en segundos.
     */
    fun obtenerTiempoActual(): Double = tiempoActual.get()

    /**
     * Devuelve la altura instantánea actual sobre el suelo en metros.
     */
    fun obtenerAlturaActual(): Double = alturaActual.get()

    /**
     * Devuelve la velocidad instantánea actual en m/s.
     */
    fun obtenerVelocidadActual(): Double = velocidadActual.get()

    /**
     * Propiedad observable del tiempo transcurrido en segundos.
     */
    fun tiempoActualObservable(): ObservableValue<Number> = tiempoActual

    /**
     * Propiedad observable de la altura instantánea en metros.
     */
    fun alturaActualObservable(): ObservableValue<Number> = alturaActual

    /**
     * Propiedad observable de la velocidad instantánea en m/s.
     */
    fun velocidadActualObservable(): ObservableValue<Number> = velocidadActual

    /**
     * Propiedad observable que indica si la simulación se encuentra en ejecución.
     */
    fun enEjecucionObservable(): ObservableValue<Boolean> = enEjecucion
}
