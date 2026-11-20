package org.example

import java.util.Locale
import kotlin.math.*

class ExplicadorFisicaPasoAPaso(
    private val calculo: CalculadoraCaidaLibre = CalculoCaidaLibre()
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

    /**
     * Construye el desglose paso a paso de la ecuación horaria de posición:
     * `y(t) = y₀ + v₀·t - ½·g·t²`.
     */
    override fun explicarPosicion(h0: Double, v0: Double, g: Double, t: Double): DetallePasoFormula {
        val m = normalizarEntrada(h0, v0, g, t)
        val v0Signo = if (m[1] == 0.0) "+ (0.00)·t" else "+ (-${formatear(m[1])})·t"
        val conValores = "y(t) = ${formatear(m[0])} $v0Signo - ½·(${formatear(m[2])})·t²"
        val sustitucion = "y(${formatear(m[3])}) = ${formatear(m[0])} + (-${formatear(m[1])} · ${formatear(m[3])}) - (0.5 · ${formatear(m[2])} · ${formatear(m[3])}²)"
        val tCaida = calculo.calcularTiempoCaida(m[0], m[1], m[2]) ?: 0.0
        val termV0 = -m[1] * m[3]
        val termG = 0.5 * m[2] * m[3] * m[3]
        val enSuelo = m[3] >= tCaida || m[0] <= 0.0
        val base = "y(${formatear(m[3])}) = ${formatear(m[0])} + (${formatear(termV0)}) - ${formatear(termG)}" + if (enSuelo) " ≤ 0.00" else ""
        val cierre = cierrePosicion(enSuelo, m[3], max(0.0, m[0] + termV0 - termG))
        return construirPaso(listOf("1. Posición en función del tiempo y(t)", "y(t) = y₀ + v₀·t - ½·g·t²", conValores, sustitucion, base, cierre, notaPosicion(enSuelo, tCaida)))
    }

    /**
     * Construye el desglose paso a paso de la ecuación horaria de velocidad:
     * `v(t) = v₀ - g·t`.
     */
    override fun explicarVelocidad(h0: Double, v0: Double, g: Double, t: Double): DetallePasoFormula {
        val m = normalizarEntrada(h0, v0, g, t)
        val v0Txt = if (m[1] == 0.0) "0.00" else "-${formatear(m[1])}"
        val conValores = "v(t) = $v0Txt - (${formatear(m[2])})·t"
        val sustitucion = "v(${formatear(m[3])}) = $v0Txt - (${formatear(m[2])} · ${formatear(m[3])})"
        val tCaida = calculo.calcularTiempoCaida(m[0], m[1], m[2]) ?: 0.0
        val vImpacto = calculo.calcularVelocidadImpacto(m[0], m[1], m[2]) ?: 0.0
        val enSuelo = m[3] >= tCaida || m[0] <= 0.0
        val eval = if (enSuelo) "Velocidad de impacto alcanzada en el suelo" else "v(${formatear(m[3])}) = $v0Txt - ${formatear(m[2] * m[3])}"
        val rapidez = if (enSuelo) vImpacto else m[1] + m[2] * m[3]
        return construirPaso(listOf("2. Velocidad en función del tiempo v(t)", "v(t) = v₀ - g·t", conValores, sustitucion, eval, resultadoVelocidad(m[3], rapidez), notaVelocidad(enSuelo, m[2], vImpacto)))
    }

    /**
     * Construye el desglose paso a paso para el cálculo del tiempo total de
     * caída libre: `t_final = √(2·y₀ / g)`.
     */
    override fun explicarTiempoCaida(h0: Double, v0: Double, g: Double): DetallePasoFormula {
        val m = normalizarEntrada(h0, v0, g, 0.0)
        val cociente = (2.0 * m[0]) / m[2]
        val tSinV0 = sqrt(cociente)
        val conValores = "t_final = √(2 · ${formatear(m[0])} / ${formatear(m[2])})"
        val sustitucion = "t_final = √(${formatear(2.0 * m[0])} / ${formatear(m[2])}) = √(${formatear(cociente)})"
        val eval = "t_final = ${formatear(tSinV0)} s (para caída desde reposo con v₀ = 0)"
        val tReal = calculo.calcularTiempoCaida(m[0], m[1], m[2]) ?: tSinV0
        val res = if (m[1] == 0.0) "t_final = ${formatear(tSinV0)} s" else "t_final = ${formatear(tReal)} s (con v₀ = ${formatear(m[1])} m/s)"
        val nota = notaTiempoCaida(m[0], m[1], tReal)
        return construirPaso(listOf("3. Tiempo de caída esperado (t_final)", "t_final = √(2·y₀ / g)", conValores, sustitucion, eval, res, nota))
    }

    /**
     * Formatea una magnitud física con dos decimales y punto separador.
     */
    override fun formatear(valor: Double): String {
        return String.format(Locale.ROOT, "%.2f", valor)
    }

    private fun normalizarEntrada(h0: Double, v0: Double, g: Double, t: Double): DoubleArray {
        val gSegura = max(0.0001, if (g > 0.0) g else 9.8)
        return doubleArrayOf(max(0.0, h0), max(0.0, v0), gSegura, max(0.0, t))
    }

    private fun construirPaso(partes: List<String>): DetallePasoFormula = DetallePasoFormula(partes)

    private fun cierrePosicion(enSuelo: Boolean, tS: Double, yRestante: Double): String =
        if (enSuelo) "y(${formatear(tS)}) = 0.00 m (Impacto en el suelo)" else "y(${formatear(tS)}) = ${formatear(yRestante)} m"

    private fun notaPosicion(enSuelo: Boolean, tCaida: Double): String =
        if (enSuelo) "El cuerpo ha alcanzado el suelo en t = ${formatear(tCaida)} s y permanece en reposo (y = 0 m)."
        else "Altura actual sobre el suelo calculada restando el efecto de la caída a la posición inicial."

    private fun resultadoVelocidad(tS: Double, rapidez: Double): String =
        "v(${formatear(tS)}) = ${formatear(-rapidez)} m/s (|v| = ${formatear(rapidez)} m/s hacia el suelo)"

    private fun notaVelocidad(enSuelo: Boolean, gS: Double, vImpacto: Double): String =
        if (enSuelo) "Al colisionar o impactar contra el suelo, la velocidad instantánea máxima de choque es ${formatear(vImpacto)} m/s."
        else "La aceleración de la gravedad incrementa la rapidez en ${formatear(gS)} m/s cada segundo de caída."

    private fun notaTiempoCaida(h0S: Double, v0S: Double, tReal: Double): String =
        if (v0S == 0.0) "Tiempo total previsto para recorrer ${formatear(h0S)} m partiendo del reposo (v₀ = 0 m/s)."
        else "Al poseer velocidad inicial v₀ > 0, el tiempo real se resuelve mediante la ecuación cuadrática: t = (−v₀ + √(v₀² + 2·g·y₀)) / g = ${formatear(tReal)} s."
}
