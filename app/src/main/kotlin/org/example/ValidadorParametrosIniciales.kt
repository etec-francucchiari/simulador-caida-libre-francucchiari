package org.example

/**
 * Valida los textos ingresados por el usuario en los campos de
 * parámetros iniciales (altura y velocidad inicial).
 *
 * Responsabilidades:
 * - Convertir el texto a un número finito, aceptando coma o punto decimal.
 * - Comprobar las restricciones físicas (valores no negativos).
 * - Informar los errores con mensajes legibles en español.
 *
 * No conoce la interfaz gráfica: recibe [String] y devuelve un
 * [ResultadoValidacion], manteniendo la lógica de validación desacoplada.
 */
class ValidadorParametrosIniciales {

    /**
     * Resultado de la validación de los parámetros iniciales.
     * Permite manejar de forma segura el flujo exitoso y el fallido.
     */
    sealed interface ResultadoValidacion {

        /** Devuelve verdadero si los valores ingresados son válidos. */
        fun esValido(): Boolean

        /**
         * Contiene los [ParametrosIniciales] válidos obtenidos del texto.
         */
        data class Exitoso(val parametros: ParametrosIniciales) : ResultadoValidacion {
            override fun esValido(): Boolean = true
        }

        /**
         * Contiene los mensajes de error legibles para la interfaz.
         */
        data class Fallido(val errores: List<String>) : ResultadoValidacion {
            override fun esValido(): Boolean = false
        }
    }

    /**
     * Valida el texto de la altura inicial.
     *
     * @param texto el texto ingresado en el campo de altura.
     * @return el mensaje de error, o `null` si el valor es válido.
     */
    fun validarAltura(texto: String): String? {
        val altura = parsearNumero(texto) ?: return MENSAJE_FORMATO_NUMERICO
        return if (altura < ParametrosIniciales.ALTURA_MINIMA) {
            MENSAJE_ALTURA_NEGATIVA
        } else {
            null
        }
    }

    /**
     * Valida el texto de la velocidad inicial.
     *
     * @param texto el texto ingresado en el campo de velocidad.
     * @return el mensaje de error, o `null` si el valor es válido.
     */
    fun validarVelocidad(texto: String): String? {
        val velocidad = parsearNumero(texto) ?: return MENSAJE_FORMATO_NUMERICO
        return if (velocidad < ParametrosIniciales.VELOCIDAD_MINIMA) {
            MENSAJE_VELOCIDAD_NEGATIVA
        } else {
            null
        }
    }

    /**
     * Valida ambos parámetros en conjunto.
     *
     * @param textoAltura texto ingresado para la altura inicial.
     * @param textoVelocidad texto ingresado para la velocidad inicial.
     * @return [ResultadoValidacion.Exitoso] con los valores válidos o
     *         [ResultadoValidacion.Fallido] con los errores encontrados.
     */
    fun validar(textoAltura: String, textoVelocidad: String): ResultadoValidacion {
        val errores = mutableListOf<String>()

        val altura = parsearNumero(textoAltura)
        val velocidad = parsearNumero(textoVelocidad)

        if (altura == null) {
            errores += MENSAJE_FORMATO_NUMERICO
        } else if (altura < ParametrosIniciales.ALTURA_MINIMA) {
            errores += MENSAJE_ALTURA_NEGATIVA
        }

        if (velocidad == null) {
            errores += MENSAJE_FORMATO_NUMERICO
        } else if (velocidad < ParametrosIniciales.VELOCIDAD_MINIMA) {
            errores += MENSAJE_VELOCIDAD_NEGATIVA
        }

        return if (errores.isEmpty()) {
            ResultadoValidacion.Exitoso(ParametrosIniciales(altura!!, velocidad!!))
        } else {
            ResultadoValidacion.Fallido(errores)
        }
    }

    /**
     * Convierte el texto en un número finito, o devuelve `null`
     * si no puede interpretarse como un número válido.
     *
     * Se acepta tanto el punto como la coma decimal.
     */
    private fun parsearNumero(texto: String): Double? {
        val valor = texto.trim().replace(',', '.').toDoubleOrNull()
        return valor?.takeIf { it.isFinite() }
    }

    companion object {
        private const val MENSAJE_FORMATO_NUMERICO =
            "Ingrese un número válido (por ejemplo: 10,5)."
        private const val MENSAJE_ALTURA_NEGATIVA =
            "La altura inicial no puede ser negativa."
        private const val MENSAJE_VELOCIDAD_NEGATIVA =
            "La velocidad inicial no puede ser negativa."
    }
}