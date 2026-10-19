package org.example

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight

/**
 * Pantalla 1 - Selección de Modo.
 *
 * Presentación de bienvenida limpia con dos opciones claras:
 * - [Modo Principiante]: Guiado con fórmulas paso a paso.
 * - [Modo Avanzado]: Desafío interactivo de cálculo manual y exportación.
 *
 * Responsabilidad Única (SRP): Solo gestiona la selección inicial del modo.
 * No conoce la lógica de simulación ni el estado físico.
 */
class ModeSelectionScreen(
    private val onModoSeleccionado: (ModoSimulacion) -> Unit
) : VBox(24.0) {

    init {
        alignment = Pos.CENTER
        padding = Insets(40.0)
        style = "-fx-background-color: linear-gradient(to bottom, #f8fafc, #e2e8f0);"
        maxWidth = Double.MAX_VALUE
        maxHeight = Double.MAX_VALUE

        // Título de bienvenida
        val titulo = Label("Simulador de Caída Libre")
        titulo.font = Font.font("System", FontWeight.BOLD, 36.0)
        titulo.style = "-fx-text-fill: #1e293b;"

        val subtitulo = Label("Elige cómo quieres aprender y experimentar")
        subtitulo.font = Font.font("System", 18.0)
        subtitulo.style = "-fx-text-fill: #64748b;"

        // Tarjeta Modo Principiante
        val tarjetaPrincipiante = crearTarjetaModo(
            titulo = "Modo Principiante",
            descripcion = "Guiado paso a paso con fórmulas del MRUV,\nsustitución de variables y cálculo en tiempo real.",
            colorBorde = "#3b82f6",
            colorFondo = "#eff6ff",
            icono = "📚",
            accion = { onModoSeleccionado(ModoSimulacion.PRINCIPIANTE) }
        )

        // Tarjeta Modo Avanzado
        val tarjetaAvanzado = crearTarjetaModo(
            titulo = "Modo Avanzado",
            descripcion = "Desafío interactivo: elige la fórmula correcta,\nsustituye los valores, resuelve el cálculo manual\ny exporta los resultados en CSV o JSON.",
            colorBorde = "#7c3aed",
            colorFondo = "#f5f3ff",
            icono = "🔬",
            accion = { onModoSeleccionado(ModoSimulacion.ESTANDAR) }
        )

        val nota = Label("Selecciona un modo para continuar →")
        nota.font = Font.font("System", FontWeight.MEDIUM, 14.0)
        nota.style = "-fx-text-fill: #94a3b8;"

        children.addAll(titulo, subtitulo, tarjetaPrincipiante, tarjetaAvanzado, nota)
    }

    private fun crearTarjetaModo(
        titulo: String,
        descripcion: String,
        colorBorde: String,
        colorFondo: String,
        icono: String,
        accion: () -> Unit,
        deshabilitado: Boolean = false
    ): VBox {
        val tarjeta = VBox(12.0)
        tarjeta.alignment = Pos.CENTER
        tarjeta.padding = Insets(24.0, 32.0, 24.0, 32.0)
        tarjeta.prefWidth = 420.0
        tarjeta.maxWidth = 420.0
        tarjeta.style = """
            -fx-background-color: $colorFondo;
            -fx-background-radius: 16;
            -fx-border-color: $colorBorde;
            -fx-border-width: 2;
            -fx-border-radius: 16;
            -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 12, 0, 0, 4);
        """
        if (deshabilitado) {
            tarjeta.opacity = 0.6
            tarjeta.style += "-fx-cursor: default;"
        } else {
            tarjeta.style += "-fx-cursor: hand;"
        }

        val labelIcono = Label(icono)
        labelIcono.font = Font.font(48.0)

        val labelTitulo = Label(titulo)
        labelTitulo.font = Font.font("System", FontWeight.BOLD, 22.0)
        labelTitulo.style = "-fx-text-fill: #1e293b;"

        val labelDescripcion = Label(descripcion)
        labelDescripcion.font = Font.font("System", 14.0)
        labelDescripcion.style = "-fx-text-fill: #475569; -fx-text-alignment: center; -fx-wrap-text: true;"
        labelDescripcion.isWrapText = true
        labelDescripcion.maxWidth = 340.0

        val boton = Button("Seleccionar")
        boton.font = Font.font("System", FontWeight.BOLD, 14.0)
        boton.prefWidth = 180.0
        boton.prefHeight = 44.0
        boton.isDisable = deshabilitado
        boton.style = if (deshabilitado) {
            """
                -fx-background-color: #cbd5e1;
                -fx-text-fill: #94a3b8;
                -fx-font-weight: bold;
                -fx-background-radius: 8;
            """
        } else {
            """
                -fx-background-color: $colorBorde;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 8;
            """
        }
        boton.setOnAction { accion() }

        if (!deshabilitado) {
            tarjeta.setOnMouseClicked { accion() }
        }

        tarjeta.children.addAll(labelIcono, labelTitulo, labelDescripcion, boton)
        return tarjeta
    }
}