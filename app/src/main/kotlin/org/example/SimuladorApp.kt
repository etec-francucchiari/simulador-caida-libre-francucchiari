package org.example

import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.Separator
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.stage.Stage

/**
 * Vista principal de la interfaz gráfica del Simulador de Caída Libre.
 *
 * Construye la pantalla principal sin conocer la lógica del simulador:
 * delega el estado reactivo en [GestorEstadoSimulacion] y se suscribe
 * a sus propiedades para refrescar la UI automáticamente.
 */
class SimuladorApp : Application() {

    companion object {
        private const val TITULO_VENTANA = "Simulador de Caída Libre"
        private const val ANCHO_VENTANA = 1000.0
        private const val ALTO_VENTANA = 720.0
        private const val TITULO_ENCABEZADO = "Simulador Interactivo de Caída Libre"
    }

    private val gestorEstado = GestorEstadoSimulacion()

    override fun start(stage: Stage) {
        stage.title = TITULO_VENTANA

        val root = construirLayoutPrincipal()
        val scene = Scene(root, ANCHO_VENTANA, ALTO_VENTANA)
        scene.stylesheets.add(javaClass.getResource("/estilos.css")?.toExternalForm())

        stage.scene = scene
        stage.isResizable = false
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
        contenedorEncabezado.padding = Insets(25.0)
        contenedorEncabezado.style = "-fx-background-color: #ecf0f1;"
        StackPane.setAlignment(titulo, Pos.CENTER)

        return contenedorEncabezado
    }

    private fun construirAreaCentral(): StackPane {
        val areaCentral = StackPane()
        areaCentral.padding = Insets(20.0)
        areaCentral.style = "-fx-background-color: #ffffff;"

        val contenedor = VBox(16.0)
        contenedor.alignment = Pos.TOP_CENTER
        contenedor.maxWidth = 880.0

        val etiquetaSelector = Label("Seleccione el planeta:")
        etiquetaSelector.font = Font.font("System", FontWeight.BOLD, 16.0)
        etiquetaSelector.style = "-fx-text-fill: #2c3e50;"

        val selectorPlaneta = ComboBox<Planeta>()
        selectorPlaneta.items.addAll(Planeta.entries)
        selectorPlaneta.maxWidth = 320.0
        // El valor del selector queda vinculado con la gravedad del gestor:
        // al cambiar el selector se actualiza la simulación y viceversa.
        selectorPlaneta.valueProperty().addListener { _, _, nuevoPlaneta ->
            if (nuevoPlaneta != null) {
                gestorEstado.establecerPlaneta(nuevoPlaneta)
            }
        }
        selectorPlaneta.value = gestorEstado.obtenerPlaneta()

        val etiquetaInfoPlaneta = Label()
        etiquetaInfoPlaneta.font = Font.font("System", 14.0)
        etiquetaInfoPlaneta.style =
            "-fx-text-fill: #34495e; -fx-wrap-text: true; -fx-text-alignment: center;"

        // Suscripción reactiva: cuando el planeta cambia en el gestor de estado,
        // se actualiza automáticamente el texto descriptivo y la gravedad.
        gestorEstado.planetaActivoProperty.addListener { _, _, planeta ->
            etiquetaInfoPlaneta.text = construirTextoInfo(planeta)
        }
        etiquetaInfoPlaneta.text = construirTextoInfo(gestorEstado.obtenerPlaneta())

        // Paneles de la Issue #3: configuración de parámetros iniciales
        // y fórmulas físicas reactivas.
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

        areaCentral.children.add(contenedor)
        StackPane.setAlignment(contenedor, Pos.CENTER)

        return areaCentral
    }

    /**
     * Construye el texto descriptivo del planeta seleccionado,
     * incluyendo su nombre, gravedad y descripción.
     */
    private fun construirTextoInfo(planeta: Planeta): String {
        return "Planeta: ${planeta.nombre}\n" +
               "Gravedad: ${planeta.gravedad} m/s²\n" +
               planeta.descripcion
    }

    private fun construirBarraInferior(): HBox {
        val botonIniciar = Button("Iniciar Simulación")
        botonIniciar.style = botonesEstiloPrimario()

        val botonSalir = Button("Salir")
        botonSalir.style = botonesEstiloSecundario()
        botonSalir.setOnAction { javafx.application.Platform.exit() }

        val barraBotones = HBox(15.0, botonIniciar, botonSalir)
        barraBotones.alignment = Pos.CENTER
        barraBotones.padding = Insets(20.0)
        barraBotones.style = "-fx-background-color: #ecf0f1;"

        return barraBotones
    }

    private fun botonesEstiloPrimario(): String {
        return "-fx-background-color: #3498db; " +
               "-fx-text-fill: white; " +
               "-fx-font-size: 14px; " +
               "-fx-padding: 10 30; " +
               "-fx-background-radius: 5;"
    }

    private fun botonesEstiloSecundario(): String {
        return "-fx-background-color: #e74c3c; " +
               "-fx-text-fill: white; " +
               "-fx-font-size: 14px; " +
               "-fx-padding: 10 30; " +
               "-fx-background-radius: 5;"
    }
}
