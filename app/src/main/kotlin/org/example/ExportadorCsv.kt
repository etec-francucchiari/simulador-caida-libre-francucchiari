package org.example

import java.io.File
import java.util.Locale

/**
 * Exportador de la tabla de datos a formato CSV.
 *
 * Escribe una fila de encabezados y una fila por cada punto de la trayectoria
 * (t, y, v, a), usando el punto como separador decimal para máxima
 * compatibilidad.
 */
class ExportadorCsv : ExportadorDatos {

    private val separador: String = ","
    private val formatoDecimal: String = "%.4f"

    override fun extension(): String = "csv"

    override fun descripcion(): String = "Archivo CSV (.csv)"

    override fun exportar(muestra: MuestraDatosSimulacion, archivo: File) {
        archivo.bufferedWriter(Charsets.UTF_8).use { escritor ->
            escritor.write(muestra.encabezados().joinToString(separador))
            escritor.newLine()
            for (fila in muestra.filas()) {
                escritor.write(
                    fila.comoValores().joinToString(separador) { formatear(it) }
                )
                escritor.newLine()
            }
        }
    }

    private fun formatear(valor: Double): String =
        String.format(Locale.ROOT, formatoDecimal, valor)
}
