package org.example

import kotlin.math.abs

/**
 * Campo de sustitución de una variable física dentro de una fórmula (Etapa 2).
 *
 * Campos [private] (encapsulación estricta) y comportamiento de dominio para
 * comprobar si el valor ingresado por el estudiante es correcto.
 *
 * @property simboloInterno símbolo de la variable ("y₀", "v₀", "g", "t").
 * @property descripcionInterna descripción legible de la variable.
 * @property valorEsperadoInterno valor numérico correcto del ejercicio.
 * @property unidadInterna unidad de medida de la variable.
 */
class CampoSustitucion(
    private val simboloInterno: String,
    private val descripcionInterna: String,
    private val valorEsperadoInterno: Double,
    private val unidadInterna: String
) {

    /** Símbolo de la variable. */
    fun simbolo(): String = simboloInterno

    /** Descripción legible de la variable. */
    fun descripcion(): String = descripcionInterna

    /** Valor numérico correcto. */
    fun valorEsperado(): Double = valorEsperadoInterno

    /** Unidad de medida. */
    fun unidad(): String = unidadInterna

    /**
     * Comportamiento de dominio: indica si [valorIngresado] coincide con el
     * valor esperado dentro de la [tolerancia].
     */
    fun esAcierto(valorIngresado: Double, tolerancia: Double = 0.05): Boolean =
        valorIngresado.isFinite() && abs(valorIngresado - valorEsperadoInterno) <= tolerancia

    override fun toString(): String =
        "$simboloInterno = $valorEsperadoInterno $unidadInterna"
}
