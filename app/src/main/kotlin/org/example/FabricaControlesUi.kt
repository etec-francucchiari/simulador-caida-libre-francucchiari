package org.example

import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox

/**
 * Contrato de la fábrica de controles compartidos del Modo Avanzado (DIP).
 */
interface FabricaControlesUi {

    /** Columna base con el ancho de contenido del desafío. */
    fun columnaBase(): VBox

    /** Botón de acción principal. */
    fun botonPrimario(texto: String): Button

    /** Botón de acción secundaria. */
    fun botonSecundario(texto: String): Button

    /** Etiqueta vacía preparada para mostrar retroalimentación. */
    fun etiquetaFeedback(): Label

    /** Aplica el texto y el color de una retroalimentación. */
    fun aplicarFeedback(etiqueta: Label, retroalimentacion: Retroalimentacion)

    /** Estilo visual de una opción seleccionable. */
    fun estiloOpcion(): String

    /** Interpreta un texto numérico aceptando coma o punto decimal. */
    fun interpretarNumero(texto: String): Double
}
