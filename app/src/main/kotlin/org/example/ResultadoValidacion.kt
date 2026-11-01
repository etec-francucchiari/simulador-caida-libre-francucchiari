package org.example

sealed interface ResultadoValidacion {
    /**
     * Contrato polimórfico del resultado de validar los parámetros iniciales de
     * la simulación (altura h₀ y velocidad inicial v₀).
     *
     * Se modela como una interfaz sellada (`sealed interface`) implementada por
     * [ValidacionExitosa] y [ValidacionFallida] para que las vistas y pruebas
     * consuman el resultado mediante despacho polimórfico sin emplear chequeos
     * de tipo (`is` / `instanceof`), respetando el Principio Abierto/Cerrado (OCP).
     */

    /**
     * Indica si todos los valores ingresados superaron las reglas de validación
     * física y de formato numérico.
     */
    fun esValido(): Boolean

    /**
     * Devuelve los [ParametrosIniciales] ya convertidos y validados cuando
     * [esValido] es `true`, o `null` cuando la validación resultó fallida.
     */
    fun parametros(): ParametrosIniciales? = null

    /**
     * Devuelve la lista de mensajes de error descriptivos cuando la validación
     * falló, o una lista vacía cuando la validación fue exitosa.
     */
    fun errores(): List<String> = emptyList()
}
