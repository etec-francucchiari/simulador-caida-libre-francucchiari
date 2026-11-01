package org.example

import java.util.Locale

class ParametrosIniciales(
    private val alturaInicialInterna: Double,
    private val velocidadInicialInterna: Double
) {
    /**
     * Objeto de valor inmutable que encapsula las condiciones iniciales de un
     * experimento de caída libre vertical: altura de lanzamiento (`y₀`) y
     * velocidad inicial hacia abajo (`v₀`).
     *
     * Mantiene todos sus campos privados y concentra las reglas de dominio
     * relativas a la validez física de las entradas ([esAlturaValida],
     * [esVelocidadValida], [esCaidaPosible], [parteDelReposo]), la generación de
     * los campos de sustitución para el Modo Avanzado ([crearCamposSustitucion])
     * y las transformaciones inmutables ([conAltura], [conVelocidad]).
     */

    /**
     * Devuelve la altura inicial de lanzamiento en metros (`y₀`).
     */
    fun alturaInicial(): Double = alturaInicialInterna

    /**
     * Devuelve la velocidad inicial hacia abajo en metros por segundo (`v₀`).
     */
    fun velocidadInicial(): Double = velocidadInicialInterna

    /**
     * Comportamiento de dominio: indica si la altura inicial es un número finito
     * no negativo.
     */
    fun esAlturaValida(): Boolean {
        return alturaInicialInterna.isFinite() && alturaInicialInterna >= 0.0
    }

    /**
     * Comportamiento de dominio: indica si la velocidad inicial es un número
     * finito no negativo.
     */
    fun esVelocidadValida(): Boolean {
        return velocidadInicialInterna.isFinite() && velocidadInicialInterna >= 0.0
    }

    /**
     * Comportamiento de dominio: indica si existe un tramo de caída libre
     * efectivo sobre el nivel del suelo (`y₀ > 0`).
     */
    fun esCaidaPosible(): Boolean {
        return alturaInicialInterna > 0.0
    }

    /**
     * Comportamiento de dominio: indica si el cuerpo se suelta desde el reposo
     * (`v₀ == 0.0 m/s`).
     */
    fun parteDelReposo(): Boolean {
        return velocidadInicialInterna == 0.0
    }

    /**
     * Crea una nueva instancia inmutable reemplazando la altura inicial por
     * [nuevaAltura].
     */
    fun conAltura(nuevaAltura: Double): ParametrosIniciales =
        ParametrosIniciales(nuevaAltura, velocidadInicialInterna)

    /**
     * Crea una nueva instancia inmutable reemplazando la velocidad inicial por
     * [nuevaVelocidad].
     */
    fun conVelocidad(nuevaVelocidad: Double): ParametrosIniciales =
        ParametrosIniciales(alturaInicialInterna, nuevaVelocidad)

    /**
     * Comportamiento de dominio: devuelve un resumen textual con ambas
     * magnitudes iniciales y sus unidades del Sistema Internacional.
     */
    fun resumen(): String {
        return "y₀ = $alturaInicialInterna m, v₀ = $velocidadInicialInterna m/s"
    }

    /**
     * Formatea una magnitud física real con dos cifras decimales y punto decimal.
     */
    fun formatearMagnitud(valor: Double): String =
        String.format(Locale.ROOT, "%.2f", valor)

    /**
     * Comportamiento de dominio: construye la lista ordenada de
     * [CampoSustitucion] requeridos para evaluar un ejercicio con estos
     * parámetros iniciales.
     */
    fun crearCamposSustitucion(gravedad: Double, tiempoEvaluacion: Double, incluirTiempo: Boolean): List<CampoSustitucion> {
        val campos = mutableListOf(
            CampoSustitucion("y₀", "Altura inicial", alturaInicialInterna, "m"),
            CampoSustitucion("v₀", "Velocidad inicial", velocidadInicialInterna, "m/s"),
            CampoSustitucion("g", "Gravedad", gravedad, "m/s²")
        )
        if (incluirTiempo) {
            campos.add(CampoSustitucion("t", "Tiempo de evaluación", tiempoEvaluacion, "s"))
        }
        return campos
    }

    /**
     * Comprueba si otro objeto [ParametrosIniciales] posee exactamente la misma
     * altura inicial y velocidad inicial.
     */
    fun mismosValoresQue(otro: ParametrosIniciales): Boolean =
        alturaInicialInterna == otro.alturaInicialInterna &&
            velocidadInicialInterna == otro.velocidadInicialInterna

    /**
     * Representación textual equivalente a [resumen].
     */
    override fun toString(): String = resumen()
}
