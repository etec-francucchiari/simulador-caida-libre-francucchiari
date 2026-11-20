package org.example

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.stage.Stage

class SimuladorApp : Application() {
    /**
     * Aplicación JavaFX del simulador de caída libre.
     *
     * Se limita a configurar la ventana y a delegar la composición de servicios y
     * la navegación en [NavegadorPantallas] (SRP / DIP).
     */

    private val dimensiones = doubleArrayOf(1180.0, 780.0, 980.0, 660.0)

    override fun start(stage: Stage) {
        stage.title = "Simulador de Caída Libre"
        val contenedorRaiz = StackPane()
        contenedorRaiz.style = "-fx-background-color: #f8fafc;"
        val scene = Scene(contenedorRaiz, dimensiones[0], dimensiones[1])
        scene.stylesheets.add(javaClass.getResource("/estilos.css")?.toExternalForm())
        val navegador = NavegadorPantallas(contenedorRaiz)
        navegador.mostrarSeleccionModo()
        stage.scene = scene
        stage.minWidth = dimensiones[2]
        stage.minHeight = dimensiones[3]
        stage.isResizable = true
        stage.show()
    }
}
