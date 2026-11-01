package org.example

import java.io.File

interface ExportacionDatos {
    /**
     * Contrato del servicio de muestreo y exportación de datos cinemáticos de
     * la simulación (Principio de Inversión de Dependencias - DIP).
     *
     * La interfaz gráfica ([BarraExportacion]) depende únicamente de esta
     * abstracción en lugar de la implementación concreta [ServicioExportacion].
     */

    /**
     * Construye y devuelve una [MuestraDatosSimulacion] completa con las filas
     * `(t, y, v, a)` correspondientes al escenario activo en el contexto.
     */
    fun generarMuestra(): MuestraDatosSimulacion

    /**
     * Genera la muestra de la trayectoria activa y la escribe en el [archivo]
     * de destino utilizando la estrategia [exportador] seleccionada.
     *
     * @param exportador formato de salida (CSV, JSON, etc.).
     * @param archivo archivo de destino en el sistema de archivos local.
     */
    fun exportar(exportador: ExportadorDatos, archivo: File)
}
