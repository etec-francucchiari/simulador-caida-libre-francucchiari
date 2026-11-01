package org.example

class ValidacionExitosa(private val parametrosInternos: ParametrosIniciales) : ResultadoValidacion {
    /**
     * Representación polimórfica de un resultado exitoso al validar los
     * parámetros iniciales de lanzamiento ([ResultadoValidacion]).
     *
     * Encapsula de forma privada el objeto de valor [ParametrosIniciales]
     * resultante y responde afirmativamente a [esValido], manteniendo vacía la
     * colección de errores heredada del contrato.
     *
     * @property parametrosInternos parámetros físicos validados listos para aplicar.
     */

    /**
     * Devuelve la instancia de [ParametrosIniciales] con la altura y velocidad
     * inicial ya validadas.
     */
    override fun parametros(): ParametrosIniciales {
        return parametrosInternos
    }

    /**
     * Confirma que la validación de los campos de entrada fue satisfactoria.
     */
    override fun esValido(): Boolean {
        return true
    }
}
