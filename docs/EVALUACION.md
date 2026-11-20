<!-- evaluacion:inicio -->
# Evaluación — etec-francucchiari/simulador-caida-libre-francucchiari

> Documento **generado automáticamente** por la cátedra de Programación II (RepoSense + análisis de calidad POO/SOLID). **No editar a mano**: se regenera en cada evaluación.
> Rama evaluada: `main`. Análisis **heurístico** (regex + métricas): es una criba para orientar la revisión, **no una nota**.
> Generado: 2026-11-20.

## 1. Veredicto

| Aspecto | Resultado |
|---|---|
| Índice de calidad POO/SOLID | **91/100** |
| Código (LOC) | 7,113 |
| Archivos de código | 145 |
| Clases (bien usadas) | 143 (99 OK) |
| Código dentro de clases (%POO) | 99% |
| Funciones fuera de clases | 0 |
| Duplicación lógica | 0.6% |
| Señales S/O/L/I/D | 16/0/0/0/2 |

> Este informe **describe** lo que el análisis midió con los criterios ya establecidos de la materia. No es una nota.

### Semáforo de aprobación

Criterios de `CRITERIOS-CALIDAD.md`. **Aprobar** se juega con el perfil `estandar`; **destacar**, con el `exigente` (meta de excelencia).

| Semáforo | Resultado |
|---|---|
| Aprobar (`estandar`) | **no cumple** — 3/4 |
| Destacar (`exigente`) | **no cumple** — 4/5 |

**Criterios para aprobar**

| Criterio | Requisito | Tu resultado | Cumple |
|---|---|---|---|
| Código dentro de clases (%POO) | ≥ 85% | 99% | sí |
| Duplicación lógica | ≤ 10% | 0.6% | sí |
| Funciones fuera de clases | ≤ 1 | 0 | sí |
| Señales POO/SOLID críticas | ninguna | Baja cohesión (LCOM) | no |

**Criterios para destacar**

| Criterio | Requisito | Tu resultado | Cumple |
|---|---|---|---|
| Código dentro de clases (%POO) | ≥ 95% | 99% | sí |
| Duplicación lógica | ≤ 5% | 0.6% | sí |
| Funciones fuera de clases | ≤ 1 | 0 | sí |
| Señales POO/SOLID críticas | ninguna | Baja cohesión (LCOM), Acoplamiento alto (CBO) | no |
| Índice de calidad POO/SOLID | ≥ 60 | 91/100 | sí |

### Cómo se calcula el %POO (y por qué no sube solo)

`%POO = líneas de lógica dentro de clases ÷ líneas de lógica totales`.

Se cuentan las líneas de **lógica y campos**; no cuentan `package`/`import`/`using`/anotaciones ni comentarios, Javadoc o docstrings. Es una **proporción**: si agregás código nuevo dentro de una clase, el numerador y el denominador crecen juntos y el porcentaje casi no cambia.

Sube cuando el código que hoy está **fuera** de clases pasa a ser método de una clase (o desaparece): funciones sueltas, `main`, constantes de módulo, scripts y lógica de archivos sueltos. También importa que ese trabajo esté **integrado en `main`**: la evaluación siempre mide `main`, así que lo que sigue en otra rama no se refleja todavía.

## 2. Autoría (RepoSense)

| Autor | Commits | Código (LOC) | Tests (LOC) | Docs (LOC) | Días activos | % del código |
|---|---:|---:|---:|---:|---:|---:|
| francucchiari-code | 16 | 6,511 | 1,595 | 1 | 14 | 100% |

> _El LOC de autoría lo calcula RepoSense sobre el historial y puede diferir del LOC de código del análisis de calidad (que mira solo el estado de `main`)._

## 3. Fallas detectadas

Reúne lo que el análisis marcó y lo que todavía falta para cada semáforo. Cuando la señal se puede asociar a archivos, se indican para orientar la revisión. No es una nota.

| Falla | Tu resultado | Requisito | Archivos |
|---|---|---|---|
| Señales POO/SOLID críticas | Baja cohesión (LCOM) | Aprobar: ninguna<br>Destacar: ninguna | `app/src/main/kotlin/org/example/fisica/ConfiguracionFisica.kt`, `app/src/main/kotlin/org/example/logica/ComposicionAplicacion.kt`, `app/src/main/kotlin/org/example/logica/ControladorDesafio.kt`, `app/src/main/kotlin/org/example/logica/DetallePasoFormula.kt`, `app/src/main/kotlin/org/example/logica/EjercicioCaidaLibre.kt`, `app/src/main/kotlin/org/example/logica/EstadoInstantaneo.kt` (+12 más) |
| Métodos/funciones largos o con muchos parámetros | **1** método(s)/función(es) largos o con muchos parámetros | Sin señales | `app/src/main/kotlin/org/example/fisica/ExplicadorFisicaPasoAPaso.kt` |

## 4. Aspectos evaluados

Cada aspecto indica **qué mide**, **por qué importa** y **tu resultado**. Cuando el análisis reportó una señal, se agregan preguntas para **investigar la causa** (no hay recetas: entender el porqué es parte de la evaluación).

### 1. Índice de calidad POO/SOLID (indice)

- **Qué mide:** Un resumen numérico (0-100) que combina la riqueza de clases bien usadas con la penalización por señales POO/SOLID y duplicación, y luego aplica topes duros.
- **Por qué importa:** Ordena y prioriza la revisión: un índice bajo indica varias señales que conviene mirar juntas.
- **Tu resultado:** índice **91/100**

### 2. Duplicación lógica (DRY) (duplicacion) — sin señales

- **Qué mide:** Porcentaje de bloques de LÓGICA repetidos. Quedan fuera imports/includes, anotaciones, declaraciones de campos y comentarios/Javadoc/docstrings: esos bloques son boilerplate, no lógica.
- **Por qué importa:** Cada copia de lógica se mantiene por separado: un arreglo hay que repetirlo y los defectos se multiplican.
- **Tu resultado:** **0.6%** de duplicación lógica

### 3. Adopción de POO (% de código en clases) (poo) — sin señales

- **Qué mide:** Proporción de las **líneas de lógica** que viven dentro de una clase. Quedan fuera `package`/`import`/`using`/anotaciones y comentarios/Javadoc/docstrings: no son código que pueda estar dentro de una clase y, si contaran, el porcentaje quedaría topado por el boilerplate de cada archivo.
- **Por qué importa:** El diseño orientado a objetos es el eje de la materia: el comportamiento debería vivir como método de una clase.
- **Tu resultado:** **99.3%** del código dentro de clases
- **Cómo leerlo:** Es una **proporción**, no un acumulado: agregar código dentro de una clase no la sube por sí solo (numerador y denominador crecen juntos). Sube cuando el código que está **fuera** de clases (funciones sueltas, `main`, constantes de módulo, scripts) pasa a ser método de una clase o deja de existir. La medición se hace sobre `main`: el trabajo que todavía vive en otra rama no se refleja hasta que se integra.

### 4. Funciones fuera de clases (sueltas) — sin señales

- **Qué mide:** Funciones de nivel de módulo que no pertenecen a ninguna clase (en esta materia el comportamiento debe ser método de una clase).
- **Por qué importa:** Son la forma más directa de código procedural en un proyecto que debería ser orientado a objetos.
- **Tu resultado:** **0** función(es) fuera de clases

### 5. Clase demasiado grande (SRP) (god) — sin señales

- **Qué mide:** Clases con demasiadas líneas o métodos de LÓGICA. No cuentan los getters/setters triviales ni la cantidad de campos por sí sola.
- **Por qué importa:** Una clase que hace de todo cambia por muchos motivos y cuesta entenderla y probarla.
- **Tu resultado:** **0** clase(s) demasiado grande(s)

### 6. Baja cohesión (LCOM) (cohesion) — con señales

- **Qué mide:** Métodos y campos de una clase que casi no se relacionan entre sí.
- **Por qué importa:** Una unidad que cambia por motivos distintos y comparte poca información suele esconder más de una responsabilidad.
- **Tu resultado:** **16** clase(s) con baja cohesión
- **Para investigar la causa:**
    - ¿Qué métodos de la clase no comparten estado con el resto?
    - ¿Cambian por motivos distintos según la tarea?
    - ¿Se pensó su responsabilidad antes de escribirla, o se fue agregando?

### 7. Complejidad alta (complejidad) — sin señales

- **Qué mide:** Complejidad ciclomática: cantidad de caminos posibles dentro de un método o función (condicionales, bucles, casos).
- **Por qué importa:** Muchos caminos = difícil de seguir y de probar; suelen esconderse casos sin cubrir.
- **Tu resultado:** **0** método(s)/función(es) con complejidad alta

### 8. Métodos/funciones largos o con muchos parámetros (tamano) — con señales

- **Qué mide:** Tamaño de los métodos/funciones y cantidad de parámetros.
- **Por qué importa:** Una función que hace varias cosas cuesta leerla, nombrarla y reutilizarla.
- **Tu resultado:** **1** método(s)/función(es) largos o con muchos parámetros
- **Para investigar la causa:**
    - ¿La función creció por acumulación de pasos?
    - ¿Agrupa operaciones que podrían describirse por separado?
    - ¿Los parámetros representan una misma idea (un objeto) o cosas sueltas?

### 9. Acoplamiento alto (CBO) (acoplamiento) — con señales

- **Qué mide:** Cuánto depende una clase de otros tipos del proyecto. No cuentan los tipos de la plataforma (biblioteca estándar, DOM, motor Godot) ni los métodos/propiedades propias.
- **Por qué importa:** Piezas muy enlazadas hacen que un cambio se propague y que sea difícil reemplazar partes.
- **Tu resultado:** **2** clase(s) muy acoplada(s)
- **Para investigar la causa:**
    - ¿La clase conoce detalles internos de muchas otras?
    - ¿Se puede cambiar una parte sin tocar las demás?
    - ¿Los colaboradores llegan como parámetros o se crean por dentro?

### 10. Herencia profunda (DIT) (herencia) — sin señales

- **Qué mide:** Profundidad de la cadena de herencia de una clase.
- **Por qué importa:** Jerarquías muy profundas vuelven difícil saber de dónde viene cada comportamiento.
- **Tu resultado:** **0** clase(s) con herencia profunda

### 11. Switch sobre tipos (OCP) (ocp) — sin señales

- **Qué mide:** Cadenas de dispatch por tipo (`else if ... is` / `else if ... instanceof`, `switch` sobre tipo o `typeof`). Un `is Tipo variable` aislado (pattern matching / cast seguro) NO cuenta como switch.
- **Por qué importa:** Cada tipo nuevo obliga a tocar varios lugares, en vez de extender con una implementación nueva.
- **Tu resultado:** **0** clase(s) con switch sobre tipos

### 12. Interfaz con demasiados métodos (ISP) (isp) — sin señales

- **Qué mide:** Interfaces o clases abstractas con muchos métodos declarados.
- **Por qué importa:** Obliga a implementar operaciones que no se usan y acopla a los clientes a métodos que no necesitan.
- **Tu resultado:** **0** interfaz(es) con demasiados métodos

### 13. Override vacío o 'no implementado' (LSP) (lsp) — sin señales

- **Qué mide:** Métodos sobrescritos que quedan vacíos o lanzan 'no implementado'.
- **Por qué importa:** Suele indicar que la jerarquía no representa bien el problema y rompe la sustitución entre clases.
- **Tu resultado:** **0** override(s) vacío(s) o 'no implementado'

### 14. Clase sin métodos (solo datos) (data) — sin señales

- **Qué mide:** Clases que solo acumulan campos y no tienen comportamiento.
- **Por qué importa:** Separar datos de comportamiento reparte por el proyecto la lógica que debería estar cerca de esos datos.
- **Tu resultado:** **0** clase(s) sin métodos (solo datos)

### 15. Campos mutables públicos (encapsulación) (encapsulacion) — sin señales

- **Qué mide:** Clases que exponen estado **modificable** desde afuera. No cuentan constantes (`static final`, `const`), campos `final`/`readonly`, propiedades `val` de Kotlin ni propiedades con `private set`.
- **Por qué importa:** Expone el estado interno y hace que cualquier cambio dependa de quién lo modifica desde afuera.
- **Tu resultado:** **0** clase(s) con campos mutables públicos

### 16. Clase solo con métodos estáticos (utilitaria) (static) — sin señales

- **Qué mide:** Clases donde todos los métodos son estáticos.
- **Por qué importa:** Es comportamiento procedural disfrazado de clase: no se instancia ni participa del modelo de objetos.
- **Tu resultado:** **0** clase(s) solo con métodos estáticos

### Archivos donde se originan las señales

> Se listan **19** hallazgo(s) localizados en **19** archivo(s). La columna `Línea` es la línea del archivo donde el análisis detectó la señal; `—` indica una señal de nivel repositorio (sin archivo propio).

| Aspecto | Archivo | Línea | Hallazgo |
|---|---|---:|---|
| Baja cohesión (LCOM) | `app/src/main/kotlin/org/example/fisica/ConfiguracionFisica.kt` | 3 | clase 'ConfiguracionFisica' con baja cohesión (39%) |
| Baja cohesión (LCOM) | `app/src/main/kotlin/org/example/logica/ComposicionAplicacion.kt` | 3 | clase 'ComposicionAplicacion' con baja cohesión (40%) |
| Baja cohesión (LCOM) | `app/src/main/kotlin/org/example/logica/ControladorDesafio.kt` | 3 | clase 'ControladorDesafio' con baja cohesión (42%) |
| Baja cohesión (LCOM) | `app/src/main/kotlin/org/example/logica/DetallePasoFormula.kt` | 3 | clase 'DetallePasoFormula' con baja cohesión (26%) |
| Baja cohesión (LCOM) | `app/src/main/kotlin/org/example/logica/EjercicioCaidaLibre.kt` | 3 | clase 'EjercicioCaidaLibre' con baja cohesión (37%) |
| Baja cohesión (LCOM) | `app/src/main/kotlin/org/example/logica/EstadoInstantaneo.kt` | 5 | clase 'EstadoInstantaneo' con baja cohesión (29%) |
| Baja cohesión (LCOM) | `app/src/main/kotlin/org/example/logica/GestorEstadoSimulacion.kt` | 5 | clase 'GestorEstadoSimulacion' con baja cohesión (41%) |
| Baja cohesión (LCOM) | `app/src/main/kotlin/org/example/logica/ModoSimulacion.kt` | 3 | clase 'ModoSimulacion' con baja cohesión (9%) |
| Baja cohesión (LCOM) | `app/src/main/kotlin/org/example/logica/NucleoSimulacion.kt` | 3 | clase 'NucleoSimulacion' con baja cohesión (5%) |
| Baja cohesión (LCOM) | `app/src/main/kotlin/org/example/logica/ObjetivoEjercicio.kt` | 3 | clase 'ObjetivoEjercicio' con baja cohesión (33%) |
| Baja cohesión (LCOM) | `app/src/main/kotlin/org/example/logica/OpcionFormula.kt` | 3 | clase 'OpcionFormula' con baja cohesión (30%) |
| Baja cohesión (LCOM) | `app/src/main/kotlin/org/example/logica/OpcionRespuesta.kt` | 5 | clase 'OpcionRespuesta' con baja cohesión (29%) |
| Baja cohesión (LCOM) | `app/src/main/kotlin/org/example/modelos/MemoriaParametrosSimulacion.kt` | 6 | clase 'MemoriaParametrosSimulacion' con baja cohesión (40%) |
| Baja cohesión (LCOM) | `app/src/main/kotlin/org/example/ui/CampoSustitucion.kt` | 5 | clase 'CampoSustitucion' con baja cohesión (33%) |
| Baja cohesión (LCOM) | `app/src/main/kotlin/org/example/ui/FotogramaCaida.kt` | 5 | clase 'FotogramaCaida' con baja cohesión (8%) |
| Baja cohesión (LCOM) | `app/src/main/kotlin/org/example/ui/MemoriaInstantaneaSimulacion.kt` | 6 | clase 'MemoriaInstantaneaSimulacion' con baja cohesión (42%) |
| Métodos/funciones largos o con muchos parámetros | `app/src/main/kotlin/org/example/fisica/ExplicadorFisicaPasoAPaso.kt` | 99 | método 'ExplicadorFisicaPasoAPaso.textosVelocidad' con 5 parámetros |
| Acoplamiento alto (CBO) | `app/src/main/kotlin/org/example/ui/FabricaControles.kt` | 6 | clase 'FabricaControles' muy acoplada (CBO=8) |
| Acoplamiento alto (CBO) | `app/src/main/kotlin/org/example/ui/ModoAvanzadoScreen.kt` | 6 | clase 'ModoAvanzadoScreen' muy acoplada (CBO=8) |

### Desglose de la columna S (SRP)

> La columna **S** no es un puntaje: cuenta **señales** de SRP. Aquí suma **16** entrada(s) sobre **16** clase(s) distinta(s).

| Señal | Entradas | Regla (perfil exigente) |
|---|---:|---|
| Cohesión baja | 16 | < 50 % de los pares de métodos comparten algún campo (con ≥ 4 métodos con lógica y ≥ 3 campos). |

**Clases con baja cohesión (16)**

- `NucleoSimulacion`: cohesión baja (5% de pares comparten campos)
- `FotogramaCaida`: cohesión baja (8% de pares comparten campos)
- `ModoSimulacion`: cohesión baja (9% de pares comparten campos)
- `DetallePasoFormula`: cohesión baja (26% de pares comparten campos)
- `OpcionRespuesta`: cohesión baja (29% de pares comparten campos)
- `EstadoInstantaneo`: cohesión baja (29% de pares comparten campos)
- `OpcionFormula`: cohesión baja (30% de pares comparten campos)
- `ObjetivoEjercicio`: cohesión baja (33% de pares comparten campos)
- `CampoSustitucion`: cohesión baja (33% de pares comparten campos)
- `EjercicioCaidaLibre`: cohesión baja (37% de pares comparten campos)
- `ConfiguracionFisica`: cohesión baja (39% de pares comparten campos)
- `ComposicionAplicacion`: cohesión baja (40% de pares comparten campos)
- `MemoriaParametrosSimulacion`: cohesión baja (40% de pares comparten campos)
- `GestorEstadoSimulacion`: cohesión baja (41% de pares comparten campos)
- `ControladorDesafio`: cohesión baja (42% de pares comparten campos)
- `MemoriaInstantaneaSimulacion`: cohesión baja (42% de pares comparten campos)

> Los **1** hallazgo(s) de métodos largos o con muchos parámetros se listan en «Métodos/funciones largos o con muchos parámetros» y **no suman** a la columna S.

> El análisis no excluye tests: una clase de test que supere los umbrales también suma.

## 5. Evolución por commit

Métricas calculadas sobre el estado de `main` en **cada commit** (20 commits con trabajo), del primero al último. La calidad usa el perfil `exigente`; el aporte de código lo mide RepoSense. El índice puede **bajar** al crecer el proyecto: se acumulan señales.

```mermaid
xychart-beta
    title "Índice POO/SOLID a lo largo de los commits"
    x-axis ["e4c7dc4", "f9cdb25", "21e6166", "6f7333f", "bdbdb04", "fbb8e8c", "6d00bd3", "54c463a", "ced6b32", "89325b5", "78e89ac", "8a543a5", "33d96dd", "96ce9c6", "b5fd2c3", "eeb8386", "2a2f4a1", "0353746", "82ec01d", "c3714a7"]
    y-axis "Índice" 0 --> 100
    line [0, 0, 58, 59, 81, 80, 77, 77, 78, 91, 91, 91, 91, 91, 91, 91, 91, 91, 91, 91]
```

| # | Fecha | Commit | Autor | LOC | Índice | %POO | Duplic. | Sueltas |
|---|---|---|---|---:|---:|---:|---:|---:|
| 1 | 2026-08-14 | `e4c7dc4` | francucchiari-code | 0 | 0 | 0% | 0.0% | 0 |
| 2 | 2026-08-31 | `f9cdb25` | dqmdz-etec | 0 | 0 | 0% | 0.0% | 0 |
| 3 | 2026-09-07 | `21e6166` | francucchiari-code | 172 | 58 | 64% | 0.0% | 0 |
| 4 | 2026-09-14 | `6f7333f` | francucchiari-code | 286 | 59 | 76% | 0.0% | 0 |
| 5 | 2026-09-25 | `bdbdb04` | francucchiari-code | 937 | 81 | 92% | 0.0% | 0 |
| 6 | 2026-10-02 | `fbb8e8c` | francucchiari-code | 1,647 | 80 | 96% | 0.0% | 0 |
| 7 | 2026-10-16 | `6d00bd3` | francucchiari-code | 3,453 | 77 | 98% | 1.7% | 0 |
| 8 | 2026-10-19 | `54c463a` | francucchiari-code | 5,932 | 77 | 99% | 3.6% | 0 |
| 9 | 2026-10-30 | `ced6b32` | francucchiari-code | 6,129 | 78 | 99% | 3.5% | 0 |
| 10 | 2026-11-02 | `89325b5` | Simulador ETEC | 7,136 | 91 | 99% | 0.6% | 0 |
| 11 | 2026-11-07 | `78e89ac` | dqmdz-etec | 7,136 | 91 | 99% | 0.6% | 0 |
| 12 | 2026-11-07 | `8a543a5` | dqmdz-etec | 7,136 | 91 | 99% | 0.6% | 0 |
| 13 | 2026-11-07 | `33d96dd` | dqmdz-etec | 7,136 | 91 | 99% | 0.6% | 0 |
| 14 | 2026-11-07 | `96ce9c6` | dqmdz-etec | 7,136 | 91 | 99% | 0.6% | 0 |
| 15 | 2026-11-13 | `b5fd2c3` | Simulador ETEC | 7,113 | 91 | 99% | 0.6% | 0 |
| 16 | 2026-11-13 | `eeb8386` | Simulador ETEC | 7,113 | 91 | 99% | 0.6% | 0 |
| 17 | 2026-11-13 | `2a2f4a1` | dqmdz-etec | 7,113 | 91 | 99% | 0.6% | 0 |
| 18 | 2026-11-14 | `0353746` | dqmdz-etec | 7,113 | 91 | 99% | 0.6% | 0 |
| 19 | 2026-11-16 | `82ec01d` | dqmdz-etec | 7,113 | 91 | 99% | 0.6% | 0 |
| 20 | 2026-11-17 | `c3714a7` | dqmdz-etec | 7,113 | 91 | 99% | 0.6% | 0 |

---
_Informe generado por la cátedra. Los criterios y umbrales de medición corresponden al perfil `exigente` de `calidad.py`; el semáforo de aprobación, a `CRITERIOS-CALIDAD.md` (ver `README.md`). Ante dudas, revisá el archivo señalado._
<!-- evaluacion:fin -->
