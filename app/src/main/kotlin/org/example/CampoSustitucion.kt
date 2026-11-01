package org.example

import kotlin.math.abs

class CampoSustitucion(
    private val simboloInterno: String,
    private val descripcionInterna: String,
    private val valorEsperadoInterno: Double,
    private val unidadInterna: String
) {
    /**
     * Modelo de dominio que representa un campo de sustitución de una variable
     * física conocida dentro de una ecuación cinemática del MRUV (Etapa 2 del
     * Modo Avanzado).
     *
     * Respeta el principio de encapsulación estricta manteniendo todos sus
     * atributos como campos privados ([simboloInterno], [descripcionInterna],
     * [valorEsperadoInterno], [unidadInterna]) y exponiendo comportamiento de
     * dominio propio ([esAcierto]) para validar la entrada numérica del
     * estudiante dentro de una tolerancia configurable.
     *
     * @property simboloInterno símbolo algebraico de la variable ("y₀", "v₀", "g", "t").
     * @property descripcionInterna descripción pedagógica legible de la magnitud.
     * @property valorEsperadoInterno valor numérico verdadero en el escenario activo.
     * @property unidadInterna unidad de medida en el Sistema Internacional.
     */

    /**
     * Devuelve el símbolo algebraico de la variable física (por ejemplo, `"y₀"`,
     * `"v₀"`, `"g"` o `"t"`).
     */
    fun simbolo(): String = simboloInterno

    /**
     * Devuelve la descripción legible de la variable para presentar junto al
     * campo de texto en la interfaz de sustitución.
     */
    fun descripcion(): String = descripcionInterna

    /**
     * Devuelve el valor numérico esperado que el estudiante debe sustituir en
     * la fórmula seleccionada.
     */
    fun valorEsperado(): Double = valorEsperadoInterno

    /**
     * Devuelve la unidad del Sistema Internacional asociada a la magnitud
     * (`"m"`, `"m/s"`, `"m/s²"` o `"s"`).
     */
    fun unidad(): String = unidadInterna

    /**
     * Comportamiento de dominio: evalúa si el [valorIngresado] por el estudiante
     * es finito y coincide con [valorEsperadoInterno] dentro de la [tolerancia]
     * absoluta permitida (por defecto ±0.05).
     *
     * @param valorIngresado número real introducido en el campo de sustitución.
     * @param tolerancia margen de error absoluto admisible por redondeo.
     * @return `true` si la diferencia absoluta no supera la tolerancia.
     */
    fun esAcierto(valorIngresado: Double, tolerancia: Double = 0.05): Boolean {
        return valorIngresado.isFinite() && abs(valorIngresado - valorEsperadoInterno) <= tolerancia
    }

    /**
     * Representación textual de la asignación física con su unidad de medida.
     */
    override fun toString(): String {
        return "$simboloInterno = $valorEsperadoInterno $unidadInterna"
    }
}
