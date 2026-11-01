package org.example

class RegistroEstilosEntorno {
    /**
     * Proveedor de estilos visuales predefinidos para los entornos del sistema solar.
     *
     * Desacopla a [CatalogoEstilosEntorno] de las implementaciones concretas de cada
     * planeta ([EstiloTierra], [EstiloLuna], [EstiloMarte], [EstiloJupiter]),
     * reduciendo el acoplamiento eferente (CBO) y centralizando el registro de paletas.
     */

    private val claveTierra = "Tierra"
    private val claveLuna = "Luna"

    /**
     * Construye el mapa inmutable de estilos indexado por el nombre canónico del entorno.
     *
     * @return diccionario de nombre de entorno a su [EstiloEntorno] correspondiente.
     */
    fun crearMapa(): Map<String, EstiloEntorno> {
        return mapOf(
            claveTierra to EstiloTierra(),
            claveLuna to EstiloLuna(),
            "Marte" to EstiloMarte(),
            "Júpiter" to EstiloJupiter()
        )
    }

    /**
     * Devuelve la cantidad total de estilos planetarios registrados en el catálogo base.
     */
    fun cantidadRegistrados(): Int {
        return crearMapa().size
    }
}
