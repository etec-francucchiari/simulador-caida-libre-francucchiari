package org.example

import javafx.scene.control.Label
import javafx.scene.layout.*

class TarjetaEntorno(
    entorno: EntornoGravitatorio,
    onSeleccionar: (EntornoGravitatorio) -> Unit
) {
    /**
     * Tarjeta visual seleccionable para un entorno gravitatorio predefinido.
     *
     * Encapsula la composición, el icono y los efectos de hover de la tarjeta.
     */

    private val datos = Pair(entorno, onSeleccionar)

    /** Construye y devuelve la tarjeta. */
    fun nodo(): VBox {
        val tarjeta = VBox(8.0)
        tarjeta.style = estiloTarjeta(false)
        val filaSuperior = HBox(12.0)
        filaSuperior.style = "-fx-alignment: center-left;"
        val icono = Label(icono())
        icono.style = "-fx-font-size: 32px;"
        val flecha = Label("→")
        flecha.style = "-fx-text-fill: #cbd5e1; -fx-font-size: 24px; -fx-font-weight: bold;"
        filaSuperior.children.addAll(icono, construirInfo(), flecha)
        tarjeta.children.add(filaSuperior)
        tarjeta.setOnMouseClicked { datos.second(datos.first) }
        tarjeta.setOnMouseEntered { tarjeta.style = estiloTarjeta(true) }
        tarjeta.setOnMouseExited { tarjeta.style = estiloTarjeta(false) }
        return tarjeta
    }

    private fun construirInfo(): VBox {
        val info = VBox(2.0)
        info.prefWidth = 480.0
        val nombre = Label(datos.first.nombre())
        nombre.style = "-fx-text-fill: #1e293b; -fx-font-size: 20px; -fx-font-weight: bold;"
        val gravedad = Label("g = ${datos.first.gravedadFormateada()} m/s²")
        gravedad.style = "-fx-text-fill: #3b82f6; -fx-font-size: 16px; -fx-font-weight: bold;"
        info.children.addAll(nombre, gravedad)
        return info
    }

    private fun icono(): String = when (datos.first.nombre()) {
        "Tierra" -> "🌍"
        "Luna" -> "🌙"
        "Marte" -> "🔴"
        "Júpiter" -> "🪐"
        else -> "🪐"
    }

    private fun estiloTarjeta(hover: Boolean): String {
        val activa = hover && datos.first.nombre().isNotEmpty()
        return if (activa) {
            "-fx-alignment: center; -fx-padding: 20 24; -fx-background-color: #f8fafc; -fx-background-radius: 12; " +
                "-fx-border-color: #3b82f6; -fx-border-width: 2; -fx-border-radius: 12; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(59,130,246,0.15), 12, 0, 0, 4); -fx-cursor: hand;"
        } else {
            "-fx-alignment: center; -fx-padding: 20 24; -fx-background-color: white; -fx-background-radius: 12; " +
                "-fx-border-color: #e2e8f0; -fx-border-width: 1.5; -fx-border-radius: 12; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 8, 0, 0, 2); -fx-cursor: hand;"
        }
    }
}
