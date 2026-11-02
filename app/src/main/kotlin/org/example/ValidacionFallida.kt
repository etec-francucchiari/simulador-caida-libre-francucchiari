package org.example

class ValidacionFallida(private val erroresInternos: List<String>) : ResultadoValidacion {
    /**
     * Representación polimórfica de un resultado fallido al validar los
     * parámetros iniciales de lanzamiento ([ResultadoValidacion]).
     *
     * Conserva en un atributo privado la lista de mensajes de error detectados
     * durante el análisis léxico y de dominio, respondiendo `false` en [esValido]
     * y `null` en la consulta de parámetros válidos.
     *
     * @property erroresInternos lista de mensajes de error legibles para la UI.
     */

    /**
     * Devuelve la lista inmutable de errores de validación recolectados.
     */
    override fun errores(): List<String> {
        return erroresInternos
    }

    /**
     * Indica que la validación no superó todas las restricciones de entrada.
     */
    override fun esValido(): Boolean {
        return false
    }
}
