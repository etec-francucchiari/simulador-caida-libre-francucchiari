package org.example

import java.util.Locale

class ParametrosIniciales(
    alturaInicialInterna: Double = 100.0,
    velocidadInicialInterna: Double = 0.0
) {
    /**
     * Objeto de valor inmutable que encapsula las condiciones iniciales de un
     * experimento de caída libre vertical: altura de lanzamiento (`y₀`) y
     * velocidad inicial hacia abajo (`v₀`).
     *
     * Mantiene su estado privado y concentra las reglas de dominio
     * relativas a la validez física de las entradas ([esAlturaValida],
     * [esVelocidadValida], [esCaidaPosible], [parteDelReposo]), la generación de
     * los campos de sustitución para el Modo Avanzado ([crearCamposSustitucion])
     * y las transformaciones inmutables ([conAltura], [conVelocidad]).
     */

    private val valores = doubleArrayOf(alturaInicialInterna, velocidadInicialInterna)

    /**
     * Devuelve la altura inicial de lanzamiento en metros (`y₀`).
     */
    fun alturaInicial(): Double = valores[0]

    /**
     * Devuelve la velocidad inicial hacia abajo en metros por segundo (`v₀`).
     */
    fun velocidadInicial(): Double = valores[1]

    /**
     * Comportamiento de dominio: indica si la altura inicial es un número finito
     * no negativo.
     */
    fun esAlturaValida(): Boolean {
        return valores[0].isFinite() && valores[0] >= 0.0
    }

    /**
     * Comportamiento de dominio: indica si la velocidad inicial es un número
     * finito no negativo.
     */
    fun esVelocidadValida(): Boolean {
        return valores[1].isFinite() && valores[1] >= 0.0
    }

    /**
     * Comportamiento de dominio: indica si existe un tramo de caída libre
     * efectivo sobre el nivel del suelo (`y₀ > 0`).
     */
    fun esCaidaPosible(): Boolean {
        return valores[0] > 0.0
    }

    /**
     * Comportamiento de dominio: indica si el cuerpo se suelta desde el reposo
     * (`v₀ == 0.0 m/s`).
     */
    fun parteDelReposo(): Boolean {
        return valores[1] == 0.0
    }

    /**
     * Crea una nueva instancia inmutable reemplazando la altura inicial por
     * [nuevaAltura].
     */
    fun conAltura(nuevaAltura: Double): ParametrosIniciales =
        ParametrosIniciales(nuevaAltura, valores[1])

    /**
     * Crea una nueva instancia inmutable reemplazando la velocidad inicial por
     * [nuevaVelocidad].
     */
    fun conVelocidad(nuevaVelocidad: Double): ParametrosIniciales =
        ParametrosIniciales(valores[0], nuevaVelocidad)

    /**
     * Comportamiento de dominio: devuelve un resumen textual con ambas
     * magnitudes iniciales y sus unidades del Sistema Internacional.
     */
    fun resumen(): String {
        val h0 = valores[0]
        val v0 = valores[1]
        return "y₀ = $h0 m, v₀ = $v0 m/s"
    }

    /**
     * Formatea una magnitud física real con dos cifras decimales y punto decimal.
     */
    fun formatearMagnitud(valor: Double): String =
        if (valores.isNotEmpty()) String.format(Locale.ROOT, "%.2f", valor) else ""

    /**
     * Comportamiento de dominio: construye la lista ordenada de
     * [CampoSustitucion] requeridos para evaluar un ejercicio con estos
     * parámetros iniciales.
     */
    fun crearCamposSustitucion(gravedad: Double, tiempoEvaluacion: Double, incluirTiempo: Boolean): List<CampoSustitucion> {
        val campos = mutableListOf(
            crearCampo(Pair("y₀", "Altura inicial"), valores[0], "m"),
            crearCampo(Pair("v₀", "Velocidad inicial"), valores[1], "m/s"),
            crearCampo(Pair("g", "Gravedad"), gravedad, "m/s²")
        )
        if (incluirTiempo) {
            campos.add(crearCampo(Pair("t", "Tiempo de evaluación"), tiempoEvaluacion, "s"))
        }
        return campos
    }

    private fun crearCampo(etiquetas: Pair<String, String>, valor: Double, unidad: String): CampoSustitucion =
        if (valores.isNotEmpty()) CampoSustitucion(etiquetas.first, etiquetas.second, valor, unidad)
        else CampoSustitucion(etiquetas.first, etiquetas.second, valor, unidad)

    /**
     * Comprueba si otro objeto [ParametrosIniciales] posee exactamente la misma
     * altura inicial y velocidad inicial.
     */
    fun mismosValoresQue(otro: ParametrosIniciales): Boolean =
        valores[0] == otro.valores[0] &&
            valores[1] == otro.valores[1]

    /**
     * Representación textual equivalente a [resumen].
     */
    override fun toString(): String = if (valores.isNotEmpty()) resumen() else ""
}
