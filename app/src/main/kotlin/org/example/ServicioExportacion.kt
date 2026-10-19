package org.example

import java.io.File

/**
 * Servicio instanciable que construye la tabla de datos de la simulación y la
 * exporta usando un [ExportadorDatos].
 *
 * Depende por constructor de las abstracciones [EstadoSimulacion] y del
 * servicio de física [CalculoCaidaLibre] (DIP), de modo que no conoce las
 * implementaciones concretas del gestor ni de los formatos de salida.
 */
class ServicioExportacion(
    private val estado: EstadoSimulacion,
    private val calculo: CalculadoraCaidaLibre,
    private val cantidadPuntos: Int = 200
) : ExportacionDatos {

    /**
     * Comportamiento de dominio: genera la muestra de datos (t, y, v, a) a
     * partir del escenario activo en el estado de la simulación.
     */
    override fun generarMuestra(): MuestraDatosSimulacion {
        val alturaInicial = estado.obtenerAlturaInicial()
        val velocidadInicial = estado.obtenerVelocidadInicial()
        val entorno = estado.obtenerPlaneta()
        val gravedad = entorno.gravedad()

        val puntos = calculo.generarPuntosTrayectoria(
            alturaInicial,
            velocidadInicial,
            gravedad,
            cantidadPuntos
        )

        val filas = puntos.map { punto ->
            FilaDatosSimulacion(
                punto.tiempo(),
                punto.posicion(),
                punto.velocidad(),
                gravedad
            )
        }

        return MuestraDatosSimulacion(
            entornoInterno = entorno,
            parametrosInternos = ParametrosIniciales(alturaInicial, velocidadInicial),
            filasInternas = filas
        )
    }

    /**
     * Exporta la muestra actual al [archivo] usando el [exportador] indicado.
     */
    override fun exportar(exportador: ExportadorDatos, archivo: File) {
        exportador.exportar(generarMuestra(), archivo)
    }
}
