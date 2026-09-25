package org.example

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty

/**
 * Gestor de estado reactivo del simulador de caída libre.
 *
 * Centraliza el estado de la simulación: el planeta seleccionado y los
 * parámetros iniciales (altura h₀ y velocidad v₀). Expone propiedades
 * JavaFX observables para que la interfaz gráfica se actualice
 * automáticamente ante cambios de estado.
 *
 * Principios aplicados:
 * - **Encapsulamiento**: el estado interno es privado; solo se accede vía properties.
 * - **Reactividad**: las UI escuchan los cambios sin polling manual.
 * - **Desacoplamiento**: la lógica de negocio no conoce JavaFX Controls,
 *   solo JavaFX Beans (propiedades observables).
 */
class GestorEstadoSimulacion {

    /**
     * Propiedad observable con el planeta activo de la simulación.
     * La UI puede vincular (bind) cualquier control a esta propiedad
     * para recibir notificaciones automáticas al cambiar el planeta.
     */
    val planetaActivoProperty: SimpleObjectProperty<Planeta> =
        SimpleObjectProperty(Planeta.TIERRA)

    /**
     * Propiedad observable con la altura inicial (h₀) en metros.
     */
    val alturaInicialProperty: SimpleDoubleProperty =
        SimpleDoubleProperty(ParametrosIniciales.ALTURA_DEFECTO)

    /**
     * Propiedad observable con la velocidad inicial (v₀) en m/s.
     */
    val velocidadInicialProperty: SimpleDoubleProperty =
        SimpleDoubleProperty(ParametrosIniciales.VELOCIDAD_DEFECTO)

    /**
     * Obtiene el planeta actualmente seleccionado.
     */
    fun obtenerPlaneta(): Planeta = planetaActivoProperty.get()

    /**
     * Establece el planeta activo de la simulación.
     * Dispara la notificación a todos los listeners vinculados
     * a [planetaActivoProperty].
     *
     * @param planeta el nuevo planeta seleccionado.
     */
    fun establecerPlaneta(planeta: Planeta) {
        planetaActivoProperty.set(planeta)
    }

    /**
     * Obtiene la altura inicial configurada en metros.
     */
    fun obtenerAlturaInicial(): Double = alturaInicialProperty.get()

    /**
     * Establece la altura inicial de la simulación.
     * Dispara la notificación a los listeners vinculados
     * a [alturaInicialProperty].
     *
     * @param altura la nueva altura inicial en metros.
     */
    fun establecerAlturaInicial(altura: Double) {
        alturaInicialProperty.set(altura)
    }

    /**
     * Obtiene la velocidad inicial configurada en m/s.
     */
    fun obtenerVelocidadInicial(): Double = velocidadInicialProperty.get()

    /**
     * Establece la velocidad inicial de la simulación.
     * Dispara la notificación a los listeners vinculados
     * a [velocidadInicialProperty].
     *
     * @param velocidad la nueva velocidad inicial en m/s.
     */
    fun establecerVelocidadInicial(velocidad: Double) {
        velocidadInicialProperty.set(velocidad)
    }
}