package org.example

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Pruebas del modelo de entornos gravitatorios [Planeta] y [PlanetaPersonalizado],
 * así como del catálogo instanciable [CatalogoPlanetas].
 *
 * Verifica los valores exigidos por la reestructuración de la interfaz:
 * - Tierra (g = 9.81 m/s²)
 * - Luna (g = 1.62 m/s²)
 * - Marte (g = 3.71 m/s²)
 * - Entorno personalizado con gravedad arbitraria.
 */
class PlanetaTest {

    private val catalogo = CatalogoPlanetas()

    @Test
    fun entornosPredefinidosTienenLaGravedadEsperada() {
        assertEquals(9.81, Planeta.TIERRA.gravedad(), 1e-9)
        assertEquals(1.62, Planeta.LUNA.gravedad(), 1e-9)
        assertEquals(3.71, Planeta.MARTE.gravedad(), 1e-9)
        assertEquals(24.79, Planeta.JUPITER.gravedad(), 1e-9)
    }

    @Test
    fun predefinidosContieneCuatroEntornos() {
        val nombres = catalogo.predefinidos().map { it.nombre() }
        assertTrue(nombres.containsAll(listOf("Tierra", "Luna", "Marte", "Júpiter")))
        assertEquals(4, catalogo.predefinidos().size)
    }

    @Test
    fun entornoPersonalizadoConservaSuGravedad() {
        val personalizado = catalogo.personalizado(5.5)
        assertEquals(5.5, personalizado.gravedad(), 1e-9)
        assertEquals("Personalizado", personalizado.nombre())
        assertTrue(personalizado is PlanetaPersonalizado)
    }

    @Test
    fun planetaAStringIncluyeNombreYGravedad() {
        assertTrue(Planeta.MARTE.toString().contains("Marte"))
        assertTrue(Planeta.MARTE.toString().contains("3.71"))
    }
}
