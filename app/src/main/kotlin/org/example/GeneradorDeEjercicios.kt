package org.example

/**
 * Contrato del generador de ejercicios del Modo Avanzado (DIP).
 */
interface GeneradorDeEjercicios {

    /** Genera un ejercicio aleatorio válido. */
    fun generar(): EjercicioCaidaLibre

    /** Construye un ejercicio con un escenario explícitamente configurado. */
    fun generarConfigurado(
        entorno: EntornoGravitatorio,
        alturaInicial: Double,
        velocidadInicial: Double,
        objetivo: ObjetivoEjercicio
    ): EjercicioCaidaLibre
}
