package org.example

/**
 * Modelo de dominio que representa la tabla de datos generada por una
 * simulación de caída libre, lista para ser exportada.
 *
 * Encapsula el entorno, los parámetros iniciales y las filas, y expone
 * comportamiento de dominio (encabezados y estado de la muestra).
 *
 * @property entornoInterno entorno gravitatorio de la simulación.
 * @property parametrosInternos parámetros iniciales usados.
 * @property filasInternas filas cinemáticas de la tabla.
 */
class MuestraDatosSimulacion(
    private val entornoInterno: EntornoGravitatorio,
    private val parametrosInternos: ParametrosIniciales,
    private val filasInternas: List<FilaDatosSimulacion>
) {

    /** Entorno gravitatorio de la simulación. */
    fun entorno(): EntornoGravitatorio = entornoInterno

    /** Parámetros iniciales de la simulación. */
    fun parametros(): ParametrosIniciales = parametrosInternos

    /** Filas cinemáticas de la tabla. */
    fun filas(): List<FilaDatosSimulacion> = filasInternas

    /** Cantidad de filas de la muestra. */
    fun cantidadFilas(): Int = filasInternas.size

    /** Comportamiento de dominio: la muestra no contiene datos. */
    fun estaVacia(): Boolean = filasInternas.isEmpty()

    /** Comportamiento de dominio: encabezados de las columnas de la tabla. */
    fun encabezados(): List<String> = listOf("t (s)", "y (m)", "v (m/s)", "a (m/s²)")

    /** Comportamiento de dominio: instante del impacto (última fila). */
    fun tiempoDeImpacto(): Double =
        filasInternas.lastOrNull()?.tiempo() ?: 0.0

    override fun toString(): String =
        "MuestraDatosSimulacion(${entornoInterno.nombre()}, ${filasInternas.size} filas)"
}
