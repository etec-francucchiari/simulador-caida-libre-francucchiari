package org.example

import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Pruebas del exportador JSON.
 */
class ExportadorJsonTest {

    private fun muestraDeEjemplo(): MuestraDatosSimulacion =
        MuestraDatosSimulacion(
            entornoInterno = Planeta.TIERRA,
            parametrosInternos = ParametrosIniciales(100.0, 0.0),
            filasInternas = listOf(
                FilaDatosSimulacion(0.0, 100.0, 0.0, 9.81),
                FilaDatosSimulacion(1.0, 95.095, 9.81, 9.81)
            )
        )

    @Test
    fun generaMetadatosYArregloDeDatos() {
        val archivo = File.createTempFile("simulacion", ".json")
        archivo.deleteOnExit()

        ExportadorJson().exportar(muestraDeEjemplo(), archivo)

        val contenido = archivo.readText()
        assertTrue(contenido.contains("\"entorno\": \"Tierra\""))
        assertTrue(contenido.contains("\"gravedad\": 9.8100"))
        assertTrue(contenido.contains("\"datos\": ["))
        assertTrue(contenido.contains("\"t\": 0.0000"))
        assertTrue(contenido.contains("\"a\": 9.8100"))
    }

    @Test
    fun exponeSuExtensionYDescripcion() {
        assertEquals("json", ExportadorJson().extension())
        assertTrue(ExportadorJson().descripcion().contains("JSON"))
    }
}
