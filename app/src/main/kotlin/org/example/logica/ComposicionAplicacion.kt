package org.example

class ComposicionAplicacion(
    nucleo: ProveedorNucleoSimulacion = NucleoSimulacion()
) : ServiciosAplicacion {
    /**
     * Raíz de composición (`Composition Root`) de la aplicación JavaFX.
     *
     * Implementa el contrato [ServiciosAplicacion] coordinando las instancias
     * centrales contenidas en [ProveedorNucleoSimulacion] junto con los
     * ensambladores especializados de cada modalidad (DIP / LCOM / CBO).
     */

    private val composicion = Pair(nucleo, EnsambladorPrincipiante(nucleo))

    override fun catalogo() = composicion.first.catalogo()

    override fun contexto() = composicion.first.contexto()

    override fun control() = composicion.first.control()

    override fun desafio() = composicion.first.desafio()

    override fun crearPantallaPrincipiante(onCambiarPlaneta: () -> Unit, onCambiarModo: () -> Unit) =
        composicion.second.construir(onCambiarPlaneta, onCambiarModo)

    override fun crearPantallaAvanzada(onCambiarModo: () -> Unit) =
        composicion.second.construirAvanzada(onCambiarModo)
}
