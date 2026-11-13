package org.example

class ValidadorParametrosIniciales(private val configuracion: ConfiguracionSimulacion) : ValidadorParametros {
    /**
     * Servicio de dominio que valida los textos ingresados por el usuario en los
     * campos de parámetros iniciales (altura h₀ y velocidad inicial v₀).
     *
     * Implementa el contrato [ValidadorParametros] y recibe por constructor la
     * abstracción [ConfiguracionSimulacion] (Principio de Inversión de
     * Dependencias - DIP), de modo que no depende de la implementación concreta
     * de la configuración física.
     */
    private val mensajeFormatoNumerico = "Ingrese un número válido (por ejemplo: 10,5)."
    private val mensajeAlturaNegativa = "La altura inicial no puede ser negativa."
    private val mensajeVelocidadNegativa = "La velocidad inicial no puede ser negativa."

    /**
     * Valida el texto correspondiente a la altura inicial y devuelve un mensaje
     * de error descriptivo, o `null` cuando el valor es válido.
     */
    override fun validarAltura(texto: String): String? {
        val altura = parsearNumero(texto) ?: return mensajeFormatoNumerico
        return if (!configuracion.esAlturaValida(altura)) mensajeAlturaNegativa else null
    }

    /**
     * Valida el texto correspondiente a la velocidad inicial y devuelve un
     * mensaje de error descriptivo, o `null` cuando el valor es válido.
     */
    override fun validarVelocidad(texto: String): String? {
        val velocidad = parsearNumero(texto) ?: return mensajeFormatoNumerico
        return if (!configuracion.esVelocidadValida(velocidad)) mensajeVelocidadNegativa else null
    }

    /**
     * Evalúa conjuntamente ambos textos de entrada y construye polimórficamente
     * una [ValidacionExitosa] o una [ValidacionFallida].
     */
    override fun validar(textoAltura: String, textoVelocidad: String): ResultadoValidacion {
        val errores = listOfNotNull(validarAltura(textoAltura), validarVelocidad(textoVelocidad))
        if (errores.isNotEmpty()) {
            return ValidacionFallida(errores)
        }
        return ValidacionExitosa(ParametrosIniciales(parsearNumero(textoAltura)!!, parsearNumero(textoVelocidad)!!))
    }

    /**
     * Convierte el texto en un número real finito, aceptando coma o punto decimal.
     */
    private fun parsearNumero(texto: String): Double? {
        val valor = texto.trim().replace(',', '.').toDoubleOrNull()
        return valor?.takeIf { it.isFinite() }
    }
}
