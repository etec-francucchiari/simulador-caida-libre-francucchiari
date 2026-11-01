package org.example

import kotlin.test.*

class EstadoEjecucionTest {
    /**
     * Pruebas unitarias de la máquina de estados del ciclo de vida de la
     * simulación (Patrón State):
     * - [EstadoEjecucionEnCurso]
     * - [EstadoEjecucionPausado]
     * - [EstadoEjecucionReiniciado]
     * - [EstadoEjecucionEnModoEjercicio]
     *
     * Verifica que cada estado concreto responda polimórficamente a las
     * transiciones `iniciar()`, `pausar()`, `alternar()`, `reiniciar()` y
     * `debeAvanzar(deltaSegundos)`.
     */

    private val nucleo = NucleoSimulacion()

    /**
     * El estado en curso debe reportarse activo, autorizar el avance para deltas
     * positivos, transicionar a pausado al alternar y volver a en curso al
     * alternar por segunda vez.
     */
    @Test
    fun estadoEjecutandosePausaAlAlternar() {
        val estado = EstadoEjecucionEnCurso()
        assertTrue(estado.esActiva())
        assertEquals("Ejecutándose", estado.nombre())
        assertTrue(estado.debeAvanzar(0.05))
        assertFalse(estado.debeAvanzar(0.0))
        val pausado = estado.alternar()
        assertFalse(pausado.esActiva())
        assertFalse(pausado.debeAvanzar(0.05))
        val reanudado = pausado.alternar()
        assertTrue(reanudado.esActiva())
        assertTrue(reanudado.debeAvanzar(0.05))
    }

    /**
     * El estado reiniciado comienza inactivo, no avanza fotogramas y pasa al
     * estado en curso tanto al invocar `iniciar()` como `alternar()`.
     */
    @Test
    fun estadoReiniciadoIniciaLaEjecucion() {
        val reiniciado = EstadoEjecucionReiniciado()
        assertFalse(reiniciado.esActiva())
        assertEquals("Reiniciado", reiniciado.nombre())
        assertFalse(reiniciado.debeAvanzar(0.05))
        val iniciado = reiniciado.iniciar()
        assertTrue(iniciado.esActiva())
        assertTrue(iniciado.debeAvanzar(0.05))
        val alternado = reiniciado.alternar()
        assertTrue(alternado.esActiva())
        assertTrue(alternado.debeAvanzar(0.05))
        assertTrue(reiniciado.reiniciar().iniciar().esActiva())
    }

    /**
     * El estado en modo ejercicio conserva la referencia al desafío al pausarse
     * y reanudarse durante la validación experimental.
     */
    @Test
    fun estadoEnModoEjercicioPermitePausaYReanudaEnLaMismaFase() {
        val estado = EstadoEjecucionEnModoEjercicio(nucleo.desafio())
        assertFalse(estado.esActiva())
        val ejecutando = estado.iniciar()
        assertTrue(ejecutando.esActiva())
        assertTrue(ejecutando.debeAvanzar(0.05))
        val pausado = ejecutando.pausar()
        assertFalse(pausado.esActiva())
        assertFalse(pausado.debeAvanzar(0.05))
        val reanudado = pausado.iniciar()
        assertTrue(reanudado.esActiva())
        assertTrue(reanudado.debeAvanzar(0.05))
    }
}
