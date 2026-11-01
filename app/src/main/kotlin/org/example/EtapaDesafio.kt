package org.example

enum class EtapaDesafio(
    private val numeroInterno: Int,
    private val tituloInterno: String,
    private val descripcionInterna: String
) {
    FORMULA(
        numeroInterno = 1,
        tituloInterno = "Selección de la fórmula",
        descripcionInterna = "Elige la ecuación correcta para resolver el ejercicio propuesto."
    ),
    SUSTITUCION(
        numeroInterno = 2,
        tituloInterno = "Sustitución y resultado",
        descripcionInterna = "Reemplaza los datos numéricos y elige la respuesta final."
    ),
    VALIDACION(
        numeroInterno = 3,
        tituloInterno = "Validación y simulación",
        descripcionInterna = "Contrasta el resultado teórico con la simulación en tiempo real."
    );

    /**
     * Enumerador de dominio que modela las tres etapas secuenciales del desafío
     * interactivo en el Modo Avanzado:
     * 1. [FORMULA]: identificación simbólica de la ley horaria aplicable.
     * 2. [SUSTITUCION]: reemplazo numérico de constantes y elección del valor.
     * 3. [VALIDACION]: comprobación experimental mediante la simulación visual.
     *
     * Todos sus atributos se mantienen privados y expone comportamiento de
     * clasificación ([esFormula], [esSustitucion], [esValidacion]) y transición
     * de estados ([siguiente], [reiniciar]).
     */

    /**
     * Número ordinal humano de la etapa (del `1` al `3`).
     */
    fun numero(): Int = numeroInterno

    /**
     * Título breve de la etapa para encabezados y migas de pan.
     */
    fun titulo(): String = tituloInterno

    /**
     * Explicación didáctica de la consigna que el estudiante debe realizar.
     */
    fun descripcion(): String = descripcionInterna

    /**
     * Comportamiento de dominio: indica si corresponde a la primera etapa
     * de selección de fórmula algebraica.
     */
    fun esFormula(): Boolean {
        return numeroInterno == 1
    }

    /**
     * Comportamiento de dominio: indica si corresponde a la segunda etapa
     * de sustitución numérica y cálculo.
     */
    fun esSustitucion(): Boolean {
        return numeroInterno == 2
    }

    /**
     * Comportamiento de dominio: indica si corresponde a la tercera etapa
     * de validación experimental con la animación desbloqueada.
     */
    fun esValidacion(): Boolean {
        return numeroInterno == 3
    }

    /**
     * Comportamiento de dominio: devuelve la etapa inmediata siguiente en la
     * progresión pedagógica, o la misma instancia si ya se está en [VALIDACION].
     */
    fun siguiente(): EtapaDesafio {
        return entries.firstOrNull { it.numeroInterno == numeroInterno + 1 } ?: this
    }

    /**
     * Comportamiento de dominio: devuelve la etapa inicial ([FORMULA]) para
     * comenzar un nuevo ejercicio.
     */
    fun reiniciar(): EtapaDesafio {
        return entries.first()
    }

    /**
     * Representación textual con el progreso (`Etapa X de 3`) y el título.
     */
    override fun toString(): String {
        return "Etapa $numeroInterno de 3 · $tituloInterno"
    }
}
