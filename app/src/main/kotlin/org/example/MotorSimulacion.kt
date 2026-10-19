package org.example

/**
 * Contrato del motor de cálculo del estado instantáneo de la simulación (DIP).
 */
interface MotorSimulacion {

    /** Tiempo total de caída en segundos (0.0 si no es calculable). */
    fun tiempoCaida(alturaInicial: Double, velocidadInicial: Double, gravedad: Double): Double

    /** Determina si el cuerpo se encuentra detenido en el suelo. */
    fun haLlegadoAlSuelo(
        alturaInicial: Double,
        velocidadInicial: Double,
        gravedad: Double,
        tiempoActual: Double,
        alturaActual: Double
    ): Boolean

    /** Calcula el estado cinemático para el tiempo solicitado. */
    fun estadoEn(
        alturaInicial: Double,
        velocidadInicial: Double,
        gravedad: Double,
        tiempoSolicitado: Double
    ): EstadoInstantaneo
}
