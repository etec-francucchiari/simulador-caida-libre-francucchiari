package org.example

import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop

/** Estilo visual neutro para entornos personalizados. */
class EstiloNeutro : EstiloEntorno {

    override fun dibujarFondo(gc: GraphicsContext, ancho: Double, alto: Double) {
        val neutro = LinearGradient(
            0.0, 0.0, 0.0, 1.0, true, CycleMethod.NO_CYCLE,
            Stop(0.0, Color.web("#1e293b")),
            Stop(1.0, Color.web("#475569"))
        )
        gc.fill = neutro
        gc.fillRect(0.0, 0.0, ancho, alto)
    }

    override fun dibujarSuelo(gc: GraphicsContext, ancho: Double, alto: Double, ySuelo: Double) {
        gc.fill = Color.web("#334155")
        gc.fillRect(0.0, ySuelo, ancho, alto - ySuelo)
        gc.fill = Color.web("#64748b")
        gc.fillRect(0.0, ySuelo, ancho, 4.0)
    }
}
