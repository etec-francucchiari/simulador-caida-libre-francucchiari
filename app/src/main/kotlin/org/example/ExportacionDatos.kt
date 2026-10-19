package org.example

import java.io.File

/**
 * Contrato del servicio de exportación de datos de la simulación (DIP).
 *
 * La vista depende de esta abstracción en lugar de [ServicioExportacion].
 */
interface ExportacionDatos {

    /** Construye la tabla de datos a partir del estado activo. */
    fun generarMuestra(): MuestraDatosSimulacion

    /** Exporta la muestra actual usando el exportador indicado. */
    fun exportar(exportador: ExportadorDatos, archivo: File)
}
