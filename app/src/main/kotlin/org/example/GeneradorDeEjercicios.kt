package org.example

interface GeneradorDeEjercicios {
    /**
     * Contrato del generador de problemas cinemáticos de caída libre para el
     * Modo Avanzado (Principio de Inversión de Dependencias - DIP).
     *
     * Aísla al controlador del desafío ([ControladorDesafio]) de la lógica de
     * muestreo pseudoaleatorio y de la selección de parámetros implementada en
     * [GeneradorEjercicios].
     */

    /**
     * Genera un nuevo [EjercicioCaidaLibre] con entorno gravitatorio, altura
     * inicial, velocidad inicial y objetivo de aprendizaje seleccionados de
     * forma aleatoria dentro de rangos físicamente coherentes.
     */
    fun generar(): EjercicioCaidaLibre

    /**
     * Construye un [EjercicioCaidaLibre] determinista a partir de un escenario
     * explícitamente configurado, calculando el valor verdadero y el instante
     * de evaluación acordes al [objetivo] solicitado.
     *
     * @param entorno cuerpo celeste o entorno gravitatorio del problema.
     * @param alturaInicial altura de lanzamiento en metros (h₀ > 0).
     * @param velocidadInicial velocidad inicial hacia abajo en m/s (v₀ ≥ 0).
     * @param objetivo magnitud cinemática que el estudiante debe determinar.
     */
    fun generarConfigurado(entorno: EntornoGravitatorio, alturaInicial: Double, velocidadInicial: Double, objetivo: ObjetivoEjercicio): EjercicioCaidaLibre
}
