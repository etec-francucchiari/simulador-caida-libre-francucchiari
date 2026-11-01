package org.example

class CatalogoPlanetas : CatalogoEntornos {
    /**
     * Servicio de dominio instanciable que administra el catálogo de entornos
     * gravitatorios disponibles en la pantalla de selección ([PlanetSelectionScreen])
     * y en el generador de problemas ([GeneradorEjercicios]).
     *
     * Implementa el contrato [CatalogoEntornos] proporcionando acceso a los
     * cuatro cuerpos celestes predefinidos ([Planeta]), la creación de entornos
     * personalizados ([PlanetaPersonalizado]) y la validación física de la
     * aceleración gravitatoria ingresada.
     */

    private val gravedadMinima = 0.0

    /**
     * Devuelve la lista inmutable de entornos predefinidos del sistema solar:
     * Tierra, Luna, Marte y Júpiter.
     */
    override fun predefinidos(): List<EntornoGravitatorio> {
        return Planeta.entries
    }

    /**
     * Instancia un nuevo [PlanetaPersonalizado] configurado con la aceleración
     * de la [gravedad] especificada en m/s².
     */
    override fun personalizado(gravedad: Double): EntornoGravitatorio {
        return PlanetaPersonalizado(gravedad)
    }

    /**
     * Comprueba que el valor de [gravedad] sea un número real finito y
     * estrictamente mayor que cero (`g > 0 m/s²`).
     */
    override fun esGravedadValida(gravedad: Double): Boolean {
        return gravedad.isFinite() && gravedad > gravedadMinima
    }
}
