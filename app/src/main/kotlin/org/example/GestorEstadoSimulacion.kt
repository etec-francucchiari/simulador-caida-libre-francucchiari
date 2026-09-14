package org.example

import javafx.beans.property.SimpleObjectProperty

/**
 * Gestor de estado reactivo del simulador de caída libre.
 *
 * Centraliza el estado de la simulación (por ahora, el planeta seleccionado)
 * y expone propiedades JavaFX observables para que la interfaz gráfica
 * se actualice automáticamente ante cambios de estado.
 *
 * Principios aplicados:
 * - **Encapsulamiento**: el estado interno es privado; solo se accede vía properties.
 * - **Reactividad**: las UI escuchan los cambios sin polling manual.
 * - **Desacoplamiento**: la lógica de negocio no conoce JavaFX Controls,
 *   solo JavaFX Beans (propiedades observables).
 */
class GestorEstadoSimulacion {

    /**
     * Propiedad observable que contiene el planeta activo de la simulación.
     * La UI puede vincular (bind) cualquier control a esta propiedad
     * para recibir notificaciones automáticas al cambiar el planeta.
     */
    val planetaActivoProperty: SimpleObjectProperty<Planeta> =
        SimpleObjectProperty(Planeta.TIERRA)

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
}
