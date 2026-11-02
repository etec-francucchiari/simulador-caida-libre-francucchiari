package org.example

class ConfiguracionFisica(
    private val alturaPorDefectoInterna: Double = 100.0,
    private val velocidadPorDefectoInterna: Double = 0.0,
    private val alturaMinimaInterna: Double = 0.0,
    private val velocidadMinimaInterna: Double = 0.0
) : ConfiguracionSimulacion {
    /**
     * Implementación de dominio de la configuración física del simulador de
     * caída libre ([ConfiguracionSimulacion]).
     *
     * Encapsula de forma privada los parámetros iniciales por defecto y las
     * cotas mínimas físicamente admisibles para la altura y la velocidad
     * inicial, proporcionando reglas de validación, ajuste al rango permitido y
     * generación de resúmenes descriptivos.
     *
     * @property alturaPorDefectoInterna altura inicial por defecto en metros (100.0 m).
     * @property velocidadPorDefectoInterna velocidad inicial por defecto en m/s (0.0 m/s).
     * @property alturaMinimaInterna cota inferior permitida para la altura (0.0 m).
     * @property velocidadMinimaInterna cota inferior permitida para la velocidad (0.0 m/s).
     */

    /**
     * Devuelve la altura inicial por defecto expresada en metros.
     */
    override fun alturaPorDefecto(): Double = alturaPorDefectoInterna

    /**
     * Devuelve la velocidad inicial por defecto expresada en metros por segundo.
     */
    override fun velocidadPorDefecto(): Double = velocidadPorDefectoInterna

    /**
     * Devuelve la altura mínima permitida por el modelo físico (nivel del suelo).
     */
    override fun alturaMinima(): Double = alturaMinimaInterna

    /**
     * Devuelve la velocidad inicial mínima permitida en caída hacia abajo.
     */
    override fun velocidadMinima(): Double = velocidadMinimaInterna

    /**
     * Comprueba si una [altura] es un número real finito mayor o igual que la
     * cota mínima configurada.
     */
    override fun esAlturaValida(altura: Double): Boolean {
        return altura.isFinite() && altura >= alturaMinimaInterna
    }

    /**
     * Comprueba si una [velocidad] es un número real finito mayor o igual que
     * la cota mínima configurada.
     */
    override fun esVelocidadValida(velocidad: Double): Boolean {
        return velocidad.isFinite() && velocidad >= velocidadMinimaInterna
    }

    /**
     * Comportamiento de dominio: recorta o reemplaza una [altura] inválida por
     * la altura mínima admisible.
     */
    fun ajustarAltura(altura: Double): Double {
        return if (esAlturaValida(altura)) altura else alturaMinimaInterna
    }

    /**
     * Comportamiento de dominio: recorta o reemplaza una [velocidad] inválida
     * por la velocidad mínima admisible.
     */
    fun ajustarVelocidad(velocidad: Double): Double {
        return if (esVelocidadValida(velocidad)) velocidad else velocidadMinimaInterna
    }

    /**
     * Comportamiento de dominio: construye un resumen textual legible con los
     * valores por defecto y los límites configurados.
     */
    fun resumen(): String {
        return "Altura por defecto: $alturaPorDefectoInterna m; " +
            "velocidad por defecto: $velocidadPorDefectoInterna m/s; " +
            "mínimos: $alturaMinimaInterna m y $velocidadMinimaInterna m/s."
    }
}
