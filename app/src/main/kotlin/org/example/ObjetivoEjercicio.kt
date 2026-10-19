package org.example

/**
 * Objetivo de cálculo de un ejercicio del Modo Avanzado.
 *
 * Se modela como enumeración con campos [private] (encapsulación estricta) y
 * comportamiento de dominio: descripción didáctica, variable incógnita y
 * clasificación del objetivo. Evita una enumeración anémica.
 */
enum class ObjetivoEjercicio(
    private val descripcionInterna: String,
    private val variableInterna: String,
    private val unidadInterna: String
) {

    CALCULAR_POSICION(
        descripcionInterna = "calcular la posición (altura) del objeto",
        variableInterna = "y",
        unidadInterna = "m"
    ),
    CALCULAR_VELOCIDAD(
        descripcionInterna = "calcular la velocidad del objeto",
        variableInterna = "v",
        unidadInterna = "m/s"
    ),
    CALCULAR_TIEMPO(
        descripcionInterna = "calcular el tiempo total de caída",
        variableInterna = "t",
        unidadInterna = "s"
    );

    /** Descripción didáctica del objetivo. */
    fun descripcion(): String = descripcionInterna

    /** Variable incógnita del ejercicio. */
    fun variable(): String = variableInterna

    /** Unidad de medida del resultado. */
    fun unidad(): String = unidadInterna

    /** Comportamiento de dominio: el objetivo es la posición. */
    fun esPosicion(): Boolean = this == CALCULAR_POSICION

    /** Comportamiento de dominio: el objetivo es la velocidad. */
    fun esVelocidad(): Boolean = this == CALCULAR_VELOCIDAD

    /** Comportamiento de dominio: el objetivo es el tiempo de caída. */
    fun esTiempo(): Boolean = this == CALCULAR_TIEMPO

    override fun toString(): String = descripcionInterna
}
