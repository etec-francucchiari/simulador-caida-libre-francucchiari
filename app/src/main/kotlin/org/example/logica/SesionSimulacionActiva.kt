package org.example

class SesionSimulacionActiva(
    dominio: ProveedorDominioFisico
) {
    /**
     * Encapsula la sesión activa de simulación (contexto reactivo, controlador
     * de reproducción y desafío avanzado) construida a partir de
     * [ProveedorDominioFisico] (SRP / LCOM / CBO).
     */

    private val estadoSesion = inicializarSesion(dominio)

    private fun inicializarSesion(
        dominio: ProveedorDominioFisico
    ): Pair<ProveedorDominioFisico, Triple<GestorEstadoSimulacion, ControladorSimulacion, DesafioAvanzado>> {
        val fabricaCtx = FabricaContextoSimulacion(dominio.configuracion(), dominio.calculo())
        val ctx = fabricaCtx.crear(dominio.catalogo().predefinidos().first())
        val ctrl = fabricaCtx.crearControl(ctx)
        val desafio = FabricaDesafioAvanzado(dominio).crear()
        return Pair(dominio, Triple(ctx, ctrl, desafio))
    }

    fun dominio(): ProveedorDominioFisico = estadoSesion.first

    fun contexto(): GestorEstadoSimulacion = estadoSesion.second.first

    fun control(): ControladorSimulacion = estadoSesion.second.second

    fun desafio(): DesafioAvanzado = estadoSesion.second.third
}
