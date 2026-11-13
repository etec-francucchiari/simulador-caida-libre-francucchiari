package org.example

enum class Planeta(
    private val nombreInterno: String,
    private val gravedadInterna: Double
) : EntornoGravitatorio {
    TIERRA(
        nombreInterno = "Tierra",
        gravedadInterna = 9.81
    ),
    LUNA(
        nombreInterno = "Luna",
        gravedadInterna = 1.62
    ),
    MARTE(
        nombreInterno = "Marte",
        gravedadInterna = 3.71
    ),
    JUPITER(
        nombreInterno = "Júpiter",
        gravedadInterna = 24.79
    );

    /**
     * Entornos gravitatorios predefinidos del sistema solar.
     *
     * Se modela como enumeración para garantizar un conjunto finito e inmutable de
     * entornos. Cada entrada encapsula sus datos como campos [private]; el acceso
     * se realiza mediante los métodos del contrato [EntornoGravitatorio].
     */

    override fun nombre(): String = nombreInterno

    override fun gravedad(): Double = gravedadInterna

    override fun descripcion(): String {
        return "$nombreInterno (g = $gravedadInterna m/s²)"
    }

    /**
     * Comportamiento de dominio: una representación legible de cada entorno.
     */
    override fun toString(): String {
        return "$nombreInterno ($gravedadInterna m/s²)"
    }
}
