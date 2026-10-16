package org.example

/**
 * Abstracción de comandos que modifican el estado de la simulación (DIP).
 *
 * Agrupa las operaciones de configuración y de flujo temporal que los
 * controladores de UI pueden invocar sin conocer la implementación concreta.
 */
interface ComandosSimulacion {

    /** Establece el entorno gravitatorio activo. */
    fun establecerPlaneta(planeta: EntornoGravitatorio)

    /** Establece la altura inicial en metros. */
    fun establecerAlturaInicial(altura: Double)

    /** Establece la velocidad inicial en m/s. */
    fun establecerVelocidadInicial(velocidad: Double)

    /** Inicia o reanuda la simulación. */
    fun iniciarSimulacion()

    /** Pausa la simulación. */
    fun pausarSimulacion()

    /** Reinicia el tiempo y los valores instantáneos. */
    fun reiniciarSimulacion()

    /**
     * Avanza el tiempo en [deltaSegundos].
     *
     * @return `true` si la simulación continúa en el aire.
     */
    fun avanzarTiempo(deltaSegundos: Double): Boolean
}