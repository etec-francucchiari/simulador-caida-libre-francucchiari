package org.example

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight

/**
 * Tarjeta visual seleccionable para un entorno gravitatorio predefinido.
 *
 * Encapsula la composición, el icono y los efectos de hover de la tarjeta.
 */
class TarjetaEntorno(
    private val entorno: EntornoGravitatorio,
    private val onSeleccionar: (EntornoGravitatorio) -> Unit
) {

    /** Construye y devuelve la tarjeta. */
    fun nodo(): VBox {
        val tarjeta = VBox(8.0)
        tarjeta.alignment = Pos.CENTER
        tarjeta.padding = Insets(20.0, 24.0, 20.0, 24.0)
        tarjeta.maxWidth = Double.MAX_VALUE
        tarjeta.style = estiloBase()

        val filaSuperior = HBox(12.0)
        filaSuperior.alignment = Pos.CENTER_LEFT

        val icono = Label(icono())
        icono.font = Font.font(32.0)

        val info = VBox(2.0)
        val nombre = Label(entorno.nombre())
        nombre.font = Font.font("System", FontWeight.BOLD, 20.0)
        nombre.style = "-fx-text-fill: #1e293b;"

        val gravedad = Label("g = ${entorno.gravedadFormateada()} m/s²")
        gravedad.font = Font.font("System", FontWeight.BOLD, 16.0)
        gravedad.style = "-fx-text-fill: #3b82f6;"

        val descripcion = Label(entorno.descripcion())
        descripcion.font = Font.font("System", 12.0)
        descripcion.style = "-fx-text-fill: #64748b;"

        info.children.addAll(nombre, gravedad, descripcion)
        HBox.setHgrow(info, Priority.ALWAYS)

        val flecha = Label("→")
        flecha.font = Font.font("System", FontWeight.BOLD, 24.0)
        flecha.style = "-fx-text-fill: #cbd5e1;"

        filaSuperior.children.addAll(icono, info, flecha)
        tarjeta.children.add(filaSuperior)

        tarjeta.setOnMouseClicked { onSeleccionar(entorno) }
        tarjeta.onMouseEntered = { tarjeta.style = estiloHover() }
        tarjeta.onMouseExited = { tarjeta.style = estiloBase() }

        return tarjeta
    }

    private fun icono(): String = when (entorno.nombre()) {
        "Tierra" -> "🌍"
        "Luna" -> "🌙"
        "Marte" -> "🔴"
        "Júpiter" -> "🪐"
        else -> "🪐"
    }

    private fun estiloBase(): String = """
        -fx-background-color: white;
        -fx-background-radius: 12;
        -fx-border-color: #e2e8f0;
        -fx-border-width: 1.5;
        -fx-border-radius: 12;
        -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 8, 0, 0, 2);
        -fx-cursor: hand;
    """.trimIndent()

    private fun estiloHover(): String = """
        -fx-background-color: #f8fafc;
        -fx-background-radius: 12;
        -fx-border-color: #3b82f6;
        -fx-border-width: 2;
        -fx-border-radius: 12;
        -fx-effect: dropshadow(three-pass-box, rgba(59,130,246,0.15), 12, 0, 0, 4);
        -fx-cursor: hand;
    """.trimIndent()
}
