package org.example

import java.util.Locale
import kotlin.math.max
import kotlin.math.sqrt

/**
 * Servicio de dominio que genera las explicaciones físicas paso a paso de las
 * fórmulas del MRUV para el Modo Principiante.
 *
 * Es instanciable y recibe por constructor su dependencia [CalculoCaidaLibre]
 * (inversión de dependencias). Realiza el reemplazo dinámico de variables
 * (y₀, v₀, g) y la evaluación numérica instantánea.
 */
class ExplicadorFisicaPasoAPaso(
    private val calculo: CalculadoraCaidaLibre
) : ExplicadorFisica {

    /**
     * Explicación paso a paso de la fórmula de posición:
     * `y(t) = y₀ + v₀·t - ½·g·t²`.
     */
    override fun explicarPosicion(h0: Double, v0: Double, g: Double, t: Double): DetallePasoFormula {
        val h0Segura = max(0.0, h0)
        val v0Segura = max(0.0, v0)
        val gSegura = if (g > 0.0) g else 9.8
        val tSeguro = max(0.0, t)

        val formulaGeneral = "y(t) = y₀ + v₀·t - ½·g·t²"

        val v0SignoTexto = if (v0Segura == 0.0) "+ (0.00)·t" else "+ (-${formatear(v0Segura)})·t"
        val medioG = 0.5 * gSegura
        val formulaConValores =
            "y(t) = ${formatear(h0Segura)} $v0SignoTexto - ½·(${formatear(gSegura)})·t²"

        val tiempoCaida = calculo.calcularTiempoCaida(h0Segura, v0Segura, gSegura) ?: 0.0
        val haTocadoSuelo = tSeguro >= tiempoCaida || h0Segura <= 0.0

        val sustitucionInstante =
            "y(${formatear(tSeguro)}) = ${formatear(h0Segura)} + (-${formatear(v0Segura)} · ${formatear(tSeguro)}) - (0.5 · ${formatear(gSegura)} · ${formatear(tSeguro)}²)"

        val terminoV0 = -v0Segura * tSeguro
        val terminoGrav = medioG * tSeguro * tSeguro

        val evaluacionIntermedia: String
        val resultadoFinal: String
        val aclaracion: String

        if (haTocadoSuelo) {
            evaluacionIntermedia =
                "y(${formatear(tSeguro)}) = ${formatear(h0Segura)} + (${formatear(terminoV0)}) - ${formatear(terminoGrav)} ≤ 0.00"
            resultadoFinal = "y(${formatear(tSeguro)}) = 0.00 m (Impacto en el suelo)"
            aclaracion =
                "El cuerpo ha alcanzado el suelo en t = ${formatear(tiempoCaida)} s y permanece en reposo (y = 0 m)."
        } else {
            val yActual = max(0.0, h0Segura + terminoV0 - terminoGrav)
            evaluacionIntermedia =
                "y(${formatear(tSeguro)}) = ${formatear(h0Segura)} + (${formatear(terminoV0)}) - ${formatear(terminoGrav)}"
            resultadoFinal = "y(${formatear(tSeguro)}) = ${formatear(yActual)} m"
            aclaracion =
                "Altura actual sobre el suelo calculada restando el efecto de la caída a la posición inicial."
        }

        return DetallePasoFormula(
            "1. Posición en función del tiempo y(t)",
            formulaGeneral,
            formulaConValores,
            sustitucionInstante,
            evaluacionIntermedia,
            resultadoFinal,
            aclaracion
        )
    }

    /**
     * Explicación paso a paso de la fórmula de velocidad:
     * `v(t) = v₀ - g·t`.
     */
    override fun explicarVelocidad(h0: Double, v0: Double, g: Double, t: Double): DetallePasoFormula {
        val h0Segura = max(0.0, h0)
        val v0Segura = max(0.0, v0)
        val gSegura = if (g > 0.0) g else 9.8
        val tSeguro = max(0.0, t)

        val formulaGeneral = "v(t) = v₀ - g·t"
        val v0Texto = if (v0Segura == 0.0) "0.00" else "-${formatear(v0Segura)}"
        val formulaConValores = "v(t) = $v0Texto - (${formatear(gSegura)})·t"

        val tiempoCaida = calculo.calcularTiempoCaida(h0Segura, v0Segura, gSegura) ?: 0.0
        val haTocadoSuelo = tSeguro >= tiempoCaida || h0Segura <= 0.0

        val sustitucionInstante =
            "v(${formatear(tSeguro)}) = $v0Texto - (${formatear(gSegura)} · ${formatear(tSeguro)})"

        val evaluacionIntermedia: String
        val resultadoFinal: String
        val aclaracion: String

        if (haTocadoSuelo) {
            val vImpacto = calculo.calcularVelocidadImpacto(h0Segura, v0Segura, gSegura) ?: 0.0
            evaluacionIntermedia = "Velocidad de impacto alcanzada en el suelo"
            resultadoFinal =
                "v(${formatear(tSeguro)}) = -${formatear(vImpacto)} m/s (|v| = ${formatear(vImpacto)} m/s hacia el suelo)"
            aclaracion =
                "Al colisionar o impactar contra el suelo, la velocidad instantánea máxima de choque es ${formatear(vImpacto)} m/s."
        } else {
            val terminoGt = gSegura * tSeguro
            val vVector = -v0Segura - terminoGt
            val rapidez = v0Segura + terminoGt
            evaluacionIntermedia = "v(${formatear(tSeguro)}) = $v0Texto - ${formatear(terminoGt)}"
            resultadoFinal =
                "v(${formatear(tSeguro)}) = ${formatear(vVector)} m/s (|v| = ${formatear(rapidez)} m/s hacia el suelo)"
            aclaracion =
                "La aceleración de la gravedad incrementa la rapidez en ${formatear(gSegura)} m/s cada segundo de caída."
        }

        return DetallePasoFormula(
            "2. Velocidad en función del tiempo v(t)",
            formulaGeneral,
            formulaConValores,
            sustitucionInstante,
            evaluacionIntermedia,
            resultadoFinal,
            aclaracion
        )
    }

    /**
     * Explicación paso a paso del tiempo total de caída:
     * `t_final = √(2·y₀ / g)`.
     */
    override fun explicarTiempoCaida(h0: Double, v0: Double, g: Double): DetallePasoFormula {
        val h0Segura = max(0.0, h0)
        val v0Segura = max(0.0, v0)
        val gSegura = if (g > 0.0) g else 9.8

        val formulaGeneral = "t_final = √(2·y₀ / g)"
        val formulaConValores = "t_final = √(2 · ${formatear(h0Segura)} / ${formatear(gSegura)})"

        val dosH0 = 2.0 * h0Segura
        val cociente = dosH0 / gSegura
        val tEsperadoSinV0 = sqrt(cociente)

        val sustitucionInstante =
            "t_final = √(${formatear(dosH0)} / ${formatear(gSegura)}) = √(${formatear(cociente)})"
        val evaluacionIntermedia =
            "t_final = ${formatear(tEsperadoSinV0)} s (para caída desde reposo con v₀ = 0)"

        val resultadoFinal: String
        val aclaracion: String

        if (v0Segura == 0.0) {
            resultadoFinal = "t_final = ${formatear(tEsperadoSinV0)} s"
            aclaracion =
                "Tiempo total previsto para recorrer ${formatear(h0Segura)} m partiendo del reposo (v₀ = 0 m/s)."
        } else {
            val tReal = calculo.calcularTiempoCaida(h0Segura, v0Segura, gSegura) ?: tEsperadoSinV0
            resultadoFinal =
                "t_final = ${formatear(tReal)} s (con v₀ = ${formatear(v0Segura)} m/s)"
            aclaracion =
                "Al poseer velocidad inicial v₀ > 0, el tiempo real se resuelve mediante la ecuación cuadrática: t = (−v₀ + √(v₀² + 2·g·y₀)) / g = ${formatear(tReal)} s."
        }

        return DetallePasoFormula(
            "3. Tiempo de caída esperado (t_final)",
            formulaGeneral,
            formulaConValores,
            sustitucionInstante,
            evaluacionIntermedia,
            resultadoFinal,
            aclaracion
        )
    }

    /**
     * Comportamiento auxiliar de dominio: formatos numéricos consistentes.
     */
    fun formatear(valor: Double): String = String.format(Locale.ROOT, "%.2f", valor)
}