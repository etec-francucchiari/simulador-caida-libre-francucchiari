<!-- evaluacion:inicio -->
# Evaluación — etec-francucchiari/simulador-caida-libre-francucchiari

> Documento **generado automáticamente** por la cátedra de Programación II (RepoSense + análisis de calidad POO/SOLID). **No editar a mano**: se regenera en cada evaluación.
> Rama evaluada: `main`. Análisis **heurístico** (regex + métricas): es una criba para orientar la revisión, **no una nota**.
> Generado: 2026-11-27.

## 1. Veredicto

| Aspecto | Resultado |
|---|---|
| Índice de calidad POO/SOLID | **98/100** |
| Commit evaluado | `90e2a49e64cf` (2026-11-25) |
| Código (LOC) | 7,260 |
| Archivos de código | 164 |
| Clases (bien usadas) | 162 (128 OK) |
| Código dentro de clases (%POO) | 99% |
| Funciones fuera de clases | 0 |
| Duplicación lógica | 0.6% |
| Señales S/O/L/I/D | 0/0/0/0/1 |

> Este informe **describe** lo que el análisis midió con los criterios ya establecidos de la materia. No es una nota.

### Semáforo de aprobación

Criterios de `CRITERIOS-CALIDAD.md`. **Aprobar** se juega con el perfil `estandar`; **destacar**, con el `exigente` (meta de excelencia).

| Semáforo | Resultado |
|---|---|
| Aprobar (`estandar`) | **cumple** — 4/4 |
| Destacar (`exigente`) | **no cumple** — 4/5 |

**Criterios para aprobar**

| Criterio | Requisito | Tu resultado | Cumple |
|---|---|---|---|
| Código dentro de clases (%POO) | ≥ 85% | 99% | sí |
| Duplicación lógica | ≤ 10% | 0.6% | sí |
| Funciones fuera de clases | ≤ 1 | 0 | sí |
| Señales POO/SOLID críticas | ninguna | ninguna | sí |

**Criterios para destacar**

| Criterio | Requisito | Tu resultado | Cumple |
|---|---|---|---|
| Código dentro de clases (%POO) | ≥ 95% | 99% | sí |
| Duplicación lógica | ≤ 5% | 0.6% | sí |
| Funciones fuera de clases | ≤ 1 | 0 | sí |
| Señales POO/SOLID críticas | ninguna | Acoplamiento alto (CBO) | no |
| Índice de calidad POO/SOLID | ≥ 60 | 98/100 | sí |

### Última actividad en cualquier rama

> Datos al cierre de la evaluación, antes de publicar este informe. Se revisan todas las ramas (`refs/heads/*`) y los heads de PR (`refs/pull/*/head`); **último commit de alumno** excluye la cuenta docente y los bots.

| | Fecha (UTC-3) | Usuario | Rama | Commit | Mensaje |
|---|---|---|---|---|---|
| Último commit | 2026-11-25 10:40 | @dqmdz-etec | `main` | `90e2a49e` | docs: actividad en días de clase (calendario oficial) |
| Último commit de alumno | 2026-11-20 08:37 | francucchiari-code | `21-correcciones-de-mas-erorres` | `cca1dd7d` | Correccion de mas errores |

### Cómo se calcula el %POO (y por qué no sube solo)

`%POO = líneas de lógica dentro de clases ÷ líneas de lógica totales`.

Se cuentan las líneas de **lógica y campos**; no cuentan `package`/`import`/`using`/anotaciones ni comentarios, Javadoc o docstrings. Es una **proporción**: si agregás código nuevo dentro de una clase, el numerador y el denominador crecen juntos y el porcentaje casi no cambia.

Sube cuando el código que hoy está **fuera** de clases pasa a ser método de una clase (o desaparece): funciones sueltas, `main`, constantes de módulo, scripts y lógica de archivos sueltos. También importa que ese trabajo esté **integrado en `main`**: la evaluación siempre mide `main`, así que lo que sigue en otra rama no se refleja todavía.

## 2. Autoría (RepoSense)

| Autor | Commits | Código (LOC) | Tests (LOC) | Docs (LOC) | Días activos | % del código |
|---|---:|---:|---:|---:|---:|---:|
| francucchiari-code | 17 | 6,993 | 1,381 | 1 | 15 | 100% |

> _El LOC de autoría lo calcula RepoSense sobre el historial y puede diferir del LOC de código del análisis de calidad (que mira solo el estado de `main`)._

## 3. Fallas detectadas

Reúne lo que el análisis marcó y lo que todavía falta para cada semáforo. Cuando la señal se puede asociar a archivos, se indican para orientar la revisión. No es una nota.

| Falla | Tu resultado | Requisito | Archivos |
|---|---|---|---|
| Señales POO/SOLID críticas | Acoplamiento alto (CBO) | Destacar: ninguna | `app/src/main/kotlin/org/example/ui/PanelParametrosIniciales.kt` |

## 4. Aspectos evaluados

Cada aspecto indica **qué mide**, **por qué importa** y **tu resultado**. Cuando el análisis reportó una señal, se agregan preguntas para **investigar la causa** (no hay recetas: entender el porqué es parte de la evaluación).

### 1. Índice de calidad POO/SOLID (indice)

- **Qué mide:** Un resumen numérico (0-100) que combina la riqueza de clases bien usadas con la penalización por señales POO/SOLID y duplicación, y luego aplica topes duros.
- **Por qué importa:** Ordena y prioriza la revisión: un índice bajo indica varias señales que conviene mirar juntas.
- **Tu resultado:** índice **98/100**

### 2. Duplicación lógica (DRY) (duplicacion) — sin señales

- **Qué mide:** Porcentaje de bloques de LÓGICA repetidos. Quedan fuera imports/includes, anotaciones, declaraciones de campos y comentarios/Javadoc/docstrings: esos bloques son boilerplate, no lógica.
- **Por qué importa:** Cada copia de lógica se mantiene por separado: un arreglo hay que repetirlo y los defectos se multiplican.
- **Tu resultado:** **0.6%** de duplicación lógica

### 3. Adopción de POO (% de código en clases) (poo) — sin señales

- **Qué mide:** Proporción de las **líneas de lógica** que viven dentro de una clase. Quedan fuera `package`/`import`/`using`/anotaciones y comentarios/Javadoc/docstrings: no son código que pueda estar dentro de una clase y, si contaran, el porcentaje quedaría topado por el boilerplate de cada archivo.
- **Por qué importa:** El diseño orientado a objetos es el eje de la materia: el comportamiento debería vivir como método de una clase.
- **Tu resultado:** **99.4%** del código dentro de clases
- **Cómo leerlo:** Es una **proporción**, no un acumulado: agregar código dentro de una clase no la sube por sí solo (numerador y denominador crecen juntos). Sube cuando el código que está **fuera** de clases (funciones sueltas, `main`, constantes de módulo, scripts) pasa a ser método de una clase o deja de existir. La medición se hace sobre `main`: el trabajo que todavía vive en otra rama no se refleja hasta que se integra.

### 4. Funciones fuera de clases (sueltas) — sin señales

- **Qué mide:** Funciones de nivel de módulo que no pertenecen a ninguna clase (en esta materia el comportamiento debe ser método de una clase).
- **Por qué importa:** Son la forma más directa de código procedural en un proyecto que debería ser orientado a objetos.
- **Tu resultado:** **0** función(es) fuera de clases

### 5. Clase demasiado grande (SRP) (god) — sin señales

- **Qué mide:** Clases con demasiadas líneas o métodos de LÓGICA. No cuentan los getters/setters triviales ni la cantidad de campos por sí sola.
- **Por qué importa:** Una clase que hace de todo cambia por muchos motivos y cuesta entenderla y probarla.
- **Tu resultado:** **0** clase(s) demasiado grande(s)

### 6. Baja cohesión (LCOM) (cohesion) — sin señales

- **Qué mide:** Métodos y campos de una clase que casi no se relacionan entre sí.
- **Por qué importa:** Una unidad que cambia por motivos distintos y comparte poca información suele esconder más de una responsabilidad.
- **Tu resultado:** **0** clase(s) con baja cohesión

### 7. Complejidad alta (complejidad) — sin señales

- **Qué mide:** Complejidad ciclomática: cantidad de caminos posibles dentro de un método o función (condicionales, bucles, casos).
- **Por qué importa:** Muchos caminos = difícil de seguir y de probar; suelen esconderse casos sin cubrir.
- **Tu resultado:** **0** método(s)/función(es) con complejidad alta

### 8. Métodos/funciones largos o con muchos parámetros (tamano) — sin señales

- **Qué mide:** Tamaño de los métodos/funciones y cantidad de parámetros.
- **Por qué importa:** Una función que hace varias cosas cuesta leerla, nombrarla y reutilizarla.
- **Tu resultado:** **0** método(s)/función(es) largos o con muchos parámetros

### 9. Acoplamiento alto (CBO) (acoplamiento) — con señales

- **Qué mide:** Cuánto depende una clase de otros tipos del proyecto. No cuentan los tipos de la plataforma (biblioteca estándar, DOM, motor Godot) ni los métodos/propiedades propias.
- **Por qué importa:** Piezas muy enlazadas hacen que un cambio se propague y que sea difícil reemplazar partes.
- **Tu resultado:** **1** clase(s) muy acoplada(s)
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

### Resumen de señales por tipo

| Señal | Cantidad | Principio | Archivos |
|---|---:|---|---:|
| acoplamiento alto | 1 | D | 1 |

### Resumen por archivo

> Archivos ordenados por cantidad de señales. Se excluyen **0** señal(es) de nivel repositorio sin archivo propio.

| Archivo | Señales | Aspectos | Líneas |
|---|---:|---|---|
| `app/src/main/kotlin/org/example/ui/PanelParametrosIniciales.kt` | 1 | acoplamiento | 6 |

### Archivos donde se originan las señales

> Se listan **1** hallazgo(s) localizados en **1** archivo(s). La columna `Línea` es la línea del archivo donde el análisis detectó la señal; `—` indica una señal de nivel repositorio (sin archivo propio).

| Aspecto | Archivo | Línea | Hallazgo |
|---|---|---:|---|
| Acoplamiento alto (CBO) | `app/src/main/kotlin/org/example/ui/PanelParametrosIniciales.kt` | 6 | clase 'PanelParametrosIniciales' muy acoplada (CBO=8) |

### Clases señaladas — métricas

> Solo clases con al menos una señal. `LOC lóg.` y `Mét. lóg.` descuentan getters/setters; `LCOM` es la cantidad de pares de métodos sin campos en común.

| Clase | Archivo | Línea | LOC lóg. | Mét. lóg. | Campos | CBO | LCOM | Cohesión | WMC | RFC | Señales |
|---|---|---:|---:|---:|---:|---:|---:|---:|---:|---:|---|
| `PanelParametrosIniciales` | `app/src/main/kotlin/org/example/ui/PanelParametrosIniciales.kt` | 6 | 78 | 6 | 1 | 8 | 0 | 100% | 14 | 41 | acoplamiento alto×1 |

### Umbrales aplicados (perfil exigente)

| Señal | Regla |
|---|---|
| Clase grande (SRP) | ≥ 200 LOC lógico, o ≥ 15 métodos con lógica y ≥ 100 LOC lógico; no cuentan getters/setters. |
| Baja cohesión (LCOM) | < 50 % de pares de métodos comparten algún campo, con ≥ 4 métodos con lógica y ≥ 3 campos. |
| Complejidad alta | algún método con complejidad ciclomática ≥ 10. |
| Método largo | ≥ 30 líneas. |
| Muchos parámetros | > 4 parámetros (5 o más). |
| Acoplamiento alto (CBO) | CBO ≥ 8. |
| Herencia profunda | DIT ≥ 3. |
| Type-check / switch de tipos | ≥ 1 cadena de type-check. |
| Interfaz demasiado grande (ISP) | > 6 métodos (7 o más). |
| Override vacío (LSP) | método vacío o marcado como 'no implementado'. |
| Clase solo de datos | 0 métodos y ≥ 3 campos. |
| Campos mutables públicos | ≥ 4 campos mutables públicos y ≥ 4 campos. |
| Clase estática | ≥ 2 métodos y todos estáticos. |
| Duplicación lógica | ventana de 8 líneas; solo lógica; se informa desde 1 %. |

## 5. Evolución por commit

Métricas calculadas sobre el estado de `main` en **cada commit** (26 commits con trabajo), del primero al último. La calidad usa el perfil `exigente`; el aporte de código lo mide RepoSense. El índice puede **bajar** al crecer el proyecto: se acumulan señales.

```mermaid
xychart-beta
    title "Índice POO/SOLID a lo largo de los commits"
    x-axis ["e4c7dc4", "f9cdb25", "21e6166", "6f7333f", "bdbdb04", "fbb8e8c", "6d00bd3", "54c463a", "ced6b32", "89325b5", "78e89ac", "8a543a5", "33d96dd", "96ce9c6", "b5fd2c3", "eeb8386", "2a2f4a1", "0353746", "82ec01d", "c3714a7", "37a7b42", "b4d3d28", "770fb1b", "fb2f2ab", "9c164f2", "90e2a49"]
    y-axis "Índice" 0 --> 100
    line [0, 0, 58, 59, 81, 80, 77, 77, 78, 91, 91, 91, 91, 91, 91, 91, 91, 91, 91, 91, 91, 98, 98, 98, 98, 98]
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
| 21 | 2026-11-20 | `37a7b42` | dqmdz-etec | 7,113 | 91 | 99% | 0.6% | 0 |
| 22 | 2026-11-20 | `b4d3d28` | dqmdz-etec | 7,260 | 98 | 99% | 0.6% | 0 |
| 23 | 2026-11-20 | `770fb1b` | dqmdz-etec | 7,260 | 98 | 99% | 0.6% | 0 |
| 24 | 2026-11-20 | `fb2f2ab` | dqmdz-etec | 7,260 | 98 | 99% | 0.6% | 0 |
| 25 | 2026-11-20 | `9c164f2` | dqmdz-etec | 7,260 | 98 | 99% | 0.6% | 0 |
| 26 | 2026-11-25 | `90e2a49` | dqmdz-etec | 7,260 | 98 | 99% | 0.6% | 0 |

<!-- actividad-clases:inicio -->
## Actividad en días de clase

> Período evaluado **2026-08-14 → 2026-11-27** · **24 clases** (lunes, viernes) · días de clase del calendario, extendidos hasta la fecha de corte.
>
> `Commits` = commits de trabajo (no-merge); `Merges` = merges de PR (también son push). Los días sin actividad dicen **sin actividad**. El cuadro principal usa sólo las fechas de clase del calendario oficial.
>
> **Aportes fuera del calendario:** cada alumno tiene un cuadro aparte con las fechas no lectivas con actividad, desde **2026-08-14** hasta **2026-11-27** (ventana de aportes). Esas fechas no forman parte del cuadro principal, pero se muestran para no perder trabajo hecho en fines de semana, feriados o después del período.
>
> Se recorre `git log --all` sobre un mirror (todas las ramas y `refs/pull/*`), con deduplicación por commit. Fecha = fecha de autor en UTC-3. Los commits locales no pusheados no se ven. Si el trabajo fue en pareja y el commit es de otra persona, figura a nombre del autor del commit; para reflejarlo, usar `Co-authored-by` o una nota manual.
>
> Sección informativa: no modifica el semáforo de aprobación.

### francucchiari-code

**13/24 clases con actividad** · 15 commits · 11 merges

| Fecha | Marca | Repositorios | Commits / merges |
|---|:--:|---|--:|
| 2026-08-14 | **Sí** | simulador-caida-libre-francucchiari | 1 / 0 |
| 2026-08-21 | No | sin actividad | 0 / 0 |
| 2026-08-24 | No | sin actividad | 0 / 0 |
| 2026-08-28 | No | sin actividad | 0 / 0 |
| 2026-08-31 | **Sí** | simulador-caida-libre-francucchiari | 2 / 0 |
| 2026-09-04 | **Sí** | simulador-caida-libre-francucchiari | 2 / 0 |
| 2026-09-07 | **Sí** | simulador-caida-libre-francucchiari | 1 / 1 |
| 2026-09-14 | **Sí** | simulador-caida-libre-francucchiari | 1 / 1 |
| 2026-09-25 | **Sí** | simulador-caida-libre-francucchiari | 1 / 1 |
| 2026-09-28 | No | sin actividad | 0 / 0 |
| 2026-10-02 | **Sí** | simulador-caida-libre-francucchiari | 2 / 1 |
| 2026-10-05 | No | sin actividad | 0 / 0 |
| 2026-10-09 | No | sin actividad | 0 / 0 |
| 2026-10-16 | **Sí** | simulador-caida-libre-francucchiari | 1 / 1 |
| 2026-10-19 | **Sí** | simulador-caida-libre-francucchiari | 1 / 1 |
| 2026-10-23 | No | sin actividad | 0 / 0 |
| 2026-10-26 | No | sin actividad | 0 / 0 |
| 2026-10-30 | **Sí** | simulador-caida-libre-francucchiari | 1 / 2 |
| 2026-11-02 | **Sí** | simulador-caida-libre-francucchiari | 0 / 1 |
| 2026-11-06 | No | sin actividad | 0 / 0 |
| 2026-11-13 | **Sí** | simulador-caida-libre-francucchiari | 1 / 2 |
| 2026-11-16 | No | sin actividad | 0 / 0 |
| 2026-11-20 | **Sí** | simulador-caida-libre-francucchiari | 1 / 0 |
| 2026-11-27 | No | sin actividad | 0 / 0 |

#### Aportes fuera del calendario de clases — francucchiari-code

**francucchiari-code — 2 fechas fuera del calendario** · 2 commits · 0 merges

| Alumno | Fecha | Repositorios | Commits / merges |
|---|---|---|---|
| francucchiari-code (`francucchiari-code`) | 2026-11-01 | simulador-caida-libre-francucchiari | 1 / 0 |
| francucchiari-code (`francucchiari-code`) | 2026-11-11 | simulador-caida-libre-francucchiari | 1 / 0 |

<!-- actividad-clases:fin -->

---
_Informe generado por la cátedra. Los criterios y umbrales de medición corresponden al perfil `exigente` de `calidad.py`; el semáforo de aprobación, a `CRITERIOS-CALIDAD.md` (ver `README.md`). Ante dudas, revisá el archivo señalado._
<!-- evaluacion:fin -->
