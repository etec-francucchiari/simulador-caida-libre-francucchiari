package org.example

import kotlin.math.abs

/**
 * Opción de respuesta numérica de la Etapa 2 del desafío (opción múltiple).
 *
 * Campos [private] (encapsulación estricta) y comportamiento de dominio para
 * comparar el valor con una tolerancia y describir el resultado.
 *
 * @property valorInterno valor numérico asociado a la opción.
 * @property textoInterno texto ya formateado con su unidad (por ejemplo "45.00 m").
 * @property correctaInterna indica si es la respuesta correcta.
 * @property retroalimentacionInterna explicación del error de cálculo común.
 */
class OpcionRespuesta(
    private val valorInterno: Double,
    private val textoInterno: String,
    private val correctaInterna: Boolean,
    private val retroalimentacionInterna: String
) {

    /** Valor numérico de la opción. */
    fun valor(): Double = valorInterno

    /** Texto formateado con unidad. */
    fun texto(): String = textoInterno

    /** Indica si es la respuesta correcta. */
    fun esCorrecta(): Boolean = correctaInterna

    /** Comportamiento de dominio: explicación del error de cálculo. */
    fun retroalimentacion(): String = retroalimentacionInterna

    /**
     * Comportamiento de dominio: indica si el valor de la opción coincide con
     * [valor] dentro de la [tolerancia] indicada.
     */
    fun coincideCon(valor: Double, tolerancia: Double): Boolean =
        abs(valorInterno - valor) <= tolerancia

    override fun toString(): String = textoInterno
}
