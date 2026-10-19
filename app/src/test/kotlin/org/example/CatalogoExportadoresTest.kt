package org.example

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Pruebas del catálogo de exportadores.
 */
class CatalogoExportadoresTest {

    private val catalogo = CatalogoExportadores(listOf(ExportadorCsv(), ExportadorJson()))

    @Test
    fun exponeLosFormatosSoportados() {
        assertEquals(2, catalogo.disponibles().size)
        assertTrue(catalogo.extensiones().contains("csv"))
        assertTrue(catalogo.extensiones().contains("json"))
    }

    @Test
    fun buscaPorExtensionIgnorandoPuntoYMayusculas() {
        assertEquals("csv", catalogo.porExtension("csv")?.extension())
        assertEquals("csv", catalogo.porExtension(".CSV")?.extension())
        assertEquals("json", catalogo.porExtension("JSON")?.extension())
        assertNull(catalogo.porExtension("xml"))
    }
}
