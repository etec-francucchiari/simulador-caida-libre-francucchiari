package org.example

import java.io.File
import kotlin.test.*

class ServicioExportacionTest {
    /**
     * Pruebas unitarias del servicio de muestreo y exportación de datos de la
     * simulación ([ServicioExportacion]).
     */

    private val nucleo = NucleoSimulacion()

    private fun crearServicio(puntos: Int): ServicioExportacion {
        val gestor = nucleo.contexto()
        gestor.establecerAlturaInicial(100.0)
        gestor.establecerVelocidadInicial(0.0)
        return ServicioExportacion(gestor, nucleo.calculo(), puntos)
    }

    @Test
    fun generaLaMuestraConAceleracionConstanteYExtremosCorrectos() {
        val servicio = crearServicio(50)
        val muestra = servicio.generarMuestra()
        assertEquals(50, muestra.cantidadFilas())
        val primera = muestra.filas().first()
        assertEquals(0.0, primera.tiempo(), 1e-9)
        assertEquals(100.0, primera.posicion(), 1e-9)
        assertEquals(0.0, primera.velocidad(), 1e-9)
        assertEquals(nucleo.contexto().obtenerPlaneta().gravedad(), primera.aceleracion(), 1e-9)
        val ultima = muestra.filas().last()
        assertEquals(0.0, ultima.posicion(), 1e-9)
        assertTrue(ultima.haImpactadoSuelo())
    }

    @Test
    fun exportaUsandoElExportadorIndicado() {
        val archivo = File.createTempFile("muestra", ".csv")
        archivo.deleteOnExit()
        val servicio = crearServicio(20)
        servicio.exportar(archivo = archivo)
        val lineas = archivo.readLines()
        assertEquals(21, lineas.size)
    }
}
