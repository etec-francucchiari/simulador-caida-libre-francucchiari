package org.example

import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.layout.BorderPane

class PrincipianteSimulationScreen(
    encabezado: Node,
    pestanaFormulas: Node,
    pestanaSimulacion: Node,
    barraControles: Node
) : BorderPane() {
    /**
     * Pantalla 3 - Simulación del Modo Principiante.
     *
     * Compone la cabecera, las pestañas de fórmulas y simulación, y los controles
     * recibidos mediante inyección de dependencias por constructor (DIP).
     */

    init {
        inicializar(encabezado, pestanaFormulas, pestanaSimulacion, barraControles)
    }

    private fun inicializar(encabezado: Node, pestanaFormulas: Node, pestanaSimulacion: Node, barraControles: Node) {
        top = encabezado
        center = construirPanelCentral(pestanaFormulas, pestanaSimulacion)
        bottom = barraControles
    }

    private fun construirPanelCentral(pestanaFormulas: Node, pestanaSimulacion: Node): TabPane {
        val tabFormulas = Tab("📐 Fórmulas paso a paso", pestanaFormulas)
        tabFormulas.isClosable = false
        val tabSimulacion = Tab("🎬 Simulación", pestanaSimulacion)
        tabSimulacion.isClosable = false
        val tabPane = TabPane(tabFormulas, tabSimulacion)
        tabPane.style = "-fx-background-color: #ffffff;"
        return tabPane
    }
}
