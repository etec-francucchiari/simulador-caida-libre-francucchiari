package org.example

import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop

/** Estilo visual del entorno marciano. */
class EstiloMarte : EstiloEntorno {

    override fun dibujarFondo(gc: GraphicsContext, ancho: Double, alto: Double) {
        val marte = LinearGradient(
            0.0, 0.0, 0.0, 1.0, true, CycleMethod.NO_CYCLE,
            Stop(0.0, Color.web("#450a0a")),
            Stop(0.4, Color.web("#b45309")),
            Stop(1.0, Color.web("#f97316"))
        )
        gc.fill = marte
        gc.fillRect(0.0, 0.0, ancho, alto)

        gc.fill = Color.rgb(251, 146, 60, 0.25)
        gc.fillOval(20.0, alto * 0.45, 90.0, 24.0)
        gc.fillOval(ancho - 110.0, alto * 0.60, 120.0, 30.0)
    }

    override fun dibujarSuelo(gc: GraphicsContext, ancho: Double, alto: Double, ySuelo: Double) {
        gc.fill = Color.web("#7c2d12")
        gc.fillRect(0.0, ySuelo, ancho, alto - ySuelo)
        gc.fill = Color.web("#ea580c")
        gc.fillRect(0.0, ySuelo, ancho, 4.0)

        gc.fill = Color.web("#431407")
        gc.fillOval(60.0, ySuelo + 12.0, 26.0, 7.0)
        gc.fillOval(180.0, ySuelo + 16.0, 20.0, 6.0)
    }
}
