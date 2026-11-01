package org.example

import java.io.File
import kotlin.test.*

class ExportadorJsonTest {
    /**
     * Pruebas unitarias del exportador de datos cinemáticos a formato JSON
     * ([ExportadorJson]).
     *
     * Verifica que el documento JSON resultante incluya tanto los metadatos del
     * escenario experimental (`entorno`, `gravedad`, `alturaInicial`,
     * `velocidadInicial`) como el arreglo `datos` con los objetos `{t, y, v, a}`
     * de cada punto de la trayectoria muestreada, delegando la creación de la
     * muestra de referencia en [FabricaMuestraPrueba] (DRY).
     */

    private val fabricaMuestra = FabricaMuestraPrueba()

    /**
     * Comprueba que el archivo exportado contenga las claves de metadatos del
     * entorno y los objetos de la colección `datos` formateados a cuatro
     * decimales con separador de punto.
     */
    @Test
    fun generaMetadatosYArregloDeDatos() {
        val archivo = File.createTempFile("simulacion", ".json")
        archivo.deleteOnExit()

        ExportadorJson().exportar(fabricaMuestra.crearMuestraTierra(), archivo)

        val contenido = archivo.readText()
        assertTrue(contenido.contains("\"entorno\": \"Tierra\""))
        assertTrue(contenido.contains("\"gravedad\": 9.8100"))
        assertTrue(contenido.contains("\"datos\": ["))
        assertTrue(contenido.contains("\"t\": 0.0000"))
        assertTrue(contenido.contains("\"a\": 9.8100"))
    }

    /**
     * Verifica que el exportador declare la extensión `"json"` y una descripción
     * clara para el filtro del selector de archivos.
     */
    @Test
    fun exponeSuExtensionYDescripcion() {
        assertEquals("json", ExportadorJson().extension())
        assertTrue(ExportadorJson().descripcion().contains("JSON"))
    }
}
