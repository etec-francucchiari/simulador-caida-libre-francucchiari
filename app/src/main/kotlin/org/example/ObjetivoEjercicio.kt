package org.example

enum class ObjetivoEjercicio(
    private val descripcionInterna: String,
    private val variableInterna: String,
    private val unidadInterna: String
) {
    CALCULAR_POSICION(
        descripcionInterna = "calcular la posición (altura) del objeto",
        variableInterna = "y",
        unidadInterna = "m"
    ),
    CALCULAR_VELOCIDAD(
        descripcionInterna = "calcular la velocidad del objeto",
        variableInterna = "v",
        unidadInterna = "m/s"
    ),
    CALCULAR_TIEMPO(
        descripcionInterna = "calcular el tiempo total de caída",
        variableInterna = "t",
        unidadInterna = "s"
    );

    /**
     * Enumerador de dominio que clasifica la magnitud física incógnita que el
     * estudiante debe resolver en un [EjercicioCaidaLibre] del Modo Avanzado:
     * - [CALCULAR_POSICION]: determinar la altura instantánea `y(t)` en metros.
     * - [CALCULAR_VELOCIDAD]: determinar la rapidez `v(t)` en metros por segundo.
     * - [CALCULAR_TIEMPO]: determinar el tiempo total de caída libre en segundos.
     *
     * Conserva sus atributos como campos privados inmutables y ofrece predicados
     * de dominio ([esPosicion], [esVelocidad], [esTiempo]) para orientar la
     * generación de fórmulas y distractores sin condicionales acoplados.
     */

    /**
     * Devuelve la descripción didáctica del objetivo para insertar en el
     * enunciado del problema.
     */
    fun descripcion(): String = descripcionInterna

    /**
     * Devuelve el símbolo algebraico de la variable incógnita (`"y"`, `"v"` o `"t"`).
     */
    fun variable(): String = variableInterna

    /**
     * Devuelve la unidad del Sistema Internacional asociada al resultado esperado.
     */
    fun unidad(): String = unidadInterna

    /**
     * Comportamiento de dominio: indica si la incógnita del ejercicio es la
     * posición vertical instantánea `y(t)`.
     */
    fun esPosicion(): Boolean {
        return variableInterna == "y"
    }

    /**
     * Comportamiento de dominio: indica si la incógnita del ejercicio es la
     * velocidad instantánea `v(t)`.
     */
    fun esVelocidad(): Boolean {
        return variableInterna == "v"
    }

    /**
     * Comportamiento de dominio: indica si la incógnita del ejercicio es el
     * tiempo total de caída hasta tocar el suelo.
     */
    fun esTiempo(): Boolean {
        return variableInterna == "t"
    }

    /**
     * Representación textual legible del objetivo de aprendizaje.
     */
    override fun toString(): String {
        return descripcionInterna
    }
}
