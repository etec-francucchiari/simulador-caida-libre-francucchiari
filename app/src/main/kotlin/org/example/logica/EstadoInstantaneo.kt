package org.example

import kotlin.math.abs

class EstadoInstantaneo(
    private val tiempoInterno: Double,
    private val alturaInterna: Double,
    private val velocidadInterna: Double,
    private val enSueloInterno: Boolean
) {
    /**
     * Modelo de dominio inmutable que representa el estado cinemático de un
     * cuerpo en caída libre en un instante determinado de la simulación.
     *
     * Agrupa el tiempo transcurrido, la altura sobre el suelo, la velocidad
     * vertical y el indicador de contacto con el suelo bajo encapsulación
     * estricta, proveyendo consultas de dominio ([estaEnElAire], [rapidez],
     * [esEstadoInicial]) para evitar lógica dispersa en la capa de presentación.
     *
     * @property tiempoInterno instante evaluado en segundos (t ≥ 0).
     * @property alturaInterna altura instantánea sobre el suelo en metros (y ≥ 0).
     * @property velocidadInterna velocidad instantánea en m/s.
     * @property enSueloInterno `true` si el móvil ya alcanzó el nivel y = 0.
     */

    /**
     * Devuelve el tiempo transcurrido desde el inicio de la caída en segundos.
     */
    fun tiempo(): Double = tiempoInterno

    /**
     * Devuelve la altura instantánea sobre el nivel del suelo en metros.
     */
    fun altura(): Double = alturaInterna

    /**
     * Devuelve la velocidad instantánea del móvil en metros por segundo.
     */
    fun velocidad(): Double = velocidadInterna

    /**
     * Indica si el móvil ha impactado contra el suelo en o antes de este instante.
     */
    fun enSuelo(): Boolean = enSueloInterno

    /**
     * Comportamiento de dominio: determina si el cuerpo continúa descendiendo
     * en el aire sin haber tocado aún el suelo.
     */
    fun estaEnElAire(): Boolean {
        return !enSueloInterno && alturaInterna >= 0.0
    }

    /**
     * Comportamiento de dominio: calcula el módulo (valor absoluto) de la
     * velocidad instantánea en m/s, independientemente de la convención de signo.
     */
    fun rapidez(): Double {
        return abs(velocidadInterna)
    }

    /**
     * Comportamiento de dominio: verifica si la instantánea corresponde al
     * origen temporal (t = 0 s) previo al inicio del descenso.
     */
    fun esEstadoInicial(): Boolean {
        return tiempoInterno == 0.0 && !enSueloInterno
    }
}
