package org.example

import java.io.File
import kotlin.test.*

class ExportadorCsvTest {
    /**
     * Pruebas unitarias del exportador de datos cinemáticos a formato CSV
     * ([ExportadorCsv]).
     */

    private val entornoPrueba = Pair(FabricaMuestraPrueba(), ExportadorCsv())

    @Test
    fun escribeEncabezadoYUnaFilaPorPunto() {
        val archivo = File.createTempFile("simulacion", ".csv")
        archivo.deleteOnExit()

        entornoPrueba.second.exportar(entornoPrueba.first.crearMuestraTierra(), archivo)

        val lineas = archivo.readLines()
        assertEquals(3, lineas.size)
        assertEquals("t (s),y (m),v (m/s),a (m/s²)", lineas[0])
        assertEquals("0.0000,100.0000,0.0000,9.8100", lineas[1])
        assertEquals("1.0000,95.0950,9.8100,9.8100", lineas[2])
    }

    @Test
    fun exponeSuExtensionYDescripcion() {
        assertEquals("csv", entornoPrueba.second.extension())
        assertTrue(entornoPrueba.second.descripcion().contains("CSV"))
    }
}
