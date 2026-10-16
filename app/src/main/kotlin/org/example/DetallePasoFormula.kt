package org.example

/**
 * Modelo de dominio que encapsula el desglose didáctico y numérico paso a paso
 * de una fórmula cinemática del Movimiento Rectilíneo Uniformemente Variado.
 *
 * Campos [private] (encapsulación estricta) y comportamiento de dominio:
 * [tieneAclaracion], [esFormulaDePosicion] y [comoTexto].
 */
class DetallePasoFormula(
    private val tituloInterno: String,
    private val formulaGeneralInterna: String,
    private val formulaConValoresInterna: String,
    private val sustitucionInstanteInterna: String,
    private val evaluacionIntermediaInterna: String,
    private val resultadoFinalInterna: String,
    private val aclaracionDidacticaInterna: String = ""
) {

    /** Nombre descriptivo de la magnitud física calculada. */
    fun titulo(): String = tituloInterno

    /** Ecuación formal teórica del MRUV. */
    fun formulaGeneral(): String = formulaGeneralInterna

    /** Ecuación con los parámetros de entrada reemplazados. */
    fun formulaConValores(): String = formulaConValoresInterna

    /** Expresión con el tiempo instantáneo evaluado. */
    fun sustitucionInstante(): String = sustitucionInstanteInterna

    /** Desglose aritmético de operaciones intermedias. */
    fun evaluacionIntermedia(): String = evaluacionIntermediaInterna

    /** Valor numérico final con sus unidades. */
    fun resultadoFinal(): String = resultadoFinalInterna

    /** Explicación conceptual adicional para el estudiante. */
    fun aclaracionDidactica(): String = aclaracionDidacticaInterna

    /** Comportamiento de dominio: hay nota didáctica disponible. */
    fun tieneAclaracion(): Boolean = aclaracionDidacticaInterna.isNotBlank()

    /** Comportamiento de dominio: corresponden a la fórmula de posición. */
    fun esFormulaDePosicion(): Boolean = formulaGeneralInterna.contains("y(t)")

    /** Comportamiento de dominio: representación completa del paso. */
    fun comoTexto(): String = listOf(
        tituloInterno,
        formulaGeneralInterna,
        formulaConValoresInterna,
        sustitucionInstanteInterna,
        evaluacionIntermediaInterna,
        resultadoFinalInterna,
        aclaracionDidacticaInterna
    ).filter { it.isNotBlank() }.joinToString(separator = "\n")

    override fun toString(): String = "DetallePasoFormula($tituloInterno)"
}