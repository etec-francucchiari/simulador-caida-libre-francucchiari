package org.example

enum class Planeta(
    nombreInterno: String,
    gravedadInterna: Double
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
     * entornos. Cada entrada encapsula sus datos como estado privado; el acceso
     * se realiza mediante los métodos del contrato [EntornoGravitatorio].
     */

    private val datos = Pair(nombreInterno, gravedadInterna)

    override fun nombre(): String = datos.first

    override fun gravedad(): Double = datos.second

    override fun descripcion(): String {
        val n = datos.first
        val g = datos.second
        return "$n (g = $g m/s²)"
    }

    /**
     * Comportamiento de dominio: una representación legible de cada entorno.
     */
    override fun toString(): String {
        val n = datos.first
        val g = datos.second
        return "$n ($g m/s²)"
    }
}
