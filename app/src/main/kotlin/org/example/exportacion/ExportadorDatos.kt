package org.example

import java.io.File

interface ExportadorDatos {
    /**
     * Contrato de estrategia de exportación de datos cinemáticos de la
     * simulación (Patrón Strategy / Principio de Inversión de Dependencias).
     *
     * Permite incorporar nuevos formatos de serialización ([ExportadorCsv],
     * [ExportadorJson], etc.) sin modificar el servicio de muestreo ni la
     * interfaz gráfica de usuario (OCP).
     */

    /**
     * Devuelve la extensión de archivo canónica asociada al formato en
     * minúsculas y sin punto inicial (por ejemplo, `"csv"` o `"json"`).
     */
    fun extension(): String

    /**
     * Devuelve una descripción legible del formato para mostrar en el filtro
     * del selector de archivos.
     */
    fun descripcion(): String

    /**
     * Serializa y escribe el contenido completo de la [muestra] cinemática en
     * el [archivo] de destino especificado.
     *
     * @param muestra tabla de datos con entorno, parámetros iniciales y filas.
     * @param archivo destino en el sistema de archivos donde guardar la salida.
     * @throws java.io.IOException si ocurre un error de entrada/salida en disco.
     */
    fun exportar(muestra: MuestraDatosSimulacion, archivo: File)
}
