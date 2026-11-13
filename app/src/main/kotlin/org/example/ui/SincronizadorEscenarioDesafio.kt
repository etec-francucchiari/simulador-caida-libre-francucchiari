package org.example

class SincronizadorEscenarioDesafio(
    private val desafio: DesafioAvanzado,
    private val comandos: ComandosSimulacion,
    private val control: ControlReproduccion
) {
    /**
     * Sincroniza el estado físico del simulador con el escenario del ejercicio
     * activo del Modo Avanzado y bloquea la ejecución hasta su resolución.
     *
     * Extraído de [ModoAvanzadoScreen] para separar la coordinación del estado de
     * dominio de la disposición visual de la pantalla (SRP / DIP).
     */

    /**
     * Aplica el escenario del ejercicio vigente (planeta, altura y velocidad inicial)
     * al contexto de simulación y activa el bloqueo [EstadoEjecucionEnModoEjercicio].
     */
    fun sincronizar() {
        val ejercicio = desafio.ejercicio()
        comandos.establecerPlaneta(ejercicio.entorno())
        comandos.establecerAlturaInicial(ejercicio.alturaInicial())
        comandos.establecerVelocidadInicial(ejercicio.velocidadInicial())
        control.reiniciar()
        comandos.cambiarEstado(EstadoEjecucionEnModoEjercicio(desafio))
    }

    /**
     * Detiene y reinicia la reproducción actual, solicita un nuevo ejercicio al
     * desafío y sincroniza el estado físico con el nuevo escenario.
     */
    fun iniciarNuevoEjercicio() {
        control.reiniciar()
        desafio.nuevoEjercicio()
        sincronizar()
    }
}
