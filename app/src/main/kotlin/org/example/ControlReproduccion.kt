package org.example

/**
 * Contrato de reproducción de la simulación que consumen los controles de UI.
 *
 * Abstrae el motor de animación para que la barra de controles no dependa de
 * la implementación concreta ([ControladorSimulacion]).
 */
interface ControlReproduccion {

    /** Alterna entre iniciar y pausar. */
    fun alternarEjecucion()

    /** Inicia o reanuda la simulación. */
    fun iniciar()

    /** Pausa la simulación. */
    fun pausar()

    /** Reinicia la simulación a t = 0. */
    fun reiniciar()
}