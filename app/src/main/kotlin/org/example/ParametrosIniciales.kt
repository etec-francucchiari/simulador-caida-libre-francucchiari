package org.example

/**
 * Parámetros iniciales de la simulación de caída libre.
 *
 * Modelo de dominio con campos [private] (encapsulación estricta) que expone
 * comportamiento relevante del problema físico: validación de rangos y
 * construcción inmutable de nuevas configuraciones.
 *
 * @property alturaInicialInterna altura inicial (y₀) en metros.
 * @property velocidadInicialInterna velocidad inicial (v₀) en m/s.
 */
class ParametrosIniciales(
    private val alturaInicialInterna: Double,
    private val velocidadInicialInterna: Double
) {

    /** Altura inicial (y₀) en metros. */
    fun alturaInicial(): Double = alturaInicialInterna

    /** Velocidad inicial (v₀) en m/s. */
    fun velocidadInicial(): Double = velocidadInicialInterna

    /** Comportamiento de dominio: la altura es físicamente admisible. */
    fun esAlturaValida(): Boolean = alturaInicialInterna.isFinite() && alturaInicialInterna >= 0.0

    /** Comportamiento de dominio: la velocidad es físicamente admisible. */
    fun esVelocidadValida(): Boolean =
        velocidadInicialInterna.isFinite() && velocidadInicialInterna >= 0.0

    /** Comportamiento de dominio: existe una caída efectiva (altura > 0). */
    fun esCaidaPosible(): Boolean = alturaInicialInterna > 0.0

    /** Comportamiento de dominio: el cuerpo parte del reposo. */
    fun parteDelReposo(): Boolean = velocidadInicialInterna == 0.0

    /** Construye nuevos parámetros con otra altura (inmutabilidad). */
    fun conAltura(nuevaAltura: Double): ParametrosIniciales =
        ParametrosIniciales(nuevaAltura, velocidadInicialInterna)

    /** Construye nuevos parámetros con otra velocidad (inmutabilidad). */
    fun conVelocidad(nuevaVelocidad: Double): ParametrosIniciales =
        ParametrosIniciales(alturaInicialInterna, nuevaVelocidad)

    /** Comportamiento de dominio: resumen legible. */
    fun resumen(): String = "y₀ = $alturaInicialInterna m, v₀ = $velocidadInicialInterna m/s"

    /**
     * Comportamiento de dominio: compara los valores físicos con otros
     * parámetros sin recurrir a comprobaciones de tipo.
     */
    fun mismosValoresQue(otro: ParametrosIniciales): Boolean =
        alturaInicialInterna == otro.alturaInicialInterna &&
            velocidadInicialInterna == otro.velocidadInicialInterna

    override fun toString(): String = resumen()
}