package org.example

import java.io.File
import kotlin.test.*

class ExportadorCsvTest {
    /**
     * Pruebas unitarias del exportador de datos cinemáticos a formato CSV
     * ([ExportadorCsv]).
     *
     * Verifica que el archivo generado contenga la fila de encabezados con las
     * cuatro magnitudes físicas del MRUV:
     * - `t (s)`: tiempo transcurrido en segundos
     * - `y (m)`: altura instantánea sobre el suelo en metros
     * - `v (m/s)`: velocidad instantánea en metros por segundo
     * - `a (m/s²)`: aceleración gravitatoria constante del entorno
     *
     * Utiliza [FabricaMuestraPrueba] para obtener la muestra de datos sin
     * duplicar lógica de preparación entre formatos de exportación (DRY).
     */

    private val fabricaMuestra = FabricaMuestraPrueba()

    /**
     * Verifica que `exportar` escriba una primera línea con los encabezados
     * separados por comas y una línea adicional por cada [FilaDatosSimulacion]
     * con precisión de cuatro cifras decimales.
     */
    @Test
    fun escribeEncabezadoYUnaFilaPorPunto() {
        val archivo = File.createTempFile("simulacion", ".csv")
        archivo.deleteOnExit()

        ExportadorCsv().exportar(fabricaMuestra.crearMuestraTierra(), archivo)

        val lineas = archivo.readLines()
        assertEquals(3, lineas.size)
        assertEquals("t (s),y (m),v (m/s),a (m/s²)", lineas[0])
        assertEquals("0.0000,100.0000,0.0000,9.8100", lineas[1])
        assertEquals("1.0000,95.0950,9.8100,9.8100", lineas[2])
    }

    /**
     * Comprueba que el contrato [ExportadorDatos] reporte la extensión `"csv"`
     * (sin punto inicial) y una descripción legible para el diálogo de guardado.
     */
    @Test
    fun exponeSuExtensionYDescripcion() {
        assertEquals("csv", ExportadorCsv().extension())
        assertTrue(ExportadorCsv().descripcion().contains("CSV"))
    }
}
