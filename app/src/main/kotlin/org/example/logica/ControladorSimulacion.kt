package org.example

import javafx.animation.AnimationTimer

class ControladorSimulacion(
    private val contexto: ContextoSimulacion
) : ControlReproduccion {
    /**
     * Controlador del bucle temporal de la simulación de caída libre.
     *
     * Es instanciable y depende de la abstracción [ContextoSimulacion] inyectada
     * por constructor (DIP). Implementa [ControlReproduccion] para desacoplar la
     * barra de controles de UI.
     */

    private val deltaMaximoSegundos: Double = 0.1
    private val nanoASegundos: Double = 1_000_000_000.0
    private var ultimoTimestampNano: Long = 0L
    private val temporizador: AnimationTimer =
        TemporizadorAnimacion { ahoraNano -> procesarFrame(ahoraNano) }

    init {
        contexto.enEjecucionObservable().addListener { _, _, enEjecucion ->
            if (enEjecucion) {
                iniciarTemporizador()
            } else {
                detenerTemporizador()
            }
        }
    }

    /** Límite máximo de delta de tiempo por fotograma en segundos. */
    fun deltaMaximoSegundos(): Double = deltaMaximoSegundos

    /** Procesa un fotograma del bucle de animación. */
    fun procesarFrame(ahoraNano: Long): Boolean {
        if (!contexto.estadoEjecucion().esActiva()) {
            detenerTemporizador()
            return false
        }
        if (ultimoTimestampNano == 0L) {
            ultimoTimestampNano = ahoraNano
            return true
        }
        val deltaSegundos = (ahoraNano - ultimoTimestampNano) / nanoASegundos
        ultimoTimestampNano = ahoraNano
        if (deltaSegundos <= 0.0) {
            return contexto.estadoEjecucion().esActiva()
        }
        val deltaSeguro = deltaSegundos.coerceAtMost(deltaMaximoSegundos)
        val sigueEnElAire = contexto.avanzarTiempo(deltaSeguro)
        if (!sigueEnElAire || contexto.haLlegadoAlSuelo() || contexto.obtenerAlturaActual() <= 0.0) {
            detenerTemporizador()
            return false
        }
        return true
    }

    override fun alternarEjecucion() {
        if (!contexto.estaEnEjecucion() && contexto.haLlegadoAlSuelo()) {
            contexto.reiniciarSimulacion()
        }
        contexto.cambiarEstado(contexto.estadoEjecucion().alternar())
    }

    override fun iniciar() {
        contexto.iniciarSimulacion()
    }

    override fun pausar() {
        contexto.pausarSimulacion()
    }

    override fun reiniciar() {
        detenerTemporizador()
        contexto.reiniciarSimulacion()
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
