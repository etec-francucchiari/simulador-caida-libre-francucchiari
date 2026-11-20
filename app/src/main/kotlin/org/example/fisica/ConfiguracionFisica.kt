package org.example

class ConfiguracionFisica(
    alturaPorDefectoInterna: Double = 100.0,
    velocidadPorDefectoInterna: Double = 0.0,
    alturaMinimaInterna: Double = 0.0,
    velocidadMinimaInterna: Double = 0.0
) : ConfiguracionSimulacion {
    /**
     * Implementación de dominio de la configuración física del simulador de
     * caída libre ([ConfiguracionSimulacion]).
     *
     * Encapsula de forma privada los parámetros iniciales por defecto y las
     * cotas mínimas físicamente admisibles para la altura y la velocidad
     * inicial, proporcionando reglas de validación, ajuste al rango permitido y
     * generación de resúmenes descriptivos.
     */

    private val parametrosConfigurados = doubleArrayOf(
        alturaPorDefectoInterna,
        velocidadPorDefectoInterna,
        alturaMinimaInterna,
        velocidadMinimaInterna
    )

    /**
     * Devuelve la altura inicial por defecto expresada en metros.
     */
    override fun alturaPorDefecto(): Double = parametrosConfigurados[0]

    /**
     * Devuelve la velocidad inicial por defecto expresada en metros por segundo.
     */
    override fun velocidadPorDefecto(): Double = parametrosConfigurados[1]

    /**
     * Devuelve la altura mínima permitida por el modelo físico (nivel del suelo).
     */
    override fun alturaMinima(): Double = parametrosConfigurados[2]

    /**
     * Devuelve la velocidad inicial mínima permitida en caída hacia abajo.
     */
    override fun velocidadMinima(): Double = parametrosConfigurados[3]

    /**
     * Comprueba si una [altura] es un número real finito mayor o igual que la
     * cota mínima configurada.
     */
    override fun esAlturaValida(altura: Double): Boolean {
        return altura.isFinite() && altura >= parametrosConfigurados[2]
    }

    /**
     * Comprueba si una [velocidad] es un número real finito mayor o igual que
     * la cota mínima configurada.
     */
    override fun esVelocidadValida(velocidad: Double): Boolean {
        return velocidad.isFinite() && velocidad >= parametrosConfigurados[3]
    }

    /**
     * Comportamiento de dominio: recorta o reemplaza una [altura] inválida por
     * la altura mínima admisible.
     */
    fun ajustarAltura(altura: Double): Double {
        return if (esAlturaValida(altura)) altura else parametrosConfigurados[2]
    }

    /**
     * Comportamiento de dominio: recorta o reemplaza una [velocidad] inválida
     * por la velocidad mínima admisible.
     */
    fun ajustarVelocidad(velocidad: Double): Double {
        return if (esVelocidadValida(velocidad)) velocidad else parametrosConfigurados[3]
    }

    /**
     * Comportamiento de dominio: construye un resumen textual legible con los
     * valores por defecto y los límites configurados.
     */
    fun resumen(): String {
        val hDef = parametrosConfigurados[0]
        val vDef = parametrosConfigurados[1]
        val hMin = parametrosConfigurados[2]
        val vMin = parametrosConfigurados[3]
        return "Altura por defecto: $hDef m; " +
            "velocidad por defecto: $vDef m/s; " +
            "mínimos: $hMin m y $vMin m/s."
    }
}
