package org.example

interface ComandosSimulacion {
    /**
     * Abstracción de los comandos que mutan el estado de la simulación de caída
     * libre (Principio de Segregación de Interfaces - ISP y Principio de
     * Inversión de Dependencias - DIP).
     *
     * Separa las operaciones de escritura y control del flujo temporal respecto
     * de las consultas reactivas de solo lectura definidas en [EstadoSimulacion],
     * de modo que los paneles meramente informativos no dependan de métodos de
     * modificación que no utilizan.
     */

    /**
     * Establece el [planeta] o entorno gravitatorio activo y reinicia el
     * tiempo transcurrido al origen (`t = 0 s`).
     */
    fun establecerPlaneta(planeta: EntornoGravitatorio)

    /**
     * Actualiza la [altura] inicial de lanzamiento en metros y reinicia el
     * estado instantáneo de la simulación.
     */
    fun establecerAlturaInicial(altura: Double)

    /**
     * Actualiza la [velocidad] inicial hacia abajo en metros por segundo y
     * reinicia el estado instantáneo de la simulación.
     */
    fun establecerVelocidadInicial(velocidad: Double)

    /**
     * Inicia o reanuda la simulación siempre que la altura inicial sea positiva,
     * reiniciando desde el tope si el cuerpo ya se encontraba en el suelo.
     */
    fun iniciarSimulacion()

    /**
     * Pausa el avance temporal conservando las magnitudes instantáneas actuales.
     */
    fun pausarSimulacion()

    /**
     * Restablece el tiempo actual a `0.0 s`, la altura actual a `h₀` y la
     * velocidad actual a `v₀`, deteniendo la ejecución activa.
     */
    fun reiniciarSimulacion()

    /**
     * Avanza el tiempo de la simulación en [deltaSegundos] cuando el estado de
     * ejecución lo autoriza, devolviendo `true` si el móvil continúa en el aire.
     */
    fun avanzarTiempo(deltaSegundos: Double): Boolean

    /**
     * Aplica un [nuevoEstado] de la máquina de estados ([EstadoEjecucion]) y
     * sincroniza el indicador observable de ejecución activa.
     */
    fun cambiarEstado(nuevoEstado: EstadoEjecucion): EstadoEjecucion
}
