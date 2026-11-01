package org.example

import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import kotlin.math.min

class RenderizadorCaidaJavaFx(
    private val catalogoEstilos: CatalogoEstilos
) : RenderizadorAnimacionCaida {
    /**
     * Implementación JavaFX del renderizador de la animación.
     *
     * Dibuja suelo, regla graduada, sombra, esfera, vector de velocidad y efecto
     * de impacto delegando el fondo y suelo en [EstiloEntorno] (OCP/DIP).
     */

    private val radioEsfera = 15.0
    private val margenSuperior = 35.0
    private val altoSuelo = 35.0
    private val xCentroEsfera = 145.0
    private val xRegla = 35.0

    override fun descripcionEntorno(fotograma: FotogramaCaida): String {
        return fotograma.descripcionEntorno()
    }

    override fun dibujar(gc: GraphicsContext, fotograma: FotogramaCaida) {
        val ancho = fotograma.ancho()
        val alto = fotograma.alto()
        val estilo = catalogoEstilos.para(fotograma.entorno())
        val ySuelo = alto - altoSuelo
        gc.clearRect(0.0, 0.0, ancho, alto)
        estilo.dibujarFondo(gc, ancho, alto)
        estilo.dibujarSuelo(gc, ancho, alto, ySuelo)
        dibujarReglaGraduada(gc, ySuelo, fotograma)
        val rangoCaida = ySuelo - margenSuperior - radioEsfera
        val fraccion = fotograma.fraccionAltura()
        val centroY = ySuelo - radioEsfera - (fraccion * rangoCaida)
        dibujarSombraYEsfera(gc, ySuelo, centroY, fraccion)
        if (fotograma.velocidadActual() > 0.05 && !fotograma.haImpactado()) {
            dibujarVectorVelocidad(gc, xCentroEsfera, centroY + radioEsfera, fotograma)
        }
        dibujarEtiquetaYImpacto(gc, centroY, ySuelo, fotograma)
    }

    private fun dibujarReglaGraduada(gc: GraphicsContext, ySuelo: Double, fotograma: FotogramaCaida) {
        val yTop = margenSuperior
        gc.stroke = Color.web("#475569")
        gc.lineWidth = 1.5
        gc.strokeLine(xRegla, yTop, xRegla, ySuelo)
        gc.fill = Color.web("#334155")
        val divisiones = 4
        for (i in 0..divisiones) {
            val f = i.toDouble() / divisiones
            val yPos = ySuelo - f * (ySuelo - yTop)
            val valorMetros = f * fotograma.alturaInicial()
            gc.strokeLine(xRegla - 4.0, yPos, xRegla + 4.0, yPos)
            gc.fillText("${fotograma.formatear(valorMetros)}m", 2.0, yPos + 3.0)
        }
    }

    private fun dibujarSombraYEsfera(gc: GraphicsContext, ySuelo: Double, centroY: Double, fraccion: Double) {
        val sombraAncho = 28.0 * (1.0 - 0.4 * fraccion)
        val sombraAlto = 8.0 * (1.0 - 0.4 * fraccion)
        val opacidad = (0.2 + 0.5 * (1.0 - fraccion)).coerceIn(0.1, 0.7)
        gc.fill = Color.rgb(15, 23, 42, opacidad)
        gc.fillOval(xCentroEsfera - sombraAncho / 2.0, ySuelo - sombraAlto / 2.0, sombraAncho, sombraAlto)
        gc.fill = Color.web("#ef4444")
        gc.fillOval(xCentroEsfera - radioEsfera, centroY - radioEsfera, radioEsfera * 2.0, radioEsfera * 2.0)
        gc.fill = Color.web("#fca5a5")
        gc.fillOval(xCentroEsfera - radioEsfera * 0.55, centroY - radioEsfera * 0.55, radioEsfera * 0.7, radioEsfera * 0.7)
        gc.stroke = Color.web("#7f1d1d")
        gc.lineWidth = 1.0
        gc.strokeOval(xCentroEsfera - radioEsfera, centroY - radioEsfera, radioEsfera * 2.0, radioEsfera * 2.0)
    }

    private fun dibujarVectorVelocidad(gc: GraphicsContext, x: Double, inicioY: Double, fotograma: FotogramaCaida) {
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

    private fun dibujarEtiquetaYImpacto(gc: GraphicsContext, centroY: Double, ySuelo: Double, fotograma: FotogramaCaida) {
        val etiquetaX = xCentroEsfera + radioEsfera + 10.0
        val etiquetaY = centroY - 10.0
        gc.fill = Color.rgb(15, 23, 42, 0.85)
        gc.fillRoundRect(etiquetaX, etiquetaY, 78.0, 20.0, 6.0, 6.0)
        gc.fill = Color.web("#ffffff")
        gc.fillText("y = ${fotograma.formatear(fotograma.alturaActual())} m", etiquetaX + 6.0, etiquetaY + 14.0)
        if (fotograma.haImpactado()) {
            gc.stroke = Color.rgb(34, 197, 94, 0.8)
            gc.lineWidth = 2.0
            gc.strokeOval(xCentroEsfera - 28.0, ySuelo - 6.0, 56.0, 12.0)
            gc.strokeOval(xCentroEsfera - 38.0, ySuelo - 9.0, 76.0, 18.0)
            val bannerX = xCentroEsfera - 50.0
            val bannerY = ySuelo - radioEsfera * 2.0 - 24.0
            gc.fill = Color.web("#15803d")
            gc.fillRoundRect(bannerX, bannerY, 100.0, 22.0, 8.0, 8.0)
            gc.fill = Color.web("#ffffff")
            gc.fillText("¡Impacto y = 0 m!", bannerX + 6.0, bannerY + 15.0)
        }
    }
}
