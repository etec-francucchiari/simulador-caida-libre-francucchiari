package org.example

import java.io.File

/**
 * Contrato de exportación de datos de la simulación (DIP).
 *
 * Permite incorporar nuevos formatos (CSV, JSON, etc.) sin modificar la
 * interfaz de usuario, que depende solo de esta abstracción.
 */
interface ExportadorDatos {

    /** Extensión de archivo asociada al formato (sin punto). */
    fun extension(): String

    /** Descripción legible del formato para el selector de archivos. */
    fun descripcion(): String

    /**
     * Exporta la [muestra] al [archivo] indicado.
     *
     * @throws java.io.IOException si ocurre un error de escritura.
     */
    fun exportar(muestra: MuestraDatosSimulacion, archivo: File)
}
