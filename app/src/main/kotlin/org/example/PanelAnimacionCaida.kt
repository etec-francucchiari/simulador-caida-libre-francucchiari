package org.example

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.RadialGradient
import javafx.scene.paint.Stop
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import java.util.Locale
import kotlin.math.min

/**
 * Componente visual de animación en tiempo real de la caída libre.
 *
 * Responsabilidad Única (SRP):
 * - Renderiza en un [Canvas] de JavaFX la representación física continua del objeto
 *   cayendo hacia el suelo en el planeta seleccionado.
 * - Muestra la escala métrica graduada de altura, el vector velocidad descendente,
 *   la sombra proyectada en el suelo y el efecto de impacto al tocar la superficie.
 * - Sincronizado reactivamente con [GestorEstadoSimulacion] y conducido a 60 fps
 *   por el bucle de animación de [ControladorSimulacion].
 */
class PanelAnimacionCaida(
    private val estado: EstadoSimulacion
) : VBox(6.0) {

    // Dimensiones y constantes de dibujo, encapsuladas como campos de instancia.
    private val anchoCanvas = 260.0
    private val altoCanvas = 370.0
    private val radioEsfera = 15.0
    private val margenSuperior = 35.0
    private val altoSuelo = 35.0
    private val xCentroEsfera = 145.0
    private val xRegla = 35.0

    private val canvas = Canvas(anchoCanvas, altoCanvas)
    private val etiquetaSubtitulo = Label()

    init {
        alignment = Pos.TOP_CENTER
        padding = Insets(10.0)
        style = "-fx-background-color: #ffffff; " +
                "-fx-background-radius: 8; " +
                "-fx-border-color: #d5dbe0; " +
                "-fx-border-radius: 8; " +
                "-fx-border-width: 1;"

        val etiquetaTitulo = Label("Animación en Tiempo Real")
        etiquetaTitulo.font = Font.font("System", FontWeight.BOLD, 15.0)
        etiquetaTitulo.style = "-fx-text-fill: #1e293b;"

        etiquetaSubtitulo.font = Font.font("System", 12.0)
        etiquetaSubtitulo.style = "-fx-text-fill: #64748b;"

        children.addAll(etiquetaTitulo, etiquetaSubtitulo, canvas)

        // Suscripciones reactivas para redibujado continuo
        estado.tiempoActualObservable().addListener { _, _, _ -> redibujar() }
        estado.alturaActualObservable().addListener { _, _, _ -> redibujar() }
        estado.alturaInicialObservable().addListener { _, _, _ -> redibujar() }
        estado.velocidadActualObservable().addListener { _, _, _ -> redibujar() }
        estado.planetaObservable().addListener { _, _, _ -> redibujar() }

        redibujar()
    }

    /**
     * Dibuja todos los elementos gráficos en el Canvas según el estado físico actual.
     */
    fun redibujar() {
        val gc = canvas.graphicsContext2D
        val w = canvas.width
        val h = canvas.height
        val planeta = estado.obtenerPlaneta()
        val h0 = estado.obtenerAlturaInicial()
        val yActual = estado.obtenerAlturaActual()
        val vActual = estado.obtenerVelocidadActual()
        val haImpactado = estado.haLlegadoAlSuelo() || yActual <= 0.0

        etiquetaSubtitulo.text =
            "${planeta.nombre()} (g = ${formatear(planeta.gravedad())} m/s²)"

        // 1. Limpiar fondo
        gc.clearRect(0.0, 0.0, w, h)

        // 2. Fondo ambiental según el planeta
        dibujarFondoPlaneta(gc, w, h, planeta)

        // 3. Suelo de la superficie
        val ySuelo = h - altoSuelo
        dibujarSuelo(gc, w, h, ySuelo, planeta)

        // 4. Regla graduada vertical con marcas de altura
        dibujarReglaGraduada(gc, ySuelo, h0)

        // 5. Cálculo de la posición Y del objeto
        val rangoCaida = ySuelo - margenSuperior - radioEsfera
        val fraccion = if (h0 > 0.0) (yActual / h0).coerceIn(0.0, 1.0) else 0.0
        val centroY = ySuelo - radioEsfera - (fraccion * rangoCaida)

        // 6. Sombra proyectada en el suelo
        dibujarSombra(gc, ySuelo, fraccion)

        // 7. Esfera con gradiente 3D
        dibujarEsfera(gc, xCentroEsfera, centroY, radioEsfera)

        // 8. Vector de velocidad hacia abajo
        if (vActual > 0.05 && !haImpactado) {
            dibujarVectorVelocidad(gc, xCentroEsfera, centroY, radioEsfera, vActual)
        }

        // 9. Etiqueta flotante con la altura actual
        dibujarEtiquetaAltura(gc, xCentroEsfera, centroY, yActual)

        // 10. Marcador de impacto en el suelo
        if (haImpactado) {
            dibujarEfectoImpacto(gc, xCentroEsfera, ySuelo)
        }
    }

    private fun dibujarFondoPlaneta(gc: GraphicsContext, w: Double, h: Double, planeta: EntornoGravitatorio) {
        when (planeta) {
            Planeta.TIERRA -> {
                val cieloGradiente = LinearGradient(
                    0.0, 0.0, 0.0, 1.0, true, CycleMethod.NO_CYCLE,
                    Stop(0.0, Color.web("#e0f2fe")),
                    Stop(1.0, Color.web("#bfdbfe"))
                )
                gc.fill = cieloGradiente
                gc.fillRect(0.0, 0.0, w, h)

                // Nube decorativa tenue
                gc.fill = Color.rgb(255, 255, 255, 0.45)
                gc.fillOval(w - 70.0, 20.0, 50.0, 20.0)
                gc.fillOval(w - 85.0, 25.0, 45.0, 18.0)
            }
            Planeta.LUNA -> {
                val espacioGradiente = LinearGradient(
                    0.0, 0.0, 0.0, 1.0, true, CycleMethod.NO_CYCLE,
                    Stop(0.0, Color.web("#090d16")),
                    Stop(1.0, Color.web("#1e293b"))
                )
                gc.fill = espacioGradiente
                gc.fillRect(0.0, 0.0, w, h)

                // Estrellas estáticas en el espacio
                gc.fill = Color.rgb(255, 255, 255, 0.8)
                val estrellasX = doubleArrayOf(20.0, 60.0, 110.0, 170.0, 220.0, 240.0, 80.0, 190.0, 130.0)
                val estrellasY = doubleArrayOf(25.0, 65.0, 30.0, 45.0, 75.0, 20.0, 100.0, 110.0, 70.0)
                for (i in estrellasX.indices) {
                    gc.fillOval(estrellasX[i], estrellasY[i], 1.8, 1.8)
                }

                // Tierra a lo lejos
                gc.fill = Color.web("#38bdf8")
                gc.fillOval(w - 45.0, 25.0, 22.0, 22.0)
            }
            Planeta.MARTE -> {
                val marteGradiente = LinearGradient(
                    0.0, 0.0, 0.0, 1.0, true, CycleMethod.NO_CYCLE,
                    Stop(0.0, Color.web("#450a0a")),
                    Stop(0.4, Color.web("#b45309")),
                    Stop(1.0, Color.web("#f97316"))
                )
                gc.fill = marteGradiente
                gc.fillRect(0.0, 0.0, w, h)

                // Dunas y polvo marciano
                gc.fill = Color.rgb(251, 146, 60, 0.25)
                gc.fillOval(20.0, h * 0.45, 90.0, 24.0)
                gc.fillOval(w - 110.0, h * 0.60, 120.0, 30.0)
            }
            Planeta.JUPITER -> {
                val jupiterGradiente = LinearGradient(
                    0.0, 0.0, 0.0, 1.0, true, CycleMethod.NO_CYCLE,
                    Stop(0.0, Color.web("#451a03")),
                    Stop(0.5, Color.web("#9a3412")),
                    Stop(1.0, Color.web("#ea580c"))
                )
                gc.fill = jupiterGradiente
                gc.fillRect(0.0, 0.0, w, h)

                // Bandas atmosféricas
                gc.fill = Color.rgb(180, 83, 9, 0.35)
                gc.fillRect(0.0, 50.0, w, 22.0)
                gc.fillRect(0.0, 120.0, w, 30.0)
                gc.fillRect(0.0, 210.0, w, 25.0)
            }
            else -> {
                // Entorno personalizado: cielo neutro
                val neutroGradiente = LinearGradient(
                    0.0, 0.0, 0.0, 1.0, true, CycleMethod.NO_CYCLE,
                    Stop(0.0, Color.web("#1e293b")),
                    Stop(1.0, Color.web("#475569"))
                )
                gc.fill = neutroGradiente
                gc.fillRect(0.0, 0.0, w, h)
            }
        }
    }

    private fun dibujarSuelo(
        gc: GraphicsContext,
        w: Double,
        h: Double,
        ySuelo: Double,
        planeta: EntornoGravitatorio
    ) {
        when (planeta) {
            Planeta.TIERRA -> {
                gc.fill = Color.web("#15803d")
                gc.fillRect(0.0, ySuelo, w, h - ySuelo)
                gc.fill = Color.web("#16a34a")
                gc.fillRect(0.0, ySuelo, w, 4.0)
            }
            Planeta.LUNA -> {
                gc.fill = Color.web("#475569")
                gc.fillRect(0.0, ySuelo, w, h - ySuelo)
                gc.fill = Color.web("#64748b")
                gc.fillRect(0.0, ySuelo, w, 3.0)

                // Cráteres decorativos
                gc.fill = Color.web("#334155")
                gc.fillOval(50.0, ySuelo + 10.0, 30.0, 8.0)
                gc.fillOval(170.0, ySuelo + 14.0, 24.0, 6.0)
            }
            Planeta.MARTE -> {
                gc.fill = Color.web("#7c2d12")
                gc.fillRect(0.0, ySuelo, w, h - ySuelo)
                gc.fill = Color.web("#ea580c")
                gc.fillRect(0.0, ySuelo, w, 4.0)

                // Rocas marcianas decorativas
                gc.fill = Color.web("#431407")
                gc.fillOval(60.0, ySuelo + 12.0, 26.0, 7.0)
                gc.fillOval(180.0, ySuelo + 16.0, 20.0, 6.0)
            }
            Planeta.JUPITER -> {
                gc.fill = Color.web("#431407")
                gc.fillRect(0.0, ySuelo, w, h - ySuelo)
                gc.fill = Color.web("#c2410c")
                gc.fillRect(0.0, ySuelo, w, 4.0)
            }
            else -> {
                gc.fill = Color.web("#334155")
                gc.fillRect(0.0, ySuelo, w, h - ySuelo)
                gc.fill = Color.web("#64748b")
                gc.fillRect(0.0, ySuelo, w, 4.0)
            }
        }
    }

    private fun dibujarReglaGraduada(gc: GraphicsContext, ySuelo: Double, h0: Double) {
        val yTop = margenSuperior

        gc.stroke = Color.web("#475569")
        gc.lineWidth = 1.5
        gc.strokeLine(xRegla, yTop, xRegla, ySuelo)

        // 5 marcas graduadas (0%, 25%, 50%, 75%, 100%)
        val divisiones = 4
        gc.font = Font.font("System", FontWeight.BOLD, 10.0)
        gc.fill = Color.web("#334155")

        for (i in 0..divisiones) {
            val f = i.toDouble() / divisiones
            val yPos = ySuelo - f * (ySuelo - yTop)
            val valorMetros = f * h0

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

        // Punta de flecha hacia abajo
        gc.fill = Color.web("#ca8a04")
        gc.fillPolygon(
            doubleArrayOf(x, x - 5.0, x + 5.0),
            doubleArrayOf(finY + 5.0, finY - 4.0, finY - 4.0),
            3
        )

        // Etiqueta de velocidad junto a la flecha
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
        // Ondas de choque en el suelo
        gc.stroke = Color.rgb(34, 197, 94, 0.8)
        gc.lineWidth = 2.0
        gc.strokeOval(x - 28.0, ySuelo - 6.0, 56.0, 12.0)
        gc.strokeOval(x - 38.0, ySuelo - 9.0, 76.0, 18.0)

        // Cartel flotante de impacto
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
