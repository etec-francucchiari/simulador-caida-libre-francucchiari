package org.example

import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.*

class EstiloLuna : EstiloEntorno {
    /**
     * Implementación de [EstiloEntorno] correspondiente a la Luna (`g = 1.62 m/s²`).
     *
     * Representa el cielo oscuro sin atmósfera del espacio exterior con un campo
     * de estrellas brillantes, la Tierra visible en la lejanía y el regolito
     * lunar grisáceo con cráteres en la superficie.
     */

    /**
     * Pinta el fondo espacial oscuro, las estrellas puntuales y el disco azul
     * del planeta Tierra visto desde la superficie lunar.
     */
    override fun dibujarFondo(gc: GraphicsContext, ancho: Double, alto: Double) {
        val espacio = LinearGradient(
            0.0, 0.0, 0.0, 1.0, true, CycleMethod.NO_CYCLE,
            Stop(0.0, Color.web("#090d16")),
            Stop(1.0, Color.web("#1e293b"))
        )
        gc.fill = espacio
        gc.fillRect(0.0, 0.0, ancho, alto)
        gc.fill = Color.rgb(255, 255, 255, 0.8)
        val estrellasX = doubleArrayOf(20.0, 60.0, 110.0, 170.0, 220.0, 240.0, 80.0, 190.0, 130.0)
        val estrellasY = doubleArrayOf(25.0, 65.0, 30.0, 45.0, 75.0, 20.0, 100.0, 110.0, 70.0)
        for (i in estrellasX.indices) {
            gc.fillOval(estrellasX[i], estrellasY[i], 1.8, 1.8)
        }
        gc.fill = Color.web("#38bdf8")
        gc.fillOval(ancho - 45.0, 25.0, 22.0, 22.0)
    }

    /**
     * Pinta la superficie de regolito gris y dos cráteres elípticos debajo de
     * la cota [ySuelo].
     */
    override fun dibujarSuelo(gc: GraphicsContext, ancho: Double, alto: Double, ySuelo: Double) {
        gc.fill = Color.web("#475569")
        gc.fillRect(0.0, ySuelo, ancho, alto - ySuelo)
        gc.fill = Color.web("#64748b")
        gc.fillRect(0.0, ySuelo, ancho, 3.0)
        gc.fill = Color.web("#334155")
        gc.fillOval(50.0, ySuelo + 10.0, 30.0, 8.0)
        gc.fillOval(170.0, ySuelo + 14.0, 24.0, 6.0)
    }
}
