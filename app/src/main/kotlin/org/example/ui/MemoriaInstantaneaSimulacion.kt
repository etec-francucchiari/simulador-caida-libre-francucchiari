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

    private val instantanea = Pair(
        Triple(
            SimpleDoubleProperty(0.0),
            SimpleDoubleProperty(configuracion.alturaPorDefecto()),
            SimpleDoubleProperty(configuracion.velocidadPorDefecto())
        ),
        Pair(SimpleBooleanProperty(false), configuracion)
    )

    /**
     * Construye la memoria reactiva de parámetros iniciales asociada a la misma configuración.
     */
    fun crearMemoriaParametros(planetaInicial: EntornoGravitatorio): MemoriaParametrosSimulacion {
        return MemoriaParametrosSimulacion(instantanea.second.second, planetaInicial)
    }

    /**
     * Construye el estado de ejecución inicial en reposo para el gestor reactivo.
     */
    fun crearEstadoInicial(): EstadoEjecucion {
        instantanea.second.first.set(false)
        return EstadoEjecucionReiniciado()
    }

    /**
     * Aplica de manera atómica los valores cinemáticos de un [EstadoInstantaneo]
     * calculado por el motor físico a las propiedades observables de la interfaz.
     *
     * @param estado instantánea física con tiempo, altura y velocidad.
     */
    fun aplicarEstado(estado: EstadoInstantaneo) {
        instantanea.first.first.set(estado.tiempo())
        instantanea.first.second.set(estado.altura())
        instantanea.first.third.set(estado.velocidad())
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
        instantanea.first.first.set(0.0)
        instantanea.first.second.set(alturaInicial)
        instantanea.first.third.set(velocidadInicial)
        instantanea.second.first.set(activa)
    }

    /**
     * Actualiza el indicador observable que señala si el bucle de animación está activo.
     *
     * @param activa `true` cuando el estado de ejecución permite avanzar fotogramas.
     */
    fun actualizarEjecucion(activa: Boolean) {
        instantanea.second.first.set(activa)
    }

    /**
     * Devuelve el tiempo transcurrido actual en segundos.
     */
    fun obtenerTiempoActual(): Double = instantanea.first.first.get()

    /**
     * Devuelve la altura instantánea actual sobre el suelo en metros.
     */
    fun obtenerAlturaActual(): Double = instantanea.first.second.get()

    /**
     * Devuelve la velocidad instantánea actual en m/s.
     */
    fun obtenerVelocidadActual(): Double = instantanea.first.third.get()

    /**
     * Propiedad observable del tiempo transcurrido en segundos.
     */
    fun tiempoActualObservable(): ObservableValue<Number> = instantanea.first.first

    /**
     * Propiedad observable de la altura instantánea en metros.
     */
    fun alturaActualObservable(): ObservableValue<Number> = instantanea.first.second

    /**
     * Propiedad observable de la velocidad instantánea en m/s.
     */
    fun velocidadActualObservable(): ObservableValue<Number> = instantanea.first.third

    /**
     * Propiedad observable que indica si la simulación se encuentra en ejecución.
     */
    fun enEjecucionObservable(): ObservableValue<Boolean> = instantanea.second.first
}
