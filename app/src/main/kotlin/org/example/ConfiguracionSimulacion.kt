package org.example

interface ConfiguracionSimulacion {
    /**
     * Contrato de configuración física base del simulador de caída libre
     * (Principio de Inversión de Dependencias - DIP).
     *
     * Expone los valores iniciales por defecto y las cotas e invariantes de
     * validación que consumen [MemoriaParametrosSimulacion],
     * [MemoriaInstantaneaSimulacion] y [ValidadorParametrosIniciales] sin
     * acoplarlos a la implementación concreta [ConfiguracionFisica].
     */

    /**
     * Devuelve la altura inicial por defecto expresada en metros (`100.0 m`).
     */
    fun alturaPorDefecto(): Double

    /**
     * Devuelve la velocidad inicial por defecto expresada en metros por segundo
     * (`0.0 m/s`, correspondiente a caída desde el reposo).
     */
    fun velocidadPorDefecto(): Double

    /**
     * Devuelve la cota inferior admisible para la altura inicial en metros.
     */
    fun alturaMinima(): Double

    /**
     * Devuelve la cota inferior admisible para la velocidad inicial en m/s.
     */
    fun velocidadMinima(): Double

    /**
     * Evalúa si el valor numérico de [altura] es finito y mayor o igual que
     * [alturaMinima].
     */
    fun esAlturaValida(altura: Double): Boolean

    /**
     * Evalúa si el valor numérico de [velocidad] es finito y mayor o igual que
     * [velocidadMinima].
     */
    fun esVelocidadValida(velocidad: Double): Boolean
}
