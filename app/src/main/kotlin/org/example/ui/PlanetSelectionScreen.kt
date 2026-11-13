package org.example

import javafx.scene.control.*
import javafx.scene.layout.VBox

class PlanetSelectionScreen(
    private val catalogo: CatalogoEntornos,
    private val onPlanetaSeleccionado: (EntornoGravitatorio) -> Unit,
    private val onVolver: () -> Unit
) : VBox(20.0) {
    /**
     * Pantalla 2 - Selección de Planeta/Entorno.
     *
     * Compone las tarjetas de los entornos predefinidos ([TarjetaEntorno]) y la
     * tarjeta de gravedad personalizada ([TarjetaEntornoPersonalizado]), aplicando
     * SRP y DIP (recibe [CatalogoEntornos]).
     */

    init {
        inicializar()
    }

    private fun inicializar() {
        style = "-fx-alignment: top-center; -fx-padding: 30; -fx-background-color: linear-gradient(to bottom, #f8fafc, #e2e8f0);"
        val titulo = Label("Selecciona el Entorno Gravitatorio")
        titulo.style = "-fx-text-fill: #1e293b; -fx-font-size: 28px; -fx-font-weight: bold;"
        val gridPlanetas = VBox(16.0)
        gridPlanetas.style = "-fx-alignment: center;"
        gridPlanetas.maxWidth = 560.0
        for (entorno in catalogo.predefinidos()) {
            gridPlanetas.children.add(TarjetaEntorno(entorno, onPlanetaSeleccionado).nodo())
        }
        gridPlanetas.children.add(TarjetaEntornoPersonalizado(catalogo, onPlanetaSeleccionado).nodo())
        children.addAll(crearBotonVolver(), titulo, gridPlanetas)
    }

    private fun crearBotonVolver(): Button {
        val botonVolver = Button("← Volver")
        botonVolver.style = estiloBotonVolver(false)
        botonVolver.setOnAction { onVolver() }
        botonVolver.setOnMouseEntered { botonVolver.style = estiloBotonVolver(true) }
        botonVolver.setOnMouseExited { botonVolver.style = estiloBotonVolver(false) }
        return botonVolver
    }

    private fun estiloBotonVolver(resaltado: Boolean) =
        if (resaltado) {
            "-fx-background-color: #e2e8f0; -fx-text-fill: #334155; -fx-font-size: 13px; -fx-padding: 8 16; -fx-background-radius: 8; -fx-cursor: hand;"
        } else {
            "-fx-background-color: transparent; -fx-text-fill: #64748b; -fx-font-size: 13px; -fx-padding: 8 16; -fx-background-radius: 8; -fx-cursor: hand;"
        }
}
