package org.example

import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.layout.BorderPane

class PrincipianteSimulationScreen(
    pestanaFormulas: Node,
    pestanaSimulacion: Node
) : BorderPane() {
    /**
     * Pantalla 3 - Simulación del Modo Principiante.
     *
     * Compone la cabecera, las pestañas de fórmulas y simulación, y los controles
     * recibidos mediante inyección de dependencias por constructor (DIP).
     */

    init {
        center = construirPanelCentral(pestanaFormulas, pestanaSimulacion)
    }

    /**
     * Configura la cabecera de navegación y la barra de controles de reproducción.
     */
    fun configurarBarras(
        estado: EstadoSimulacion,
        control: ControlReproduccion,
        onCambiarPlaneta: () -> Unit,
        onCambiarModo: () -> Unit
    ) {
        top = EncabezadoSimulacion(estado, onCambiarPlaneta, onCambiarModo)
        bottom = BarraControlesSimulacion(estado, control)
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
