package org.example

/**
 * Contrato de validación de los parámetros iniciales (DIP).
 *
 * La vista de configuración depende de esta abstracción y no de
 * [ValidadorParametrosIniciales].
 */
interface ValidadorParametros {

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
}
