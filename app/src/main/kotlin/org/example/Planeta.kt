package org.example

/**
 * Entornos gravitatorios predefinidos del sistema solar.
 *
 * Se modela como enumeración para garantizar un conjunto finito e inmutable de
 * entornos. Cada entrada encapsula sus datos como campos [private]; el acceso
 * se realiza mediante los métodos del contrato [EntornoGravitatorio].
 *
 * No contiene `object` ni utilidades estáticas: el catálogo de selección vive en
 * [CatalogoPlanetas].
 */
enum class Planeta(
    private val nombreInterno: String,
    private val gravedadInterna: Double,
    private val descripcionInterna: String
) : EntornoGravitatorio {

    TIERRA(
        nombreInterno = "Tierra",
        gravedadInterna = 9.81,
        descripcionInterna = "Planeta natal. Gravedad estándar de 9.81 m/s²."
    ),
    LUNA(
        nombreInterno = "Luna",
        gravedadInterna = 1.62,
        descripcionInterna = "Satélite natural de la Tierra. Gravedad ~6 veces menor."
    ),
    MARTE(
        nombreInterno = "Marte",
        gravedadInterna = 3.71,
        descripcionInterna = "El planeta rojo. Gravedad ~38% de la de la Tierra."
    ),
    JUPITER(
        nombreInterno = "Júpiter",
        gravedadInterna = 24.79,
        descripcionInterna = "El gigante gaseoso. Gravedad ~2.5 veces la de la Tierra."
    );

    override fun nombre(): String = nombreInterno

    override fun gravedad(): Double = gravedadInterna

    override fun descripcion(): String = descripcionInterna

    /**
     * Comportamiento de dominio: una representación legible de cada entorno.
     */
    override fun toString(): String = "$nombreInterno (${gravedadFormateada()} m/s²)"
}