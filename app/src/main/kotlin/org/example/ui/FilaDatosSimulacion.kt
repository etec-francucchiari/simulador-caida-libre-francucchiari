package org.example

class FilaDatosSimulacion(
    private val tiempoInterno: Double,
    private val posicionInterna: Double,
    private val velocidadInterna: Double,
    private val aceleracionInterna: Double
) {
    /**
     * Modelo de dominio de un registro (fila) perteneciente a la tabla de datos
     * muestreados de la simulación ([MuestraDatosSimulacion]).
     *
     * Encapsula de forma privada las cuatro magnitudes cinemáticas fundamentales
     * del movimiento (`t`, `y`, `v`, `a`) y expone operaciones de dominio para
     * detectar el impacto contra el suelo ([haImpactadoSuelo]) y proyectar la
     * tupla ordenada de valores numéricos ([comoValores]) requerida por los
     * exportadores CSV y JSON.
     *
     * @property tiempoInterno instante muestreado en segundos.
     * @property posicionInterna altura sobre el suelo en metros.
     * @property velocidadInterna rapidez hacia abajo en metros por segundo.
     * @property aceleracionInterna aceleración gravitatoria del entorno en m/s².
     */

    /**
     * Devuelve el instante de tiempo del registro en segundos.
     */
    fun tiempo(): Double = tiempoInterno

    /**
     * Devuelve la posición vertical (altura sobre el suelo) en metros.
     */
    fun posicion(): Double = posicionInterna

    /**
     * Devuelve la velocidad instantánea en metros por segundo.
     */
    fun velocidad(): Double = velocidadInterna

    /**
     * Devuelve la aceleración gravitatoria constante en m/s².
     */
    fun aceleracion(): Double = aceleracionInterna

    /**
     * Comportamiento de dominio: indica si en este registro el móvil ya se
     * encuentra en contacto con el suelo (altura menor o igual a cero).
     */
    fun haImpactadoSuelo(): Boolean {
        return posicionInterna <= 0.0
    }

    /**
     * Comportamiento de dominio: proyecta las cuatro magnitudes cinemáticas en
     * el orden canónico `(t, y, v, a)` para su formateo tabular.
     */
    fun comoValores(): List<Double> {
        return listOf(tiempoInterno, posicionInterna, velocidadInterna, aceleracionInterna)
    }

    /**
     * Representación legible de la fila con sus cuatro magnitudes etiquetadas.
     */
    override fun toString(): String {
        return "FilaDatosSimulacion(t=$tiempoInterno, y=$posicionInterna, " +
            "v=$velocidadInterna, a=$aceleracionInterna)"
    }
}
