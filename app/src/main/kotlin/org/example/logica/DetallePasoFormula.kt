package org.example

class DetallePasoFormula(
    private val partesInternas: List<String>
) {
    constructor(
        tituloInterno: String,
        formulaGeneralInterna: String,
        formulaConValoresInterna: String,
        sustitucionInstanteInterna: String,
        evaluacionIntermediaInterna: String,
        resultadoFinalInterna: String,
        aclaracionDidacticaInterna: String = ""
    ) : this(
        listOf(
            tituloInterno,
            formulaGeneralInterna,
            formulaConValoresInterna,
            sustitucionInstanteInterna,
            evaluacionIntermediaInterna,
            resultadoFinalInterna,
            aclaracionDidacticaInterna
        )
    )

    /**
     * Modelo de dominio que encapsula el desglose didáctico y numérico paso a paso
     * de una fórmula cinemática del Movimiento Rectilíneo Uniformemente Variado
     * (MRUV) aplicado a la caída libre vertical.
     *
     * Conserva todos sus atributos como estado privado inmutable y ofrece
     * predicados de clasificación ([tieneAclaracion], [esFormulaDePosicion],
     * [esFormulaDeVelocidad], [esFormulaDeTiempo]) junto con la exportación
     * estructurada en texto multilínea ([comoTexto]).
     */

    /**
     * Nombre descriptivo de la magnitud física explicada en la tarjeta didáctica.
     */
    fun titulo(): String = partesInternas[0]

    /**
     * Ecuación simbólica general del MRUV antes de reemplazar constantes.
     */
    fun formulaGeneral(): String = partesInternas[1]

    /**
     * Ecuación particularizada con los parámetros iniciales del escenario activo
     * (y₀, v₀ y aceleración gravitatoria g).
     */
    fun formulaConValores(): String = partesInternas[2]

    /**
     * Expresión algebraica resultante tras sustituir el instante de tiempo `t`
     * actual de la simulación.
     */
    fun sustitucionInstante(): String = partesInternas[3]

    /**
     * Paso aritmético intermedio con el valor numérico de cada término antes de
     * la suma o raíz final.
     */
    fun evaluacionIntermedia(): String = partesInternas[4]

    /**
     * Resultado numérico final formateado con su unidad del Sistema Internacional.
     */
    fun resultadoFinal(): String = partesInternas[5]

    /**
     * Nota pedagógica opcional que aclara casos particulares (como el impacto
     * contra el suelo o el uso de la fórmula cuadrática).
     */
    fun aclaracionDidactica(): String = partesInternas.getOrElse(6) { "" }

    /**
     * Comportamiento de dominio: indica si existe una aclaración conceptual no
     * vacía para mostrar al pie de la tarjeta.
     */
    fun tieneAclaracion(): Boolean {
        return partesInternas.getOrElse(6) { "" }.isNotBlank()
    }

    /**
     * Comportamiento de dominio: indica si el desglose corresponde a la ecuación
     * horaria de posición `y(t)`.
     */
    fun esFormulaDePosicion(): Boolean {
        return partesInternas[1].contains("y(t)")
    }

    /**
     * Comportamiento de dominio: indica si el desglose corresponde a la ecuación
     * horaria de velocidad `v(t)`.
     */
    fun esFormulaDeVelocidad(): Boolean {
        return partesInternas[1].contains("v(t)")
    }

    /**
     * Comportamiento de dominio: indica si el desglose corresponde al cálculo
     * del tiempo total de caída `t_final`.
     */
    fun esFormulaDeTiempo(): Boolean {
        return partesInternas[1].contains("t_final")
    }

    /**
     * Comportamiento de dominio: concatena las etapas no vacías de la
     * explicación en un bloque de texto ordenado línea por línea.
     */
    fun comoTexto(): String {
        return partesInternas.filter { it.isNotBlank() }.joinToString(separator = "\n")
    }

    /**
     * Representación compacta del paso de fórmula identificada por su título.
     */
    override fun toString(): String = "DetallePasoFormula(" + partesInternas[0] + ")"
}
