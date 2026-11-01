package org.example

import kotlin.test.*

class ExplicadorFisicaVelocidadYTiempoTest {
    /**
     * Pruebas unitarias para la explicación didáctica de la velocidad con impulso
     * inicial, la velocidad de impacto y el tiempo total de caída en
     * [ExplicadorFisicaPasoAPaso].
     */

    private val explicador = ExplicadorFisicaPasoAPaso(CalculoCaidaLibre())

    /**
     * Con velocidad inicial hacia abajo v₀ = 5.0 m/s y g = 9.8 m/s², en t = 1.0 s
     * la velocidad es -14.80 m/s (|v| = 14.80 m/s).
     */
    @Test
    fun explicarVelocidadConVelocidadInicial() {
        val paso = explicador.explicarVelocidad(100.0, 5.0, 9.8, 1.0)
        assertTrue(paso.formulaConValores().contains("-5.00"))
        assertTrue(paso.resultadoFinal().contains("-14.80 m/s"))
        assertTrue(paso.resultadoFinal().contains("|v| = 14.80 m/s"))
    }

    /**
     * Tras colisionar con el suelo, la explicación de velocidad exhibe el valor
     * alcanzado en el instante de impacto y su aclaración pedagógica.
     */
    @Test
    fun explicarVelocidadAlImpactarMuestraVelocidadDeImpacto() {
        val paso = explicador.explicarVelocidad(4.9, 0.0, 9.8, 2.0)
        assertTrue(paso.resultadoFinal().contains("9.80 m/s"))
        assertTrue(paso.aclaracionDidactica().contains("impactar contra el suelo"))
    }

    /**
     * Para caída desde el reposo (v₀ = 0), la explicación del tiempo total
     * utiliza la expresión simplificada t_final = √(2·y₀ / g).
     */
    @Test
    fun explicarTiempoCaidaParaCaidaDesdeReposo() {
        val paso = explicador.explicarTiempoCaida(4.9, 0.0, 9.8)
        assertEquals("t_final = √(2·y₀ / g)", paso.formulaGeneral())
        assertTrue(paso.formulaConValores().contains("√(2 · 4.90 / 9.80)"))
        assertTrue(paso.resultadoFinal().contains("1.00 s"))
    }

    /**
     * Compara el tiempo total de caída desde 100 m en Tierra, Luna y Júpiter,
     * comprobando que una mayor gravedad reduce el tiempo de vuelo.
     */
    @Test
    fun explicarTiempoCaidaEnDiferentesPlanetas() {
        val pasoTierra = explicador.explicarTiempoCaida(100.0, 0.0, Planeta.TIERRA.gravedad())
        assertTrue(pasoTierra.resultadoFinal().contains("4.52 s"))
        val pasoLuna = explicador.explicarTiempoCaida(100.0, 0.0, Planeta.LUNA.gravedad())
        assertTrue(pasoLuna.resultadoFinal().contains("11.11 s"))
        val pasoJupiter = explicador.explicarTiempoCaida(100.0, 0.0, Planeta.JUPITER.gravedad())
        assertTrue(pasoJupiter.resultadoFinal().contains("2.84 s"))
    }

    /**
     * Cuando v₀ > 0, la aclaración didáctica explica el uso de la fórmula
     * cuadrática general en lugar de la raíz directa.
     */
    @Test
    fun explicarTiempoCaidaConVelocidadInicialMuestraAclaracion() {
        val paso = explicador.explicarTiempoCaida(4.9, 2.0, 9.8)
        assertTrue(paso.resultadoFinal().contains("0.82 s"))
        assertTrue(paso.aclaracionDidactica().contains("ecuación cuadrática"))
    }
}
