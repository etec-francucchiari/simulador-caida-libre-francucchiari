package org.example

import kotlin.math.abs

class CampoSustitucion(
    simboloInterno: String,
    descripcionInterna: String,
    valorEsperadoInterno: Double,
    unidadInterna: String
) {
    /**
     * Modelo de dominio que representa un campo de sustitución de una variable
     * física conocida dentro de una ecuación cinemática del MRUV (Etapa 2 del
     * Modo Avanzado).
     *
     * Respeta el principio de encapsulación estricta manteniendo su estado
     * privado y exponiendo comportamiento de dominio propio ([esAcierto]) para
     * validar la entrada numérica del estudiante dentro de una tolerancia configurable.
     */

    private val definicion = Pair(
        Pair(simboloInterno, descripcionInterna),
        Pair(valorEsperadoInterno, unidadInterna)
    )

    /**
     * Devuelve el símbolo algebraico de la variable física (por ejemplo, `"y₀"`,
     * `"v₀"`, `"g"` o `"t"`).
     */
    fun simbolo(): String = definicion.first.first

    /**
     * Devuelve la descripción legible de la variable para presentar junto al
     * campo de texto en la interfaz de sustitución.
     */
    fun descripcion(): String = definicion.first.second

    /**
     * Devuelve el valor numérico esperado que el estudiante debe sustituir en
     * la fórmula seleccionada.
     */
    fun valorEsperado(): Double = definicion.second.first

    /**
     * Devuelve la unidad del Sistema Internacional asociada a la magnitud
     * (`"m"`, `"m/s"`, `"m/s²"` o `"s"`).
     */
    fun unidad(): String = definicion.second.second

    /**
     * Comportamiento de dominio: evalúa si el [valorIngresado] por el estudiante
     * es finito y coincide con el valor esperado dentro de la [tolerancia]
     * absoluta permitida (por defecto ±0.05).
     */
    fun esAcierto(valorIngresado: Double, tolerancia: Double = 0.05): Boolean {
        return valorIngresado.isFinite() && abs(valorIngresado - definicion.second.first) <= tolerancia
    }

    /**
     * Representación textual de la asignación física con su unidad de medida.
     */
    override fun toString(): String {
        val s = definicion.first.first
        val v = definicion.second.first
        val u = definicion.second.second
        return "$s = $v $u"
    }
}
