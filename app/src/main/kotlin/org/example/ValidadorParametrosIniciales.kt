package org.example

/**
 * Valida los textos ingresados por el usuario en los campos de parámetros
 * iniciales (altura y velocidad).
 *
 * Implementa el contrato [ValidadorParametros] y recibe por constructor la
 * abstracción [ConfiguracionSimulacion] (DIP), de modo que no depende de la
 * implementación concreta de la configuración.
 */
class ValidadorParametrosIniciales(
    private val configuracion: ConfiguracionSimulacion
) : ValidadorParametros {

    private val mensajeFormatoNumerico =
        "Ingrese un número válido (por ejemplo: 10,5)."
    private val mensajeAlturaNegativa =
        "La altura inicial no puede ser negativa."
    private val mensajeVelocidadNegativa =
        "La velocidad inicial no puede ser negativa."

    override fun validarAltura(texto: String): String? {
        val altura = parsearNumero(texto) ?: return mensajeFormatoNumerico
        return if (!configuracion.esAlturaValida(altura)) mensajeAlturaNegativa else null
    }

    override fun validarVelocidad(texto: String): String? {
        val velocidad = parsearNumero(texto) ?: return mensajeFormatoNumerico
        return if (!configuracion.esVelocidadValida(velocidad)) mensajeVelocidadNegativa else null
    }

    override fun validar(textoAltura: String, textoVelocidad: String): ResultadoValidacion {
        val errores = mutableListOf<String>()

        val altura = parsearNumero(textoAltura)
        val velocidad = parsearNumero(textoVelocidad)

        if (altura == null) {
            errores += mensajeFormatoNumerico
        } else if (!configuracion.esAlturaValida(altura)) {
            errores += mensajeAlturaNegativa
        }

        if (velocidad == null) {
            errores += mensajeFormatoNumerico
        } else if (!configuracion.esVelocidadValida(velocidad)) {
            errores += mensajeVelocidadNegativa
        }

        return if (errores.isEmpty()) {
            ValidacionExitosa(ParametrosIniciales(altura!!, velocidad!!))
        } else {
            ValidacionFallida(errores)
        }
    }

    /**
     * Convierte el texto en un número finito, aceptando coma o punto decimal.
     */
    private fun parsearNumero(texto: String): Double? {
        val valor = texto.trim().replace(',', '.').toDoubleOrNull()
        return valor?.takeIf { it.isFinite() }
    }
}
