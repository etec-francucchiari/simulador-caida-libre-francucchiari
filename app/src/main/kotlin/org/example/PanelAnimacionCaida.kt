package org.example

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.canvas.Canvas
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight

/**
 * Componente visual de animación en tiempo real de la caída libre.
 *
 * Responsabilidad Única (SRP): mantiene el lienzo, el subtítulo y las
 * suscripciones reactivas; delega todo el dibujo en un
 * [RenderizadorAnimacionCaida] inyectado por constructor (DIP).
 */
class PanelAnimacionCaida(
    private val estado: EstadoSimulacion,
    private val renderizador: RenderizadorAnimacionCaida
) : VBox(6.0) {

    private val anchoCanvas = 260.0
    private val altoCanvas = 370.0

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

        estado.tiempoActualObservable().addListener { _, _, _ -> redibujar() }
        estado.alturaActualObservable().addListener { _, _, _ -> redibujar() }
        estado.alturaInicialObservable().addListener { _, _, _ -> redibujar() }
        estado.velocidadActualObservable().addListener { _, _, _ -> redibujar() }
        estado.planetaObservable().addListener { _, _, _ -> redibujar() }

        redibujar()
    }

    /** Reconstruye el fotograma y delega su dibujo en el renderizador. */
    fun redibujar() {
        val alturaActual = estado.obtenerAlturaActual()
        val fotograma = FotogramaCaida(
            anchoInterno = canvas.width,
            altoInterno = canvas.height,
            entornoInterno = estado.obtenerPlaneta(),
            alturaInicialInterna = estado.obtenerAlturaInicial(),
            alturaActualInterna = alturaActual,
            velocidadActualInterna = estado.obtenerVelocidadActual(),
            impactadoInterno = estado.haLlegadoAlSuelo() || alturaActual <= 0.0
        )

        etiquetaSubtitulo.text = renderizador.descripcionEntorno(fotograma)
        renderizador.dibujar(canvas.graphicsContext2D, fotograma)
    }
}
