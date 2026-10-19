package org.example

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.text.Font

/**
 * Pantalla 3 - Simulación del Modo Principiante.
 *
 * Compone la cabecera, las pestañas de fórmulas y simulación, y los controles.
 * Todas sus dependencias se reciben por constructor como abstracciones
 * ([EstadoSimulacion], [ComandosSimulacion], [ControlReproduccion]) y servicios
 * ([CalculoCaidaLibre], [ExplicadorFisicaPasoAPaso], [ValidadorParametrosIniciales]),
 * aplicando inversión de dependencias (DIP).
 */
class PrincipianteSimulationScreen(
    private val estado: EstadoSimulacion,
    private val comandos: ComandosSimulacion,
    private val control: ControlReproduccion,
    private val series: SeriesCinematica,
    private val explicador: ExplicadorFisica,
    private val validador: ValidadorParametros,
    private val renderizadorAnimacion: RenderizadorAnimacionCaida,
    private val onCambiarPlaneta: () -> Unit,
    private val onCambiarModo: () -> Unit
) : BorderPane() {

    init {
        top = EncabezadoSimulacion(estado, onCambiarPlaneta, onCambiarModo)
        center = construirPanelCentral()
        bottom = BarraControlesSimulacion(estado, control)
    }

    private fun construirPanelCentral(): TabPane {
        val tabPane = TabPane()
        tabPane.tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
        tabPane.style = "-fx-background-color: #ffffff;"

        val tabFormulas = Tab("📐 Fórmulas paso a paso", construirVistaFormulas())
        val tabSimulacion = Tab("🎬 Simulación", construirVistaSimulacion())

        tabPane.tabs.addAll(tabFormulas, tabSimulacion)
        return tabPane
    }

    /**
     * Pestaña didáctica: parámetros iniciales + explicación de fórmulas.
     */
    private fun construirVistaFormulas(): ScrollPane {
        val panelParametros = PanelParametrosIniciales(estado, comandos, validador)
        val panelExplicacion = PanelExplicacionFisica(estado, explicador)

        val contenedor = VBox(16.0)
        contenedor.padding = Insets(18.0)
        contenedor.alignment = Pos.TOP_CENTER
        contenedor.maxWidth = 720.0
        contenedor.children.addAll(panelParametros, panelExplicacion)

        val scroll = ScrollPane(contenedor)
        scroll.isFitToWidth = true
        scroll.style = "-fx-background-color: #ffffff; -fx-background: #ffffff;"
        return scroll
    }

    /**
     * Pestaña de simulación: indicadores, animación y gráficos.
     */
    private fun construirVistaSimulacion(): VBox {
        val contenedor = VBox(12.0)
        contenedor.padding = Insets(14.0)

        val panelValores = PanelValoresInstantaneos(estado)

        val panelAnimacion = PanelAnimacionCaida(estado, renderizadorAnimacion)
        panelAnimacion.prefWidth = 300.0
        panelAnimacion.minWidth = 280.0

        val panelGraficos = PanelGraficosSimulacion(estado, series)
        HBox.setHgrow(panelGraficos, Priority.ALWAYS)

        val filaVisualizacion = HBox(12.0, panelAnimacion, panelGraficos)
        filaVisualizacion.alignment = Pos.TOP_CENTER
        VBox.setVgrow(filaVisualizacion, Priority.ALWAYS)

        val ayuda = Label(
            "Usa los controles inferiores: Iniciar/Pausar para animar y Reiniciar para volver a t = 0."
        )
        ayuda.font = Font.font("System", 12.0)
        ayuda.style = "-fx-text-fill: #64748b; -fx-font-style: italic;"

        contenedor.children.addAll(panelValores, ayuda, filaVisualizacion)
        return contenedor
    }
}