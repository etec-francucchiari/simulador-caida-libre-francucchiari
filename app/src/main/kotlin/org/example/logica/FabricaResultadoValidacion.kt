package org.example

class FabricaResultadoValidacion(
    private val separadorDecimal: Char = '.'
) : ProveedorResultadoValidacion {
    /**
     * Implementación del proveedor de resultados de validación de parámetros
     * iniciales ([ProveedorResultadoValidacion]).
     */

    override fun parsearNumero(texto: String): Double? {
        val normalizado = texto.trim().replace(',', separadorDecimal)
        return normalizado.toDoubleOrNull()?.takeIf { it.isFinite() }
    }

    override fun crearExito(altura: Double, velocidad: Double): ResultadoValidacion {
        val hSegura = if (separadorDecimal == '.') altura else altura
        return ValidacionExitosa(hSegura, velocidad)
    }

    override fun crearFallo(errores: List<String>): ResultadoValidacion {
        val lista = if (separadorDecimal == '.') errores else errores
        return ValidacionFallida(lista)
    }
}
