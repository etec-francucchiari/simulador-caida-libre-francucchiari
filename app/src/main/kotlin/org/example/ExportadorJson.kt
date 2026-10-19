package org.example

import java.io.File
import java.util.Locale

/**
 * Exportador de la tabla de datos a formato JSON.
 *
 * Genera un objeto con los metadatos del entorno y los parámetros iniciales,
 * junto con un arreglo `datos` con las filas cinemáticas (t, y, v, a).
 */
class ExportadorJson : ExportadorDatos {

    private val formatoDecimal: String = "%.4f"

    override fun extension(): String = "json"

    override fun descripcion(): String = "Archivo JSON (.json)"

    override fun exportar(muestra: MuestraDatosSimulacion, archivo: File) {
        val constructor = StringBuilder()
        constructor.append("{\n")
        constructor.append("  \"entorno\": \"").append(escapar(muestra.entorno().nombre())).append("\",\n")
        constructor.append("  \"gravedad\": ").append(numero(muestra.entorno().gravedad())).append(",\n")
        constructor.append("  \"alturaInicial\": ").append(numero(muestra.parametros().alturaInicial())).append(",\n")
        constructor.append("  \"velocidadInicial\": ").append(numero(muestra.parametros().velocidadInicial())).append(",\n")
        constructor.append("  \"datos\": [\n")

        val filas = muestra.filas()
        filas.forEachIndexed { indice, fila ->
            constructor.append("    { \"t\": ").append(numero(fila.tiempo()))
                .append(", \"y\": ").append(numero(fila.posicion()))
                .append(", \"v\": ").append(numero(fila.velocidad()))
                .append(", \"a\": ").append(numero(fila.aceleracion())).append(" }")
            if (indice < filas.size - 1) constructor.append(",")
            constructor.append("\n")
        }

        constructor.append("  ]\n")
        constructor.append("}\n")

        archivo.writeText(constructor.toString(), Charsets.UTF_8)
    }

    private fun numero(valor: Double): String =
        String.format(Locale.ROOT, formatoDecimal, valor)

    private fun escapar(texto: String): String =
        texto.replace("\\", "\\\\").replace("\"", "\\\"")
}
