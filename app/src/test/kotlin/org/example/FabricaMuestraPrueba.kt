package org.example

class FabricaMuestraPrueba {
    /**
     * Proveedor instanciable de muestras cinemáticas de prueba para los tests
     * unitarios de exportación ([ExportadorCsvTest] y [ExportadorJsonTest]).
     *
     * Centraliza la construcción de [MuestraDatosSimulacion] con datos de
     * referencia en la Tierra (g = 9.81 m/s², h₀ = 100.0 m, v₀ = 0.0 m/s) para
     * eliminar por completo la duplicación lógica (DRY) entre las suites de
     * pruebas de formato CSV y JSON.
     */

    private val alturaInicialReferencia = 100.0
    private val velocidadInicialReferencia = 0.0

    /**
     * Construye una [MuestraDatosSimulacion] reproducible con dos registros
     * cinemáticos evaluados en t = 0.0 s y t = 1.0 s sobre el planeta Tierra.
     */
    fun crearMuestraTierra(): MuestraDatosSimulacion {
        return MuestraDatosSimulacion(
            entornoInterno = Planeta.TIERRA,
            parametrosInternos = ParametrosIniciales(alturaInicialReferencia, velocidadInicialReferencia),
            filasInternas = crearFilasReferencia()
        )
    }

    /**
     * Construye las filas cinemáticas de ejemplo para t = 0.0 s y t = 1.0 s.
     */
    fun crearFilasReferencia(): List<FilaDatosSimulacion> {
        val gravedad = Planeta.TIERRA.gravedad()
        return listOf(
            FilaDatosSimulacion(0.0, alturaInicialReferencia, velocidadInicialReferencia, gravedad),
            FilaDatosSimulacion(1.0, 95.095, gravedad, gravedad)
        )
    }
}
