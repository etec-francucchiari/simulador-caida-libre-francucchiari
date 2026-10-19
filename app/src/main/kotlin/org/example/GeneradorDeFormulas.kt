package org.example

/**
 * Contrato del generador de opciones de fórmula (Etapa 1 del desafío).
 */
interface GeneradorDeFormulas {

    /** Devuelve tres opciones de fórmula con una sola correcta. */
    fun generar(ejercicio: EjercicioCaidaLibre): List<OpcionFormula>
}
