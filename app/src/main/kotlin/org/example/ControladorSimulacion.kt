package org.example

import javafx.animation.AnimationTimer

/**
 * Controlador del bucle temporal de la simulación de caída libre.
 *
 * Desacopla la gestión del tiempo de animación de la interfaz gráfica y
 * del cálculo físico:
 * - Utiliza un [AnimationTimer] de JavaFX sincronizado con la tasa de refresco
 *   de la pantalla (aproximadamente 60 fps).
 * - Calcula el delta de tiempo real transcurrido entre fotogramas consecutivos.
 * - Invoca a [GestorEstadoSimulacion.avanzarTiempo] para avanzar el estado
 *   físico reactivo en cada fotograma.
 * - Detecta automáticamente la colisión cuando el cuerpo llega al suelo (y <= 0)
 *   o cuando el usuario pausa la simulación, deteniendo el bucle.
 *
 * Sigue los principios SOLID:
 * - Responsabilidad Única (SRP): gestiona únicamente el bucle de animación y el flujo temporal.
 * - Inversión de Dependencias (DIP) / Desacoplamiento: interactúa exclusivamente con
 *   [GestorEstadoSimulacion] sin acoplamiento a elementos visuales de JavaFX.
 */
class ControladorSimulacion(
    private val gestorEstado: GestorEstadoSimulacion
) {

    companion object {
        /**
         * Límite máximo de delta de tiempo por fotograma (en segundos) para evitar
         * saltos numéricos bruscos si el hilo principal sufre pausas o retrasos.
         */
        const val DELTA_MAXIMO_SEGUNDOS: Double = 0.1

        /**
         * Factor de conversión de nanosegundos a segundos.
         */
        private const val NANO_A_SEGUNDOS: Double = 1_000_000_000.0
    }

    private var ultimoTimestampNano: Long = 0L

    private val temporizador: AnimationTimer = object : AnimationTimer() {
        override fun handle(ahoraNano: Long) {
            procesarFrame(ahoraNano)
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
     * Procesa un fotograma individual del bucle de animación a partir
     * de la marca de tiempo en nanosegundos provista.
     *
     * Calcula el delta de tiempo transcurrido en segundos, limita dicho delta
     * a un umbral seguro para evitar discontinuidades numéricas y actualiza
     * el estado dinámico en [GestorEstadoSimulacion].
     *
     * Si el objeto impacta el suelo (y <= 0) o la simulación ya no está activa,
     * se detiene automáticamente el temporizador.
     *
     * @param ahoraNano marca de tiempo del fotograma actual en nanosegundos.
     * @return `true` si la simulación continúa en el aire, o `false` si ha concluido o tocado el suelo.
     */
    fun procesarFrame(ahoraNano: Long): Boolean {
        if (!gestorEstado.estaEnEjecucion()) {
            detenerTemporizador()
            return false
        }

        if (ultimoTimestampNano == 0L) {
            ultimoTimestampNano = ahoraNano
            return true
        }

        val deltaSegundos = (ahoraNano - ultimoTimestampNano) / NANO_A_SEGUNDOS
        ultimoTimestampNano = ahoraNano

        if (deltaSegundos <= 0.0) {
            return gestorEstado.estaEnEjecucion()
        }

        // Se limita el delta máximo por frame para evitar saltos bruscos si hay demoras
        val deltaSeguro = deltaSegundos.coerceAtMost(DELTA_MAXIMO_SEGUNDOS)
        val sigueEnElAire = gestorEstado.avanzarTiempo(deltaSeguro)

        if (!sigueEnElAire || gestorEstado.haLlegadoAlSuelo() || gestorEstado.obtenerAlturaActual() <= 0.0) {
            detenerTemporizador()
            return false
        }

        return true
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
     * Inicia o reanuda la simulación en tiempo real.
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
     * Reinicia la simulación volviendo al tiempo t = 0 y a los valores iniciales.
     */
    fun reiniciar() {
        detenerTemporizador()
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
