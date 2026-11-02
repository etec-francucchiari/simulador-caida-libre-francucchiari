package org.example

enum class ModoSimulacion(
    private val tituloInterno: String,
    private val descripcionInterna: String,
    private val nombreTarjetaInterno: String,
    private val resumenTarjetaInterno: String,
    private val colorBordeInterno: String,
    private val colorFondoInterno: String,
    private val iconoInterno: String
) {
    PRINCIPIANTE(
        tituloInterno = "Modo Principiante (Fórmulas paso a paso)",
        descripcionInterna = "Explicación física paso a paso con reemplazo de fórmulas MRUV y cálculo dinámico en tiempo real.",
        nombreTarjetaInterno = "Modo Principiante",
        resumenTarjetaInterno = "Guiado paso a paso con fórmulas del MRUV,\nsustitución de variables y cálculo en tiempo real.",
        colorBordeInterno = "#3b82f6",
        colorFondoInterno = "#eff6ff",
        iconoInterno = "📚"
    ),
    ESTANDAR(
        tituloInterno = "Modo Avanzado / Estándar",
        descripcionInterna = "Visualización directa de parámetros cinemáticos, gráficos e indicadores estándar.",
        nombreTarjetaInterno = "Modo Avanzado",
        resumenTarjetaInterno = "Desafío interactivo: elige la fórmula correcta,\nsustituye los valores, resuelve el cálculo manual\ny exporta los resultados en CSV o JSON.",
        colorBordeInterno = "#7c3aed",
        colorFondoInterno = "#f5f3ff",
        iconoInterno = "🔬"
    );

    /**
     * Enumerador de dominio que representa las modalidades pedagógicas de
     * operación del simulador de caída libre:
     * - [PRINCIPIANTE]: exploración asistida con selección de planeta, desglose
     *   algebraico paso a paso y curvas cinemáticas en tiempo real.
     * - [ESTANDAR]: desafío interactivo por etapas con validación experimental
     *   y exportación de muestras en CSV y JSON.
     *
     * Encapsula de forma privada sus textos descriptivos y su paleta cromática,
     * exponiendo predicados de dominio ([esPrincipiante], [esAvanzado]) para
     * evitar comparaciones de constantes en la interfaz gráfica.
     */

    /**
     * Devuelve el título completo del modo de simulación.
     */
    fun titulo(): String = tituloInterno

    /**
     * Devuelve la descripción pedagógica detallada de la modalidad.
     */
    fun descripcion(): String = descripcionInterna

    /**
     * Devuelve el nombre corto mostrado en la cabecera de la tarjeta selectora.
     */
    fun nombreTarjeta(): String = nombreTarjetaInterno

    /**
     * Devuelve el resumen multilínea presentado en la pantalla de bienvenida.
     */
    fun resumenTarjeta(): String = resumenTarjetaInterno

    /**
     * Devuelve el color hexadecimal del borde y acentos de la tarjeta.
     */
    fun colorBorde(): String = colorBordeInterno

    /**
     * Devuelve el color hexadecimal de fondo suave de la tarjeta.
     */
    fun colorFondo(): String = colorFondoInterno

    /**
     * Devuelve el icono representativo de la modalidad.
     */
    fun icono(): String = iconoInterno

    /**
     * Comportamiento de dominio: indica si corresponde al modo guiado para
     * principiantes.
     */
    fun esPrincipiante(): Boolean {
        return iconoInterno == "📚"
    }

    /**
     * Comportamiento de dominio: indica si corresponde al modo de desafío
     * avanzado / estándar.
     */
    fun esAvanzado(): Boolean {
        return iconoInterno == "🔬"
    }

    /**
     * Devuelve el título legible como representación textual por defecto.
     */
    override fun toString(): String {
        return tituloInterno
    }
}
