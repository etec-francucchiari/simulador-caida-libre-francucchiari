package org.example

import java.util.Locale
import java.util.Random
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Servicio instanciable que genera las opciones de respuesta numérica
 * (Etapa 2) de un ejercicio: la correcta y dos distractores construidos a
 * partir de errores de cálculo comunes.
 *
 * La aleatoriedad del orden se inyecta por constructor para poder reproducir
 * resultados en pruebas.
 */
class GeneradorOpcionesRespuesta(
    private val aleatorio: Random = Random()
) : GeneradorDeRespuestas {

    /**
     * Comportamiento de dominio: devuelve la respuesta correcta y dos
     * distractores numéricamente distintos entre sí, en orden aleatorio.
     */
    override fun generar(ejercicio: EjercicioCaidaLibre): List<OpcionRespuesta> {
        val correcto = ejercicio.valorCorrecto()
        val unidad = ejercicio.unidadResultado()

        val correcta = OpcionRespuesta(
            valorInterno = correcto,
            textoInterno = formatear(correcto, unidad),
            correctaInterna = true,
            retroalimentacionInterna =
                "¡Excelente! Aplicaste correctamente la fórmula del ejercicio."
        )

        val seleccionados = seleccionarDistintos(candidatosIncorrectos(ejercicio), correcto)

        val opciones = mutableListOf(correcta)
        seleccionados.forEach { (valor, explicacion) ->
            opciones.add(
                OpcionRespuesta(
                    valorInterno = valor,
                    textoInterno = formatear(valor, unidad),
                    correctaInterna = false,
                    retroalimentacionInterna = explicacion
                )
            )
        }
        opciones.shuffle(aleatorio)
        return opciones
    }

    private fun candidatosIncorrectos(
        ejercicio: EjercicioCaidaLibre
    ): List<Pair<Double, String>> {
        val y0 = ejercicio.alturaInicial()
        val v0 = ejercicio.velocidadInicial()
        val g = ejercicio.gravedad()
        val t = ejercicio.tiempoEvaluacion()

        return when (ejercicio.objetivo()) {
            ObjetivoEjercicio.CALCULAR_POSICION -> listOf(
                (y0 - v0 * t - 0.5 * g * t) to
                    "Quizá olvidaste elevar el tiempo al cuadrado (t², no t).",
                (y0 - v0 * t + 0.5 * g * t * t) to
                    "Revisa el signo del término de la gravedad: debe restar."
            )

            ObjetivoEjercicio.CALCULAR_VELOCIDAD -> listOf(
                (g * t) to
                    "Olvidaste sumar la velocidad inicial v₀ a g·t.",
                (v0 - g * t) to
                    "Revisa el signo: en una caída la rapidez es v₀ + g·t."
            )

            ObjetivoEjercicio.CALCULAR_TIEMPO -> listOf(
                sqrt(2.0 * y0 / g) to
                    "Usaste la fórmula simplificada que supone v₀ = 0.",
                ((v0 + sqrt(v0 * v0 + 2.0 * g * y0)) / g) to
                    "Revisa el signo de v₀ en el numerador: debe restarse."
            )
        }
    }

    private fun seleccionarDistintos(
        candidatos: List<Pair<Double, String>>,
        correcto: Double
    ): List<Pair<Double, String>> {
        val seleccionados = mutableListOf<Pair<Double, String>>()
        val tolerancia = 1e-6

        for (candidato in candidatos) {
            val valor = candidato.first
            if (!valor.isFinite()) continue
            if (abs(valor - correcto) <= tolerancia) continue
            if (seleccionados.any { abs(it.first - valor) <= tolerancia }) continue
            seleccionados.add(candidato)
            if (seleccionados.size == 2) break
        }

        // Red de seguridad: garantiza siempre dos distractores distintos.
        var factor = 1.25
        while (seleccionados.size < 2) {
            val valor = correcto * factor + 0.5
            if (abs(valor - correcto) > tolerancia &&
                seleccionados.none { abs(it.first - valor) <= tolerancia }
            ) {
                seleccionados.add(valor to "Error de cálculo común.")
            }
            factor += 0.25
        }
        return seleccionados
    }

    private fun formatear(valor: Double, unidad: String): String =
        "${String.format(Locale.ROOT, "%.2f", valor)} $unidad"
}
