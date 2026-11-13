package org.example

import javafx.animation.AnimationTimer

class TemporizadorAnimacion(private val alProcesarFrame: (Long) -> Unit) : AnimationTimer() {
    /**
     * Adaptador instanciable del bucle de fotogramas de JavaFX ([AnimationTimer])
     * que delega cada pulso de reloj en una función inyectada por constructor.
     *
     * Se mantiene en su propio archivo independiente para respetar estrictamente
     * la regla de una sola clase por archivo y evitar clases internas anónimas
     * dentro de [ControladorSimulacion] (SRP).
     *
     * @property alProcesarFrame acción invocada en cada pulso con el timestamp
     *   actual en nanosegundos provisto por el pulso de renderizado de JavaFX.
     */

    /**
     * Recibe el pulso de tiempo en nanosegundos desde JavaFX y lo transfiere al
     * callback configurado en el controlador de simulación.
     */
    override fun handle(ahoraNano: Long) {
        alProcesarFrame(ahoraNano)
    }
}
