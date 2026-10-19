package org.example

import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop

/** Estilo visual del entorno joviano. */
class EstiloJupiter : EstiloEntorno {

    override fun dibujarFondo(gc: GraphicsContext, ancho: Double, alto: Double) {
        val jupiter = LinearGradient(
            0.0, 0.0, 0.0, 1.0, true, CycleMethod.NO_CYCLE,
            Stop(0.0, Color.web("#451a03")),
            Stop(0.5, Color.web("#9a3412")),
            Stop(1.0, Color.web("#ea580c"))
        )
        gc.fill = jupiter
        gc.fillRect(0.0, 0.0, ancho, alto)

        gc.fill = Color.rgb(180, 83, 9, 0.35)
        gc.fillRect(0.0, 50.0, ancho, 22.0)
        gc.fillRect(0.0, 120.0, ancho, 30.0)
        gc.fillRect(0.0, 210.0, ancho, 25.0)
    }

    override fun dibujarSuelo(gc: GraphicsContext, ancho: Double, alto: Double, ySuelo: Double) {
        gc.fill = Color.web("#431407")
        gc.fillRect(0.0, ySuelo, ancho, alto - ySuelo)
        gc.fill = Color.web("#c2410c")
        gc.fillRect(0.0, ySuelo, ancho, 4.0)
    }
}
