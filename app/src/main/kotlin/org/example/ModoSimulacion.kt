package org.example

/**
 * Representa los modos de operación y visualización del simulador de caída libre.
 *
 * Los textos (título y descripción) se encapsulan como campos [private] y se
 * exponen mediante métodos, evitando atributos públicos directos.
 *
 * Comportamiento de dominio incluido para evitar una enumeración anémica:
 * [esPrincipiante] y [esAvanzado].
 */
enum class ModoSimulacion(
    private val tituloInterno: String,
    private val descripcionInterna: String
) {
    PRINCIPIANTE(
        tituloInterno = "Modo Principiante (Fórmulas paso a paso)",
        descripcionInterna =
            "Explicación física paso a paso con reemplazo de fórmulas MRUV y cálculo dinámico en tiempo real."
    ),
    ESTANDAR(
        tituloInterno = "Modo Avanzado / Estándar",
        descripcionInterna =
            "Visualización directa de parámetros cinemáticos, gráficos e indicadores estándar."
    );

    /** Título legible del modo. */
    fun titulo(): String = tituloInterno

    /** Descripción didáctica del modo. */
    fun descripcion(): String = descripcionInterna

    /** Comportamiento de dominio: indica si es el modo guiado. */
    fun esPrincipiante(): Boolean = this == PRINCIPIANTE

    /** Comportamiento de dominio: indica si es el modo avanzado estándar. */
    fun esAvanzado(): Boolean = this == ESTANDAR

    override fun toString(): String = tituloInterno
}