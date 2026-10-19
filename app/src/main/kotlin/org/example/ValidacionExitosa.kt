package org.example

/**
 * Resultado exitoso de la validación de parámetros iniciales.
 *
 * @property parametrosInternos parámetros físicos ya validados.
 */
class ValidacionExitosa(
    private val parametrosInternos: ParametrosIniciales
) : ResultadoValidacion {

    /** Parámetros válidos resultantes. */
    override fun parametros(): ParametrosIniciales = parametrosInternos

    override fun esValido(): Boolean = true
}
