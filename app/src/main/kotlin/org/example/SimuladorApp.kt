package org.example

import javafx.application.Application
import javafx.application.Platform
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.Separator
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.stage.Stage

/**
 * Vista principal de la interfaz gráfica del Simulador de Caída Libre.
 *
 * Integra todos los componentes visuales:
 * - Selector de planeta y propiedades gravitacionales.
 * - Panel de configuración de parámetros iniciales (altura y velocidad).
 * - Panel de fórmulas físicas teóricas.
 * - Panel de indicadores físicos dinámicos (tiempo transcurrido, altura y velocidad actual).
 * - Componente visual de gráficos cinemáticos en tiempo real (y(t) y v(t)).
 *
 * Sigue los principios SOLID: desacopla el bucle de simulación en [ControladorSimulacion]
 * y el estado reactivo en [GestorEstadoSimulacion].
 */
class SimuladorApp : Application() {

    companion object {
        private const val TITULO_VENTANA = "Simulador de Caída Libre"
        private const val ANCHO_VENTANA = 1120.0
        private const val ALTO_VENTANA = 740.0
        private const val TITULO_ENCABEZADO = "Simulador Interactivo de Caída Libre"
    }

    private val gestorEstado = GestorEstadoSimulacion()
    private val controladorSimulacion = ControladorSimulacion(gestorEstado)

    override fun start(stage: Stage) {
        stage.title = TITULO_VENTANA

        val root = construirLayoutPrincipal()
        val scene = Scene(root, ANCHO_VENTANA, ALTO_VENTANA)
        scene.stylesheets.add(javaClass.getResource("/estilos.css")?.toExternalForm())

        stage.scene = scene
        stage.minWidth = 980.0
        stage.minHeight = 650.0
        stage.isResizable = true
        stage.show()
    }

    private fun construirLayoutPrincipal(): BorderPane {
        val borderPane = BorderPane()

        borderPane.top = construirEncabezado()
        borderPane.center = construirAreaCentral()
        borderPane.bottom = construirBarraInferior()

        return borderPane
    }

    private fun construirEncabezado(): StackPane {
        val titulo = Label(TITULO_ENCABEZADO)
        titulo.font = Font.font("System", FontWeight.BOLD, 22.0)
        titulo.style = "-fx-text-fill: #2c3e50;"

        val contenedorEncabezado = StackPane(titulo)
        contenedorEncabezado.padding = Insets(18.0)
        contenedorEncabezado.style = "-fx-background-color: #ecf0f1;"
        StackPane.setAlignment(titulo, Pos.CENTER)

        return contenedorEncabezado
    }

    private fun construirAreaCentral(): ScrollPane {
        val layoutHorizontal = HBox(16.0)
        layoutHorizontal.padding = Insets(16.0)
        layoutHorizontal.alignment = Pos.TOP_CENTER

        // Columna Izquierda: Parámetros y Fórmulas
        val columnaIzquierda = construirColumnaIzquierda()
        columnaIzquierda.prefWidth = 440.0
        columnaIzquierda.maxWidth = 480.0

        // Columna Derecha: Valores Instantáneos y Gráficos en Tiempo Real
        val columnaDerecha = construirColumnaDerecha()
        HBox.setHgrow(columnaDerecha, Priority.ALWAYS)

        layoutHorizontal.children.addAll(columnaIzquierda, columnaDerecha)

        val scrollPane = ScrollPane(layoutHorizontal)
        scrollPane.isFitToWidth = true
        scrollPane.style = "-fx-background-color: #ffffff; -fx-background: #ffffff;"

        return scrollPane
    }

    private fun construirColumnaIzquierda(): VBox {
        val contenedor = VBox(12.0)
        contenedor.alignment = Pos.TOP_CENTER

        val etiquetaSelector = Label("Seleccione el planeta:")
        etiquetaSelector.font = Font.font("System", FontWeight.BOLD, 15.0)
        etiquetaSelector.style = "-fx-text-fill: #2c3e50;"

        val selectorPlaneta = ComboBox<Planeta>()
        selectorPlaneta.items.addAll(Planeta.entries)
        selectorPlaneta.maxWidth = Double.MAX_VALUE
        selectorPlaneta.valueProperty().addListener { _, _, nuevoPlaneta ->
            if (nuevoPlaneta != null) {
                gestorEstado.establecerPlaneta(nuevoPlaneta)
            }
        }
        selectorPlaneta.value = gestorEstado.obtenerPlaneta()

        val etiquetaInfoPlaneta = Label()
        etiquetaInfoPlaneta.font = Font.font("System", 13.0)
        etiquetaInfoPlaneta.style =
            "-fx-text-fill: #34495e; -fx-wrap-text: true; -fx-text-alignment: center;"

        gestorEstado.planetaActivoProperty.addListener { _, _, planeta ->
            etiquetaInfoPlaneta.text = construirTextoInfo(planeta)
        }
        etiquetaInfoPlaneta.text = construirTextoInfo(gestorEstado.obtenerPlaneta())

        val panelParametros = PanelParametrosIniciales(gestorEstado)
        val panelIndicadores = PanelIndicadoresFisicos(gestorEstado)

        contenedor.children.addAll(
            etiquetaSelector,
            selectorPlaneta,
            etiquetaInfoPlaneta,
            Separator(),
            panelParametros,
            panelIndicadores
        )

        return contenedor
    }

    private fun construirColumnaDerecha(): VBox {
        val contenedor = VBox(12.0)
        contenedor.alignment = Pos.TOP_CENTER

        val panelValoresInstantaneos = PanelValoresInstantaneos(gestorEstado)
        val panelGraficos = PanelGraficosSimulacion(gestorEstado)
        VBox.setVgrow(panelGraficos, Priority.ALWAYS)

        contenedor.children.addAll(
            panelValoresInstantaneos,
            panelGraficos
        )

        return contenedor
    }

    private fun construirTextoInfo(planeta: Planeta): String {
        return "Planeta: ${planeta.nombre} | Gravedad: ${planeta.gravedad} m/s²\n" +
                planeta.descripcion
    }

    private fun construirBarraInferior(): HBox {
        val botonIniciar = Button("Iniciar Simulación")
        botonIniciar.style = botonesEstiloPrimario()

        gestorEstado.enEjecucionProperty.addListener { _, _, enEjecucion ->
            if (enEjecucion) {
                botonIniciar.text = "Pausar Simulación"
                botonIniciar.style = botonesEstiloPausar()
            } else {
                botonIniciar.text = "Iniciar Simulación"
                botonIniciar.style = botonesEstiloPrimario()
            }
        }

        botonIniciar.setOnAction {
            controladorSimulacion.alternarEjecucion()
        }

        val botonReiniciar = Button("Reiniciar")
        botonReiniciar.style = botonesEstiloReinicio()
        botonReiniciar.setOnAction {
            controladorSimulacion.reiniciar()
        }

        val botonSalir = Button("Salir")
        botonSalir.style = botonesEstiloSecundario()
        botonSalir.setOnAction { Platform.exit() }

        val barraBotones = HBox(15.0, botonIniciar, botonReiniciar, botonSalir)
        barraBotones.alignment = Pos.CENTER
        barraBotones.padding = Insets(16.0)
        barraBotones.style = "-fx-background-color: #ecf0f1;"

        return barraBotones
    }

    private fun botonesEstiloPrimario(): String {
        return "-fx-background-color: #3498db; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 10 24; " +
                "-fx-background-radius: 5;"
    }

    private fun botonesEstiloPausar(): String {
        return "-fx-background-color: #e67e22; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 10 24; " +
                "-fx-background-radius: 5;"
    }

    private fun botonesEstiloReinicio(): String {
        return "-fx-background-color: #7f8c8d; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 10 24; " +
                "-fx-background-radius: 5;"
    }

    private fun botonesEstiloSecundario(): String {
        return "-fx-background-color: #e74c3c; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 10 24; " +
                "-fx-background-radius: 5;"
    }
}
