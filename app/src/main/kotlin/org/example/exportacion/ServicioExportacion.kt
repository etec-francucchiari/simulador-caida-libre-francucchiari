package org.example

import java.io.File

class ServicioExportacion(
    estado: EstadoSimulacion,
    calculo: CalculadoraCaidaLibre = CalculoCaidaLibre(),
    cantidadPuntos: Int = 200
) : ExportacionDatos {
    /**
     * Servicio instanciable que construye la tabla de datos de la simulación y la
     * exporta usando un [ExportadorDatos].
     *
     * Depende por constructor de las abstracciones [EstadoSimulacion] y del
     * servicio de física [CalculadoraCaidaLibre] (DIP), de modo que no conoce las
     * implementaciones concretas del gestor ni de los formatos de salida.
     */

    private val config = Triple(estado, calculo, cantidadPuntos)

    /**
     * Comportamiento de dominio: genera la muestra de datos (t, y, v, a) a
     * partir del escenario activo en el estado de la simulación.
     */
    override fun generarMuestra(): MuestraDatosSimulacion {
        val h0 = config.first.obtenerAlturaInicial()
        val v0 = config.first.obtenerVelocidadInicial()
        val entorno = config.first.obtenerPlaneta()
        val puntos = config.second.generarPuntosTrayectoria(h0, v0, entorno.gravedad(), config.third)
        return MuestraDatosSimulacion(entorno, h0, v0, puntos)
    }

    /**
     * Exporta la muestra actual al [archivo] usando el [exportador] indicado.
     */
    override fun exportar(exportador: ExportadorDatos, archivo: File) {
        exportador.exportar(generarMuestra(), archivo)
    }
}
