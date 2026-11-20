package org.example

import javafx.scene.canvas.Canvas
import javafx.scene.control.Label
import javafx.scene.layout.VBox

class PanelAnimacionCaida(
    estado: EstadoSimulacion,
    renderizador: RenderizadorAnimacionCaida = RenderizadorCaidaJavaFx()
) : VBox(6.0) {
    /**
     * Componente visual de animación en tiempo real de la caída libre.
     *
     * Responsabilidad Única (SRP): mantiene el lienzo, el subtítulo y las
     * suscripciones reactivas; delega la construcción del fotograma y su
     * dibujo en el [RenderizadorAnimacionCaida] inyectado por constructor (DIP).
     */

    private val vista = Pair(
        Pair(estado, renderizador),
        Pair(Canvas(260.0, 370.0), Label())
    )

    init {
        inicializar()
    }

    private fun inicializar() {
        style = "-fx-alignment: top-center; -fx-padding: 10; -fx-background-color: #ffffff; " +
            "-fx-background-radius: 8; -fx-border-color: #d5dbe0; -fx-border-radius: 8; -fx-border-width: 1;"
        val etiquetaTitulo = Label("Animación en Tiempo Real")
        etiquetaTitulo.style = "-fx-text-fill: #1e293b; -fx-font-size: 15px; -fx-font-weight: bold;"
        vista.second.second.style = "-fx-text-fill: #64748b; -fx-font-size: 12px;"
        children.addAll(etiquetaTitulo, vista.second.second, vista.second.first)
        val est = vista.first.first
        est.tiempoActualObservable().addListener { _, _, _ -> redibujar() }
        est.alturaActualObservable().addListener { _, _, _ -> redibujar() }
        est.alturaInicialObservable().addListener { _, _, _ -> redibujar() }
        est.velocidadActualObservable().addListener { _, _, _ -> redibujar() }
        est.planetaObservable().addListener { _, _, _ -> redibujar() }
        redibujar()
    }

    /** Reconstruye el fotograma y delega su dibujo en el renderizador. */
    fun redibujar() {
        val canvas = vista.second.first
        val rend = vista.first.second
        val fotograma = rend.construirFotograma(vista.first.first, canvas.width, canvas.height)
        vista.second.second.text = rend.descripcionEntorno(fotograma)
        rend.dibujar(canvas.graphicsContext2D, fotograma)
    }
}
