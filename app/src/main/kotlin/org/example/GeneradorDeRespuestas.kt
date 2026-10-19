package org.example

/**
 * Contrato del generador de opciones de respuesta numérica (Etapa 2).
 */
interface GeneradorDeRespuestas {

    /** Devuelve tres opciones de respuesta con una sola correcta. */
    fun generar(ejercicio: EjercicioCaidaLibre): List<OpcionRespuesta>
}
