package org.example

/**
 * Contrato de configuración física del simulador (DIP).
 *
 * Expone los valores por defecto y las reglas de validación que consumen el
 * gestor de estado y el validador de parámetros, sin acoplarlos a
 * [ConfiguracionFisica].
 */
interface ConfiguracionSimulacion {

    /** Altura inicial por defecto en metros. */
    fun alturaPorDefecto(): Double

    /** Velocidad inicial por defecto en m/s. */
    fun velocidadPorDefecto(): Double

    /** Altura mínima admitida en metros. */
    fun alturaMinima(): Double

    /** Velocidad mínima admitida en m/s. */
    fun velocidadMinima(): Double

    /** Indica si la altura está dentro del rango válido. */
    fun esAlturaValida(altura: Double): Boolean

    /** Indica si la velocidad está dentro del rango válido. */
    fun esVelocidadValida(velocidad: Double): Boolean
}
