package org.example

import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.RadialGradient
import javafx.scene.paint.Stop
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import java.util.Locale
import kotlin.math.min

/**
 * Implementación JavaFX del renderizador de la animación.
 *
 * Dibuja suelo, regla graduada, sombra, esfera, vector de velocidad y efecto
 * de impacto. El fondo y el suelo de cada entorno se delegan en un
 * [EstiloEntorno] resuelto por [CatalogoEstilosEntorno], sin comprobaciones de
 * tipo (OCP).
 */
class RenderizadorCaidaJavaFx(
    private val catalogoEstilos: CatalogoEstilos
) : RenderizadorAnimacionCaida {

    private val radioEsfera = 15.0
    private val margenSuperior = 35.0
    private val altoSuelo = 35.0
    private val xCentroEsfera = 145.0
    private val xRegla = 35.0

    override fun descripcionEntorno(fotograma: FotogramaCaida): String {
        val entorno = fotograma.entorno()
        return "${entorno.nombre()} (g = ${formatear(entorno.gravedad())} m/s²)"
    }

    override fun dibujar(gc: GraphicsContext, fotograma: FotogramaCaida) {
        val ancho = fotograma.ancho()
        val alto = fotograma.alto()
        val estilo = catalogoEstilos.para(fotograma.entorno())
        val ySuelo = alto - altoSuelo

        gc.clearRect(0.0, 0.0, ancho, alto)
        estilo.dibujarFondo(gc, ancho, alto)
        estilo.dibujarSuelo(gc, ancho, alto, ySuelo)

        dibujarReglaGraduada(gc, ySuelo, fotograma.alturaInicial())

        val rangoCaida = ySuelo - margenSuperior - radioEsfera
        val fraccion = fotograma.fraccionAltura()
        val centroY = ySuelo - radioEsfera - (fraccion * rangoCaida)

        dibujarSombra(gc, ySuelo, fraccion)
        dibujarEsfera(gc, xCentroEsfera, centroY, radioEsfera)

        if (fotograma.velocidadActual() > 0.05 && !fotograma.haImpactado()) {
            dibujarVectorVelocidad(
                gc,
                xCentroEsfera,
                centroY,
                radioEsfera,
                fotograma.velocidadActual()
            )
        }

        dibujarEtiquetaAltura(gc, xCentroEsfera, centroY, fotograma.alturaActual())

        if (fotograma.haImpactado()) {
            dibujarEfectoImpacto(gc, xCentroEsfera, ySuelo)
        }
    }

    private fun dibujarReglaGraduada(gc: GraphicsContext, ySuelo: Double, alturaInicial: Double) {
        val yTop = margenSuperior

        gc.stroke = Color.web("#475569")
        gc.lineWidth = 1.5
        gc.strokeLine(xRegla, yTop, xRegla, ySuelo)

        val divisiones = 4
        gc.font = Font.font("System", FontWeight.BOLD, 10.0)
        gc.fill = Color.web("#334155")

        for (i in 0..divisiones) {
            val f = i.toDouble() / divisiones
            val yPos = ySuelo - f * (ySuelo - yTop)
            val valorMetros = f * alturaInicial

            gc.strokeLine(xRegla - 4.0, yPos, xRegla + 4.0, yPos)
            gc.fillText("${formatear(valorMetros)}m", 2.0, yPos + 3.0)
        }
    }

    private fun dibujarSombra(gc: GraphicsContext, ySuelo: Double, fraccion: Double) {
        val sombraAncho = 28.0 * (1.0 - 0.4 * fraccion)
        val sombraAlto = 8.0 * (1.0 - 0.4 * fraccion)
        val opacidad = (0.2 + 0.5 * (1.0 - fraccion)).coerceIn(0.1, 0.7)

        gc.fill = Color.rgb(15, 23, 42, opacidad)
        gc.fillOval(
            xCentroEsfera - sombraAncho / 2.0,
            ySuelo - sombraAlto / 2.0,
            sombraAncho,
            sombraAlto
        )
    }

    private fun dibujarEsfera(gc: GraphicsContext, x: Double, y: Double, radio: Double) {
        val gradienteEsfera = RadialGradient(
            0.0, 0.0,
            x - radio * 0.35, y - radio * 0.35,
            radio * 1.3,
            false,
            CycleMethod.NO_CYCLE,
            Stop(0.0, Color.web("#fca5a5")),
            Stop(0.4, Color.web("#ef4444")),
            Stop(1.0, Color.web("#991b1b"))
        )

        gc.fill = gradienteEsfera
        gc.fillOval(x - radio, y - radio, radio * 2.0, radio * 2.0)

        gc.stroke = Color.web("#7f1d1d")
        gc.lineWidth = 1.0
        gc.strokeOval(x - radio, y - radio, radio * 2.0, radio * 2.0)
    }

    private fun dibujarVectorVelocidad(
        gc: GraphicsContext,
        x: Double,
        y: Double,
        radio: Double,
        velocidad: Double
    ) {
        val longitud = min(60.0, velocidad * 1.4)
        val inicioY = y + radio
        val finY = inicioY + longitud

        gc.stroke = Color.web("#eab308")
        gc.lineWidth = 2.5
        gc.strokeLine(x, inicioY, x, finY)

        gc.fill = Color.web("#ca8a04")
        gc.fillPolygon(
            doubleArrayOf(x, x - 5.0, x + 5.0),
            doubleArrayOf(finY + 5.0, finY - 4.0, finY - 4.0),
            3
        )

        gc.font = Font.font("System", FontWeight.BOLD, 10.0)
        gc.fill = Color.web("#854d0e")
        gc.fillText("v = ${formatear(velocidad)} m/s", x + 10.0, finY)
    }

    private fun dibujarEtiquetaAltura(gc: GraphicsContext, x: Double, y: Double, altura: Double) {
        val texto = "y = ${formatear(altura)} m"
        val etiquetaX = x + radioEsfera + 10.0
        val etiquetaY = y - 10.0

        gc.fill = Color.rgb(15, 23, 42, 0.85)
        gc.fillRoundRect(etiquetaX, etiquetaY, 78.0, 20.0, 6.0, 6.0)

        gc.fill = Color.WHITE
        gc.font = Font.font("System", FontWeight.BOLD, 10.5)
        gc.fillText(texto, etiquetaX + 6.0, etiquetaY + 14.0)
    }

    private fun dibujarEfectoImpacto(gc: GraphicsContext, x: Double, ySuelo: Double) {
        gc.stroke = Color.rgb(34, 197, 94, 0.8)
        gc.lineWidth = 2.0
        gc.strokeOval(x - 28.0, ySuelo - 6.0, 56.0, 12.0)
        gc.strokeOval(x - 38.0, ySuelo - 9.0, 76.0, 18.0)

        val bannerW = 100.0
        val bannerH = 22.0
        val bannerX = x - bannerW / 2.0
        val bannerY = ySuelo - radioEsfera * 2.0 - 24.0

        gc.fill = Color.web("#15803d")
        gc.fillRoundRect(bannerX, bannerY, bannerW, bannerH, 8.0, 8.0)

        gc.fill = Color.WHITE
        gc.font = Font.font("System", FontWeight.BOLD, 11.0)
        gc.fillText("¡Impacto y = 0 m!", bannerX + 6.0, bannerY + 15.0)
    }

    private fun formatear(valor: Double): String =
        String.format(Locale.ROOT, "%.2f", valor)
}
