package org.example

import kotlin.test.*

class PlanetaTest {
    /**
     * Pruebas unitarias del modelo de entornos gravitatorios ([Planeta] y
     * [PlanetaPersonalizado]) y del catálogo instanciable [CatalogoPlanetas].
     *
     * Verifica las aceleraciones gravitatorias superficiales de referencia:
     * - Tierra: g = 9.81 m/s²
     * - Luna: g = 1.62 m/s²
     * - Marte: g = 3.71 m/s²
     * - Júpiter: g = 24.79 m/s²
     * - Entorno personalizado con gravedad arbitraria definida por el usuario.
     */
    private val catalogo = CatalogoPlanetas()

    /**
     * Comprueba que cada constante del enumerador [Planeta] exponga su
     * aceleración gravitatoria exacta en m/s².
     */
    @Test
    fun entornosPredefinidosTienenLaGravedadEsperada() {
        assertEquals(9.81, Planeta.TIERRA.gravedad(), 1e-9)
        assertEquals(1.62, Planeta.LUNA.gravedad(), 1e-9)
        assertEquals(3.71, Planeta.MARTE.gravedad(), 1e-9)
        assertEquals(24.79, Planeta.JUPITER.gravedad(), 1e-9)
    }

    /**
     * Verifica que el catálogo devuelva exactamente los cuatro cuerpos celestes
     * predefinidos con sus nombres en español.
     */
    @Test
    fun predefinidosContieneCuatroEntornos() {
        val nombres = catalogo.predefinidos().map { it.nombre() }
        assertTrue(nombres.containsAll(listOf("Tierra", "Luna", "Marte", "Júpiter")))
        assertEquals(4, catalogo.predefinidos().size)
    }

    /**
     * Verifica que `catalogo.personalizado(g)` cree una instancia de
     * [EntornoGravitatorio] marcada como personalizada y con la gravedad dada.
     */
    @Test
    fun entornoPersonalizadoConservaSuGravedad() {
        val personalizado = catalogo.personalizado(5.5)
        assertEquals(5.5, personalizado.gravedad(), 1e-9)
        assertEquals("Personalizado", personalizado.nombre())
        assertTrue(personalizado.esPersonalizado())
    }

    /**
     * La representación textual de un planeta debe incluir tanto su nombre como
     * su gravedad formateada a dos decimales.
     */
    @Test
    fun planetaAStringIncluyeNombreYGravedad() {
        assertTrue(Planeta.MARTE.toString().contains("Marte"))
        assertTrue(Planeta.MARTE.toString().contains("3.71"))
    }
}
