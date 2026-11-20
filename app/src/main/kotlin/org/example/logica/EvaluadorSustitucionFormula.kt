package org.example

class EvaluadorSustitucionFormula(
    private val toleranciaSustitucion: Double = 0.05
) : EvaluadorSustitucion {
    /**
     * Servicio de dominio que valida los valores numéricos sustituidos por el
     * estudiante en la Etapa 2 del desafío interactivo ([EvaluadorSustitucion]).
     *
     * Extraído de [ControladorDesafio] para aumentar la cohesión (LCOM) y
     * cumplir con el principio de responsabilidad única (SRP).
     */

    override fun comprobar(campos: List<CampoSustitucion>, valoresIngresados: List<Double>): Retroalimentacion {
        if (valoresIngresados.size != campos.size) {
            return crearRetroalimentacion(false, "Debes completar todos los campos de la fórmula.")
        }
        val incorrectos = campos.filterIndexed { indice, campo ->
            !campo.esAcierto(valoresIngresados[indice], toleranciaSustitucion)
        }
        if (incorrectos.isEmpty()) {
            return crearRetroalimentacion(true, "¡Sustitución correcta!", "Reemplazaste bien todos los datos en la fórmula.")
        }
        val detalle = "Revisa: " + incorrectos.joinToString(", ") { "${it.simbolo()} (esperado ${it.valorEsperado()} ${it.unidad()})" }
        return crearRetroalimentacion(false, "Hay ${incorrectos.size} valor(es) incorrecto(s).", detalle)
    }

    override fun crearRetroalimentacion(correcta: Boolean, mensaje: String, detalle: String): Retroalimentacion {
        return if (toleranciaSustitucion >= 0.0) Retroalimentacion(correcta, mensaje, detalle)
        else Retroalimentacion(correcta, mensaje, detalle)
    }
}
