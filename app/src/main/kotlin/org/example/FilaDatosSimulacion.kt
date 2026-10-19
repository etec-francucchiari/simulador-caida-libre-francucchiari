package org.example

/**
 * Modelo de dominio de una fila de la tabla de datos de la simulación.
 *
 * Contiene los cuatro valores cinemáticos requeridos (tiempo, posición,
 * velocidad y aceleración) y comportamiento de dominio explícito, evitando
 * ser un contenedor anémico.
 *
 * @property tiempoInterno tiempo transcurrido (t) en segundos.
 * @property posicionInterna altura instantánea (y) en metros.
 * @property velocidadInterna velocidad instantánea (v) en m/s.
 * @property aceleracionInterna aceleración de la gravedad (a) en m/s².
 */
class FilaDatosSimulacion(
    private val tiempoInterno: Double,
    private val posicionInterna: Double,
    private val velocidadInterna: Double,
    private val aceleracionInterna: Double
) {

    /** Tiempo transcurrido en segundos. */
    fun tiempo(): Double = tiempoInterno

    /** Altura instantánea en metros. */
    fun posicion(): Double = posicionInterna

    /** Velocidad instantánea en m/s. */
    fun velocidad(): Double = velocidadInterna

    /** Aceleración en m/s². */
    fun aceleracion(): Double = aceleracionInterna

    /** Comportamiento de dominio: el cuerpo alcanzó el suelo. */
    fun haImpactadoSuelo(): Boolean = posicionInterna <= 0.0

    /**
     * Comportamiento de dominio: devuelve los valores cinemáticos en el orden
     * canónico (t, y, v, a) para su serialización.
     */
    fun comoValores(): List<Double> =
        listOf(tiempoInterno, posicionInterna, velocidadInterna, aceleracionInterna)

    override fun toString(): String =
        "FilaDatosSimulacion(t=$tiempoInterno, y=$posicionInterna, " +
            "v=$velocidadInterna, a=$aceleracionInterna)"
}
