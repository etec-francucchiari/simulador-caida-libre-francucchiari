package org.example

class CatalogoEstilosEntorno(
    private val estilos: Map<String, EstiloEntorno> = RegistroEstilosEntorno().crearMapa(),
    private val estiloPorDefecto: EstiloEntorno = EstiloNeutro()
) : CatalogoEstilos {
    /**
     * Implementación instanciable de [CatalogoEstilos] que resuelve la paleta y
     * el renderizado ambiental de cada entorno gravitatorio.
     *
     * Utiliza un diccionario inyectado por constructor (provisto por defecto por
     * [RegistroEstilosEntorno]) y un estilo de respaldo ([EstiloNeutro]) para
     * los entornos personalizados, cumpliendo con los principios OCP y DIP sin
     * emplear condicionales por tipo de planeta.
     */

    /**
     * Devuelve el [EstiloEntorno] registrado para el nombre del [entorno]
     * recibido, o [estiloPorDefecto] si no posee un estilo específico.
     */
    override fun para(entorno: EntornoGravitatorio): EstiloEntorno {
        return estilos[entorno.nombre()] ?: estiloPorDefecto
    }

    /**
     * Comportamiento de dominio: indica si el [entorno] cuenta con una entrada
     * propia dentro del mapa de estilos planetarios predefinidos.
     */
    fun tieneEstiloPropio(entorno: EntornoGravitatorio): Boolean {
        return estilos.containsKey(entorno.nombre())
    }
}
