package org.example

import javafx.scene.control.*
import javafx.scene.layout.VBox

class FabricaControles : FabricaControlesUi {
    /**
     * Fábrica instanciable de controles y estilos compartidos por las vistas del
     * Modo Avanzado.
     *
     * Centraliza la creación de botones, etiquetas de retroalimentación y
     * utilidades de presentación, evitando duplicación entre las etapas.
     */

    private val anchoBase = 780.0

    /** Columna base con el ancho de contenido del desafío. */
    override fun columnaBase(): VBox {
        val columna = VBox(12.0)
        columna.style = "-fx-alignment: top-left;"
        columna.prefWidth = anchoBase
        columna.maxWidth = anchoBase
        return columna
    }

    /** Botón de acción principal. */
    override fun botonPrimario(texto: String): Button {
        val boton = Button(texto)
        boton.style =
            "-fx-background-color: #4f46e5; -fx-text-fill: white; -fx-padding: 9 20; " +
                "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;"
        return boton
    }

    /** Botón de acción secundaria. */
    override fun botonSecundario(texto: String): Button {
        val boton = Button(texto)
        boton.style =
            "-fx-background-color: #e0e7ff; -fx-text-fill: #3730a3; -fx-padding: 8 16; " +
                "-fx-font-size: 13px; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;"
        return boton
    }

    /** Etiqueta vacía preparada para mostrar retroalimentación. */
    override fun etiquetaFeedback(): Label {
        val etiqueta = Label()
        etiqueta.isWrapText = true
        etiqueta.maxWidth = anchoBase - 40.0
        return etiqueta
    }

    /** Aplica el texto y el color de una retroalimentación. */
    override fun aplicarFeedback(etiqueta: Label, retroalimentacion: Retroalimentacion) {
        etiqueta.text = retroalimentacion.resumen()
        etiqueta.style = if (retroalimentacion.esCorrecta()) {
            "-fx-text-fill: #16a34a; -fx-font-weight: bold;"
        } else {
            "-fx-text-fill: #dc2626; -fx-font-weight: bold;"
        }
    }

    /** Estilo visual de una opción seleccionable. */
    override fun estiloOpcion(): String =
        "-fx-background-color: white; -fx-text-fill: #1e293b; -fx-padding: 12 16; " +
            "-fx-background-radius: 8; -fx-border-color: #cbd5e1; -fx-border-radius: 8; -fx-cursor: hand;"

    /** Interpreta un texto numérico aceptando coma o punto decimal. */
    override fun interpretarNumero(texto: String): Double =
        texto.trim().replace(',', '.').toDoubleOrNull() ?: Double.NaN

    /** Construye la barra inferior de controles de reproducción. */
    override fun crearBarraControles(estado: EstadoSimulacion, control: ControlReproduccion): BarraControlesSimulacion =
        BarraControlesSimulacion(estado, control)

    /** Construye el panel visual de animación de la caída libre. */
    override fun crearPanelAnimacion(estado: EstadoSimulacion, renderizador: RenderizadorAnimacionCaida): PanelAnimacionCaida =
        PanelAnimacionCaida(estado, renderizador)
}
