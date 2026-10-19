package org.example

/**
 * Opción de fórmula matemática presentada en la Etapa 1 del desafío.
 *
 * Campos [private] (encapsulación estricta) y comportamiento de dominio para
 * consultar si la opción es la correcta y obtener su retroalimentación.
 *
 * @property textoInterno fórmula mostrada al usuario.
 * @property correctaInterna indica si es la ecuación correcta del ejercicio.
 * @property retroalimentacionInterna explicación del error cuando es incorrecta.
 */
class OpcionFormula(
    private val textoInterno: String,
    private val correctaInterna: Boolean,
    private val retroalimentacionInterna: String
) {

    /** Fórmula mostrada al usuario. */
    fun texto(): String = textoInterno

    /** Indica si es la ecuación correcta. */
    fun esCorrecta(): Boolean = correctaInterna

    /** Comportamiento de dominio: explicación del error de esta opción. */
    fun retroalimentacion(): String = retroalimentacionInterna

    override fun toString(): String = textoInterno
}
