package org.example

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.stage.Stage

/**
 * Aplicación JavaFX del simulador de caída libre.
 *
 * Se limita a configurar la ventana y a delegar la composición de servicios y
 * la navegación en [ComposicionAplicacion] y [NavegadorPantallas] (SRP). No
 * contiene `object` ni utilidades estáticas.
 */
class SimuladorApp : Application() {

    private val tituloVentana = "Simulador de Caída Libre"
    private val anchoVentana = 1180.0
    private val altoVentana = 780.0
    private val anchoMinimo = 980.0
    private val altoMinimo = 660.0

    override fun start(stage: Stage) {
        stage.title = tituloVentana

        val contenedorRaiz = StackPane()
        contenedorRaiz.style = "-fx-background-color: #f8fafc;"

        val scene = Scene(contenedorRaiz, anchoVentana, altoVentana)
        scene.stylesheets.add(javaClass.getResource("/estilos.css")?.toExternalForm())

        val navegador = NavegadorPantallas(contenedorRaiz, ComposicionAplicacion())
        navegador.mostrarSeleccionModo()

        stage.scene = scene
        stage.minWidth = anchoMinimo
        stage.minHeight = altoMinimo
        stage.isResizable = true
        stage.show()
    }
}
