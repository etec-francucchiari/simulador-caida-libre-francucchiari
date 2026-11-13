package org.example

class DetallePasoFormula(
    private val tituloInterno: String,
    private val formulaGeneralInterna: String,
    private val formulaConValoresInterna: String,
    private val sustitucionInstanteInterna: String,
    private val evaluacionIntermediaInterna: String,
    private val resultadoFinalInterna: String,
    private val aclaracionDidacticaInterna: String = ""
) {
    /**
     * Modelo de dominio que encapsula el desglose didáctico y numérico paso a paso
     * de una fórmula cinemática del Movimiento Rectilíneo Uniformemente Variado
     * (MRUV) aplicado a la caída libre vertical.
     *
     * Conserva todos sus atributos como campos privados inmutables y ofrece
     * predicados de clasificación ([tieneAclaracion], [esFormulaDePosicion],
     * [esFormulaDeVelocidad], [esFormulaDeTiempo]) junto con la exportación
     * estructurada en texto multilínea ([comoTexto]).
     */

    /**
     * Nombre descriptivo de la magnitud física explicada en la tarjeta didáctica.
     */
    fun titulo(): String = tituloInterno

    /**
     * Ecuación simbólica general del MRUV antes de reemplazar constantes.
     */
    fun formulaGeneral(): String = formulaGeneralInterna

    /**
     * Ecuación particularizada con los parámetros iniciales del escenario activo
     * (y₀, v₀ y aceleración gravitatoria g).
     */
    fun formulaConValores(): String = formulaConValoresInterna

    /**
     * Expresión algebraica resultante tras sustituir el instante de tiempo `t`
     * actual de la simulación.
     */
    fun sustitucionInstante(): String = sustitucionInstanteInterna

    /**
     * Paso aritmético intermedio con el valor numérico de cada término antes de
     * la suma o raíz final.
     */
    fun evaluacionIntermedia(): String = evaluacionIntermediaInterna

    /**
     * Resultado numérico final formateado con su unidad del Sistema Internacional.
     */
    fun resultadoFinal(): String = resultadoFinalInterna

    /**
     * Nota pedagógica opcional que aclara casos particulares (como el impacto
     * contra el suelo o el uso de la fórmula cuadrática).
     */
    fun aclaracionDidactica(): String = aclaracionDidacticaInterna

    /**
     * Comportamiento de dominio: indica si existe una aclaración conceptual no
     * vacía para mostrar al pie de la tarjeta.
     */
    fun tieneAclaracion(): Boolean {
        return aclaracionDidacticaInterna.isNotBlank()
    }

    /**
     * Comportamiento de dominio: indica si el desglose corresponde a la ecuación
     * horaria de posición `y(t)`.
     */
    fun esFormulaDePosicion(): Boolean {
        return formulaGeneralInterna.contains("y(t)")
    }

    /**
     * Comportamiento de dominio: indica si el desglose corresponde a la ecuación
     * horaria de velocidad `v(t)`.
     */
    fun esFormulaDeVelocidad(): Boolean {
        return formulaGeneralInterna.contains("v(t)")
    }

    /**
     * Comportamiento de dominio: indica si el desglose corresponde al cálculo
     * del tiempo total de caída `t_final`.
     */
    fun esFormulaDeTiempo(): Boolean {
        return formulaGeneralInterna.contains("t_final")
    }

    /**
     * Comportamiento de dominio: concatena las etapas no vacías de la
     * explicación en un bloque de texto ordenado línea por línea.
     */
    fun comoTexto(): String {
        return listOf(
            tituloInterno,
            formulaGeneralInterna,
            formulaConValoresInterna,
            sustitucionInstanteInterna,
            evaluacionIntermediaInterna,
            resultadoFinalInterna,
            aclaracionDidacticaInterna
        ).filter { it.isNotBlank() }.joinToString(separator = "\n")
    }

    /**
     * Representación compacta del paso de fórmula identificada por su título.
     */
    override fun toString(): String = "DetallePasoFormula($tituloInterno)"
}
