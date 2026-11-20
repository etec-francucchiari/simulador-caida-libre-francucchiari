package org.example

interface ProveedorResultadoValidacion {
    /**
     * Contrato para la construcción polimórfica de resultados de validación y
     * el análisis numérico de entradas de texto (DIP).
     */

    /**
     * Interpreta un texto numérico devolviendo un real finito o `null`.
     */
    fun parsearNumero(texto: String): Double?

    /**
     * Construye un resultado exitoso con la altura y velocidad validadas.
     */
    fun crearExito(altura: Double, velocidad: Double): ResultadoValidacion

    /**
     * Construye un resultado fallido con la lista de mensajes de error.
     */
    fun crearFallo(errores: List<String>): ResultadoValidacion
}
