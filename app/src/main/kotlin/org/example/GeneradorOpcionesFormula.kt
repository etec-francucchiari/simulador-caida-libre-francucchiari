package org.example

import java.util.Random

/**
 * Servicio instanciable que genera las opciones de fórmula (Etapa 1) de un
 * ejercicio: una correcta y dos distractores basados en errores típicos
 * (signos, potencias o factores olvidados).
 *
 * La aleatoriedad del orden se inyecta por constructor para poder reproducir
 * resultados en pruebas.
 */
class GeneradorOpcionesFormula(
    private val aleatorio: Random = Random()
) : GeneradorDeFormulas {

    /**
     * Comportamiento de dominio: devuelve las tres opciones de fórmula
     * (una correcta y dos distractores) en orden aleatorio.
     */
    override fun generar(ejercicio: EjercicioCaidaLibre): List<OpcionFormula> {
        val correcta = OpcionFormula(
            textoInterno = ejercicio.formulaSimbolica(),
            correctaInterna = true,
            retroalimentacionInterna =
                "Es la ecuación que modela ${ejercicio.objetivo().descripcion()}."
        )

        val distractores = when (ejercicio.objetivo()) {
            ObjetivoEjercicio.CALCULAR_POSICION -> listOf(
                OpcionFormula(
                    "y(t) = y₀ − v₀·t + ½·g·t²",
                    false,
                    "Error de signo: en una caída el término de la gravedad resta, no suma."
                ),
                OpcionFormula(
                    "y(t) = y₀ − v₀·t − g·t²",
                    false,
                    "Falta el factor ½ del término cuadrático: es ½·g·t², no g·t²."
                )
            )

            ObjetivoEjercicio.CALCULAR_VELOCIDAD -> listOf(
                OpcionFormula(
                    "v(t) = v₀ + g·t²",
                    false,
                    "Potencia incorrecta: la velocidad crece linealmente con t, no al cuadrado."
                ),
                OpcionFormula(
                    "v(t) = v₀ − g·t",
                    false,
                    "Error de signo: al caer, la rapidez aumenta, por lo que se suma g·t."
                )
            )

            ObjetivoEjercicio.CALCULAR_TIEMPO -> listOf(
                OpcionFormula(
                    "t = (−y₀ + √(y₀² + 2·g·v₀)) / g",
                    false,
                    "Se intercambiaron las variables: la constante 2 multiplica a g·y₀, no a g·v₀."
                ),
                OpcionFormula(
                    "t = (v₀ + √(v₀² + 2·g·y₀)) / g",
                    false,
                    "Error de signo: el término v₀ del numerador debe restarse, no sumarse."
                )
            )
        }

        val opciones = (distractores + correcta).toMutableList()
        opciones.shuffle(aleatorio)
        return opciones
    }
}
