package org.example

import javafx.scene.control.*
import javafx.scene.layout.*

class TarjetaEntornoPersonalizado(
    catalogo: CatalogoEntornos,
    onSeleccionar: (EntornoGravitatorio) -> Unit
) {
    /**
     * Tarjeta para definir un entorno con gravedad personalizada.
     *
     * Valida el valor ingresado con el [CatalogoEntornos] inyectado y comunica el
     * error al usuario sin aceptar magnitudes inválidas.
     */

    private val vista = Pair(
        Pair(catalogo, onSeleccionar),
        Pair(TextField(), Label())
    )

    /** Construye y devuelve la tarjeta. */
    fun nodo(): VBox {
        val tarjeta = VBox(12.0)
        tarjeta.style = "-fx-alignment: center; -fx-padding: 20 24; -fx-background-color: #fefce8; " +
            "-fx-background-radius: 12; -fx-border-color: #f59e0b; -fx-border-width: 2; " +
            "-fx-border-radius: 12; -fx-border-style: dashed; -fx-cursor: hand;"
        configurarCampo()
        val botonAplicar = crearBotonAplicar()
        vista.second.second.style = "-fx-font-size: 11px; -fx-text-fill: #dc2626;"
        tarjeta.children.addAll(construirCabecera(), vista.second.first, botonAplicar, vista.second.second)
        return tarjeta
    }

    private fun construirCabecera(): HBox {
        val filaIcono = HBox(12.0)
        filaIcono.style = "-fx-alignment: center-left;"
        val icono = Label(if (vista.second.first.promptText != null) "⚙️" else "⚙️")
        icono.style = "-fx-font-size: 32px;"
        val info = VBox(2.0)
        val nombre = Label("Entorno Personalizado")
        nombre.style = "-fx-text-fill: #1e293b; -fx-font-size: 20px; -fx-font-weight: bold;"
        val gravedad = Label("Define tu propia gravedad (g)")
        gravedad.style = "-fx-text-fill: #f59e0b; -fx-font-size: 14px;"
        info.children.addAll(nombre, gravedad)
        filaIcono.children.addAll(icono, info)
        return filaIcono
    }

    private fun configurarCampo() {
        val campo = vista.second.first
        campo.promptText = "Ej: 9.81, 1.62, 3.71, 24.79..."
        campo.prefWidth = 280.0
        campo.maxWidth = 280.0
        campo.style = "-fx-font-size: 14px; -fx-background-color: white; -fx-background-radius: 8; " +
            "-fx-border-color: #d1d5db; -fx-border-width: 1.5; -fx-border-radius: 8; -fx-padding: 10 14;"
        campo.setOnAction { aplicarGravedad() }
    }

    private fun crearBotonAplicar(): Button {
        val boton = Button("Usar esta gravedad")
        boton.prefWidth = 180.0
        boton.style = "-fx-background-color: #f59e0b; -fx-text-fill: white; -fx-font-size: 13px; " +
            "-fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 10 20; -fx-cursor: hand;"
        boton.disableProperty().bind(vista.second.first.textProperty().isEmpty())
        boton.setOnAction { aplicarGravedad() }
        return boton
    }

    private fun aplicarGravedad() {
        val cat = vista.first.first
        val texto = vista.second.first.text.trim().replace(',', '.')
        val gravedadIngresada = texto.toDoubleOrNull()
        if (gravedadIngresada != null && cat.esGravedadValida(gravedadIngresada)) {
            vista.second.second.text = ""
            vista.first.second(cat.personalizado(gravedadIngresada))
        } else {
            vista.second.second.text = "Ingresa una gravedad positiva válida."
        }
    }
}
