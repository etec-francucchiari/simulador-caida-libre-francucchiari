package org.example

import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.layout.BorderPane

class PrincipianteSimulationScreen(
    pestanaFormulas: Node,
    pestanaSimulacion: Node,
    private val nucleoOpcional: ProveedorNucleoSimulacion? = null
) : BorderPane() {
    constructor(
        pestanaFormulas: Node,
        nucleo: ProveedorNucleoSimulacion
    ) : this(
        pestanaFormulas = pestanaFormulas,
        pestanaSimulacion = VistaPestanaSimulacion(nucleo.contexto(), nucleo.calculo()).construir(),
        nucleoOpcional = nucleo
    )

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
        onCambiarPlaneta: () -> Unit,
        onCambiarModo: () -> Unit
    ) {
        val nuc = nucleoOpcional ?: return
        val encabezado = EncabezadoSimulacion(nuc.contexto(), onCambiarPlaneta, onCambiarModo)
        top = encabezado
        bottom = encabezado.crearBarraControles(nuc.control())
    }

    private fun construirPanelCentral(pestanaFormulas: Node, pestanaSimulacion: Node): TabPane {
        val tituloSim = if (nucleoOpcional != null) "🎬 Simulación" else "🎬 Simulación"
        val tabFormulas = Tab("📐 Fórmulas paso a paso", pestanaFormulas)
        tabFormulas.isClosable = false
        val tabSimulacion = Tab(tituloSim, pestanaSimulacion)
        tabSimulacion.isClosable = false
        val tabPane = TabPane(tabFormulas, tabSimulacion)
        tabPane.style = "-fx-background-color: #ffffff;"
        return tabPane
    }
}
