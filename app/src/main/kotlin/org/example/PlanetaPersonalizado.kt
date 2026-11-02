package org.example

class PlanetaPersonalizado(private val gravedadInterna: Double) : EntornoGravitatorio {
    /**
     * Entorno gravitatorio con una gravedad definida por el usuario.
     *
     * Implementa [EntornoGravitatorio] permitiendo valores de gravedad arbitrarios,
     * a diferencia de los entornos predefinidos de [Planeta].
     *
     * El valor de gravedad se almacena como campo [private]; solo se expone a
     * través del método del contrato.
     */

    override fun nombre(): String {
        return "Personalizado"
    }

    override fun gravedad(): Double {
        return gravedadInterna
    }

    override fun descripcion(): String {
        return "Entorno con gravedad definida por el usuario."
    }

    override fun esPersonalizado(): Boolean {
        return true
    }

    override fun toString(): String {
        return "Personalizado (${gravedadFormateada()} m/s²)"
    }
}
