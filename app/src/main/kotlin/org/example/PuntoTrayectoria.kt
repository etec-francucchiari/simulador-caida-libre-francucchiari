package org.example

/**
 * Representa un punto de la trayectoria de caída libre en un instante dado.
 *
 * Estructura de datos inmutable que encapsula el estado físico del cuerpo
 * en un momento temporal específico: tiempo transcurrido, altura sobre
 * el suelo y velocidad instantánea.
 *
 * Al ser inmutable y no depender de JavaFX, puede compartirse de forma
 * segura entre la capa de cálculo físico y la capa de representación gráfica.
 *
 * @property tiempo tiempo transcurrido en segundos desde el inicio de la caída (t ≥ 0).
 * @property posicion altura instantánea sobre el suelo en metros (y ≥ 0).
 * @property velocidad magnitud de la velocidad instantánea hacia el suelo en m/s (v ≥ 0).
 */
data class PuntoTrayectoria(
    val tiempo: Double,
    val posicion: Double,
    val velocidad: Double
)
