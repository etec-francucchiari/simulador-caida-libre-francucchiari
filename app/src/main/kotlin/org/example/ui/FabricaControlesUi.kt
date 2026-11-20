package org.example

import javafx.scene.control.*
import javafx.scene.layout.VBox

interface FabricaControlesUi {
    /**
     * Contrato de la fábrica de controles visuales y utilidades de formato
     * compartidas por las vistas de etapa del Modo Avanzado (Principio de
     * Inversión de Dependencias - DIP).
     */

    fun columnaBase(): VBox

    fun botonPrimario(texto: String): Button

    fun botonSecundario(texto: String): Button

    fun etiquetaFeedback(): Label

    fun aplicarFeedback(etiqueta: Label, retroalimentacion: Retroalimentacion)

    fun estiloOpcion(): String

    fun interpretarNumero(texto: String): Double

    fun crearPanelAnimacion(estado: EstadoSimulacion): PanelAnimacionCaida
}
