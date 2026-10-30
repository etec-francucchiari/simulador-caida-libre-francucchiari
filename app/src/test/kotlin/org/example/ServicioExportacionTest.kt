package org.example

import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Pruebas del servicio de exportación de la simulación.
 */
class ServicioExportacionTest {

    private val calculo = CalculoCaidaLibre()

    @Test
    fun generaLaMuestraConAceleracionConstanteYExtremosCorrectos() {
        val gestor = GestorEstadoSimulacion(ConfiguracionFisica(), Planeta.TIERRA, MotorEstadoCaida(CalculoCaidaLibre()), EstadoEjecucionReiniciado())
        gestor.establecerAlturaInicial(100.0)
        gestor.establecerVelocidadInicial(0.0)

        val servicio = ServicioExportacion(gestor, calculo, 50)
        val muestra = servicio.generarMuestra()

        assertEquals(50, muestra.cantidadFilas())

        val primera = muestra.filas().first()
        assertEquals(0.0, primera.tiempo(), 1e-9)
        assertEquals(100.0, primera.posicion(), 1e-9)
        assertEquals(0.0, primera.velocidad(), 1e-9)
        assertEquals(Planeta.TIERRA.gravedad(), primera.aceleracion(), 1e-9)

        val ultima = muestra.filas().last()
        assertEquals(0.0, ultima.posicion(), 1e-9)
        assertTrue(ultima.haImpactadoSuelo())
    }

    @Test
    fun exportaUsandoElExportadorIndicado() {
        val archivo = File.createTempFile("muestra", ".csv")
        archivo.deleteOnExit()

        val servicio = ServicioExportacion(GestorEstadoSimulacion(ConfiguracionFisica(), Planeta.TIERRA, MotorEstadoCaida(CalculoCaidaLibre()), EstadoEjecucionReiniciado()), calculo, 20)
        servicio.exportar(ExportadorCsv(), archivo)

        val lineas = archivo.readLines()
        assertEquals(21, lineas.size)
    }
}
