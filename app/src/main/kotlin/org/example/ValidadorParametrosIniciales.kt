package org.example

/**
 * Valida los textos ingresados por el usuario en los campos de parámetros
 * iniciales (altura y velocidad).
 *
 * Es una clase instanciable que recibe por constructor la [ConfiguracionFisica]
 * (inversión de dependencias) y expone su resultado mediante un contrato
 * [ResultadoValidacion] con implementaciones encapsuladas.
 */
class ValidadorParametrosIniciales(
    private val configuracion: ConfiguracionFisica = ConfiguracionFisica()
) {

    private val mensajeFormatoNumerico =
        "Ingrese un número válido (por ejemplo: 10,5)."
    private val mensajeAlturaNegativa =
        "La altura inicial no puede ser negativa."
    private val mensajeVelocidadNegativa =
        "La velocidad inicial no puede ser negativa."

    /**
     * Resultado de la validación. Contrato común con dos implementaciones
     * encapsuladas: [Exitoso] y [Fallido].
     */
    sealed interface ResultadoValidacion {

        /** Devuelve verdadero si los valores ingresados son válidos. */
        fun esValido(): Boolean

        /** Resultado exitoso con los [ParametrosIniciales] obtenidos. */
        class Exitoso(
            private val parametrosInternos: ParametrosIniciales
        ) : ResultadoValidacion {
            /** Parámetros válidos resultantes. */
            fun parametros(): ParametrosIniciales = parametrosInternos
            override fun esValido(): Boolean = true
        }

        /** Resultado fallido con los mensajes de error legibles. */
        class Fallido(
            private val erroresInternos: List<String>
        ) : ResultadoValidacion {
            /** Errores encontrados durante la validación. */
            fun errores(): List<String> = erroresInternos
            override fun esValido(): Boolean = false
        }
    }

    /**
     * Valida el texto de la altura inicial.
     *
     * @return mensaje de error, o `null` si el valor es válido.
     */
    fun validarAltura(texto: String): String? {
        val altura = parsearNumero(texto) ?: return mensajeFormatoNumerico
        return if (!configuracion.esAlturaValida(altura)) mensajeAlturaNegativa else null
    }

    /**
     * Valida el texto de la velocidad inicial.
     *
     * @return mensaje de error, o `null` si el valor es válido.
     */
    fun validarVelocidad(texto: String): String? {
        val velocidad = parsearNumero(texto) ?: return mensajeFormatoNumerico
        return if (!configuracion.esVelocidadValida(velocidad)) mensajeVelocidadNegativa else null
    }

    /**
     * Valida ambos parámetros en conjunto.
     */
    fun validar(textoAltura: String, textoVelocidad: String): ResultadoValidacion {
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
            ResultadoValidacion.Exitoso(ParametrosIniciales(altura!!, velocidad!!))
        } else {
            ResultadoValidacion.Fallido(errores)
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