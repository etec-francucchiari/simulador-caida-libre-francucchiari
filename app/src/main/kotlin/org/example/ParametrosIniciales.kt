package org.example

/**
 * Parámetros iniciales de la simulación de caída libre.
 *
 * Representa de forma inmutable los valores de entrada del usuario:
 * la altura inicial (h₀) desde la que parte el cuerpo y la velocidad
 * inicial (v₀) con la que comienza su movimiento.
 *
 * Al ser una clase de datos inmutable, puede compartirse de forma segura
 * entre el gestor de estado reactivo, los validadores y la interfaz,
 * sin riesgo de mutación accidental.
 */
data class ParametrosIniciales(
    val alturaInicial: Double,
    val velocidadInicial: Double
) {

    companion object {
        /** Altura inicial por defecto en metros. */
        const val ALTURA_DEFECTO = 100.0

        /** Velocidad inicial por defecto en m/s. */
        const val VELOCIDAD_DEFECTO = 0.0

        /** Valor mínimo admitido para la altura (no negativa). */
        const val ALTURA_MINIMA = 0.0

        /** Valor mínimo admitido para la velocidad (no negativa). */
        const val VELOCIDAD_MINIMA = 0.0
    }
}