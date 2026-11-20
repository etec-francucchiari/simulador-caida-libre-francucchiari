package org.example

import kotlin.math.abs

class OpcionRespuesta(
    valorInterno: Double,
    textoInterno: String,
    correctaInterna: Boolean,
    retroalimentacionInterna: String
) {
    /**
     * Modelo de dominio inmutable que representa una opción de respuesta
     * numérica en la Etapa 2 del desafío interactivo ([VistaEtapaSustitucion]).
     *
     * Mantiene privados su valor numérico, su etiqueta con unidad del Sistema
     * Internacional, su condición de respuesta verdadera y el diagnóstico del
     * error aritmético asociado, ofreciendo comportamiento de dominio para
     * comparar proximidad numérica ([coincideCon]) y emitir la
     * [Retroalimentacion] pedagógica al ser seleccionada ([evaluarRespuesta]).
     */

    private val datos = Pair(
        Pair(valorInterno, textoInterno),
        Pair(correctaInterna, retroalimentacionInterna)
    )

    /**
     * Devuelve el valor numérico real representado por esta opción.
     */
    fun valor(): Double = datos.first.first

    /**
     * Devuelve el texto formateado con dos decimales y su unidad de medida.
     */
    fun texto(): String = datos.first.second

    /**
     * Comportamiento de dominio: indica si esta opción contiene el resultado
     * numérico verdadero del problema.
     */
    fun esCorrecta(): Boolean {
        return datos.second.first
    }

    /**
     * Devuelve la explicación del error de cálculo cometido cuando se elige un
     * distractor numérico.
     */
    fun retroalimentacion(): String = datos.second.second

    /**
     * Comportamiento de dominio: determina si el valor de esta opción difiere de
     * [valor] en a lo sumo [tolerancia] unidades.
     */
    fun coincideCon(valor: Double, tolerancia: Double): Boolean {
        return abs(datos.first.first - valor) <= tolerancia
    }

    /**
     * Comportamiento de dominio: construye la [Retroalimentacion] que recibe el
     * estudiante al confirmar esta opción como respuesta final del ejercicio.
     */
    fun evaluarRespuesta(): Retroalimentacion {
        if (datos.second.first) {
            val etiqueta = datos.first.second
            return Retroalimentacion(
                true,
                "¡Resultado correcto!",
                "El valor $etiqueta coincide con el cálculo teórico. Ahora puedes simular para comprobarlo."
            )
        }
        return Retroalimentacion(false, "Resultado incorrecto.", datos.second.second)
    }

    /**
     * Representación legible de la opción con su magnitud y unidad.
     */
    override fun toString(): String {
        return datos.first.second
    }
}
