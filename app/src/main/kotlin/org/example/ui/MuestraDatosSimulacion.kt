package org.example

class MuestraDatosSimulacion(
    private val entornoInterno: EntornoGravitatorio,
    private val parametrosInternos: ParametrosIniciales,
    private val filasInternas: List<FilaDatosSimulacion>
) {
    /**
     * Modelo de dominio inmutable que representa la tabla completa de datos
     * cinemáticos generada por una simulación de caída libre y preparada para
     * su exportación mediante cualquier [ExportadorDatos].
     *
     * Encapsula como atributos privados el entorno gravitatorio activo, los
     * parámetros iniciales de lanzamiento y la colección ordenada de registros
     * [FilaDatosSimulacion], ofreciendo comportamiento de dominio propio para
     * consultar el tamaño de la muestra ([cantidadFilas]), su vacuidad
     * ([estaVacia]), los encabezados estandarizados ([encabezados]) y el
     * instante final de impacto ([tiempoDeImpacto]).
     */

    /**
     * Devuelve el entorno gravitatorio en el que se recolectó la muestra.
     */
    fun entorno(): EntornoGravitatorio = entornoInterno

    /**
     * Devuelve los parámetros iniciales (altura y velocidad inicial) de la muestra.
     */
    fun parametros(): ParametrosIniciales = parametrosInternos

    /**
     * Devuelve la lista ordenada de filas cinemáticas muestreadas.
     */
    fun filas(): List<FilaDatosSimulacion> = filasInternas

    /**
     * Comportamiento de dominio: devuelve el número total de registros en la muestra.
     */
    fun cantidadFilas(): Int {
        return filasInternas.size
    }

    /**
     * Comportamiento de dominio: indica si la muestra carece de registros.
     */
    fun estaVacia(): Boolean {
        return filasInternas.isEmpty()
    }

    /**
     * Comportamiento de dominio: devuelve los nombres de columna con sus
     * unidades del Sistema Internacional para el encabezado tabular.
     */
    fun encabezados(): List<String> = listOf("t (s)", "y (m)", "v (m/s)", "a (m/s²)")

    /**
     * Comportamiento de dominio: obtiene el instante de tiempo correspondiente
     * al último punto muestreado (instante de impacto contra el suelo).
     */
    fun tiempoDeImpacto(): Double {
        return filasInternas.lastOrNull()?.tiempo() ?: 0.0
    }

    /**
     * Resumen textual de la muestra indicando el entorno y la cantidad de filas.
     */
    override fun toString(): String {
        return "MuestraDatosSimulacion(${entornoInterno.nombre()}, ${filasInternas.size} filas)"
    }
}
