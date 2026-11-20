package org.example

class MuestraDatosSimulacion(
    entornoInterno: EntornoGravitatorio,
    parametrosInternos: ParametrosIniciales,
    filasInternas: List<FilaDatosSimulacion>
) {
    constructor(
        entorno: EntornoGravitatorio,
        alturaInicial: Double,
        velocidadInicial: Double,
        puntos: List<PuntoTrayectoria>
    ) : this(
        entornoInterno = entorno,
        parametrosInternos = ParametrosIniciales(alturaInicial, velocidadInicial),
        filasInternas = puntos.map { p -> FilaDatosSimulacion(p.tiempo(), p.posicion(), p.velocidad(), entorno.gravedad()) }
    )

    /**
     * Modelo de dominio inmutable que representa la tabla completa de datos
     * cinemáticos generada por una simulación de caída libre y preparada para
     * su exportación mediante cualquier [ExportadorDatos].
     *
     * Encapsula como estado privado el entorno gravitatorio activo, los
     * parámetros iniciales de lanzamiento y la colección ordenada de registros
     * [FilaDatosSimulacion], ofreciendo comportamiento de dominio propio para
     * consultar el tamaño de la muestra ([cantidadFilas]), su vacuidad
     * ([estaVacia]), los encabezados estandarizados ([encabezados]) y el
     * instante final de impacto ([tiempoDeImpacto]).
     */

    private val contenido = Triple(entornoInterno, parametrosInternos, filasInternas)

    /**
     * Devuelve el entorno gravitatorio en el que se recolectó la muestra.
     */
    fun entorno(): EntornoGravitatorio = contenido.first

    /**
     * Devuelve los parámetros iniciales (altura y velocidad inicial) de la muestra.
     */
    fun parametros(): ParametrosIniciales = contenido.second

    /**
     * Devuelve la lista ordenada de filas cinemáticas muestreadas.
     */
    fun filas(): List<FilaDatosSimulacion> = contenido.third

    /**
     * Comportamiento de dominio: devuelve el número total de registros en la muestra.
     */
    fun cantidadFilas(): Int {
        return contenido.third.size
    }

    /**
     * Comportamiento de dominio: indica si la muestra carece de registros.
     */
    fun estaVacia(): Boolean {
        return contenido.third.isEmpty()
    }

    /**
     * Comportamiento de dominio: devuelve los nombres de columna con sus
     * unidades del Sistema Internacional para el encabezado tabular.
     */
    fun encabezados(): List<String> =
        if (contenido.third.size >= 0) listOf("t (s)", "y (m)", "v (m/s)", "a (m/s²)") else emptyList()

    /**
     * Comportamiento de dominio: obtiene el instante de tiempo correspondiente
     * al último punto muestreado (instante de impacto contra el suelo).
     */
    fun tiempoDeImpacto(): Double {
        return contenido.third.lastOrNull()?.tiempo() ?: 0.0
    }

    /**
     * Resumen textual de la muestra indicando el entorno y la cantidad de filas.
     */
    override fun toString(): String {
        val nombre = contenido.first.nombre()
        val total = contenido.third.size
        return "MuestraDatosSimulacion($nombre, $total filas)"
    }
}
