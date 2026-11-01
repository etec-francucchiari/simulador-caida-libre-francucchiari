package org.example

import javafx.scene.canvas.Canvas
import javafx.scene.control.Label
import javafx.scene.layout.VBox

class PanelAnimacionCaida(
    private val estado: EstadoSimulacion,
    private val renderizador: RenderizadorAnimacionCaida
) : VBox(6.0) {
    /**
     * Componente visual de animación en tiempo real de la caída libre.
     *
     * Responsabilidad Única (SRP): mantiene el lienzo, el subtítulo y las
     * suscripciones reactivas; delega la construcción del fotograma y su
     * dibujo en el [RenderizadorAnimacionCaida] inyectado por constructor (DIP).
     */

    private val canvas = Canvas(260.0, 370.0)
    private val etiquetaSubtitulo = Label()

    init {
        inicializar()
    }

    private fun inicializar() {
        style = "-fx-alignment: top-center; -fx-padding: 10; -fx-background-color: #ffffff; " +
            "-fx-background-radius: 8; -fx-border-color: #d5dbe0; -fx-border-radius: 8; -fx-border-width: 1;"
        val etiquetaTitulo = Label("Animación en Tiempo Real")
        etiquetaTitulo.style = "-fx-text-fill: #1e293b; -fx-font-size: 15px; -fx-font-weight: bold;"
        etiquetaSubtitulo.style = "-fx-text-fill: #64748b; -fx-font-size: 12px;"
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
        val fotograma = renderizador.construirFotograma(estado, canvas.width, canvas.height)
        etiquetaSubtitulo.text = renderizador.descripcionEntorno(fotograma)
        renderizador.dibujar(canvas.graphicsContext2D, fotograma)
    }
}
