package org.example

import java.io.File
import javafx.stage.FileChooser
import javafx.stage.Window

/**
 * Implementación de [SelectorArchivoExportacion] basada en el
 * [FileChooser] de JavaFX.
 *
 * No contiene estado propio relevante; es una clase instanciable inyectable.
 */
class SelectorArchivoJavaFx : SelectorArchivoExportacion {

    override fun seleccionarDestino(
        ventana: Window?,
        nombreSugerido: String,
        exportador: ExportadorDatos
    ): File? {
        val selector = FileChooser()
        selector.title = "Exportar datos de la simulación"
        selector.initialFileName = "$nombreSugerido.${exportador.extension()}"
        selector.extensionFilters.add(
            FileChooser.ExtensionFilter(
                exportador.descripcion(),
                "*.${exportador.extension()}"
            )
        )
        return selector.showSaveDialog(ventana)
    }
}
