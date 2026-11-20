package org.example

import javafx.beans.property.*
import javafx.beans.value.ObservableValue

class MemoriaParametrosSimulacion(
    configuracion: ConfiguracionSimulacion,
    planetaInicial: EntornoGravitatorio
) {
    /**
     * Encapsula las propiedades reactivas de los parámetros iniciales de caída libre:
     * entorno gravitatorio, altura inicial y velocidad inicial.
     *
     * Extraída de [GestorEstadoSimulacion] para maximizar la cohesión (LCOM) y
     * cumplir con el principio de responsabilidad única (SRP): esta clase gestiona
     * exclusivamente las condiciones de contorno previas al lanzamiento, mientras
     * que [MemoriaInstantaneaSimulacion] gestiona las magnitudes cinemáticas en curso.
     *
     * Todos los campos se declaran privados y solo se exponen mediante comandos y
     * consultas bien tipadas.
     */

    private val propiedades = Triple(
        SimpleObjectProperty<EntornoGravitatorio>(planetaInicial),
        SimpleDoubleProperty(configuracion.alturaPorDefecto()),
        SimpleDoubleProperty(configuracion.velocidadPorDefecto())
    )

    /**
     * Vincula una acción de reinicio automático que se ejecuta cada vez que cambia
     * cualquiera de los parámetros iniciales (entorno, altura o velocidad).
     *
     * @param alCambiar callback invocado tras mutar algún parámetro.
     */
    fun vincularReinicio(alCambiar: () -> Unit) {
        propiedades.second.addListener { _, _, _ -> alCambiar() }
        propiedades.third.addListener { _, _, _ -> alCambiar() }
        propiedades.first.addListener { _, _, _ -> alCambiar() }
    }

    /**
     * Actualiza el entorno gravitatorio seleccionado para la simulación.
     *
     * @param planeta nuevo entorno gravitatorio activo.
     */
    fun establecerPlaneta(planeta: EntornoGravitatorio) {
        propiedades.first.set(planeta)
    }

    /**
     * Actualiza la altura inicial de lanzamiento en metros.
     *
     * @param altura altura inicial en metros (h₀ ≥ 0).
     */
    fun establecerAlturaInicial(altura: Double) {
        propiedades.second.set(altura)
    }

    /**
     * Actualiza la velocidad inicial vertical hacia abajo en m/s.
     *
     * @param velocidad velocidad inicial en m/s (v₀ ≥ 0).
     */
    fun establecerVelocidadInicial(velocidad: Double) {
        propiedades.third.set(velocidad)
    }

    /**
     * Consulta sincrónica del entorno gravitatorio activo.
     */
    fun obtenerPlaneta(): EntornoGravitatorio = propiedades.first.get()

    /**
     * Consulta sincrónica de la altura inicial en metros.
     */
    fun obtenerAlturaInicial(): Double = propiedades.second.get()

    /**
     * Consulta sincrónica de la velocidad inicial en m/s.
     */
    fun obtenerVelocidadInicial(): Double = propiedades.third.get()

    /**
     * Propiedad observable de solo lectura del entorno gravitatorio.
     */
    fun planetaObservable(): ObservableValue<EntornoGravitatorio> = propiedades.first

    /**
     * Propiedad observable de solo lectura de la altura inicial.
     */
    fun alturaInicialObservable(): ObservableValue<Number> = propiedades.second

    /**
     * Propiedad observable de solo lectura de la velocidad inicial.
     */
    fun velocidadInicialObservable(): ObservableValue<Number> = propiedades.third
}
