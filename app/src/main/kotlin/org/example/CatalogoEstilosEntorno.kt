package org.example

/**
 * Catálogo instanciable que resuelve el estilo visual de cada entorno.
 *
 * La búsqueda se realiza por nombre en un mapa, sin `when` ni comprobaciones
 * de tipo. Un entorno no registrado recibe el estilo neutro (OCP).
 */
class CatalogoEstilosEntorno : CatalogoEstilos {

    private val estilos: Map<String, EstiloEntorno> = mapOf(
        "Tierra" to EstiloTierra(),
        "Luna" to EstiloLuna(),
        "Marte" to EstiloMarte(),
        "Júpiter" to EstiloJupiter()
    )

    private val estiloPorDefecto: EstiloEntorno = EstiloNeutro()

    /** Devuelve el estilo correspondiente al entorno indicado. */
    override fun para(entorno: EntornoGravitatorio): EstiloEntorno =
        estilos[entorno.nombre()] ?: estiloPorDefecto
}
