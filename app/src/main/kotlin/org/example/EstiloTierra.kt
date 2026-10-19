package org.example

import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop

/** Estilo visual del entorno terrestre. */
class EstiloTierra : EstiloEntorno {

    override fun dibujarFondo(gc: GraphicsContext, ancho: Double, alto: Double) {
        val cielo = LinearGradient(
            0.0, 0.0, 0.0, 1.0, true, CycleMethod.NO_CYCLE,
            Stop(0.0, Color.web("#e0f2fe")),
            Stop(1.0, Color.web("#bfdbfe"))
        )
        gc.fill = cielo
        gc.fillRect(0.0, 0.0, ancho, alto)

        gc.fill = Color.rgb(255, 255, 255, 0.45)
        gc.fillOval(ancho - 70.0, 20.0, 50.0, 20.0)
        gc.fillOval(ancho - 85.0, 25.0, 45.0, 18.0)
    }

    override fun dibujarSuelo(gc: GraphicsContext, ancho: Double, alto: Double, ySuelo: Double) {
        gc.fill = Color.web("#15803d")
        gc.fillRect(0.0, ySuelo, ancho, alto - ySuelo)
        gc.fill = Color.web("#16a34a")
        gc.fillRect(0.0, ySuelo, ancho, 4.0)
    }
}
