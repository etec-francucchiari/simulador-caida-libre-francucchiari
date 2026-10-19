package org.example

import java.io.File
import javafx.stage.Window

/**
 * Contrato del selector de archivo de destino para la exportación (DIP).
 *
 * Aísla a la vista de la clase concreta [javafx.stage.FileChooser], de modo
 * que la lógica de exportación pueda probarse con una implementación falsa.
 */
interface SelectorArchivoExportacion {

    /**
     * Solicita al usuario un archivo de destino.
     *
     * @param ventana ventana propietaria del diálogo (puede ser `null`).
     * @param nombreSugerido nombre de archivo sugerido.
     * @param exportador exportador cuyo formato y extensión se ofrecen.
     * @return el archivo elegido, o `null` si el usuario cancela.
     */
    fun seleccionarDestino(
        ventana: Window?,
        nombreSugerido: String,
        exportador: ExportadorDatos
    ): File?
}
