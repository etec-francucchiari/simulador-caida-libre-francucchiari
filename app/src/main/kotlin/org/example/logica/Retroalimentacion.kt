package org.example

class Retroalimentacion(
    private val correctaInterna: Boolean,
    private val mensajeInterno: String,
    private val detalleInterno: String = ""
) {
    /**
     * Modelo de dominio inmutable que encapsula el resultado pedagógico de una
     * acción realizada por el estudiante dentro del desafío interactivo del
     * Modo Avanzado (selección de fórmula, verificación de sustitución o
     * elección de respuesta numérica).
     *
     * Conserva sus atributos como campos privados y ofrece comportamiento de
     * dominio ([tieneDetalle], [resumen]) para componer el texto final que
     * presenta la interfaz gráfica sin exponer su estructura interna.
     *
     * @property correctaInterna `true` cuando la intervención del usuario acertó.
     * @property mensajeInterno titular breve del resultado.
     * @property detalleInterno explicación conceptual o sugerencia de corrección.
     */

    /**
     * Indica si la acción evaluada fue correcta.
     */
    fun esCorrecta(): Boolean = correctaInterna

    /**
     * Devuelve el mensaje principal breve de la retroalimentación.
     */
    fun mensaje(): String = mensajeInterno

    /**
     * Devuelve la explicación detallada complementaria, o cadena vacía si no
     * se especificó.
     */
    fun detalle(): String = detalleInterno

    /**
     * Comportamiento de dominio: indica si existe una explicación ampliada no
     * vacía además del mensaje principal.
     */
    fun tieneDetalle(): Boolean {
        return detalleInterno.isNotBlank()
    }

    /**
     * Comportamiento de dominio: unifica el mensaje principal y el detalle
     * explicativo en una sola oración lista para presentar en la vista.
     */
    fun resumen(): String {
        return if (tieneDetalle()) "$mensajeInterno $detalleInterno" else mensajeInterno
    }

    /**
     * Representación textual completa de la retroalimentación.
     */
    override fun toString(): String {
        return if (detalleInterno.isNotBlank()) "$mensajeInterno $detalleInterno" else mensajeInterno
    }
}
