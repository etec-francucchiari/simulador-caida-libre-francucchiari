package org.example

interface MotorSimulacion {
    /**
     * Contrato del motor de evaluación cinemática instantánea de la simulación
     * de caída libre (Principio de Inversión de Dependencias - DIP).
     *
     * Desacopla a [GestorEstadoSimulacion] de la implementación concreta
     * [MotorEstadoCaida], permitiendo evaluar el tiempo total de vuelo, la
     * condición de llegada al suelo y la instantánea física en cualquier tiempo.
     */

    /**
     * Calcula el tiempo total de caída libre en segundos para la altura inicial,
     * velocidad inicial y gravedad indicadas, devolviendo `0.0` si los datos no
     * permiten una caída válida.
     */
    fun tiempoCaida(alturaInicial: Double, velocidadInicial: Double, gravedad: Double): Double

    /**
     * Determina si el cuerpo ya ha impactado contra el suelo comparando la
     * [alturaActual] y el [tiempoActual] frente al [tiempoTotal] de caída dentro
     * de las tolerancias numéricas del motor.
     */
    fun haLlegadoAlSuelo(tiempoTotal: Double, tiempoActual: Double, alturaActual: Double): Boolean

    /**
     * Calcula el [EstadoInstantaneo] completo (tiempo efectivo, altura,
     * velocidad e indicador de suelo) para el [tiempoSolicitado], acotando el
     * movimiento al instante exacto de impacto cuando corresponde.
     */
    fun estadoEn(alturaInicial: Double, velocidadInicial: Double, gravedad: Double, tiempoSolicitado: Double): EstadoInstantaneo
}
