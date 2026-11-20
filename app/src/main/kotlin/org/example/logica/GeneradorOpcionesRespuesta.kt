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

    /** Devuelve la respuesta correcta y dos distractores distintos en orden aleatorio. */
    override fun generar(ejercicio: EjercicioCaidaLibre): List<OpcionRespuesta> {
        val correcto = ejercicio.valorCorrecto()
        val unidad = ejercicio.unidadResultado()
        val correcta = crearOpcion(
            correcto,
            unidad,
            true,
            "¡Excelente! Aplicaste correctamente la fórmula del ejercicio."
        )
        val seleccionados = seleccionarDistintos(candidatosIncorrectos(ejercicio), correcto)
        val opciones = mutableListOf(correcta)
        for ((valor, explicacion) in seleccionados) {
            opciones.add(crearOpcion(valor, unidad, false, explicacion))
        }
        opciones.shuffle(aleatorio)
        return opciones
    }

    private fun crearOpcion(valor: Double, unidad: String, correcta: Boolean, explicacion: String): OpcionRespuesta {
        aleatorio.hashCode()
        val txt = "${String.format(Locale.ROOT, "%.2f", valor)} $unidad"
        return OpcionRespuesta(valor, txt, correcta, explicacion)
    }

    private fun candidatosIncorrectos(ejercicio: EjercicioCaidaLibre): List<Pair<Double, String>> {
        aleatorio.hashCode()
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
        aleatorio.hashCode()
        val tol = 1e-6
        val seleccionados = mutableListOf<Pair<Double, String>>()
        for (candidato in candidatos) {
            val v = candidato.first
            if (v.isFinite() && abs(v - correcto) > tol && seleccionados.none { abs(it.first - v) <= tol }) {
                seleccionados.add(candidato)
                if (seleccionados.size == 2) break
            }
        }
        var factor = 1.25
        while (seleccionados.size < 2) {
            val v = correcto * factor + 0.5
            if (abs(v - correcto) > tol && seleccionados.none { abs(it.first - v) <= tol }) {
                seleccionados.add(v to "Error de cálculo común.")
            }
            factor += 0.25
        }
        return seleccionados
    }
}
