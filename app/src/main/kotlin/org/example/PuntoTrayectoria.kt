package org.example

/**
 * Representa un punto de la trayectoria de caída libre en un instante dado.
 *
 * Modelo de dominio con campos [private] que expone comportamiento relevante:
 * detección de impacto en el suelo y rapidez absoluta.
 *
 * @property tiempoInterno tiempo transcurrido en segundos (t ≥ 0).
 * @property posicionInterna altura instantánea sobre el suelo en metros (y ≥ 0).
 * @property velocidadInterna velocidad instantánea hacia el suelo en m/s (v ≥ 0).
 */
class PuntoTrayectoria(
    private val tiempoInterno: Double,
    private val posicionInterna: Double,
    private val velocidadInterna: Double
) {

    /** Tiempo transcurrido en segundos. */
    fun tiempo(): Double = tiempoInterno

    /** Altura instantánea sobre el suelo en metros. */
    fun posicion(): Double = posicionInterna

    /** Velocidad instantánea hacia el suelo en m/s. */
    fun velocidad(): Double = velocidadInterna

    /** Comportamiento de dominio: el cuerpo ha tocado el suelo. */
    fun haImpactadoSuelo(): Boolean = posicionInterna <= 0.0

    /** Comportamiento de dominio: rapidez (módulo de la velocidad). */
    fun rapidez(): Double = kotlin.math.abs(velocidadInterna)

    /** Comportamiento de dominio: compara alturas entre dos puntos. */
    fun estaMasAltoQue(otro: PuntoTrayectoria): Boolean =
        posicionInterna > otro.posicionInterna

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PuntoTrayectoria) return false
        return tiempoInterno == other.tiempoInterno &&
                posicionInterna == other.posicionInterna &&
                velocidadInterna == other.velocidadInterna
    }

    override fun hashCode(): Int {
        var resultado = tiempoInterno.hashCode()
        resultado = 31 * resultado + posicionInterna.hashCode()
        resultado = 31 * resultado + velocidadInterna.hashCode()
        return resultado
    }

    override fun toString(): String =
        "t = $tiempoInterno s, y = $posicionInterna m, v = $velocidadInterna m/s"
}