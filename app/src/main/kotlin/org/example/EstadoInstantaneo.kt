package org.example

/**
 * Modelo inmutable del estado cinemático en un instante de la simulación.
 *
 * Expone comportamiento de dominio para saber si el cuerpo está en el suelo y
 * evita que el gestor manipule valores sueltos.
 */
class EstadoInstantaneo(
    private val tiempoInterno: Double,
    private val alturaInterna: Double,
    private val velocidadInterna: Double,
    private val enSueloInterno: Boolean
) {

    /** Tiempo transcurrido en segundos. */
    fun tiempo(): Double = tiempoInterno

    /** Altura instantánea en metros. */
    fun altura(): Double = alturaInterna

    /** Velocidad instantánea en m/s. */
    fun velocidad(): Double = velocidadInterna

    /** Indica si el cuerpo alcanzó el suelo. */
    fun enSuelo(): Boolean = enSueloInterno

    /** Comportamiento de dominio: el cuerpo sigue en el aire. */
    fun estaEnElAire(): Boolean = !enSueloInterno

    /** Comportamiento de dominio: rapidez (módulo de la velocidad). */
    fun rapidez(): Double = kotlin.math.abs(velocidadInterna)

    /** Comportamiento de dominio: corresponde al instante inicial. */
    fun esEstadoInicial(): Boolean = tiempoInterno == 0.0
}
