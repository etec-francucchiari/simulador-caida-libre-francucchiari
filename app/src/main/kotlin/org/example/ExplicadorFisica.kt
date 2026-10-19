package org.example

/**
 * Contrato del servicio de explicaciones físicas paso a paso (DIP).
 *
 * Las vistas didácticas dependen de esta abstracción en lugar de la
 * implementación concreta [ExplicadorFisicaPasoAPaso].
 */
interface ExplicadorFisica {

    /** Explicación paso a paso de la posición y(t). */
    fun explicarPosicion(h0: Double, v0: Double, g: Double, t: Double): DetallePasoFormula

    /** Explicación paso a paso de la velocidad v(t). */
    fun explicarVelocidad(h0: Double, v0: Double, g: Double, t: Double): DetallePasoFormula

    /** Explicación paso a paso del tiempo total de caída. */
    fun explicarTiempoCaida(h0: Double, v0: Double, g: Double): DetallePasoFormula
}
