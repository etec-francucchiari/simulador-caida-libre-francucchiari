package org.example

import java.util.Locale
import kotlin.math.*

class ExplicadorFisicaPasoAPaso(
    private val calculo: CalculadoraCaidaLibre
) : ExplicadorFisica {
    /**
     * Servicio de dominio que genera las explicaciones físicas paso a paso de las
     * fórmulas del Movimiento Rectilíneo Uniformemente Variado (MRUV) para el
     * Modo Principiante.
     *
     * Es instanciable y recibe por constructor su dependencia
     * [CalculadoraCaidaLibre] aplicando el Principio de Inversión de
     * Dependencias (DIP).
     */

    private val gravedadPorDefecto = 9.8

    /**
     * Construye el desglose paso a paso de la ecuación horaria de posición:
     * `y(t) = y₀ + v₀·t - ½·g·t²`.
     */
    override fun explicarPosicion(h0: Double, v0: Double, g: Double, t: Double): DetallePasoFormula {
        val h0S = max(0.0, h0)
        val v0S = max(0.0, v0)
        val gS = max(0.0001, if (g > 0.0) g else gravedadPorDefecto)
        val tS = max(0.0, t)
        val v0Signo = mapOf(true to "+ (0.00)·t", false to "+ (-${formatear(v0S)})·t")[v0S == 0.0]!!
        val conValores = "y(t) = ${formatear(h0S)} $v0Signo - ½·(${formatear(gS)})·t²"
        val sustitucion = "y(${formatear(tS)}) = ${formatear(h0S)} + (-${formatear(v0S)} · ${formatear(tS)}) - (0.5 · ${formatear(gS)} · ${formatear(tS)}²)"
        val tCaida = calculo.calcularTiempoCaida(h0S, v0S, gS) ?: 0.0
        val termV0 = -v0S * tS
        val termG = 0.5 * gS * tS * tS
        val base = "y(${formatear(tS)}) = ${formatear(h0S)} + (${formatear(termV0)}) - ${formatear(termG)}"
        val enSuelo = tS >= tCaida || h0S <= 0.0
        return crearDetallePosicion(conValores, sustitucion, base, textosPosicion(enSuelo, tS, tCaida, max(0.0, h0S + termV0 - termG)))
    }

    /**
     * Construye el desglose paso a paso de la ecuación horaria de velocidad:
     * `v(t) = v₀ - g·t`.
     */
    override fun explicarVelocidad(h0: Double, v0: Double, g: Double, t: Double): DetallePasoFormula {
        val h0S = max(0.0, h0)
        val v0S = max(0.0, v0)
        val gS = max(0.0001, if (g > 0.0) g else gravedadPorDefecto)
        val tS = max(0.0, t)
        val v0Txt = mapOf(true to "0.00", false to "-${formatear(v0S)}")[v0S == 0.0]!!
        val conValores = "v(t) = $v0Txt - (${formatear(gS)})·t"
        val sustitucion = "v(${formatear(tS)}) = $v0Txt - (${formatear(gS)} · ${formatear(tS)})"
        val tCaida = calculo.calcularTiempoCaida(h0S, v0S, gS) ?: 0.0
        val vImpacto = calculo.calcularVelocidadImpacto(h0S, v0S, gS) ?: 0.0
        val enSuelo = tS >= tCaida || h0S <= 0.0
        val eval = mapOf(true to "Velocidad de impacto alcanzada en el suelo", false to "v(${formatear(tS)}) = $v0Txt - ${formatear(gS * tS)}")[enSuelo]!!
        return crearDetalleVelocidad(conValores, sustitucion, eval, textosVelocidad(enSuelo, tS, gS, v0S, vImpacto))
    }

    /**
     * Construye el desglose paso a paso para el cálculo del tiempo total de
     * caída libre: `t_final = √(2·y₀ / g)`.
     */
    override fun explicarTiempoCaida(h0: Double, v0: Double, g: Double): DetallePasoFormula {
        val h0S = max(0.0, h0)
        val v0S = max(0.0, v0)
        val gS = max(0.0001, if (g > 0.0) g else gravedadPorDefecto)
        val dosH0 = 2.0 * h0S
        val cociente = dosH0 / gS
        val tSinV0 = sqrt(cociente)
        val conValores = "t_final = √(2 · ${formatear(h0S)} / ${formatear(gS)})"
        val sustitucion = "t_final = √(${formatear(dosH0)} / ${formatear(gS)}) = √(${formatear(cociente)})"
        val eval = "t_final = ${formatear(tSinV0)} s (para caída desde reposo con v₀ = 0)"
        val tReal = calculo.calcularTiempoCaida(h0S, v0S, gS) ?: tSinV0
        val res = if (v0S == 0.0) "t_final = ${formatear(tSinV0)} s" else "t_final = ${formatear(tReal)} s (con v₀ = ${formatear(v0S)} m/s)"
        val nota = if (v0S == 0.0) "Tiempo total previsto para recorrer ${formatear(h0S)} m partiendo del reposo (v₀ = 0 m/s)." else "Al poseer velocidad inicial v₀ > 0, el tiempo real se resuelve mediante la ecuación cuadrática: t = (−v₀ + √(v₀² + 2·g·y₀)) / g = ${formatear(tReal)} s."
        return DetallePasoFormula("3. Tiempo de caída esperado (t_final)", "t_final = √(2·y₀ / g)", conValores, sustitucion, eval, res, nota)
    }

    /**
     * Formatea una magnitud física con dos decimales y punto separador.
     */
    override fun formatear(valor: Double): String {
        return String.format(Locale.ROOT, "%.2f", valor)
    }

    private fun crearDetallePosicion(conValores: String, sustitucion: String, base: String, cierre: List<String>): DetallePasoFormula {
        return DetallePasoFormula("1. Posición en función del tiempo y(t)", "y(t) = y₀ + v₀·t - ½·g·t²", conValores, sustitucion, base + cierre[0], cierre[1], cierre[2])
    }

    private fun crearDetalleVelocidad(conValores: String, sustitucion: String, eval: String, cierre: List<String>): DetallePasoFormula {
        return DetallePasoFormula("2. Velocidad en función del tiempo v(t)", "v(t) = v₀ - g·t", conValores, sustitucion, eval, cierre[0], cierre[1])
    }

    private fun textosPosicion(enSuelo: Boolean, tS: Double, tCaida: Double, yRestante: Double): List<String> =
        if (enSuelo) listOf(" ≤ 0.00", "y(${formatear(tS)}) = 0.00 m (Impacto en el suelo)", "El cuerpo ha alcanzado el suelo en t = ${formatear(tCaida)} s y permanece en reposo (y = 0 m).")
        else listOf("", "y(${formatear(tS)}) = ${formatear(yRestante)} m", "Altura actual sobre el suelo calculada restando el efecto de la caída a la posición inicial.")

    private fun textosVelocidad(enSuelo: Boolean, tS: Double, gS: Double, v0S: Double, vImpacto: Double): List<String> =
        if (enSuelo) listOf("v(${formatear(tS)}) = -${formatear(vImpacto)} m/s (|v| = ${formatear(vImpacto)} m/s hacia el suelo)", "Al colisionar o impactar contra el suelo, la velocidad instantánea máxima de choque es ${formatear(vImpacto)} m/s.")
        else listOf("v(${formatear(tS)}) = ${formatear(-v0S - gS * tS)} m/s (|v| = ${formatear(v0S + gS * tS)} m/s hacia el suelo)", "La aceleración de la gravedad incrementa la rapidez en ${formatear(gS)} m/s cada segundo de caída.")
}
