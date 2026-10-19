package org.example

/**
 * Modelo inmutable con los datos de un fotograma de la animación de caída.
 *
 * Expone comportamiento de dominio para calcular la fracción de altura
 * restante, evitando que el renderizador dependa directamente del gestor de
 * estado.
 */
class FotogramaCaida(
    private val anchoInterno: Double,
    private val altoInterno: Double,
    private val entornoInterno: EntornoGravitatorio,
    private val alturaInicialInterna: Double,
    private val alturaActualInterna: Double,
    private val velocidadActualInterna: Double,
    private val impactadoInterno: Boolean
) {

    /** Ancho del lienzo en píxeles. */
    fun ancho(): Double = anchoInterno

    /** Alto del lienzo en píxeles. */
    fun alto(): Double = altoInterno

    /** Entorno gravitatorio activo. */
    fun entorno(): EntornoGravitatorio = entornoInterno

    /** Altura inicial en metros. */
    fun alturaInicial(): Double = alturaInicialInterna

    /** Altura actual en metros. */
    fun alturaActual(): Double = alturaActualInterna

    /** Velocidad actual en m/s. */
    fun velocidadActual(): Double = velocidadActualInterna

    /** Indica si el cuerpo ya tocó el suelo. */
    fun haImpactado(): Boolean = impactadoInterno

    /** Fracción de altura restante respecto de la altura inicial. */
    fun fraccionAltura(): Double =
        if (alturaInicialInterna > 0.0) {
            (alturaActualInterna / alturaInicialInterna).coerceIn(0.0, 1.0)
        } else {
            0.0
        }
}
