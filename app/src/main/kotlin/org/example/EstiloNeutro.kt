package org.example

import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.*

class EstiloNeutro : EstiloEntorno {
    /**
     * Implementación de [EstiloEntorno] con estética neutra en tonos pizarra,
     * utilizada para representar entornos gravitatorios personalizados cuya
     * gravedad ha sido definida libremente por el usuario.
     */

    /**
     * Dibuja un cielo con gradiente lineal vertical en tonos azul pizarra oscuro
     * sobre toda el área del lienzo (`[ancho] × [alto]`).
     */
    override fun dibujarFondo(gc: GraphicsContext, ancho: Double, alto: Double) {
        val neutro = LinearGradient(
            0.0, 0.0, 0.0, 1.0, true, CycleMethod.NO_CYCLE,
            Stop(0.0, Color.web("#1e293b")),
            Stop(1.0, Color.web("#475569"))
        )
        gc.fill = neutro
        gc.fillRect(0.0, 0.0, ancho, alto)
    }

    /**
     * Dibuja la franja inferior del suelo y su borde superior resaltado en la
     * coordenada vertical [ySuelo].
     */
    override fun dibujarSuelo(gc: GraphicsContext, ancho: Double, alto: Double, ySuelo: Double) {
        gc.fill = Color.web("#334155")
        gc.fillRect(0.0, ySuelo, ancho, alto - ySuelo)
        gc.fill = Color.web("#64748b")
        gc.fillRect(0.0, ySuelo, ancho, 4.0)
    }
}
