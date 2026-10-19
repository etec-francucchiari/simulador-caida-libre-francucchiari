package org.example

import javafx.animation.AnimationTimer

/**
 * Temporizador de fotogramas que delega cada pulso en una acción inyectada.
 *
 * Se separa del controlador para mantener una sola clase por archivo y evitar
 * clases internas anónimas.
 */
class TemporizadorAnimacion(
    private val alProcesarFrame: (Long) -> Unit
) : AnimationTimer() {

    override fun handle(ahoraNano: Long) {
        alProcesarFrame(ahoraNano)
    }
}
