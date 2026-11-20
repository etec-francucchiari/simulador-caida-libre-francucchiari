package org.example

import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.stage.Window

class BarraExportacion(
    servicioExportacion: ExportacionDatos,
    catalogoExportadores: CatalogoDeExportadores = FabricaCatalogoExportadores().crear(),
    selectorArchivo: SelectorArchivoExportacion = SelectorArchivoJavaFx(),
    proveedorVentana: () -> Window?
) : HBox(12.0) {
    /**
     * Barra inferior de exportación de la tabla de datos de la simulación.
     *
     * Encapsula el estado de la última exportación y el acceso al selector de
     * archivo recibiendo sus dependencias por constructor (DIP).
     */

    private val estadoBarra = Pair(
        Triple(servicioExportacion, catalogoExportadores, selectorArchivo),
        Pair(proveedorVentana, Label())
    )

    init {
        inicializar()
    }

    private fun inicializar() {
        isVisible = false
        isManaged = false
        estadoBarra.second.second.style = "-fx-text-fill: #475569; -fx-font-size: 12px;"
    }

    /** Restablece el mensaje de estado. */
    fun reiniciarMensaje() {
        estadoBarra.second.second.text = ""
        estadoBarra.second.second.style = "-fx-text-fill: #475569; -fx-font-size: 12px;"
    }

    /** Exporta la muestra actual en el formato indicado. */
    fun exportar(extension: String) {
        val exportador = estadoBarra.first.second.porExtension(extension)
        if (exportador == null) {
            mostrarError("Formato no soportado: $extension")
            return
        }
        val archivo = estadoBarra.first.third.seleccionarDestino(
            ventana = estadoBarra.second.first(),
            nombreSugerido = "simulacion_caida_libre",
            exportador = exportador
        ) ?: return
        try {
            estadoBarra.first.first.exportar(exportador, archivo)
            estadoBarra.second.second.text = "Exportado: ${archivo.name}"
            estadoBarra.second.second.style = "-fx-text-fill: #16a34a; -fx-font-size: 12px;"
        } catch (excepcion: Exception) {
            mostrarError("Error al exportar: ${excepcion.message}")
        }
    }

    private fun mostrarError(mensaje: String) {
        estadoBarra.second.second.text = mensaje
        estadoBarra.second.second.style = "-fx-text-fill: #dc2626; -fx-font-size: 12px;"
    }
}
