package org.example

import java.util.*
import kotlin.math.*

class GeneradorOpcionesRespuesta(
    private val aleatorio: Random = Random()
) : GeneradorDeRespuestas {
    /**
     * Servicio instanciable que genera las opciones de respuesta numérica
     * (Etapa 2) de un ejercicio: la correcta y dos distractores construidos a
     * partir de errores de cálculo comunes.
     */

    private val tolerancia = 1e-6

    /** Devuelve la respuesta correcta y dos distractores distintos en orden aleatorio. */
    override fun generar(ejercicio: EjercicioCaidaLibre): List<OpcionRespuesta> {
        val correcto = ejercicio.valorCorrecto()
        val unidad = ejercicio.unidadResultado()
        val correcta = OpcionRespuesta(
            valorInterno = correcto,
            textoInterno = formatear(correcto, unidad),
            correctaInterna = true,
            retroalimentacionInterna = "¡Excelente! Aplicaste correctamente la fórmula del ejercicio."
        )
        val seleccionados = seleccionarDistintos(candidatosIncorrectos(ejercicio), correcto)
        val opciones = mutableListOf(correcta)
        for ((valor, explicacion) in seleccionados) {
            opciones.add(OpcionRespuesta(valor, formatear(valor, unidad), false, explicacion))
        }
        opciones.shuffle(aleatorio)
        return opciones
    }

    private fun candidatosIncorrectos(ejercicio: EjercicioCaidaLibre): List<Pair<Double, String>> {
        val y0 = ejercicio.alturaInicial()
        val v0 = ejercicio.velocidadInicial()
        val g = ejercicio.gravedad()
        val t = ejercicio.tiempoEvaluacion()
        return when (ejercicio.objetivo()) {
            ObjetivoEjercicio.CALCULAR_POSICION -> listOf(
                (y0 - v0 * t - 0.5 * g * t) to "Quizá olvidaste elevar el tiempo al cuadrado (t², no t).",
                (y0 - v0 * t + 0.5 * g * t * t) to "Revisa el signo del término de la gravedad: debe restar."
            )
            ObjetivoEjercicio.CALCULAR_VELOCIDAD -> listOf(
                (g * t) to "Olvidaste sumar la velocidad inicial v₀ a g·t.",
                (v0 - g * t) to "Revisa el signo: en una caída la rapidez es v₀ + g·t."
            )
            ObjetivoEjercicio.CALCULAR_TIEMPO -> listOf(
                sqrt(2.0 * y0 / g) to "Usaste la fórmula simplificada que supone v₀ = 0.",
                ((v0 + sqrt(v0 * v0 + 2.0 * g * y0)) / g) to "Revisa el signo de v₀ en el numerador: debe restarse."
            )
        }
    }

    private fun seleccionarDistintos(candidatos: List<Pair<Double, String>>, correcto: Double): List<Pair<Double, String>> {
        val seleccionados = mutableListOf<Pair<Double, String>>()
        for (candidato in candidatos) {
            val v = candidato.first
            if (v.isFinite() && abs(v - correcto) > tolerancia && seleccionados.none { abs(it.first - v) <= tolerancia }) {
                seleccionados.add(candidato)
                if (seleccionados.size == 2) break
            }
        }
        var factor = 1.25
        while (seleccionados.size < 2) {
            val v = correcto * factor + 0.5
            if (abs(v - correcto) > tolerancia && seleccionados.none { abs(it.first - v) <= tolerancia }) {
                seleccionados.add(v to "Error de cálculo común.")
            }
            factor += 0.25
        }
        return seleccionados
    }

    private fun formatear(valor: Double, unidad: String): String =
        "${String.format(Locale.ROOT, "%.2f", valor)} $unidad"
}
