package org.example

import java.util.Locale

interface ValidadorParametros {
    /**
     * Contrato de validación de los parámetros iniciales (DIP).
     *
     * La vista de configuración depende de esta abstracción y no de
     * [ValidadorParametrosIniciales].
     */

    /**
     * Valida el texto de la altura inicial.
     *
     * @return mensaje de error, o `null` si el valor es válido.
     */
    fun validarAltura(texto: String): String?

    /**
     * Valida el texto de la velocidad inicial.
     *
     * @return mensaje de error, o `null` si el valor es válido.
     */
    fun validarVelocidad(texto: String): String?

    /** Valida ambos parámetros en conjunto. */
    fun validar(textoAltura: String, textoVelocidad: String): ResultadoValidacion

    /** Formatea un parámetro numérico con dos cifras decimales. */
    fun formatear(valor: Double): String = String.format(Locale.ROOT, "%.2f", valor)
}
