package org.example

import java.io.File
import kotlin.test.*

class ServicioExportacionTest {
    /**
     * Pruebas unitarias del servicio de muestreo y exportación de datos de la
     * simulación ([ServicioExportacion]).
     *
     * Comprueba que la tabla [MuestraDatosSimulacion] refleje los parámetros
     * activos del contexto, mantenga constante la aceleración gravitatoria en
     * todas las filas y delegue la escritura en el [ExportadorDatos] elegido.
     */

    private val calculo = CalculoCaidaLibre()

    /**
     * La muestra generada con 50 puntos desde h₀ = 100 m en reposo debe comenzar
     * en (t = 0, y = 100, v = 0) y finalizar en el suelo (y = 0), conservando
     * la gravedad del planeta activo como aceleración constante.
     */
    @Test
    fun generaLaMuestraConAceleracionConstanteYExtremosCorrectos() {
        val gestor = NucleoSimulacion().contexto()
        gestor.establecerAlturaInicial(100.0)
        gestor.establecerVelocidadInicial(0.0)
        val servicio = ServicioExportacion(gestor, calculo, 50)
        val muestra = servicio.generarMuestra()
        assertEquals(50, muestra.cantidadFilas())
        val primera = muestra.filas().first()
        assertEquals(0.0, primera.tiempo(), 1e-9)
        assertEquals(100.0, primera.posicion(), 1e-9)
        assertEquals(0.0, primera.velocidad(), 1e-9)
        assertEquals(gestor.obtenerPlaneta().gravedad(), primera.aceleracion(), 1e-9)
        val ultima = muestra.filas().last()
        assertEquals(0.0, ultima.posicion(), 1e-9)
        assertTrue(ultima.haImpactadoSuelo())
    }

    /**
     * Al exportar una muestra de 20 puntos mediante [ExportadorCsv], el archivo
     * resultante debe contener exactamente 21 líneas (1 encabezado + 20 filas).
     */
    @Test
    fun exportaUsandoElExportadorIndicado() {
        val archivo = File.createTempFile("muestra", ".csv")
        archivo.deleteOnExit()
        val servicio = ServicioExportacion(NucleoSimulacion().contexto(), calculo, 20)
        servicio.exportar(ExportadorCsv(), archivo)
        val lineas = archivo.readLines()
        assertEquals(21, lineas.size)
    }
}
