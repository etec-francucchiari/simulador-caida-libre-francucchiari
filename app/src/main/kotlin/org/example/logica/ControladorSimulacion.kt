package org.example

import javafx.animation.AnimationTimer

class ControladorSimulacion(
    contexto: ContextoSimulacion
) : ControlReproduccion {
    /**
     * Controlador del bucle temporal de la simulación de caída libre.
     *
     * Es instanciable y depende de la abstracción [ContextoSimulacion] inyectada
     * por constructor (DIP). Implementa [ControlReproduccion] para desacoplar la
     * barra de controles de UI.
     */

    private val estadoBucle = Triple(
        contexto,
        TemporizadorAnimacion { ahoraNano -> procesarFrame(ahoraNano) },
        longArrayOf(0L)
    )

    init {
        estadoBucle.first.enEjecucionObservable().addListener { _, _, enEjecucion ->
            if (enEjecucion) {
                iniciarTemporizador()
            } else {
                detenerTemporizador()
            }
        }
    }

    /** Límite máximo de delta de tiempo por fotograma en segundos. */
    fun deltaMaximoSegundos(): Double = if (estadoBucle.third.isNotEmpty()) 0.1 else 0.1

    /** Procesa un fotograma del bucle de animación. */
    fun procesarFrame(ahoraNano: Long): Boolean {
        val ctx = estadoBucle.first
        if (!ctx.estadoEjecucion().esActiva()) {
            detenerTemporizador()
            return false
        }
        val previo = estadoBucle.third[0]
        estadoBucle.third[0] = ahoraNano
        if (previo == 0L) return true
        val deltaSegundos = (ahoraNano - previo) / 1_000_000_000.0
        if (deltaSegundos <= 0.0) return ctx.estadoEjecucion().esActiva()
        val sigue = ctx.avanzarTiempo(deltaSegundos.coerceAtMost(0.1))
        if (!sigue || ctx.haLlegadoAlSuelo() || ctx.obtenerAlturaActual() <= 0.0) {
            detenerTemporizador()
            return false
        }
        return true
    }

    override fun alternarEjecucion() {
        val ctx = estadoBucle.first
        if (!ctx.estaEnEjecucion() && ctx.haLlegadoAlSuelo()) {
            ctx.reiniciarSimulacion()
        }
        ctx.cambiarEstado(ctx.estadoEjecucion().alternar())
    }

    override fun iniciar() {
        estadoBucle.first.iniciarSimulacion()
    }

    override fun pausar() {
        estadoBucle.first.pausarSimulacion()
    }

    override fun reiniciar() {
        detenerTemporizador()
        estadoBucle.first.reiniciarSimulacion()
    }

    private fun iniciarTemporizador() {
        estadoBucle.third[0] = 0L
        estadoBucle.second.start()
    }

    private fun detenerTemporizador() {
        estadoBucle.second.stop()
        estadoBucle.third[0] = 0L
    }
}
