package org.example

import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import kotlin.math.min

class RenderizadorCaidaJavaFx(
    catalogoEstilos: CatalogoEstilos = CatalogoEstilosEntorno()
) : RenderizadorAnimacionCaida {
    /**
     * Implementación JavaFX del renderizador de la animación.
     *
     * Dibuja suelo, regla graduada, sombra, esfera, vector de velocidad y efecto
     * de impacto delegando el fondo y suelo en [EstiloEntorno] (OCP/DIP).
     */

    private val config = Pair(
        catalogoEstilos,
        doubleArrayOf(15.0, 35.0, 35.0, 145.0, 35.0)
    )

    override fun descripcionEntorno(fotograma: FotogramaCaida): String {
        return if (config.second.isNotEmpty()) fotograma.descripcionEntorno() else ""
    }

    override fun dibujar(gc: GraphicsContext, fotograma: FotogramaCaida) {
        val ancho = fotograma.ancho()
        val alto = fotograma.alto()
        val estilo = config.first.para(fotograma.entorno())
        val ySuelo = alto - config.second[2]
        gc.clearRect(0.0, 0.0, ancho, alto)
        estilo.dibujarFondo(gc, ancho, alto)
        estilo.dibujarSuelo(gc, ancho, alto, ySuelo)
        dibujarReglaGraduada(gc, ySuelo, fotograma)
        val fraccion = fotograma.fraccionAltura()
        val centroY = calcularCentroY(ySuelo, fraccion)
        dibujarSombraYEsfera(gc, ySuelo, fraccion)
        if (fotograma.velocidadActual() > 0.05 && !fotograma.haImpactado()) {
            dibujarVectorVelocidad(gc, centroY + config.second[0], fotograma)
        }
        dibujarEtiquetaAltura(gc, centroY, fotograma)
        if (fotograma.haImpactado()) {
            dibujarEfectoImpacto(gc, ySuelo)
        }
    }

    private fun calcularCentroY(ySuelo: Double, fraccion: Double): Double {
        val rangoCaida = ySuelo - config.second[1] - config.second[0]
        return ySuelo - config.second[0] - (fraccion * rangoCaida)
    }

    private fun dibujarReglaGraduada(gc: GraphicsContext, ySuelo: Double, fotograma: FotogramaCaida) {
        val yTop = config.second[1]
        val xRegla = config.second[4]
        gc.stroke = Color.web("#475569")
        gc.lineWidth = 1.5
        gc.strokeLine(xRegla, yTop, xRegla, ySuelo)
        gc.fill = Color.web("#334155")
        for (i in 0..4) {
            val f = i.toDouble() / 4.0
            val yPos = ySuelo - f * (ySuelo - yTop)
            gc.strokeLine(xRegla - 4.0, yPos, xRegla + 4.0, yPos)
            gc.fillText("${fotograma.formatear(f * fotograma.alturaInicial())}m", 2.0, yPos + 3.0)
        }
    }

    private fun dibujarSombraYEsfera(gc: GraphicsContext, ySuelo: Double, fraccion: Double) {
        val radio = config.second[0]
        val xCentro = config.second[3]
        val centroY = calcularCentroY(ySuelo, fraccion)
        val sombraAncho = 28.0 * (1.0 - 0.4 * fraccion)
        val sombraAlto = 8.0 * (1.0 - 0.4 * fraccion)
        gc.fill = Color.rgb(15, 23, 42, (0.2 + 0.5 * (1.0 - fraccion)).coerceIn(0.1, 0.7))
        gc.fillOval(xCentro - sombraAncho / 2.0, ySuelo - sombraAlto / 2.0, sombraAncho, sombraAlto)
        gc.fill = Color.web("#ef4444")
        gc.fillOval(xCentro - radio, centroY - radio, radio * 2.0, radio * 2.0)
        gc.fill = Color.web("#fca5a5")
        gc.fillOval(xCentro - radio * 0.55, centroY - radio * 0.55, radio * 0.7, radio * 0.7)
        gc.stroke = Color.web("#7f1d1d")
        gc.lineWidth = 1.0
        gc.strokeOval(xCentro - radio, centroY - radio, radio * 2.0, radio * 2.0)
    }

    private fun dibujarVectorVelocidad(gc: GraphicsContext, inicioY: Double, fotograma: FotogramaCaida) {
        val x = config.second[3]
        val velocidad = fotograma.velocidadActual()
        val finY = inicioY + min(60.0, velocidad * 1.4)
        gc.stroke = Color.web("#eab308")
        gc.lineWidth = 2.5
        gc.strokeLine(x, inicioY, x, finY)
        gc.fill = Color.web("#ca8a04")
        gc.fillPolygon(doubleArrayOf(x, x - 5.0, x + 5.0), doubleArrayOf(finY + 5.0, finY - 4.0, finY - 4.0), 3)
        gc.fill = Color.web("#854d0e")
        gc.fillText("v = ${fotograma.formatear(velocidad)} m/s", x + 10.0, finY)
    }

    private fun dibujarEtiquetaAltura(gc: GraphicsContext, centroY: Double, fotograma: FotogramaCaida) {
        val etiquetaX = config.second[3] + config.second[0] + 10.0
        val etiquetaY = centroY - 10.0
        gc.fill = Color.rgb(15, 23, 42, 0.85)
        gc.fillRoundRect(etiquetaX, etiquetaY, 78.0, 20.0, 6.0, 6.0)
        gc.fill = Color.web("#ffffff")
        gc.fillText("y = ${fotograma.formatear(fotograma.alturaActual())} m", etiquetaX + 6.0, etiquetaY + 14.0)
    }

    private fun dibujarEfectoImpacto(gc: GraphicsContext, ySuelo: Double) {
        val xCentro = config.second[3]
        val radio = config.second[0]
        gc.stroke = Color.rgb(34, 197, 94, 0.8)
        gc.lineWidth = 2.0
        gc.strokeOval(xCentro - 28.0, ySuelo - 6.0, 56.0, 12.0)
        gc.strokeOval(xCentro - 38.0, ySuelo - 9.0, 76.0, 18.0)
        val bannerX = xCentro - 50.0
        val bannerY = ySuelo - radio * 2.0 - 24.0
        gc.fill = Color.web("#15803d")
        gc.fillRoundRect(bannerX, bannerY, 100.0, 22.0, 8.0, 8.0)
        gc.fill = Color.web("#ffffff")
        gc.fillText("¡Impacto y = 0 m!", bannerX + 6.0, bannerY + 15.0)
    }
}
