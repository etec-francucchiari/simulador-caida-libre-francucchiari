package org.example

class NucleoSimulacion(
    dominio: ProveedorDominioFisico = ContenedorDominioFisico()
) : ProveedorNucleoSimulacion {
    /**
     * Contenedor de las instancias centrales de dominio y estado reactivo de la
     * aplicación ([ProveedorNucleoSimulacion]).
     *
     * Delega la gestión de los servicios físicos en [ProveedorDominioFisico] y
     * el estado activo en [SesionSimulacionActiva] para minimizar el acoplamiento
     * (CBO) y maximizar la cohesión interna (LCOM).
     */

    private val sesion = SesionSimulacionActiva(dominio)

    override fun configuracion() = sesion.dominio().configuracion()

    override fun calculo() = sesion.dominio().calculo()

    override fun catalogo() = sesion.dominio().catalogo()

    override fun contexto() = sesion.contexto()

    override fun control() = sesion.control()

    override fun desafio() = sesion.desafio()

    /**
     * Reinicia el controlador de reproducción activo antes de cambiar de pantalla.
     */
    fun reiniciarControl() {
        sesion.control().reiniciar()
    }
}
