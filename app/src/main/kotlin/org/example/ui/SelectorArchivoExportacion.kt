package org.example

import java.io.File
import javafx.stage.Window

interface SelectorArchivoExportacion {
    /**
     * Contrato del selector interactivo de archivo de destino para la
     * exportación de datos (Principio de Inversión de Dependencias - DIP).
     *
     * Aísla a la barra de exportación ([BarraExportacion]) del diálogo nativo
     * [javafx.stage.FileChooser] encapsulado en [SelectorArchivoJavaFx],
     * facilitando pruebas automatizadas sin abrir ventanas del sistema operativo.
     */

    /**
     * Solicita al usuario la ruta de destino donde guardar la muestra exportada,
     * configurando el nombre inicial sugerido y el filtro de extensión del
     * [exportador] elegido.
     *
     * @param ventana ventana anfitriona del diálogo modal, o `null`.
     * @param nombreSugerido nombre base de archivo con extensión por defecto.
     * @param exportador formato activo cuyos metadatos definen el filtro.
     * @return archivo seleccionado por el usuario, o `null` si canceló el diálogo.
     */
    fun seleccionarDestino(ventana: Window?, nombreSugerido: String, exportador: ExportadorDatos): File?
}
