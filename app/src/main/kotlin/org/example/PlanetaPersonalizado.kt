package org.example

/**
 * Entorno gravitatorio con una gravedad definida por el usuario.
 *
 * Implementa [EntornoGravitatorio] permitiendo valores de gravedad arbitrarios,
 * a diferencia de los entornos predefinidos de [Planeta].
 *
 * El valor de gravedad se almacena como campo [private]; solo se expone a
 * través del método del contrato.
 */
class PlanetaPersonalizado(
    private val gravedadInterna: Double
) : EntornoGravitatorio {

    override fun nombre(): String = "Personalizado"

    override fun gravedad(): Double = gravedadInterna

    override fun descripcion(): String = "Entorno con gravedad definida por el usuario."

    override fun toString(): String = "Personalizado (${gravedadFormateada()} m/s²)"
}