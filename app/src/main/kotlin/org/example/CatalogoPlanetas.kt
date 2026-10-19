package org.example

/**
 * Servicio instanciable que administra el catálogo de entornos gravitatorios
 * disponibles en la interfaz de selección.
 *
 * Sustituye a la antigua utilidad estática: se inyecta donde se necesita y
 * concentra la lógica de creación y validación de entornos.
 */
class CatalogoPlanetas : CatalogoEntornos {

    /**
     * Devuelve los entornos predefinidos (Tierra, Luna, Marte y Júpiter).
     */
    override fun predefinidos(): List<EntornoGravitatorio> =
        listOf(Planeta.TIERRA, Planeta.LUNA, Planeta.MARTE, Planeta.JUPITER)

    /**
     * Crea un entorno personalizado con la gravedad indicada.
     */
    override fun personalizado(gravedad: Double): EntornoGravitatorio =
        PlanetaPersonalizado(gravedad)

    /**
     * Comportamiento de dominio: valida que una gravedad sea físicamente útil
     * (finita y estrictamente positiva).
     */
    override fun esGravedadValida(gravedad: Double): Boolean =
        gravedad.isFinite() && gravedad > 0.0
}