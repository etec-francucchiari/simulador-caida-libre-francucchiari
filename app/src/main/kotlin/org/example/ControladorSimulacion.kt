package org.example

import javafx.animation.AnimationTimer

/**
 * Controlador del bucle temporal de la simulación de caída libre.
 *
 * Desacopla la gestión del tiempo de animación de la interfaz gráfica y
 * del cálculo físico:
 * - Utiliza un [AnimationTimer] de JavaFX sincronizado con la tasa de refresco
 *   de la pantalla (aproximadamente 60 fps).
 * - Calcula el delta de tiempo real transcurrido entre frames consecutivos.
 * - Invoca a [GestorEstadoSimulacion.avanzarTiempo] para avanzar el estado
 *   físico reactivo.
 * - Detiene el temporizador automáticamente cuando el cuerpo impacta en el suelo
 *   o cuando el usuario pausa la simulación.
 */
class ControladorSimulacion(
    private val gestorEstado: GestorEstadoSimulacion
) {

    private var ultimoTimestampNano: Long = 0L

    private val temporizador: AnimationTimer = object : AnimationTimer() {
        override fun handle(ahoraNano: Long) {
            if (ultimoTimestampNano == 0L) {
                ultimoTimestampNano = ahoraNano
                return
            }

            val deltaSegundos = (ahoraNano - ultimoTimestampNano) / 1_000_000_000.0
            ultimoTimestampNano = ahoraNano

            // Se limita el delta máximo por frame para evitar saltos bruscos si hay demoras
            val deltaSeguro = deltaSegundos.coerceAtMost(0.1)
            val sigueEnElAire = gestorEstado.avanzarTiempo(deltaSeguro)

            if (!sigueEnElAire) {
                detenerTemporizador()
            }
        }
    }

    init {
        // Se sincroniza el timer con el estado reactivo del gestor
        gestorEstado.enEjecucionProperty.addListener { _, _, enEjecucion ->
            if (enEjecucion) {
                iniciarTemporizador()
            } else {
                detenerTemporizador()
            }
        }
    }

    /**
     * Alterna entre iniciar y pausar la simulación.
     */
    fun alternarEjecucion() {
        if (gestorEstado.estaEnEjecucion()) {
            pausar()
        } else {
            iniciar()
        }
    }

    /**
     * Inicia o reanuda la simulación.
     */
    fun iniciar() {
        gestorEstado.iniciarSimulacion()
    }

    /**
     * Pausa la simulación en el instante actual.
     */
    fun pausar() {
        gestorEstado.pausarSimulacion()
    }

    /**
     * Reinicia la simulación volviendo al tiempo t = 0.
     */
    fun reiniciar() {
        gestorEstado.reiniciarSimulacion()
    }

    private fun iniciarTemporizador() {
        ultimoTimestampNano = 0L
        temporizador.start()
    }

    private fun detenerTemporizador() {
        temporizador.stop()
        ultimoTimestampNano = 0L
    }
}
