package org.example

import java.util.Random

/**
 * Servicio instanciable que genera ejercicios de caída libre aleatorios o
 * configurados para el Modo Avanzado.
 *
 * Recibe por constructor el catálogo de entornos, el servicio de física y una
 * fuente de aleatoriedad ([Random]) inyectable, lo que permite reproducir
 * escenarios de forma determinista en las pruebas (DIP).
 */
class GeneradorEjercicios(
    private val catalogo: CatalogoEntornos,
    private val calculo: CalculadoraCaidaLibre,
    private val aleatorio: Random = Random()
) : GeneradorDeEjercicios {

    private val alturasPosibles = listOf(20.0, 30.0, 45.0, 50.0, 60.0, 80.0, 100.0)
    private val velocidadesConImpulso = listOf(2.0, 3.0, 5.0, 8.0)
    private val velocidadesDesdeReposo = listOf(0.0, 2.0, 5.0)

    /**
     * Comportamiento de dominio: construye un ejercicio aleatorio con un
     * escenario físico válido.
     *
     * Para los objetivos de tiempo y velocidad se garantiza v₀ > 0, de modo
     * que los distractores basados en "ignorar v₀" nunca coincidan con la
     * respuesta correcta.
     */
    override fun generar(): EjercicioCaidaLibre {
        val entornos = catalogo.predefinidos()
        val entorno = entornos[aleatorio.nextInt(entornos.size)]
        val objetivo = ObjetivoEjercicio.entries[aleatorio.nextInt(ObjetivoEjercicio.entries.size)]
        val altura = alturasPosibles[aleatorio.nextInt(alturasPosibles.size)]

        val exigeImpulso = objetivo.esTiempo() || objetivo.esVelocidad()
        val velocidad = if (exigeImpulso) {
            velocidadesConImpulso[aleatorio.nextInt(velocidadesConImpulso.size)]
        } else {
            velocidadesDesdeReposo[aleatorio.nextInt(velocidadesDesdeReposo.size)]
        }

        return construir(entorno, altura, velocidad, objetivo)
    }

    /**
     * Comportamiento de dominio: construye un ejercicio con un escenario
     * explícitamente configurado.
     */
    override fun generarConfigurado(
        entorno: EntornoGravitatorio,
        alturaInicial: Double,
        velocidadInicial: Double,
        objetivo: ObjetivoEjercicio
    ): EjercicioCaidaLibre =
        construir(entorno, alturaInicial, velocidadInicial, objetivo)

    private fun construir(
        entorno: EntornoGravitatorio,
        altura: Double,
        velocidad: Double,
        objetivo: ObjetivoEjercicio
    ): EjercicioCaidaLibre {
        val gravedad = entorno.gravedad()
        val tiempoTotal = calculo.calcularTiempoCaida(altura, velocidad, gravedad) ?: 1.0

        val tiempoEvaluacion = if (objetivo.esTiempo()) {
            tiempoTotal
        } else {
            val fraccion = 0.3 + aleatorio.nextDouble() * 0.5
            redondear(tiempoTotal * fraccion)
        }

        return EjercicioCaidaLibre(
            entornoInterno = entorno,
            parametrosInternos = ParametrosIniciales(altura, velocidad),
            objetivoInterno = objetivo,
            tiempoEvaluacionInterno = tiempoEvaluacion,
            calculoInterno = calculo
        )
    }

    private fun redondear(valor: Double): Double =
        kotlin.math.round(valor * 100.0) / 100.0
}
