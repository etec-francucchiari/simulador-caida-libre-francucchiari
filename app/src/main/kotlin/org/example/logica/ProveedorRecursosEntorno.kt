package org.example

class ProveedorRecursosEntorno(
    catalogoInterno: CatalogoEntornos = CatalogoPlanetas(),
    calculoInterno: CalculadoraCaidaLibre = CalculoCaidaLibre()
) : RecursosEntornoSimulacion {
    /**
     * Implementación del proveedor de recursos de entorno y cálculo físico
     * ([RecursosEntornoSimulacion]).
     *
     * Encapsula la creación por defecto de [CatalogoPlanetas] y
     * [CalculoCaidaLibre] para desacoplar a [NucleoSimulacion] de las clases
     * concretas de dominio (SRP / DIP / CBO).
     */

    private val recursos = Pair(catalogoInterno, calculoInterno)

    override fun catalogo(): CatalogoEntornos = recursos.first

    override fun calculo(): CalculadoraCaidaLibre = recursos.second
}
