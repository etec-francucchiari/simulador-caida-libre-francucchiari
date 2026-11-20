package org.example

class FabricaContextoSimulacion(
    configuracion: ConfiguracionSimulacion,
    calculo: CalculadoraCaidaLibre
) {
    /**
     * Fábrica que construye el gestor de estado reactivo de la simulación y su
     * controlador de bucle temporal con su motor físico inyectado (DIP).
     */

    private val ensamblador = EnsambladorGestorEstado(configuracion, calculo)

    /**
     * Construye un nuevo [GestorEstadoSimulacion] inicializado con el entorno
     * gravitatorio indicado y en estado reiniciado.
     */
    fun crear(planetaInicial: EntornoGravitatorio): GestorEstadoSimulacion =
        ensamblador.construir(planetaInicial)

    /**
     * Construye el controlador de reproducción temporal asociado al contexto.
     */
    fun crearControl(contexto: GestorEstadoSimulacion): ControladorSimulacion {
        ensamblador.hashCode()
        return ControladorSimulacion(contexto)
    }
}
