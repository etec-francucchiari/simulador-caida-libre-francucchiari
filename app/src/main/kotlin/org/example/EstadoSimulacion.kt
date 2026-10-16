package org.example

import javafx.beans.value.ObservableValue

/**
 * Abstracción de solo lectura del estado de la simulación (DIP).
 *
 * Las vistas dependen de este contrato en lugar del gestor concreto, de modo
 * que pueden reutilizarse o sustituirse sin acoplarse a la implementación.
 */
interface EstadoSimulacion {

    /** Entorno gravitatorio activo. */
    fun obtenerPlaneta(): EntornoGravitatorio

    /** Altura inicial (y₀) en metros. */
    fun obtenerAlturaInicial(): Double

    /** Velocidad inicial (v₀) en m/s. */
    fun obtenerVelocidadInicial(): Double

    /** Tiempo transcurrido (t) en segundos. */
    fun obtenerTiempoActual(): Double

    /** Altura instantánea (y) en metros. */
    fun obtenerAlturaActual(): Double

    /** Velocidad instantánea (v) en m/s. */
    fun obtenerVelocidadActual(): Double

    /** Indica si la simulación está corriendo. */
    fun estaEnEjecucion(): Boolean

    /** Indica si el cuerpo alcanzó el suelo. */
    fun haLlegadoAlSuelo(): Boolean

    /** Observable (solo lectura) del entorno activo. */
    fun planetaObservable(): ObservableValue<EntornoGravitatorio>

    /** Observable (solo lectura) de la altura inicial. */
    fun alturaInicialObservable(): ObservableValue<Number>

    /** Observable (solo lectura) de la velocidad inicial. */
    fun velocidadInicialObservable(): ObservableValue<Number>

    /** Observable (solo lectura) del tiempo actual. */
    fun tiempoActualObservable(): ObservableValue<Number>

    /** Observable (solo lectura) de la altura actual. */
    fun alturaActualObservable(): ObservableValue<Number>

    /** Observable (solo lectura) de la velocidad actual. */
    fun velocidadActualObservable(): ObservableValue<Number>

    /** Observable (solo lectura) del estado de ejecución. */
    fun enEjecucionObservable(): ObservableValue<Boolean>
}