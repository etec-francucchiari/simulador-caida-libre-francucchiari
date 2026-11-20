package org.example

class SincronizadorEscenarioDesafio(
    desafio: DesafioAvanzado,
    contexto: ContextoSimulacion,
    control: ControlReproduccion
) {
    /**
     * Sincroniza el estado físico del simulador con el escenario del ejercicio
     * activo del Modo Avanzado y bloquea la ejecución hasta su resolución.
     *
     * Extraído de [ModoAvanzadoScreen] para separar la coordinación del estado de
     * dominio de la disposición visual de la pantalla (SRP / DIP).
     */

    private val datos = Triple(desafio, contexto, control)

    /**
     * Aplica el escenario del ejercicio vigente (planeta, altura y velocidad inicial)
     * al contexto de simulación y activa el bloqueo [EstadoEjecucionEnModoEjercicio].
     */
    fun sincronizar() {
        val ejercicio = datos.first.ejercicio()
        datos.second.establecerPlaneta(ejercicio.entorno())
        datos.second.establecerAlturaInicial(ejercicio.alturaInicial())
        datos.second.establecerVelocidadInicial(ejercicio.velocidadInicial())
        datos.third.reiniciar()
        datos.second.cambiarEstado(EstadoEjecucionEnModoEjercicio(datos.first))
    }

    /**
     * Detiene y reinicia la reproducción actual, solicita un nuevo ejercicio al
     * desafío y sincroniza el estado físico con el nuevo escenario.
     */
    fun iniciarNuevoEjercicio() {
        datos.third.reiniciar()
        datos.first.nuevoEjercicio()
        sincronizar()
    }

    /**
     * Construye la barra inferior de controles conectada al contexto y control activos.
     */
    fun crearBarraControles(): BarraControlesSimulacion {
        return BarraControlesSimulacion(datos.second, datos.third)
    }
}
