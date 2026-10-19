package org.example

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight

/**
 * Pantalla 2 - Selección de Planeta/Entorno.
 *
 * Compone las tarjetas de los entornos predefinidos ([TarjetaEntorno]) y la
 * tarjeta de gravedad personalizada ([TarjetaEntornoPersonalizado]), aplicando
 * SRP y DIP (recibe [CatalogoEntornos]).
 */
class PlanetSelectionScreen(
    private val catalogo: CatalogoEntornos,
    private val onPlanetaSeleccionado: (EntornoGravitatorio) -> Unit,
    private val onVolver: () -> Unit
) : VBox(20.0) {

    init {
        alignment = Pos.TOP_CENTER
        padding = Insets(30.0)
        style = "-fx-background-color: linear-gradient(to bottom, #f8fafc, #e2e8f0);"
        maxWidth = Double.MAX_VALUE
        maxHeight = Double.MAX_VALUE

        val titulo = Label("Selecciona el Entorno Gravitatorio")
        titulo.font = Font.font("System", FontWeight.BOLD, 28.0)
        titulo.style = "-fx-text-fill: #1e293b;"

        val subtitulo = Label("La gravedad determina la aceleración de la caída (g)")
        subtitulo.font = Font.font("System", 15.0)
        subtitulo.style = "-fx-text-fill: #64748b;"

        val gridPlanetas = VBox(16.0)
        gridPlanetas.alignment = Pos.CENTER
        gridPlanetas.maxWidth = 560.0

        for (entorno in catalogo.predefinidos()) {
            gridPlanetas.children.add(
                TarjetaEntorno(entorno, onPlanetaSeleccionado).nodo()
            )
        }

        gridPlanetas.children.add(
            TarjetaEntornoPersonalizado(catalogo, onPlanetaSeleccionado).nodo()
        )

        children.addAll(construirBarraSuperior(), titulo, subtitulo, gridPlanetas)
    }

    private fun construirBarraSuperior(): HBox {
        val barraSuperior = HBox()
        barraSuperior.alignment = Pos.CENTER_LEFT
        barraSuperior.maxWidth = Double.MAX_VALUE

        val botonVolver = Button("← Volver")
        botonVolver.font = Font.font("System", FontWeight.MEDIUM, 13.0)
        botonVolver.style = estiloBotonVolver(false)
        botonVolver.setOnAction { onVolver() }
        botonVolver.onMouseEntered = { botonVolver.style = estiloBotonVolver(true) }
        botonVolver.onMouseExited = { botonVolver.style = estiloBotonVolver(false) }

        barraSuperior.children.add(botonVolver)
        return barraSuperior
    }

    private fun estiloBotonVolver(resaltado: Boolean): String {
        val fondo = if (resaltado) "#e2e8f0" else "transparent"
        val texto = if (resaltado) "#334155" else "#64748b"
        return """
            -fx-background-color: $fondo;
            -fx-text-fill: $texto;
            -fx-padding: 8 16;
            -fx-background-radius: 8;
            -fx-cursor: hand;
        """.trimIndent()
    }
}
