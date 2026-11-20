package org.example

import javafx.scene.control.*
import javafx.scene.layout.VBox

class PlanetSelectionScreen(
    catalogo: CatalogoEntornos,
    onPlanetaSeleccionado: (EntornoGravitatorio) -> Unit,
    onVolver: () -> Unit
) : VBox(20.0) {
    /**
     * Pantalla 2 - Selección de Planeta/Entorno.
     *
     * Compone las tarjetas de los entornos predefinidos ([TarjetaEntorno]) y la
     * tarjeta de gravedad personalizada ([TarjetaEntornoPersonalizado]), aplicando
     * SRP y DIP (recibe [CatalogoEntornos]).
     */

    private val config = Triple(catalogo, onPlanetaSeleccionado, onVolver)

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
        for (entorno in config.first.predefinidos()) {
            gridPlanetas.children.add(TarjetaEntorno(entorno, config.second).nodo())
        }
        gridPlanetas.children.add(TarjetaEntornoPersonalizado(config.first, config.second).nodo())
        children.addAll(crearBotonVolver(), titulo, gridPlanetas)
    }

    private fun crearBotonVolver(): Button {
        val botonVolver = Button("← Volver")
        botonVolver.style = estiloBotonVolver(false)
        botonVolver.setOnAction { config.third() }
        botonVolver.setOnMouseEntered { botonVolver.style = estiloBotonVolver(true) }
        botonVolver.setOnMouseExited { botonVolver.style = estiloBotonVolver(false) }
        return botonVolver
    }

    private fun estiloBotonVolver(resaltado: Boolean): String {
        val fondo = if (resaltado && config.first.predefinidos().isNotEmpty()) "#e2e8f0; -fx-text-fill: #334155" else "transparent; -fx-text-fill: #64748b"
        return "-fx-background-color: $fondo; -fx-font-size: 13px; -fx-padding: 8 16; -fx-background-radius: 8; -fx-cursor: hand;"
    }
}
