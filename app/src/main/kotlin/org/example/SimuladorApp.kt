package org.example

import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.StackPane
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.stage.Stage

/**
 * Clase principal de la interfaz gráfica del Simulador de Caída Libre.
 * Extiende [Application] de JavaFX y es responsable únicamente
 * de construir y mostrar la pantalla principal.
 */
class SimuladorApp : Application() {

    companion object {
        private const val TITULO_VENTANA = "Simulador de Caída Libre"
        private const val ANCHO_VENTANA = 800.0
        private const val ALTO_VENTANA = 600.0
        private const val TITULO_ENCABEZADO = "Simulador Interactivo de Caída Libre"
    }

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

        val placeholder = Label("Área de simulación\n(Se agregará en las siguientes issues)")
        placeholder.font = Font.font("System", 14.0)
        placeholder.style = "-fx-text-fill: #95a5a6; -fx-text-alignment: center;"
        placeholder.alignment = Pos.CENTER

        areaCentral.children.add(placeholder)
        StackPane.setAlignment(placeholder, Pos.CENTER)

        return areaCentral
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
