package org.example

class ValidadorParametrosIniciales(
    configuracion: ConfiguracionSimulacion = ConfiguracionFisica(),
    creador: ProveedorResultadoValidacion = FabricaResultadoValidacion()
) : ValidadorParametros {
    /**
     * Servicio de dominio que valida los textos ingresados por el usuario en los
     * campos de parámetros iniciales (altura h₀ y velocidad inicial v₀).
     *
     * Implementa el contrato [ValidadorParametros] y recibe por constructor las
     * abstracciones [ConfiguracionSimulacion] y [ProveedorResultadoValidacion]
     * (Principio de Inversión de Dependencias - DIP).
     */

    private val colaboradores = Pair(configuracion, creador)

    /**
     * Valida el texto correspondiente a la altura inicial y devuelve un mensaje
     * de error descriptivo, o `null` cuando el valor es válido.
     */
    override fun validarAltura(texto: String): String? {
        val altura = colaboradores.second.parsearNumero(texto)
            ?: return "Ingrese un número válido (por ejemplo: 10,5)."
        return if (!colaboradores.first.esAlturaValida(altura)) "La altura inicial no puede ser negativa." else null
    }

    /**
     * Valida el texto correspondiente a la velocidad inicial y devuelve un
     * mensaje de error descriptivo, o `null` cuando el valor es válido.
     */
    override fun validarVelocidad(texto: String): String? {
        val velocidad = colaboradores.second.parsearNumero(texto)
            ?: return "Ingrese un número válido (por ejemplo: 10,5)."
        return if (!colaboradores.first.esVelocidadValida(velocidad)) "La velocidad inicial no puede ser negativa." else null
    }

    /**
     * Evalúa conjuntamente ambos textos de entrada y construye polimórficamente
     * el [ResultadoValidacion] mediante el proveedor inyectado.
     */
    override fun validar(textoAltura: String, textoVelocidad: String): ResultadoValidacion {
        val errores = listOfNotNull(validarAltura(textoAltura), validarVelocidad(textoVelocidad))
        if (errores.isNotEmpty()) {
            return colaboradores.second.crearFallo(errores)
        }
        val h = colaboradores.second.parsearNumero(textoAltura) ?: 0.0
        val v = colaboradores.second.parsearNumero(textoVelocidad) ?: 0.0
        return colaboradores.second.crearExito(h, v)
    }
}
