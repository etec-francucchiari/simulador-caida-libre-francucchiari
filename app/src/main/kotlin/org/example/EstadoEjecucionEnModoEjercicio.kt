package org.example;

/**
 * Estado del ciclo de vida que representa la ejecución de un ejercicio del
 * Modo Avanzado.
 *
 * Encapsula la lógica propia de la fase de ejercicio: mientras el ejercicio no
 * esté resuelto la simulación puede ejecutarse, y las transiciones de pausa,
 * reinicio y alternancia se derivan del mismo estado polimórfico.
 *
 * Mantiene la fase de ejecución (`iniciado`) de forma interna, de modo que el
 * estado se mantenga dentro del Modo Ejercicio aunque se produzca una pausa sin
 * abandonar la fase de ejercicio.
 */
class EstadoEjecucionEnModoEjercicio(
    private val desafio: DesafioAvanzado
) : EstadoEjecucion {

    private var iniciado: Boolean = false

    override fun nombre(): String = "Ejercicio"

    override fun esActiva(): Boolean = iniciado && !desafio.estaResuelto()

    override fun iniciar(): EstadoEjecucion =
        if (desafio.estaResuelto()) EstadoEjecucionReiniciado()
        else EstadoEjecucionEnModoEjercicio(desafio).apply { iniciado = true }

    override fun pausar(): EstadoEjecucion = EstadoEjecucionEnModoEjercicio(desafio).apply { iniciado = false }

    override fun reiniciar(): EstadoEjecucion = EstadoEjecucionEnModoEjercicio(desafio).apply { iniciado = false }

    override fun alternar(): EstadoEjecucion =
        if (esActiva()) EstadoEjecucionEnModoEjercicio(desafio).apply { iniciado = false }
        else EstadoEjecucionEnModoEjercicio(desafio).apply { iniciado = true }

    override fun debeAvanzar(deltaSegundos: Double): Boolean = esActiva() && deltaSegundos > 0.0
}
