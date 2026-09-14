package org.example

/**
 * Representa un planeta del sistema solar con su gravedad asociada.
 *
 * Cada planeta almacena:
 * - [nombre]: nombre legible del planeta.
 * - [gravedad]: aceleración de la gravedad en m/s².
 * - [descripcion]: texto descriptivo breve del planeta para la interfaz.
 *
 * Se utiliza como catálogo inmutable para el selector de planeta del simulador.
 */
enum class Planeta(
    val nombre: String,
    val gravedad: Double,
    val descripcion: String
) {
    TIERRA(
        nombre = "Tierra",
        gravedad = 9.8,
        descripcion = "Planeta natal. Gravedad estándar de 9.8 m/s²."
    ),
    LUNA(
        nombre = "Luna",
        gravedad = 1.62,
        descripcion = "Satélite natural de la Tierra. Gravedad 6 veces menor."
    ),
    JUPITER(
        nombre = "Júpiter",
        gravedad = 24.79,
        descripcion = "El gigante gaseoso. Gravedad casi 2.5 veces la de la Tierra."
    );

    /**
     * Devuelve una representación en cadena con el nombre y la gravedad.
     * Se usa como texto visible en el selector (ComboBox).
     */
    override fun toString(): String {
        return "$nombre (${gravedad} m/s²)"
    }
}
