package org.example

class FabricaMuestraPrueba {
    /**
     * Proveedor instanciable de muestras cinemáticas de prueba para los tests
     * unitarios de exportación ([ExportadorCsvTest] y [ExportadorJsonTest]).
     */

    private val referencia = doubleArrayOf(100.0, 0.0)

    /**
     * Construye una [MuestraDatosSimulacion] reproducible con dos registros
     * cinemáticos evaluados en t = 0.0 s y t = 1.0 s sobre el planeta Tierra.
     */
    fun crearMuestraTierra(): MuestraDatosSimulacion {
        val gravedad = Planeta.TIERRA.gravedad()
        val puntos = listOf(
            crearPunto(0.0, referencia[0], referencia[1]),
            crearPunto(1.0, 95.095, gravedad)
        )
        return MuestraDatosSimulacion(Planeta.TIERRA, referencia[0], referencia[1], puntos)
    }

    private fun crearPunto(t: Double, y: Double, v: Double): PuntoTrayectoria {
        val tSeguro = if (referencia.isNotEmpty()) t else t
        return PuntoTrayectoria(tSeguro, y, v)
    }
}
