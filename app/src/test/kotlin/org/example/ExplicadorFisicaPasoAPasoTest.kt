package org.example

import kotlin.test.*

class ExplicadorFisicaPasoAPasoTest {
    /**
     * Pruebas unitarias para el desglose algebraico paso a paso de la posición
     * y(t) y la velocidad inicial en [ExplicadorFisicaPasoAPaso].
     *
     * Verifica que cada [DetallePasoFormula] contenga la ecuación simbólica,
     * el reemplazo con los parámetros activos, la evaluación en el instante `t`,
     * los términos intermedios y el resultado final con unidades del SI.
     */

    private val explicador = ExplicadorFisicaPasoAPaso(CalculoCaidaLibre())

    /**
     * En t = 0 s desde h₀ = 100 m y en reposo, la explicación de posición debe
     * mostrar la fórmula general y concluir en y(0.00) = 100.00 m.
     */
    @Test
    fun explicarPosicionEnInicioDesdeReposo() {
        val paso = explicador.explicarPosicion(100.0, 0.0, 9.8, 0.0)
        assertEquals("y(t) = y₀ + v₀·t - ½·g·t²", paso.formulaGeneral())
        assertTrue(paso.formulaConValores().contains("100.00"))
        assertTrue(paso.formulaConValores().contains("9.80"))
        assertTrue(paso.sustitucionInstante().contains("y(0.00)"))
        assertTrue(paso.resultadoFinal().contains("100.00 m"))
    }

    /**
     * En t = 2.00 s con h₀ = 100 m, v₀ = 0 y g = 9.8 m/s², el término cuadrático
     * vale ½·9.8·4 = 19.60 m y la altura restante es 80.40 m.
     */
    @Test
    fun explicarPosicionEnInstanteIntermedioCalculaCorrectamente() {
        val paso = explicador.explicarPosicion(100.0, 0.0, 9.8, 2.0)
        assertTrue(paso.sustitucionInstante().contains("y(2.00)"))
        assertTrue(paso.evaluacionIntermedia().contains("19.60"))
        assertTrue(paso.resultadoFinal().contains("80.40 m"))
    }

    /**
     * Cuando existe velocidad inicial hacia abajo (v₀ = 10 m/s), se incorpora
     * con signo negativo (-10.00) en la convención vertical hacia arriba.
     */
    @Test
    fun explicarPosicionConVelocidadInicialHaciaAbajo() {
        val paso = explicador.explicarPosicion(50.0, 10.0, 9.8, 1.0)
        assertTrue(paso.formulaConValores().contains("-10.00"))
        assertTrue(paso.evaluacionIntermedia().contains("-10.00"))
        assertTrue(paso.evaluacionIntermedia().contains("4.90"))
        assertTrue(paso.resultadoFinal().contains("35.10 m"))
    }

    /**
     * Si el instante consultado supera el tiempo total de caída, el resultado
     * indica explícitamente el impacto en el suelo (y = 0.00 m).
     */
    @Test
    fun explicarPosicionAlColisionarConElSueloIndicaImpacto() {
        val paso = explicador.explicarPosicion(4.9, 0.0, 9.8, 1.5)
        assertTrue(paso.resultadoFinal().contains("0.00 m"))
        assertTrue(paso.resultadoFinal().contains("Impacto en el suelo"))
        assertTrue(paso.aclaracionDidactica().contains("alcanzado el suelo"))
    }

    /**
     * En t = 0 s partiendo del reposo, la velocidad instantánea explicada debe
     * ser 0.00 m/s.
     */
    @Test
    fun explicarVelocidadEnInicioDesdeReposoEsCero() {
        val paso = explicador.explicarVelocidad(100.0, 0.0, 9.8, 0.0)
        assertEquals("v(t) = v₀ - g·t", paso.formulaGeneral())
        assertTrue(paso.formulaConValores().contains("v(t) = 0.00 - (9.80)·t"))
        assertTrue(paso.resultadoFinal().contains("0.00 m/s"))
    }

    /**
     * En t = 1.50 s con g = 9.8 m/s², la velocidad vertical es -14.70 m/s y su
     * rapidez |v| = 14.70 m/s.
     */
    @Test
    fun explicarVelocidadEnInstanteIntermedioAumentaLinealmente() {
        val paso = explicador.explicarVelocidad(100.0, 0.0, 9.8, 1.5)
        assertTrue(paso.sustitucionInstante().contains("v(1.50)"))
        assertTrue(paso.evaluacionIntermedia().contains("14.70"))
        assertTrue(paso.resultadoFinal().contains("-14.70 m/s"))
        assertTrue(paso.resultadoFinal().contains("|v| = 14.70 m/s"))
    }
}
