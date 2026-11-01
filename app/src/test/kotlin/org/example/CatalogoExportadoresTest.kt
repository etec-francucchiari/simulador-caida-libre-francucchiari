package org.example

import kotlin.test.*

class CatalogoExportadoresTest {
    /**
     * Pruebas unitarias del catálogo de formatos de exportación de datos
     * ([CatalogoExportadores]).
     *
     * Comprueba el registro de las implementaciones [ExportadorCsv] y
     * [ExportadorJson], la consulta de extensiones soportadas y la búsqueda
     * insensible a mayúsculas/minúsculas o al punto inicial.
     */
    private val catalogo = CatalogoExportadores(listOf(ExportadorCsv(), ExportadorJson()))

    /**
     * Verifica que el catálogo exponga los dos formatos inyectados y liste sus
     * extensiones canónicas (`"csv"` y `"json"`).
     */
    @Test
    fun exponeLosFormatosSoportados() {
        assertEquals(2, catalogo.disponibles().size)
        assertTrue(catalogo.extensiones().contains("csv"))
        assertTrue(catalogo.extensiones().contains("json"))
    }

    /**
     * Verifica que `porExtension` normalice el prefijo `.` y las diferencias de
     * capitalización, devolviendo `null` cuando el formato no está registrado.
     */
    @Test
    fun buscaPorExtensionIgnorandoPuntoYMayusculas() {
        assertEquals("csv", catalogo.porExtension("csv")?.extension())
        assertEquals("csv", catalogo.porExtension(".CSV")?.extension())
        assertEquals("json", catalogo.porExtension("JSON")?.extension())
        assertNull(catalogo.porExtension("xml"))
    }
}
