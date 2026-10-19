package org.example

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight

/**
 * Tarjeta para definir un entorno con gravedad personalizada.
 *
 * Valida el valor ingresado con el [CatalogoEntornos] inyectado y comunica el
 * error al usuario sin aceptar magnitudes inválidas.
 */
class TarjetaEntornoPersonalizado(
    private val catalogo: CatalogoEntornos,
    private val onSeleccionar: (EntornoGravitatorio) -> Unit
) {

    private val etiquetaError = Label()

    /** Construye y devuelve la tarjeta. */
    fun nodo(): VBox {
        val tarjeta = VBox(12.0)
        tarjeta.alignment = Pos.CENTER
        tarjeta.padding = Insets(20.0, 24.0, 20.0, 24.0)
        tarjeta.maxWidth = Double.MAX_VALUE
        tarjeta.style = """
            -fx-background-color: #fefce8;
            -fx-background-radius: 12;
            -fx-border-color: #f59e0b;
            -fx-border-width: 2;
            -fx-border-radius: 12;
            -fx-border-style: dashed;
            -fx-cursor: hand;
        """.trimIndent()

        val filaIcono = HBox(12.0)
        filaIcono.alignment = Pos.CENTER_LEFT

        val icono = Label("⚙️")
        icono.font = Font.font(32.0)

        val info = VBox(2.0)
        val nombre = Label("Entorno Personalizado")
        nombre.font = Font.font("System", FontWeight.BOLD, 20.0)
        nombre.style = "-fx-text-fill: #1e293b;"

        val gravedad = Label("Define tu propia gravedad (g)")
        gravedad.font = Font.font("System", 14.0)
        gravedad.style = "-fx-text-fill: #f59e0b;"

        info.children.addAll(nombre, gravedad)
        HBox.setHgrow(info, Priority.ALWAYS)

        filaIcono.children.addAll(icono, info)
        tarjeta.children.add(filaIcono)

        val campoGravedad = TextField()
        campoGravedad.promptText = "Ej: 9.81, 1.62, 3.71, 24.79..."
        campoGravedad.font = Font.font("System", 14.0)
        campoGravedad.prefWidth = 280.0
        campoGravedad.maxWidth = 280.0
        campoGravedad.style = """
            -fx-background-color: white;
            -fx-background-radius: 8;
            -fx-border-color: #d1d5db;
            -fx-border-width: 1.5;
            -fx-border-radius: 8;
            -fx-padding: 10 14;
        """.trimIndent()

        val botonAplicar = Button("Usar esta gravedad")
        botonAplicar.font = Font.font("System", FontWeight.BOLD, 13.0)
        botonAplicar.prefWidth = 180.0
        botonAplicar.style = """
            -fx-background-color: #f59e0b;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-background-radius: 8;
            -fx-padding: 10 20;
            -fx-cursor: hand;
        """.trimIndent()
        botonAplicar.disableProperty().bind(campoGravedad.textProperty().isEmpty())

        val accionAplicar = {
            val texto = campoGravedad.text.trim().replace(',', '.')
            val gravedadIngresada = texto.toDoubleOrNull()
            if (gravedadIngresada != null && catalogo.esGravedadValida(gravedadIngresada)) {
                etiquetaError.text = ""
                onSeleccionar(catalogo.personalizado(gravedadIngresada))
            } else {
                etiquetaError.text = "Ingresa una gravedad positiva válida."
                etiquetaError.style = "-fx-text-fill: #dc2626;"
            }
        }
        botonAplicar.setOnAction { accionAplicar() }
        campoGravedad.setOnAction { accionAplicar() }

        etiquetaError.font = Font.font("System", 11.0)

        tarjeta.children.addAll(campoGravedad, botonAplicar, etiquetaError)
        return tarjeta
    }
}
