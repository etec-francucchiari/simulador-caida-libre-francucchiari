package org.example

/**
 * Contrato del resultado de validar los parámetros iniciales.
 *
 * Se modela como interfaz sellada para que las vistas consuman el resultado
 * mediante polimorfismo (sin comprobaciones de tipo explícitas).
 */
sealed interface ResultadoValidacion {

    /** Indica si los valores ingresados son válidos. */
    fun esValido(): Boolean

    /** Parámetros validados, o `null` si la validación falló. */
    fun parametros(): ParametrosIniciales? = null

    /** Errores de validación, o lista vacía si la validación fue exitosa. */
    fun errores(): List<String> = emptyList()
}
