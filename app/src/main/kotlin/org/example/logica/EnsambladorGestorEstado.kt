package org.example

class EnsambladorGestorEstado(
    configuracion: ConfiguracionSimulacion,
    calculo: CalculadoraCaidaLibre
) {
    /**
     * Colaborador que ensambla la instancia de [GestorEstadoSimulacion] con su
     * memoria instantánea y su motor cinemático [MotorEstadoCaida] (SRP / DIP).
     */

    private val base = Pair(configuracion, MotorEstadoCaida(calculo))

    /**
     * Construye el gestor reactivo inicializado con el planeta indicado.
     */
    fun construir(planetaInicial: EntornoGravitatorio): GestorEstadoSimulacion {
        val instantanea = base.second.crearMemoriaInstantanea(base.first)
        return GestorEstadoSimulacion(instantanea, planetaInicial, base.second)
    }
}
