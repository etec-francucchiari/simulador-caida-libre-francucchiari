package org.example

class ContenedorDominioFisico(
    configuracionInterna: ConfiguracionSimulacion = ConfiguracionFisica(),
    recursosInternos: RecursosEntornoSimulacion = ProveedorRecursosEntorno()
) : ProveedorDominioFisico {
    /**
     * Contenedor cohesivo de los servicios de dominio físico ([ProveedorDominioFisico]).
     *
     * Coordina [ConfiguracionSimulacion] y [RecursosEntornoSimulacion] inyectados
     * por constructor con valores por defecto (DIP).
     */

    private val dominio = Pair(configuracionInterna, recursosInternos)

    override fun configuracion() = dominio.first

    override fun calculo() = dominio.second.calculo()

    override fun catalogo() = dominio.second.catalogo()
}
