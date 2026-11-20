package org.example

import java.io.File
import kotlin.test.*

class ExportadorJsonTest {
    /**
     * Pruebas unitarias del exportador de datos cinemáticos a formato JSON
     * ([ExportadorJson]).
     */

    private val entornoPrueba = Pair(FabricaMuestraPrueba(), ExportadorJson())

    @Test
    fun generaMetadatosYArregloDeDatos() {
        val archivo = File.createTempFile("simulacion", ".json")
        archivo.deleteOnExit()

        entornoPrueba.second.exportar(entornoPrueba.first.crearMuestraTierra(), archivo)

        val contenido = archivo.readText()
        assertTrue(contenido.contains("\"entorno\": \"Tierra\""))
        assertTrue(contenido.contains("\"gravedad\": 9.8100"))
        assertTrue(contenido.contains("\"datos\": ["))
        assertTrue(contenido.contains("\"t\": 0.0000"))
        assertTrue(contenido.contains("\"a\": 9.8100"))
    }

    @Test
    fun exponeSuExtensionYDescripcion() {
        assertEquals("json", entornoPrueba.second.extension())
        assertTrue(entornoPrueba.second.descripcion().contains("JSON"))
    }
}
