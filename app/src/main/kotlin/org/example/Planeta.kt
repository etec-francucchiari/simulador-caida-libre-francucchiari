package org.example

enum class Planeta(
    private val nombreInterno: String,
    private val gravedadInterna: Double,
    private val descripcionInterna: String
) : EntornoGravitatorio {
    TIERRA(
        nombreInterno = "Tierra",
        gravedadInterna = 9.81,
        descripcionInterna = "g = 9.81 m/s²"
    ),
    LUNA(
        nombreInterno = "Luna",
        gravedadInterna = 1.62,
        descripcionInterna = "g = 1.62 m/s²"
    ),
    MARTE(
        nombreInterno = "Marte",
        gravedadInterna = 3.71,
        descripcionInterna = "g = 3.71 m/s²"
    ),
    JUPITER(
        nombreInterno = "Júpiter",
        gravedadInterna = 24.79,
        descripcionInterna = "g = 24.79 m/s²"
    );

    /**
     * Entornos gravitatorios predefinidos del sistema solar.
     *
     * Se modela como enumeración para garantizar un conjunto finito e inmutable de
     * entornos. Cada entrada encapsula sus datos como campos [private]; el acceso
     * se realiza mediante los métodos del contrato [EntornoGravitatorio].
     */

    override fun nombre(): String {
        return nombreInterno
    }

    override fun gravedad(): Double {
        return gravedadInterna
    }

    override fun descripcion(): String {
        return descripcionInterna
    }

    /**
     * Comportamiento de dominio: una representación legible de cada entorno.
     */
    override fun toString(): String {
        return "$nombreInterno ($gravedadInterna m/s²)"
    }
}
