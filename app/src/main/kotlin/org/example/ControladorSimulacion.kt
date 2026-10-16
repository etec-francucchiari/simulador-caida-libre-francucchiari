package org.example

import javafx.animation.AnimationTimer

/**
 * Controlador del bucle temporal de la simulación de caída libre.
 *
 * Es instanciable y depende de la abstracción [ContextoSimulacion] inyectada
 * por constructor (DIP). Implementa [ControlReproduccion] para desacoplar la
 * barra de controles de UI.
 *
 * Usa un [AnimationTimer] sincronizado con la tasa de refresco de pantalla,
 * limita el delta máximo por fotograma y detiene el bucle al tocar el suelo.
 */
class ControladorSimulacion(
    private val contexto: ContextoSimulacion
) : ControlReproduccion {

    private val deltaMaximoSegundos: Double = 0.1
    private val nanoASegundos: Double = 1_000_000_000.0

    private var ultimoTimestampNano: Long = 0L

    private val temporizador: AnimationTimer = TemporizadorAnimacion()

    init {
        // Se sincroniza el timer con el estado reactivo del contexto.
        contexto.enEjecucionObservable().addListener { _, _, enEjecucion ->
            if (enEjecucion) {
                iniciarTemporizador()
            } else {
                detenerTemporizador()
            }
        }
    }

    /**
     * Implementación privada del bucle de animación que delega cada fotograma en
     * [procesarFrame]. Se modela como clase interna para no recurrir a objetos
     * anónimos ni a utilidades estáticas.
     */
    private inner class TemporizadorAnimacion : AnimationTimer() {
        override fun handle(ahoraNano: Long) {
            procesarFrame(ahoraNano)
        }
    }

    /**
     * Límite máximo de delta de tiempo por fotograma en segundos.
     */
    fun deltaMaximoSegundos(): Double = deltaMaximoSegundos

    /**
     * Procesa un fotograma del bucle de animación.
     *
     * @return `true` si la simulación continúa en el aire, o `false` si concluyó.
     */
    fun procesarFrame(ahoraNano: Long): Boolean {
        if (!contexto.estaEnEjecucion()) {
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
            return contexto.estaEnEjecucion()
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
        if (contexto.estaEnEjecucion()) {
            pausar()
        } else {
            iniciar()
        }
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