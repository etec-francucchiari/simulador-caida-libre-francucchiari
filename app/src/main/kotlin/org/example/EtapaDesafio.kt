package org.example

/**
 * Etapas del desafío interactivo del Modo Avanzado.
 *
 * Enumera el avance pedagógico del ejercicio (fórmula, sustitución y
 * validación) con campos [private] y comportamiento de clasificación.
 */
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

    /** Número de etapa (1 a 3). */
    fun numero(): Int = numeroInterno

    /** Título corto de la etapa. */
    fun titulo(): String = tituloInterno

    /** Descripción didáctica de la etapa. */
    fun descripcion(): String = descripcionInterna

    /** Comportamiento de dominio: la etapa es la de selección de fórmula. */
    fun esFormula(): Boolean = this == FORMULA

    /** Comportamiento de dominio: la etapa es la de sustitución de valores. */
    fun esSustitucion(): Boolean = this == SUSTITUCION

    /** Comportamiento de dominio: la etapa es la de validación final. */
    fun esValidacion(): Boolean = this == VALIDACION

    /** Representación legible con el número de etapa. */
    override fun toString(): String = "Etapa $numeroInterno de 3 · $tituloInterno"
}
