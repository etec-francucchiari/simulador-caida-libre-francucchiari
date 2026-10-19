package org.example

/**
 * Modelo de dominio que representa el resultado de una acción del usuario
 * dentro del desafío interactivo.
 *
 * Contiene comportamiento relevante (composición del mensaje didáctico) en
 * lugar de ser un simple contenedor de datos.
 *
 * @property correctaInterna indica si la acción del usuario fue acertada.
 * @property mensajeInterno mensaje breve del resultado.
 * @property detalleInterno explicación ampliada del error o del acierto.
 */
class Retroalimentacion(
    private val correctaInterna: Boolean,
    private val mensajeInterno: String,
    private val detalleInterno: String = ""
) {

    /** Indica si la acción fue correcta. */
    fun esCorrecta(): Boolean = correctaInterna

    /** Mensaje breve del resultado. */
    fun mensaje(): String = mensajeInterno

    /** Explicación ampliada del resultado. */
    fun detalle(): String = detalleInterno

    /** Comportamiento de dominio: hay explicación ampliada. */
    fun tieneDetalle(): Boolean = detalleInterno.isNotBlank()

    /**
     * Comportamiento de dominio: combina mensaje y detalle en un único texto
     * listo para mostrar en pantalla.
     */
    fun resumen(): String =
        if (tieneDetalle()) "$mensajeInterno $detalleInterno" else mensajeInterno

    override fun toString(): String = resumen()
}
