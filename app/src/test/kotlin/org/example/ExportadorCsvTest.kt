package org.example

import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Pruebas del exportador CSV.
 */
class ExportadorCsvTest {

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
    fun escribeEncabezadoYUnaFilaPorPunto() {
        val archivo = File.createTempFile("simulacion", ".csv")
        archivo.deleteOnExit()

        ExportadorCsv().exportar(muestraDeEjemplo(), archivo)

        val lineas = archivo.readLines()
        assertEquals(3, lineas.size)
        assertEquals("t (s),y (m),v (m/s),a (m/s²)", lineas[0])
        assertEquals("0.0000,100.0000,0.0000,9.8100", lineas[1])
        assertEquals("1.0000,95.0950,9.8100,9.8100", lineas[2])
    }

    @Test
    fun exponeSuExtensionYDescripcion() {
        assertEquals("csv", ExportadorCsv().extension())
        assertTrue(ExportadorCsv().descripcion().contains("CSV"))
    }
}
