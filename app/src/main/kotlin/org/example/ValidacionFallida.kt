package org.example

/**
 * Resultado fallido de la validación de parámetros iniciales.
 *
 * @property erroresInternos mensajes de error legibles para el usuario.
 */
class ValidacionFallida(
    private val erroresInternos: List<String>
) : ResultadoValidacion {

    /** Errores encontrados durante la validación. */
    override fun errores(): List<String> = erroresInternos

    override fun esValido(): Boolean = false
}
