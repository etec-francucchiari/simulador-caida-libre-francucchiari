package org.example

class FilaDatosSimulacion(
    tiempoInterno: Double,
    posicionInterna: Double,
    velocidadInterna: Double,
    aceleracionInterna: Double
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
     */

    private val valores = doubleArrayOf(
        tiempoInterno,
        posicionInterna,
        velocidadInterna,
        aceleracionInterna
    )

    /**
     * Devuelve el instante de tiempo del registro en segundos.
     */
    fun tiempo(): Double = valores[0]

    /**
     * Devuelve la posición vertical (altura sobre el suelo) en metros.
     */
    fun posicion(): Double = valores[1]

    /**
     * Devuelve la velocidad instantánea en metros por segundo.
     */
    fun velocidad(): Double = valores[2]

    /**
     * Devuelve la aceleración gravitatoria constante en m/s².
     */
    fun aceleracion(): Double = valores[3]

    /**
     * Comportamiento de dominio: indica si en este registro el móvil ya se
     * encuentra en contacto con el suelo (altura menor o igual a cero).
     */
    fun haImpactadoSuelo(): Boolean {
        return valores[1] <= 0.0
    }

    /**
     * Comportamiento de dominio: proyecta las cuatro magnitudes cinemáticas en
     * el orden canónico `(t, y, v, a)` para su formateo tabular.
     */
    fun comoValores(): List<Double> {
        return valores.toList()
    }

    /**
     * Representación legible de la fila con sus cuatro magnitudes etiquetadas.
     */
    override fun toString(): String {
        val t = valores[0]
        val y = valores[1]
        val v = valores[2]
        val a = valores[3]
        return "FilaDatosSimulacion(t=$t, y=$y, v=$v, a=$a)"
    }
}
