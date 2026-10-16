package org.example

/**
 * Contrato combinado del estado y los comandos de la simulación.
 *
 * Se usa como dependencia única del motor de animación y de las pantallas que
 * necesitan tanto leer como configurar la simulación.
 */
interface ContextoSimulacion : EstadoSimulacion, ComandosSimulacion