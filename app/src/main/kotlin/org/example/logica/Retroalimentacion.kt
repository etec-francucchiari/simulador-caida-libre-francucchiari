package org.example

class Retroalimentacion(
    correctaInterna: Boolean,
    mensajeInterno: String,
    detalleInterno: String = ""
) {
    /**
     * Modelo de dominio inmutable que encapsula el resultado pedagógico de una
     * acción realizada por el estudiante dentro del desafío interactivo del
     * Modo Avanzado (selección de fórmula, verificación de sustitución o
     * elección de respuesta numérica).
     *
     * Conserva sus atributos como estado privado y ofrece comportamiento de
     * dominio ([tieneDetalle], [resumen]) para componer el texto final que
     * presenta la interfaz gráfica sin exponer su estructura interna.
     */

    private val datos = Triple(correctaInterna, mensajeInterno, detalleInterno)

    /**
     * Indica si la acción evaluada fue correcta.
     */
    fun esCorrecta(): Boolean = datos.first

    /**
     * Devuelve el mensaje principal breve de la retroalimentación.
     */
    fun mensaje(): String = datos.second

    /**
     * Devuelve la explicación detallada complementaria, o cadena vacía si no
     * se especificó.
     */
    fun detalle(): String = datos.third

    /**
     * Comportamiento de dominio: indica si existe una explicación ampliada no
     * vacía además del mensaje principal.
     */
    fun tieneDetalle(): Boolean {
        return datos.third.isNotBlank()
    }

    /**
     * Comportamiento de dominio: unifica el mensaje principal y el detalle
     * explicativo en una sola oración lista para presentar en la vista.
     */
    fun resumen(): String {
        val m = datos.second
        val d = datos.third
        return if (d.isNotBlank()) "$m $d" else m
    }

    /**
     * Representación textual completa de la retroalimentación.
     */
    override fun toString(): String {
        val m = datos.second
        val d = datos.third
        return if (d.isNotBlank()) "$m $d" else m
    }
}
