package org.example

interface ControlReproduccion {
    /**
     * Contrato de control de reproducción temporal de la simulación consumido
     * por las barras de herramientas y las pantallas (Principio de Inversión de
     * Dependencias - DIP).
     *
     * Abstrae el bucle de fotogramas para que [BarraControlesSimulacion] y
     * [SincronizadorEscenarioDesafio] operen sin acoplarse a la implementación
     * concreta [ControladorSimulacion].
     */

    /**
     * Conmuta entre los estados de ejecución en curso y pausa según el estado
     * activo de la simulación.
     */
    fun alternarEjecucion()

    /**
     * Inicia o reanuda el avance temporal de la caída libre, reiniciando desde
     * la altura inicial si el cuerpo ya había tocado el suelo.
     */
    fun iniciar()

    /**
     * Detiene temporalmente el avance de la simulación conservando el instante
     * actual.
     */
    fun pausar()

    /**
     * Detiene el temporizador y restablece el tiempo transcurrido a `t = 0 s`,
     * restaurando la altura y velocidad iniciales configuradas.
     */
    fun reiniciar()
}
