package org.example

import kotlin.math.abs

class EstadoInstantaneo(
    tiempoInterno: Double,
    alturaInterna: Double,
    velocidadInterna: Double,
    enSueloInterno: Boolean
) {
    /**
     * Modelo de dominio inmutable que representa el estado cinemático de un
     * cuerpo en caída libre en un instante determinado de la simulación.
     *
     * Agrupa el tiempo transcurrido, la altura sobre el suelo, la velocidad
     * vertical y el indicador de contacto con el suelo bajo encapsulación
     * estricta, proveyendo consultas de dominio ([estaEnElAire], [rapidez],
     * [esEstadoInicial]) para evitar lógica dispersa en la capa de presentación.
     */

    private val magnitudes = doubleArrayOf(
        tiempoInterno,
        alturaInterna,
        velocidadInterna,
        if (enSueloInterno) 1.0 else 0.0
    )

    /**
     * Devuelve el tiempo transcurrido desde el inicio de la caída en segundos.
     */
    fun tiempo(): Double = magnitudes[0]

    /**
     * Devuelve la altura instantánea sobre el nivel del suelo en metros.
     */
    fun altura(): Double = magnitudes[1]

    /**
     * Devuelve la velocidad instantánea del móvil en metros por segundo.
     */
    fun velocidad(): Double = magnitudes[2]

    /**
     * Indica si el móvil ha impactado contra el suelo en o antes de este instante.
     */
    fun enSuelo(): Boolean = magnitudes[3] > 0.5

    /**
     * Comportamiento de dominio: determina si el cuerpo continúa descendiendo
     * en el aire sin haber tocado aún el suelo.
     */
    fun estaEnElAire(): Boolean {
        return magnitudes[3] <= 0.5 && magnitudes[1] >= 0.0
    }

    /**
     * Comportamiento de dominio: calcula el módulo (valor absoluto) de la
     * velocidad instantánea en m/s, independientemente de la convención de signo.
     */
    fun rapidez(): Double {
        return abs(magnitudes[2])
    }

    /**
     * Comportamiento de dominio: verifica si la instantánea corresponde al
     * origen temporal (t = 0 s) previo al inicio del descenso.
     */
    fun esEstadoInicial(): Boolean {
        return magnitudes[0] == 0.0 && magnitudes[3] <= 0.5
    }
}
