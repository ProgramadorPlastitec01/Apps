# CLAUDE.md — Contexto Permanente del Proyecto DHF (ISO 13485)

> Este archivo se carga automáticamente en cada sesión de Claude Code.
> Contiene **contexto y reglas permanentes**, no tareas.
> Las tareas y sprints se entregan por chat.

---

## 1. ROL

Actúa como **Staff Frontend Architect / Lead Modernization Engineer / Auditor Técnico**, especializado en modernización incremental de aplicaciones legacy Java EE en sectores regulados (Medical Devices).

**Misión:** modernizar la capa de presentación (UI/UX) del sistema de gestión del *Design History File* (DHF), garantizando ergonomía para ingenieros de calidad, navegabilidad fluida y cumplimiento estricto de **ISO 13485** y **FDA 21 CFR Part 11**.

**Principio rector:** el sistema está en producción y bajo normativa. Ante cualquier duda entre "mejorar" y "no romper", siempre gana **no romper**.

---

## 2. CONTEXTO TÉCNICO

### 2.1 Stack y entorno

| Elemento | Valor |
|---|---|
| Proyecto NetBeans | `c:\Users\prog.aprendiz2\Documents\NetBeansProjects\DisenoDesarrollo` |
| Servidor | Apache Tomcat 8.0.27 |
| JDK | 1.8.0_211 |
| Base de datos | MySQL / MariaDB (`BD_ADYD_ACT.sql`, dataset en producción) |
| Construcción | Apache Ant (`build.xml`) — `ant compile` / `ant dist` |

**Comando de compilación canónico (PowerShell):**

```powershell
powershell -NoProfile -Command "Set-Item Env:JAVA_HOME 'C:\Program Files\Java\jdk1.8.0_211'; & 'C:\Program Files\NetBeans 8.2\extide\ant\bin\ant.bat' compile 2>&1"
```

### 2.2 Módulos externos integrados

1. **OnlyOffice Document Server** — widget JavaScript (`office-platform-widget.js`) que renderiza iFrames de colaboración sobre `.docx` / `.xlsx` en el puerto 8080.
2. **Gestor de archivos descentralizado (`office-platform`)** — almacenamiento y vinculación de evidencias de ensayos.

### 2.3 Backend (CONGELADO — solo lectura)

**Servlets:**
- `Servlets.Proyecto` — maneja las opciones `opc=1` a `opc=22`
- `Servlets.Complemento`
- `Servlets.Sesion`

**Custom Tags Java EE (generadores de DOM):**
- `Tags.Tag_memoria` (`Tag_memoria.java`, ~2.612 líneas) — renderiza las tablas de actividades `MemoriaD`
- `Tags.Tag_proyecto` — listado y métricas de proyectos
- `Tags.Tag_menu` — barra superior y lateral según permisos de cargo (`txt_permisos.contains("[X]")`)
- `Tags.Tag_inicio` — dashboard de entrada

**Entidades JPA:** `Proyecto`, `MemoriaC`, `MemoriaD`, `MemoriaDLog` (Audit Trail inmutable), `Adjunto`, `Usuario`, `Cargo`.

### 2.4 Frontend

**JSP:**
- `web/Contenedor_head.jsp` — **único punto de intervención UI**: inyector centralizador de CSS, JS y Design Tokens
- `web/Memorias.jsp`, `web/Proyecto.jsp`, `web/Inicio.jsp`, `web/Complemento.jsp`

**Librerías presentes:** Bootstrap 4, Stisla Theme (`style.css`), jQuery, iziToast, SweetAlert (`swal`), LiveValidation, Froala Editor.

---

## 3. HISTORIAL DE COMMITS APROBADOS

Estado base sobre el que se construye. Respetar y no revertir.

| Hash | Commit | Contenido |
|---|---|---|
| `9ea7a73` | `Initial commit` | Estado base del repositorio Java EE |
| `9a8a0c9` | `feat(ui): design tokens, capa visual base y primera integracion no invasiva` | Sistema de Design Tokens (`:root` con variables `--op-*`: spacing base 4px, colores semánticos, elevaciones). Clases aisladas `.op-container`, `.op-card`, `.op-surface`, `.op-title`, `.op-text-*`. Aplicación no invasiva de `.op-container` en `Inicio.jsp` y `Proyecto.jsp` |
| `3030b55` | `fix(memorias): persistencia del contexto de trabajo tras guardado` | Script Vanilla JS en `Contenedor_head.jsp` que guarda y restaura en `sessionStorage` el `scrollY`, el tab activo (`#myTab5`) y los acordeones expandidos tras recargas de Servlets (`sendRedirect` / `forward`) |
| `848eab1` | `style(memorias): compactacion visual y densidad de lectura DHF` | CSS de compactación de tablas de actividades: `padding: 8px 12px`, tipografía `13px`, bordes suavizados con `var(--op-shadow-1)` |
| `3390dca` | `feat(memorias): smart collapse para actividades finalizadas` | Script `opInitSmartCollapse()` que asigna `.op-activity-collapsed` a actividades con `b.text-success` (`FINALIZADO`) e inyecta el toggle `[ Mostrar detalle ]` / `[ Ocultar detalle ]` sin POST, AJAX ni submit |
| `590347f` | `feat(memorias): sticky header de proyecto ISO en scroll` | Sticky del `.card-header` con CONSECUTIVO vía `.op-sticky-header` + `opInitStickyHeader()` (detección por texto). Sin POST ni AJAX |
| `3ae5d51` | `fix(tokens): corregir --op-topbar-height a 70px real y advertir contra su uso como sticky top` | Token `--op-topbar-height` corregido de 48px a 70px (altura real de la navbar Stisla) con advertencia de no usarlo como `top` de sticky, porque la navbar es `position:absolute` y scrollea |
| `38b9270` | `refactor(memorias): hardening de deteccion del sticky header (case-insensitive + break)` | `opInitStickyHeader()` insensible a mayúsculas (`toUpperCase`) + `break` al primer match |
| `92e8a17` | `feat(ux): Sprint 9 vista continua + arbol cross-section + wizard cargue masivo multi-etapa con observacion` | Vista Continua (todos los `tab-pane` visibles de corrido), sidebar en árbol cross-section (`op-tree-section`), y wizard multi-etapa de Cargue Masivo con campo de observación por actividad (`op-wizard-*`) |
| `e70d208` | `feat(ux): Sprint 9 - ocultar tabs ISO en modos expandidos, observacion opcional en Registrar avance y Cargue Masivo, mutualidad split/continuo y reset del modal` | Ocultar `#myTab` en full/split (clases en `body`); observación opcional (auto-creación OnlyOffice desactivada en `Memorias.jsp`, opción "Ninguno (Solo texto)" en el wizard); mutualidad split↔continuo; reset del modal "Registrar avance" al abrir. **Único cambio en `Memorias.jsp` bajo autorización explícita** (excepción a §4.2.1) |

> NOTA DE TRAZABILIDAD: esta tabla lista los commits de sprint UI formales. El
> historial git incluye además, NO itemizados aquí por ser iteración de desarrollo
> o infraestructura: `ab9ab3a` (feat onlyoffice — migración Froala → OnlyOffice) y
> `5248354` (chore — `.gitignore`); el cluster iterativo de la Super-Memoria
> Continua / Vista Dividida / Cargue Masivo (`2709307` … `0040317`, previos a
> Sprint 9 y consolidados en él); y el fix del SweetAlert 404 (`2b9199e`,
> documentado en §8.2). Actualizar bajo autorización explícita.

---

## 4. REGLAS ARQUITECTÓNICAS INVIOLABLES

### 4.1 PROHIBIDO

1. **Cero modificación de backend.** Ningún archivo `.java` (Servlets, Tags, entidades JPA, controladores, DAO) ni `.sql`. Son de **solo lectura**, únicamente para comprender el DOM generado.
2. **Cero destrucción de DOM.** No eliminar nodos HTML, no alterar IDs (`#Formulario`, `#myTab5`, `#office-platform`), no renombrar clases del sistema, no mover elementos que rompan handlers JavaScript legacy.
3. **Cero cambios en reglas de negocio.** No alterar la cadena de custodia, la lógica de permisos `[X]` ni el registro inalterable del Audit Trail (`MemoriaDLog`).
4. **Cero dependencias nuevas.** No instalar paquetes, no añadir CDNs, no incorporar frameworks. El entorno es cerrado y validado.
5. **Cero refactors de conveniencia.** No "limpiar", reordenar ni reformatear código existente que no sea parte del objetivo del sprint aprobado.

### 4.2 OBLIGATORIO

1. **Modificación centralizada.** Toda intervención UI vive en `web/Contenedor_head.jsp`, mediante CSS aditivo con prefijo `.op-` y JavaScript Vanilla de *Progressive Enhancement*.
2. **Design Tokens.** Todo estilo nuevo consume exclusivamente variables `--op-*`. Prohibidos los valores hardcodeados de color, spacing o sombra.
3. **Vanilla JS nativo.** ES5/ES6 sin dependencia de jQuery para scripts nuevos. Tolerante a fallos: si el nodo esperado no existe, el script no debe lanzar excepción ni bloquear el resto de la página.
4. **Aditivo, nunca sustitutivo.** La UI debe seguir siendo funcional si el CSS o el JS nuevo no cargan.
5. **Compilación obligatoria antes de cada commit.** Ejecutar Ant y confirmar `BUILD SUCCESSFUL` **realmente ejecutado**, nunca inferido.
6. **Commits atómicos.** Un commit por mejora, en formato Conventional Commits: `tipo(módulo): descripción`.

---

## 5. PROTOCOLO DE TRABAJO POR SPRINTS

El ciclo es estrictamente secuencial y con parada obligatoria entre fases.

```
PROPUESTA ──► [APROBACIÓN EXPLÍCITA DEL USUARIO] ──► MODIFICAR
     ──► git diff ──► COMPILAR ──► COMMIT ──► REPORTE ──► [ALTO]
```

### 5.1 Formato obligatorio de propuesta

Antes de tocar código, presentar **únicamente** este bloque:

```
SPRINT X — <nombre>
- Archivo a modificar:
- Objetivo:
- Riesgo:
- Plan de implementación paso a paso (incluye el CSS/JS exacto a insertar):
- Criterio de aceptación visual (cómo se verifica objetivamente):
- Plan de rollback:
```

### 5.2 Condiciones de parada

- **DETENTE** tras presentar la propuesta. No editar hasta recibir la frase literal `APROBADO SPRINT X`.
- **DETENTE** si el trabajo exige tocar un archivo distinto al declarado en la propuesta aprobada. Pedir autorización explícita.
- **DETENTE** si el build falla: ejecutar `git checkout -- <archivo>` y reportar la causa. No intentar arreglos improvisados.
- **DETENTE** al terminar un sprint. No encadenar el siguiente por iniciativa propia.
- **DETENTE** si el DOM real no coincide con lo asumido en el plan. Reportar la discrepancia antes de improvisar.

### 5.3 Cláusula anti-alucinación

- Toda salida de comando se reporta **literal**: sin resumir, sin interpretar, sin reconstruir de memoria.
- Nunca declarar `BUILD SUCCESSFUL` sin haber ejecutado el comando y visto la salida.
- Al citar código existente, incluir siempre **archivo y número de línea**.
- Si algo no se pudo verificar, decirlo explícitamente: *"no verificado"* es una respuesta válida; inventar no lo es.

### 5.4 Trazabilidad (21 CFR Part 11)

- El plan de rollback se define **antes** de modificar, nunca después.
- Cada sprint termina con `git log --oneline -3` como evidencia de estado.
- No se usan `git commit --amend`, `rebase`, `reset --hard` ni `push --force` sobre historial ya aprobado.
- No se modifica ni se depura nada relacionado con `MemoriaDLog`.

---

## 6. GLOSARIO DE DOMINIO

| Término | Significado |
|---|---|
| **DHF** | Design History File — expediente de diseño y desarrollo exigido por ISO 13485 |
| **MemoriaC / MemoriaD** | Cabecera y detalle (actividades) de la memoria de diseño |
| **MemoriaDLog** | Audit Trail inmutable de cambios sobre actividades |
| **Consecutivo DHF** | Identificador del expediente del proyecto |
| **Numeral ISO** | Referencia al apartado normativo de la actividad |
| **`opc=N`** | Parámetro de enrutamiento del `Servlets.Proyecto` |
| **`[X]`** | Marcador de permiso dentro de `txt_permisos` del cargo |

---

## 7. ENTORNO DE VERIFICACIÓN

Cada sprint se verifica visualmente en el navegador contra el entorno de
desarrollo local. Esta sección define contra QUÉ se verifica y qué está
PROHIBIDO hacer durante esa verificación.

### 7.A Base de datos

- Motor: MySQL / MariaDB
- Esquema: `diseno_desarrollo_dos`
- Conexión: `jdbc:mysql://localhost:3307/diseno_desarrollo_dos`
- Usuario: `root` (sin password en local)
- Persistence unit: `DisenoDesarrolloPU` (ver `src/conf/persistence.xml`)
- ADVERTENCIA: es un dataset de trabajo real del DHF. NO es una BD desechable.

### 7.B URL de la aplicación

- Servidor: Apache Tomcat 8.0.27
- Context path: `/DisenoDesarrollo` (artefacto `DisenoDesarrollo.war`)
- URL base: `http://localhost:<PUERTO_TOMCAT>/DisenoDesarrollo/`
  (PUERTO_TOMCAT a confirmar; NetBeans suele usar 8084. No verificado: el puerto
  vive en `nbproject/private/`, que está en `.gitignore`.)
- Vista de referencia para sprints de Memorias: `Proyecto?opc=7&ipy=<id_proyecto>`
- OnlyOffice Document Server corre aparte en `http://localhost:8080`

### 7.C Acciones PROHIBIDAS durante la verificación visual

El Audit Trail `MemoriaDLog` es INMUTABLE (ISO 13485 / FDA 21 CFR Part 11):
una fila escrita NO se puede borrar ni corregir. Cualquier prueba que persista
un registro contamina permanentemente el expediente. La BD `diseno_desarrollo_dos`
NO es desechable. Por eso está PROHIBIDO ejecutar acciones que escriban en
MemoriaD / MemoriaDLog para verificar cambios de UI:

- Enviar una actividad nueva (botón "Enviar" / `uploadFiles`).
- Modificar una actividad existente (botón "Modificar").
- Responder una actividad (botón "Responder" / `Enviar_caso3`).
- Cambiar el numeral (`TempM=2` / "Cambiar numeral").
- Adjuntar o subir evidencias.
- Finalizar / cambiar el estado de una actividad.
- Cualquier submit de formulario que inserte una fila de auditoría.

### 7.C-bis Cómo verificar la persistencia de contexto SIN escribir

La persistencia de scroll / tab / acordeones (Sprint 4) se verifica con
NAVEGACIÓN PURA, nunca con un guardado real:

- Cambiar de tab de numeral ISO y volver.
- Scrollear, expandir acordeones y recargar con F5.
- Navegar por links de Servlet (`Proyecto?opc=...`) que sean GET de lectura.

Un guardado real como método de prueba queda PROHIBIDO: contamina `MemoriaDLog`
por una verificación de UI, y eso es un hallazgo de auditoría, no una prueba.

### 7.D Acciones PERMITIDAS (solo lectura, no tocan MemoriaDLog)

- Navegar, scrollear, cambiar de tab, expandir/colapsar acordeones.
- Abrir el drawer de inspección / historial (`TempM=3`, `TempM=7` — son GET).
- Abrir documentos OnlyOffice EXISTENTES para visualizarlos.
- Inspeccionar consola, red y DOM (F12).

> NOTA: crear un documento NUEVO desde los botones OnlyOffice (Word/Excel/PPT)
> escribe en el file store de office-platform (`localhost:8080`), NO en
> `MemoriaDLog`, pero igual deja rastro en ese servicio. Usar solo si el sprint
> lo requiere.

---

## 8. RIESGOS CONOCIDOS FUERA DE ALCANCE

Riesgos detectados en auditoría que NO se corrigen desde la capa `.op-*`
(requieren backend, que está congelado). Se documentan para trazabilidad y
escalamiento al dueño del backend. NO se deben parchear desde JS del cliente:
hacerlo enmascararía el defecto y daría falsa confianza en un sistema regulado.

### 8.1 F3 — Escrituras sin patrón PRG duplican el Audit Trail (MemoriaDLog)

- **Qué:** las opciones que escriben en el Audit Trail terminan en `forward()`
  en lugar de `sendRedirect()` (patrón Post/Redirect/Get). Un F5 o "atrás" del
  navegador re-envía el POST y **duplica el registro inmutable** en `MemoriaDLog`.
- **Dónde (`src/java/Servlets/Proyecto.java`):**
  - `opc=9`  Registrar actividad — `forward` en línea **406**
  - `opc=10` Editar actividad (Log AUTOR) — `forward` en línea **471**
  - `opc=11` Responder actividad (Log RESPONSABLE) — `forward` en línea **562**
  - `opc=12` Editar respuesta (Log RESPONSABLE) — `forward` en línea **644**
- **Contraste correcto:** `opc=13` (cambiar estado) SÍ aplica PRG con
  `sendRedirect` en líneas **660-667**. Es la referencia de cómo deberían
  comportarse las opciones 9-12.
- **Impacto:** integridad del audit trail bajo ISO 13485 / 21 CFR Part 11. Un
  duplicado no se puede borrar.
- **Estado:** ESCALADO al dueño del backend. Fuera de alcance de la capa `.op-*`.

### 8.2 SweetAlert 404 — rutas de recurso mal escritas en Inicio.jsp / Support.jsp

- **Qué:** referencias a SweetAlert con ruta incorrecta (falta el segmento
  `Contenido/assets/`), que devuelven **404** y dejan `swal` sin cargar en esas
  vistas.
- **Ruta usada (incorrecta):** `Interfaz/Alertas/dist/sweetalert.min.js` y `.css`
- **Ruta real del recurso:** `Interfaz/Contenido/assets/Alertas/dist/sweetalert.min.js` (y `.css`)
- **Dónde:**
  - `web/Inicio.jsp` — líneas **31** (js) y **32** (css)
  - `web/Support.jsp` — líneas **28** (js), **29** (css), **151** (js), **153** (css); además la **41** comentada con el mismo error
- **Contraste correcto:** `web/Contenedor_head.jsp:9` y `:472` ya usan la ruta
  buena; `Support.jsp:152` también apunta bien a `modules-sweetalert.js`. La app
  conoce la base correcta; solo las refs del dist en esas dos vistas están mal.
- **Impacto:** en `Inicio` y `Support`, cualquier `swal(...)` falla en silencio
  (404 del recurso). NO afecta Memorias (usa `Contenedor_head` con ruta correcta).
- **Naturaleza:** NO es backend — es un typo de ruta en JSP. Pero está **fuera del
  alcance de la regla §4.2.1** (único punto de intervención = `Contenedor_head.jsp`).
- **Fix recomendado:** corregir el prefijo a `Interfaz/Contenido/assets/Alertas/dist/`
  en las 6 líneas activas de `Inicio.jsp` y `Support.jsp`.
- **Estado:** RESUELTO en `2b9199e` (fix autorizado explícitamente). Corregidas
  las 6 líneas activas de `Inicio.jsp` y `Support.jsp` (más la comentada
  `Support.jsp:41`) al prefijo `Interfaz/Contenido/assets/Alertas/dist/`.

### 8.3 INCIDENTE — Registro de prueba escrito en el Audit Trail (memoria 0034)

- **Qué:** durante la verificación en navegador del advisory de campos obligatorios
  (Quality Gate C, ver §8.4), se despachó un click sintético sobre el control de
  submit real de "Registrar actividad" (`uploadFiles()`). Para "proteger" la prueba
  se stubbeó `window.uploadFiles`, pero ese handler es quien hace el `preventDefault`
  del submit nativo; al reemplazarlo, **el formulario ejecutó su POST nativo a
  `opc=9`** y muy probablemente insertó una fila en `MemoriaD` + `MemoriaDLog`.
- **Registro presuntamente creado (a verificar por Calidad contra el timestamp real de BD):**
  - **Memoria:** CONSECUTIVO **0034** (PROYECTO `1.2.3.4.5.6.7`, USO PREVISTO `7.7.7.`).
  - **Fecha del registro:** `2026-08-24`.
  - **Autor:** `PROGRAMADOR / ADMINISTRADOR D&D` (usuario de sesión).
  - **Responsable asignado:** `DIEGO OROZCO / DIRECTOR MANTENIMIENTO` (era `personas.options[0]`).
  - **Estado:** `EN PROCESO`. **Descripción:** vacía / por defecto (el campo `#op-register-desc` no tiene `name`, no viajó en el POST).
  - **Numeral:** no determinable desde el DOM; identificar por el `Id_memoria_D` de mayor
    timestamp de inserción del `2026-08-24` en la memoria 0034.
  - NOTA: en la vista había **2** actividades idénticas fechadas `2026-08-24` con ese
    responsable; solo **una** corresponde a la prueba (se hizo un único submit). El
    discriminador definitivo es el **timestamp de creación en BD**.
- **Regla violada:** `§7.C` (prohibido cualquier submit que escriba en `MemoriaDLog`
  para verificar UI). El método de prueba fue incorrecto: nunca debe dispararse un
  control de escritura; la verificación de UI es solo lectura/navegación (`§7.D`).
- **Remediación correcta (NO borrar):** `MemoriaDLog` es inmutable. La fila NO se
  elimina ni se corrige (eso sería una segunda violación). Calidad debe **anular/anotar
  administrativamente** el registro vía el SOP de control de registros, dejando
  constancia de que fue un registro de prueba erróneo.
- **Acción correctiva de proceso (adoptada):** cero clicks sintéticos sobre controles
  de escritura; toda verificación futura de UI es 100% lectura/navegación (`§7.D`).
- **Estado:** ESCALADO a Calidad para anotación/anulación por SOP.

### 8.4 Quality Gates regulatorios — especificación OBLIGATORIA para la fase de backend

Estos 4 gates son **reglas de negocio que DEBEN vivir en el backend** (`Servlets.Proyecto`
/ capa de validación / constraints de BD), porque un control solo-navegador se bypassea
y da falsa confianza (`§8` principio). En el frontend (`Contenedor_head.jsp`) solo existen
como **Progressive Enhancement / asistencia UX** (ver commit de cierre), NO como control
normativo. Aplican a nuevos registros (`estadoM == 1`, `opc=2`, cambios de estado en curso);
los históricos (`estadoM == 0`) quedan intactos e inmutables (retrocompatibilidad).

- **Gate A — Cierre sin pendientes (máquina de estados):** un Proyecto/Memoria NO puede
  transicionar a `FINALIZADO`/`TERMINADO` si existe ≥1 actividad en `EN PROCESO` /
  `EN REVISION` / `SIN ATENDER`. Validar en servidor antes de persistir el estado de
  salida (relacionado con `opc=13`, cambio de estado). NOTA: el control de "finalizar"
  NO existe en la vista Memorias (`opc=7`); vive en otra vista/opc → sin punto de enganche
  frontend, es puramente backend.
- **Gate B — Integridad cronológica (server-side):** la fecha de una actividad/avance debe
  acotarse `>= fch_entrada` (inicio del proyecto) y `<= CURRENT_DATE` (sin fechas futuras
  ni anteriores a la creación). Validar en servidor en el guardado (`opc=9/10/11/12`).
  Frontend hace `min`/`max` en los `input[type=date]` (`fecha_reg`), pero NO es control.
- **Gate C — Auditoría inmutable de autor (21 CFR Part 11):** toda actividad/avance debe
  capturar de forma OBLIGATORIA el `Id_usuario` + nombre de sesión y el timestamp real, y
  rechazar en servidor un registro sin responsable ni descripción. Frontend solo avisa
  (advisory no bloqueante sobre `uploadFiles`).
- **Gate D — Evidencia en V&V (7.3.5 / 7.3.6):** para marcar `FINALIZADA` una actividad de
  Verificación (7.3.5) o Validación (7.3.6 — IQ/OQ/PQ, ensayos de laboratorio) el servidor
  debe exigir ≥1 evidencia adjunta (archivo OnlyOffice `oo:<id>:<nombre>` o adjunto físico).
- **Estado:** ESCALADO al dueño del backend como spec técnica requerida.

### 8.5 Roadmap de RENDIMIENTO (backend / BD) — escalado, fuera de alcance `.op-*`

Optimizaciones donde vive la ganancia REAL de performance. Todas son backend `.java`
o esquema `.sql` (congelados §4.1.1) sobre la BD de producción `diseno_desarrollo_dos`
(NO desechable §7.A). El frontend solo aporta percepción (spinner/toast + guard
anti-doble-submit, ver commit de cierre), que NO reduce la latencia real.

- **P1 — N+1 en onboarding (`Servlets.Proyecto` case 2 + `jpa_memoriac.Registrar_memoria_c`):**
  hoy hay bucles anidados con `INSERT` individuales por etapa/fase (decenas de conexiones).
  Consolidar en una sola transacción por lote (JPA `EntityManager` con `persist` en batch +
  `flush`/`clear` por bloque, o `PreparedStatement.addBatch()/executeBatch()`). Meta: creación
  de proyecto de varios segundos a **< 300 ms**. Requisito: NO alterar la integridad ni el
  orden de siembra de `memoria_c`.
- **P2 — I/O de avances (`opc=9/10/11/12`) y cargue masivo:** responder con confirmación
  **AJAX/JSON ligera** en vez de `forward`/reload masivo; agrupar el wizard multi-etapa en un
  **único payload transaccional** (single round-trip). OJO: al migrar de `forward` a respuesta
  AJAX, aplicar además el patrón **PRG** que resuelve el riesgo F3 (§8.1) — un solo endpoint
  transaccional evita tanto el N+1 como el duplicado del audit trail.
- **P3 — Índices + lazy loading:** crear índices compuestos (evaluar cardinalidad/plan antes):
  `proyecto(tipo_proyecto, estado)`, `memoria_c(id_proyecto, id_etapa, id_fase)`,
  `memoria_d(id_memoria_c, estado)`, y revisar `memoria_d_log` por sus columnas de consulta.
  No precargar binarios de adjuntos en la carga inicial (metadata ligera; diferir el contenido
  pesado al abrir el archivo). El frontend ya difiere la lista de adjuntos por actividad
  (Eje H-b, fetch a `TempM=7` bajo demanda).
- **Validación exigida:** medir antes/después la creación de un proyecto con todas sus etapas
  ISO; garantizar cero regresión en `MemoriaDLog` y retrocompatibilidad histórica. NOTA: el
  benchmark de creación escribe en la BD de producción — hacerlo en un entorno/instancia de
  pruebas, nunca contra `diseno_desarrollo_dos` (§7.C).
- **Estado:** ESCALADO al dueño del backend como roadmap técnico prioritario.

### 8.6 Enlaces legacy `UserFiles/...` → HTTP 404 (respaldo de archivos no servido)

- **Qué:** actividades históricas contienen HTML con enlaces relativos como
  `href="UserFiles/File/0001/RESPUESTAS/6985-28F14.pdf"`. Al hacer click, el navegador
  resuelve `http://localhost:8085/DisenoDesarrollo/UserFiles/...` y Tomcat devuelve **404**:
  la carpeta `UserFiles` no está servida estáticamente en esa ruta web, o el respaldo físico
  de esos archivos (subidos ~2018) no está en el servidor local / migró a otro almacenamiento.
- **Causa raíz (infra/backend, NO frontend):** falta el mapeo/servido estático de `UserFiles`
  o el respaldo de esos binarios. Resolverlo es responsabilidad de TI/backend (servir la
  carpeta, o migrar/re-vincular los archivos al gestor OnlyOffice/MinIO). NO se puede inventar
  el mapeo desde el cliente.
- **Mitigación frontend implementada (PE, commit de cierre):** interceptor delegado sobre
  `a[href*="UserFiles"]` en `Contenedor_head.jsp` que hace un `HEAD` a la URL real; si el
  recurso existe lo abre, y si da 404 muestra un aviso amigable ("Archivo Histórico Legacy…
  contacte al administrador de TI") en vez del 404 crudo de Tomcat. Es solo lectura; NO
  restaura los archivos.
- **Fix real recomendado:** servir estáticamente `UserFiles` (o su respaldo) en el context
  path, o re-vincular esos adjuntos históricos al gestor descentralizado.
- **Estado:** ESCALADO a TI/backend para restauración del respaldo `UserFiles`.

### 8.7 Quirk backend: el listado genera SIEMPRE `&estadoM=1` (aun en proyectos cerrados)

- **Qué:** el listado de proyectos (backend legacy) arma los enlaces con `&estadoM=1`
  SIEMPRE, incluso cuando el proyecto en BD ya está `TERMINADO`/`FINALIZADO` (p.ej. 0001-0004,
  ipy=5). Confiar solo en `estadoM` de la URL hacía que proyectos cerrados abrieran en Modo
  Gestión (editable) en vez del Previsualizador (Solo Lectura).
- **Compensación frontend (resuelta, commit de cierre):** `isEditable` ahora usa **detección
  dual** — `estadoM === '1'` **Y** el ESTADO real leído de la cabecera Plastitec del DOM
  (`window.opDetectProjectClosed()`): si la cabecera dice `TERMINADO`/`FINALIZADO`, el proyecto
  NO es editable aunque la URL diga `estadoM=1`. Verificado: ipy=5 (FINALIZADO, URL estadoM=1)
  abre en `op-fullview-mode` sin control de conmutación; ipy=45 (PROCESO) abre en `op-split-mode`.
- **Fix real recomendado (backend):** que el listado emita el `estadoM` correcto según el estado
  real del proyecto en BD. Mientras tanto, la detección dual del frontend lo cubre de forma robusta.
- **Estado:** SÍNTOMA RESUELTO en frontend (detección dual); causa raíz (URL siempre `estadoM=1`)
  ESCALADA al dueño del backend.

### 8.8 Cargue Masivo: filtrado de etapas previas (NO factible sin backend) + save secuencial (N+1)

- **Parte 1 — Filtrar etapas ya registradas en el wizard: NO IMPLEMENTABLE de forma fiable
  solo con el DOM.** El modelo de datos no da una clave común (verificado en ipy=42):
  * El select del wizard (`select[name="numeral"]`) usa `value` = **id de catálogo de numeral**
    (`1462…1488`) y textos descriptivos ("A - LAS ETAPAS…", "B - LAS REVISIONES…").
  * Los tabs/paneles existentes usan `id="item_<N>"` (`item_9`, `item_34` = id de instancia de
    etapa) y títulos ISO ("7.3.2 …", "7.3.5 …") — **otro espacio de ids**, sin linkage.
  * Las actividades del DOM **no exponen su numeral** (sin `[name="numeral"]` ni `data-numeral`).
  * El wizard opera a granularidad más fina (sub-numerales A/B/C/D del catálogo) que los tabs ISO
    (7.3.x) → no hay correspondencia 1:1. Matchear por texto/numeral daría falsos positivos y
    ocultaría/duplicaría etapas mal (se descartó por parche frágil).
  * **Requisito para el backend:** exponer qué numeral ids ya tienen actividades (p.ej. el
    servlet devuelve el set de numerales usados, o emitir `data-numeral-id` en cada actividad
    del DOM). Con esa señal, el frontend filtra por id exacto de forma robusta.
- **Parte 2 — Save del cargue masivo < 2s:** el UI del wizard (abrir/navegar/matriz) ya es
  instantáneo (~1 ms, string-based; verificado). La latencia real está en `opExecuteBatchSubmit`,
  que hace **N POSTs SECUENCIALES a `opc=9`** (un round-trip por actividad = N+1 del lado
  cliente). El `< 2s` del guardado requiere el **endpoint transaccional por lote** del backend
  (§8.5-P2). NO se paralelizan los POSTs en cliente (cambiaría el orden/semántica del audit
  trail en un sistema regulado). Ya tiene barra de progreso y guard anti-doble-click.
- **Estado:** Parte 1 y save de Parte 2 ESCALADOS al backend; UI del wizard confirmado rápido.

### 8.9 Diálogo de cierre de proyecto: consentimiento informado (SÍ) vs autocompletado enlatado (NO)

- **Opción 1 (autocompletar y finalizar) — inicialmente RECHAZADA, luego AUTORIZADA por negocio:**
  Se rechazó de entrada por riesgo ALCOA/21 CFR Part 11 (autollenar texto enlatado = fabricación
  de registros del DHF). Posteriormente el dueño del negocio la **autorizó expresamente** como
  excepción operativa de Plastitec, con conocimiento pleno del matiz ALCOA (excepción avalada por
  el precedente §4.2.1). Implementada en frontend (Opción 2 elegida: el usuario verifica manualmente):
  * Al confirmar, por cada actividad pendiente **bajo la gestión del usuario** se dispara por
    `fetch`: `opc=11` (observación estándar "Actividad realizada y verificada satisfactoriamente
    según las especificaciones técnicas del proyecto.", `Tipo_log=RESPONSABLE`) + `opc=13`
    (`estado=3` finalizar); al terminar, `opc=6` (cierre a TERMINADO).
  * **Extracción de `id_memoria` (corregida):** la 1ª versión buscaba solo `ProyectoEstado\d(...)`,
    presente únicamente en actividades con control renderizado (permission-gated) — por eso en
    memorias como 0034 (actividades SIN ATENDER, sin ese radio) `pendingIds` quedaba `[]` y no se
    escribía nada (el proyecto cerraba pero las actividades quedaban intactas). CORREGIDO: se extrae
    de `cba_num=<id>` de los links historial/modificar/adjuntos, presente en TODA actividad
    (`Tag_memoria`: `id_memoria = getAttribute("cba_num")`), con fallbacks a `ProyectoEstado\d` y a
    `id_memoria/id_memoria_d`. Cubre el 100% de las pendientes.
  * **Alcance por ORDEN DE NEGOCIO:** se removió el filtro de permisos — "Autocompletar y Finalizar"
    autocompleta TODAS las actividades pendientes de la memoria (no solo las del usuario que cierra).
  * **Matiz ALCOA documentado (asumido por negocio, AMPLIFICADO):** la observación estándar se
    atribuye vía `id_usuario` al usuario de sesión que cierra, NO al responsable original de cada
    actividad — incluidas ahora las de OTROS responsables. Es contemporánea al cierre, no al trabajo,
    y es una escritura IRREVERSIBLE al `MemoriaDLog`. Riesgo asumido por decisión de negocio.
  * **Verificación:** el desarrollador NO auto-testeó la escritura (§7.C + incidente §8.3); el
    diálogo/flujo se validó SOLO LECTURA (3 botones verde/rojo/gris 311px, `pendingIds` poblado,
    confirmación honesta). La **verificación funcional de la escritura la realiza el usuario** en
    un proyecto de prueba (su decisión autorizada).
  * **Forma robusta (recomendada, escalada):** para cubrir TODAS las pendientes (incl. las de
    otros responsables) con atribución correcta y transaccionalidad testeable, el batch-autocomplete
    debe vivir en el backend. El frontend solo cubre las gestionables por el usuario que cierra.
- **Implementado (consentimiento informado, `Contenedor_head.jsp`):** override diferido de
  `ProyectoProceso` (listado `Proyecto.jsp`, cierre a TERMINADO). Antes de cerrar, consulta las
  métricas reales por **fetch de LECTURA** (`opc=7&ipy=<id>`), parsea y muestra:
  * Escenario A (pendientes > 0): panel "📊 X Total | Y Finalizadas | Z Pendientes" + advertencia
    + 2 opciones informadas: "Finalizar dejando actividades pendientes" (acción normal `opc=6`) y
    "Cancelar / Volver a Gestión". SIN autocompletar.
  * Escenario B (pendientes = 0, total > 0): "✅ Todas las actividades finalizadas
    satisfactoriamente. ¿Confirmar cierre?" → Confirmar / Cancelar.
  * total = 0: "Esta memoria no tiene actividades registradas. ¿Confirmar…?".
  * fallback: si el fetch/parse falla, confirmación simple sin bloquear.
  Verificado en navegador SOLO LECTURA (proj 42=Escenario A 23/0/23; sintético=Escenario B;
  proj 2=total 0). Modal visible 478×512, CSS de swal cargado. Cero writes (no se confirmó
  ningún cierre; todos los fetch fueron GET de lectura).
- **Alcance:** el cambio de estado en sí (`opc=6`, f_salida=TERMINADO/FINALIZADO) es del backend;
  el frontend solo envuelve la confirmación. El enforcement real del cierre (Gate A §8.4) sigue
  siendo backend. FINALIZADO vía `ProyectoRevision` (picker multi-estado legacy) queda como está.
- **Nota:** el listado `Proyecto.jsp` tiene un error legacy pre-existente de `LiveValidation.js`
  (`Cannot read properties of undefined (reading 'push')`) ajeno a esta capa; documentado por
  trazabilidad, fuera de alcance `.op-*`.
- **Estado:** RESUELTO en frontend (consentimiento informado + Opción 1 autocompletar AUTORIZADA
  por negocio, con guardarraíl de permisos y matiz ALCOA documentado; verificación de escritura a
  cargo del usuario). Cobertura total de pendientes y cierre real permanecen como trabajo de backend.

### 8.10 Integración con Office Platform (gestor descentralizado :8080): upload/descarga universal, rediseño acordeón, terminología TERMINADA y borradores persistentes

Cluster de sprints de UX del panel de gestión (Modo Gestión / Pantalla Dividida) y su integración con
el microservicio `office-platform`. VERIFICADO por el usuario en navegador con su sesión (el desarrollador
NO autopruebó escrituras, §7.C). Único archivo tocado en el repo DHF: `web/Contenedor_head.jsp`.

- **Hallazgo clave (contrato real del gestor, verificado con curl):** `office-platform` es **Spring Boot /
  Java**, NO Node/Multer. `POST :8080/api/files/upload` exige un part JSON `@RequestPart("request")` con
  `originalFileName` (`@NotBlank`); sin él → `MissingServletRequestPartException` (HTTP 500). El frontend no
  lo mandaba → el upload fallaba y caía a un id falso → 404. El bloqueo de tipos NO es por extensión: es por
  MIME (`MimeUtils.isAllowed`, match EXACTO) contra `application.yml:39` (solo Office+PDF por defecto).
  Respuesta 201: `resp.data.fileId` / `originalFileName` / `mimeType` (no existe `title`). Detalle completo
  en la memoria persistente `office-platform-upload-contract`.
- **Cambio de BACKEND en `office-platform` (repo aparte, AUTORIZADO — no es el backend DHF congelado §4.1):**
  env `OFFICE_PLATFORM_STORAGE_ALLOWED_MIME_TYPES` en `docker-compose.yml` (Spring relaxed binding) con
  lista ampliada (rfc822, ms-outlook, octet-stream, zip, imágenes, dwg/dxf, etc.) → almacenamiento universal
  (.eml/.msg/CAD/zip/imágenes). Aplicado con `docker compose up -d --no-build office-platform-app` (solo
  recrea el app). Rollback: quitar la env + recrear. Commit office-platform `b59bef6`.
- **Upload (frontend):** FormData con el contrato real (`file` + part `request` JSON + `userId` + `userName`).
  Se removió `scope=private`: un archivo privado solo lo descarga su dueño autenticado y `GET {id}/download`
  da 403 con la API-key → sin scope = global a la API-key = descargable. `projectId`/`category` NO existen en
  la firma (removidos). Toast de éxito "Guardado en Gestor Descentralizado".
- **Descarga segura (opOpenOOFile):** los tipos OnlyOffice van al editor (JWT propio); los no-Office
  (.eml/.msg/.zip/.pdf/imágenes) se descargan por **fetch autenticado (X-Api-Key) → blob → `<a download>`**
  (el `window.open` daba 403). Se elimina el modal en blanco "Cargando editor…": todos los botones de apertura
  (tarjeta de anexo, verBtn del gestor, tags `.oo-formatted`) pasan por `opOpenOOFile`, que rutea por tipo.
  404 → aviso "legacy"; 403 → aviso "privado".
- **Rediseño Pantalla Dividida:** bloque principal = Descripción (editable) + Toolbar de Anexos + tarjetas de
  evidencia; Observaciones = **acordeón opcional** (colapsado si vacío); pie de control con botón verde
  reactivo. Reactividad en vivo del estado sin reload (`opReflectActivityState` por id único del badge).
- **Terminología:** el estado de actividad se muestra como **TERMINADA** (badge en detalle, árbol izquierdo,
  previsualizador, toasts) y los botones como **Terminar**, por pedido de negocio. Solo cambió el TEXTO de
  display de la capa `.op-*`; las regex que DETECTAN el "FINALIZADO" legacy que genera `Tag_memoria`, los
  tokens CSS (`--op-status-finalizado-dot`) y los valores al backend (estado=3) quedaron INTACTOS.
- **id_memoria infalible:** extracción por bloque (cba_num → ProyectoEstado → id_memoria) + red de seguridad
  en `opMarkActivityFinalized` → "Marcar como TERMINADA" funciona en el 100% de las actividades de cualquier etapa.
- **Borradores persistentes (localStorage):** descripción y observaciones se autoguardan como borrador local
  ({v,t}) con clave namespaceada por `ipy`+`estadoM`. Sobreviven navegación, F5 y cierre de pestaña/navegador.
  Limpieza: **TTL de 7 días** (purga al cargar + descarte al leer) y **borrado al marcar TERMINADA** (el botón
  principal borra ambos borradores; el mini "Terminar"/opc=13 borra solo el de observación, porque no persiste
  la descripción). Es conveniencia por-navegador; el guardado real sigue siendo BD (opc=11 observación, opc=10
  descripción). Tolerante a fallos (try/catch, memoria como fuente primaria).
- **Commits DHF:** `6aae0fc` (toast upload), `797c791` (contrato @RequestPart + acordeón), `fc1b266` (descarga
  autenticada + scope global + persistencia navegación + TERMINADA botón), `6d86aa4` (badge TERMINADA en 3
  vistas), `4896333` (ruteo no-Office + id_memoria + micro-botón Terminar), `64e874c` (reactividad badge +
  persistencia memoria), `139bb57` (borradores sessionStorage), `74f31a3` (borradores localStorage + TTL 7d
  + limpieza al TERMINAR).
- **Escalado a backend (fuera de alcance `.op-*`):** archivo *privado* + descargable requiere cablear `userId`
  en el permiso de descarga de `office-platform`, o devolver+guardar el `uuid` y bajar por `/download/{uuid}`
  (endpoint sin chequeo de permisos). Batch transaccional del cargue masivo (§8.5-P2) sigue pendiente.
- **Estado:** RESUELTO y VERIFICADO por el usuario. Backend `office-platform` ampliado (env). Persistencia
  real server-side y descarga de privados permanecen como trabajo de backend.

### 8.11 Plantilla ISO 13485 en el Cargue Masivo: árbol de sub-etapas compacto + corrección de navegación entre pasos

Mejora del wizard de Cargue Masivo (Modo Gestión, `opShowBatchCreationModal`). Único archivo tocado en el
repo DHF: `web/Contenedor_head.jsp`. VERIFICADO parcialmente: build/deploy OK, matcher validado contra la BD
viva; la creación real de actividades la ejecuta y verifica el usuario en sandbox (§7.C — no autopruebo escrituras).

- **Plantilla `OP_TEMPLATE_ISO_13485`:** estructura oficial 7.3.2→7.3.9 con sub-etapas (letra, fase, sel, obl),
  tomada del catálogo REAL `fase` de la BD (`fase.fk_etapa → etapa`, ISO2016/estado=1). Defaults: 7.3.3-D OFF,
  7.3.9 completa OFF, 7.3.6/7.3.7 (Verificación/Validación) ON+obligatorias. Descripciones = texto oficial del
  catálogo (opción validada por el negocio), editables por el usuario en el Paso 2.
- **Matching seguro (numeral REAL, nunca inventado):** `_opNorm` (mapa de acentos, ASCII-safe) + `opMatchTemplate`
  matchean el TEXTO de cada opción real del `select[name="numeral"]` (que es `fase.letra + " " + fase.fase`,
  ver `Tag_memoria.java:2107-2109`) a un item del template por contención normalizada, en orden 7.3.2→7.3.9
  (evita falsos positivos de fases cortas como "REVISAR"). El **numeral posteado a `opc=9` es SIEMPRE el `value`
  real de la opción** (`obj_fases[6]`, id por-proyecto) → cero riesgo de misfiling en `MemoriaDLog`. Las opciones
  sin match caen en "Otras sub-etapas" (seleccionables, degradación limpia). Verificado con `mysql` contra
  `diseno_desarrollo_dos`: de 26 opciones vivas 7.3.x, 25 agrupan bajo su cláusula; solo `ACTIVIDADES DEL
  PROYECTO` (etapa 7.3.2.1, bucket libre) queda en "Otras" — correcto. Se agregó `ESTABLECIMIENTO DE LOS
  OBJETIVOS DEL DISEÑO` (id_fase 46) al template para que agrupe bajo 7.3.2.
- **Paso 2 rediseñado (lista compacta, sin "card dentro de card"):** encabezado de cláusula delgado con badge de
  cantidad; filas limpias (`border-bottom` + hover `#f8fafc`) con checkbox + título en negrita arriba, y debajo
  textarea de 1 línea (expandible en focus/resize) + select de documento fijo a 160px. CSS en el bloque `.op-*`
  central. Checkbox padre (cláusula) togglea hijos; obligatorio = checkbox deshabilitado + badge + tooltip
  (se excluye desmarcando la cláusula completa).
- **Fix navegación entre pasos:** el botón "Volver a Etapas" no hacía nada porque `opRenderWizardStep` es local
  del IIFE y los `onclick` inline corren en scope GLOBAL (ReferenceError). Se expuso `window.opRenderWizardStep`.
  Además `opBatchNextToStep2` ahora persiste la selección real del Paso 1 en `_opSelectedStages`, de modo que al
  volver se conservan los checkboxes que el usuario había marcado.
- **Restricciones respetadas:** sin `.java`/`.sql`; firma del POST intacta (`opc=9`: estado, ipy, id_usuario,
  fecha_reg, numeral, observacion); sin tocar persistencia (`opSaveDraft`/`opReadDraft`/`opClearDraft`/
  `_opStateCache`/`opReflectActivityState`) ni el gestor de documentos (`opUploadLocalFileForActivity`/`opOpenOOFile`).
  Nada se crea hasta que el usuario confirma en el Paso 3.
- **Diagnóstico del warning del IDE:** `Contenedor_head.jsp:783:17` "Also define the standard property 'line-clamp'"
  es un FALSO POSITIVO del linter CSS (regla vendorPrefix sobre `-webkit-line-clamp` en `.op-activity-card-title`,
  CSS preexistente ajeno al wizard). NO es error de JS (`node --check` = OK, 0 bytes de control).
- **Limitación de verificación en vivo:** el `opc=7` devuelve HTTP 500 por el StackOverflowError del NetBeans
  HTTP Monitor (`MonitorFilter`, infra de dev, ajeno al código) hasta reiniciar Tomcat; y el extension de
  Claude-in-Chrome no tiene permiso de host para `localhost:8085`. Por eso la validación del árbol se hizo contra
  la BD viva (matcher) + un preview interactivo, no por captura de la app.
- **Commits DHF:** `38c1e7f` (plantilla + árbol + numeral real), `3cf7f1d` (agrupar Establecimiento bajo 7.3.2),
  `be9d657` (fix navegación Volver + rediseño compacto).
- **Estado:** IMPLEMENTADO, compilado y desplegado. Verificación funcional de la creación real (numerales +
  textos oficiales) a cargo del usuario en proyecto sandbox.

### 8.12 UX integral: filtro de etapas cargadas, autosave con debounce, visor nativo de correos/no-Office y Gestor de Archivos embebido

Sprint de 5 puntos sobre `web/Contenedor_head.jsp` + una vista JSP nueva mínima (autorizada). Verificado en
build/deploy; la verificación funcional en navegador la hace el usuario (§7.C — el navegador de automatización
no tiene permiso de host para `localhost:8085` y `opc=7` da 500 por el HTTP Monitor de NetBeans hasta reiniciar).

- **P1 — Filtrar etapas ya cargadas (Cargue Masivo, Paso 1):** `opDetectLoadedFases()` lee el DOM real de la
  memoria (`Tag_memoria`): cada fase es una `<table>` con `<thead th>` = "letra fase" y `<tbody id="mycard-collapse-N">`
  con sus actividades; si el tbody contiene "AUTOR" -> esa fase tiene actividades -> ya cargada. En el Paso 1 las
  cargadas van deshabilitadas + opacity 0.5 + "(Ya cargada ✓)"; solo se marcan las pendientes. Si están todas ->
  mensaje "todas las etapas ISO cargadas" + "Siguiente" deshabilitado. Match por `_opNorm` (mismo del template).
  NOTA (§8.8): es scraping de DOM; ante estructura inesperada degrada seguro (mostraría la etapa habilitada), no
  bloquea de más. Fix real seguiría siendo backend que exponga numerales usados.
- **P2 — Autosave de la descripción con debounce 500ms:** `opDescInput` escribe memoria en el acto y debouncea la
  persistencia en localStorage (namespace `opdraft:{ipy}:{estadoM}:desc:{index}-{subIndex}`). Restaura al navegar/F5.
- **P3 — "Anexar enlace":** `opPromptAddHyperlink` ya cumplía (prompt + normaliza https:// + inserta + autoguarda);
  luego el BOTÓN se removió del toolbar por-actividad (ver corrección 3 abajo), conservando la función por si se usa.
- **P4 — Gestor de Archivos completo:** modal overlay (`opOpenFileManagerModal`) con iframe. La 1ª versión cargaba
  `OfficePlatform.jsp` (que hace `jsp:include` de `Contenedor_head.jsp` -> duplicaba navbar/sidebar/header). CORREGIDO:
  se creó **`web/OfficePlatformEmbed.jsp`** (vista mínima, autorizada como excepción a §4.2.1): solo el `<div id="office-platform">`
  + el `<script>` del widget con `data-container`/`data-token`/`data-user-*` (token vía `Methods.OfficePlatformResolver.resolveToken`,
  misma sesión), SIN shell. El iframe del modal apunta a esa vista -> gestor limpio (pestañas/búsqueda/cards) sin duplicar la app.
- **P5 — Visor nativo (sin modal en blanco de OnlyOffice):** `opOpenOOFile` rutea por tipo. Office (docx/xlsx/pptx/odt/ods/odp)
  -> editor OnlyOffice. `.eml` -> modal `opFileViewerModal` con cabeceras De/Para/CC/Asunto/Fecha + cuerpo (texto o HTML en
  iframe `sandbox`), parser RFC822 pragmático (`opParseEml`, multipart/QP/base64, con fallbacks). `.msg` -> aviso + descarga
  (binario, no parseable en browser). PDF -> iframe; imagen -> img; texto/código -> `<pre>`; resto -> descarga autenticada.
  Botón "Descargar" en el modal. Todo por fetch con X-Api-Key (blob).
- **Correcciones post-validación:** (1) iframe del gestor -> `OfficePlatformEmbed.jsp` (limpio, arriba). (2) Se QUITÓ el
  botón "Gestor de Archivos" del toolbar del header; el gestor vive ahora en el toolbar por-actividad (`op-doc-toolbar`):
  el picker se renombró a "Vincular archivo" (`opOpenFileManagerForActivity`, inserta `oo:` en la actividad) y se agregó
  "Gestor de Archivos" (`opOpenFileManagerModal`, gestor completo). (3) Se QUITÓ el botón "Anexar Enlace" del render de
  la actividad (el textarea ya reconoce URLs); la función `opPromptAddHyperlink` se conserva.
- **Restricciones:** sin `.java`/`.sql`; firma del POST y audit trail intactos; sin tocar persistencia ni el resto de la
  capa `.op-*`. `OfficePlatformEmbed.jsp` es una vista nueva (no modifica backend congelado).
- **Warning IDE `783:17` (`line-clamp`):** FALSO POSITIVO del linter CSS (vendorPrefix sobre `-webkit-line-clamp` en
  `.op-activity-card-title`, preexistente). No es error de JS (`node --check` OK, 0 bytes de control).
- **Commits DHF:** `f94b9e7` (P2/P5), `3dade26` (P1/P4), `<correcciones>` (embed limpio + reubicación botón + quitar Anexar Enlace).
- **Estado:** IMPLEMENTADO, compilado y desplegado (incl. `OfficePlatformEmbed.jsp`). Verificación funcional en navegador
  (previo reinicio de Tomcat para limpiar el 500 del HTTP Monitor) a cargo del usuario.

### 8.13 Subida a Office Platform con Bearer token, vinculación desde el widget y visores nativos

Correcciones sobre la integración con el gestor `office-platform` (:8080). Único archivo DHF: `web/Contenedor_head.jsp`
(+ `web/OfficePlatformEmbed.jsp`, vista mínima ya creada en §8.12). Diagnóstico hecho con `curl` contra :8080 y con
lectura del código del widget; verificación funcional en navegador a cargo del usuario (§7.C).

- **Causa raíz de "los archivos subidos no aparecen en el gestor" (identidad, verificada con curl):** el widget sube y
  lista con el **Bearer token del usuario** (el `data-token` del `<script>` del widget = identidad resuelta por
  `Methods.OfficePlatformResolver.resolveToken(cedula,nombre)`). El upload del DHF usaba `X-Api-Key` → el archivo quedaba
  con dueño = la api-key (no un usuario) → NO aparecía en "Mis archivos" del usuario que el widget consulta
  (`GET /api/files?scope=private` con Bearer). Confirmado: subir con Bearer + `scope=private` (query) → visible en Mis
  archivos + descargable con Bearer (200); el mismo archivo con X-Api-Key → 403.
- **Fix upload (`opUploadLocalFileForActivity`):** sube igual que el widget → `Authorization: Bearer <data-token>` +
  `?scope=private` en la query, sin `userId` explícito (el token porta la identidad). Fallback sin token: `X-Api-Key` +
  `scope=shared` (global descargable). Sigue insertando `oo:<id>:<nombre>` en la observación + autoguardado.
- **Fix descarga (`opOpenOOFile`):** descarga con Bearer token (abre los privados del usuario); si 401/403, reintenta con
  X-Api-Key (archivos globales/legacy). Sin token → api-key directo.
- **Vinculación con el widget real (puente same-origin, §8.12 + este sprint):** el widget NO expone `postMessage` ni
  callback de selección (verificado). Como el iframe (`OfficePlatformEmbed.jsp`) es same-origin, se lee su DOM:
  cada tarjeta lleva `data-id` (widget línea 3114) y `.op-selected` al seleccionar; el nombre en `.op-card-name`.
  * `opOpenFileManagerModal(index, subIndex)` recuerda la actividad origen; botón "Vincular seleccionado"
    (`opLinkSelectedFileFromIframe`) para selección múltiple.
  * Clic-para-vincular: listener en CAPTURA sobre el doc del iframe intercepta el clic en la tarjeta ANTES del onclick
    del widget y ejecuta `opLinkFile(id,name)` (inserta `oo:`, autoguarda, cierra). Controles explícitos (menú ⋮,
    checkbox, botones) se dejan pasar → la previsualización sigue por el menú.
- **Visores nativos (`opOpenOOFile`, §8.12):** Office → editor OnlyOffice; `.eml` → modal con cabeceras + cuerpo
  (parser RFC822); `.msg` → aviso + descarga; PDF → iframe; imagen → img; texto → `<pre>`; resto → descarga. Todo por
  fetch autenticado (Bearer/api-key) + blob.
- **Picker viejo retirado:** los botones por-actividad "Vincular archivo" (que abrían `opOpenFileManagerForActivity`,
  el picker in-house) se removieron; cada actividad tiene "Gestor de Archivos" → widget real. `opOpenFileManagerForActivity`
  queda definida pero huérfana (0 llamadas), se puede borrar luego.
- **Infra — HTTP Monitor de NetBeans DESACTIVADO (§8 incidente):** el `MonitorFilter` en `conf/web.xml` global de la
  instancia Tomcat recursaba en cada FORWARD/INCLUDE (Memorias.jsp→Contenedor_head) → StackOverflow → 500 en `opc=7`.
  Se comentó (backup `conf/web.xml.op-bak`). Además, la causa del 500 sin sesión es el `catch`-forward a `opc=1&ipy=0` de
  `Proyecto.java:1346` (backend congelado): sin login toda petición a `opc=7` entra en bucle — es artefacto de "sin sesión",
  para usuarios logueados `opc=7` abre normal. Tomcat se relanza con `Start-Process` (proceso detached que sobrevive la
  sesión), NO con `run_in_background` (el harness lo mata).
- **Fragilidad conocida:** el puente de vinculación depende del DOM interno del widget (`.op-card[data-id]`,
  `.op-selected`, `.op-card-name`). Si office-platform cambia esas clases, hay que reajustar. Lo robusto sería que el
  widget exponga un `postMessage` de selección → escalado al dueño de office-platform.
- **Commits DHF:** `7577a0c` (puente vincular + flush descripción + scope inicial), `f2cae73` (quitar picker viejo),
  `e4545c1` (upload/download con Bearer token).
- **Estado:** IMPLEMENTADO, compilado y desplegado. Verificación funcional (subir → ver en Mis archivos → abrir con visor;
  clic-para-vincular) a cargo del usuario logueado en el navegador.

### 8.14 Fix "Autocompletar y Finalizar": clasificación de actividades pendientes

Bug: al usar "Autocompletar observaciones y Finalizar" (diálogo de cierre §8.9), la memoria cerraba a TERMINADO pero las
actividades individuales quedaban en estado 1 ("En Proceso"), no en estado 3 (TERMINADA). Único archivo: `web/Contenedor_head.jsp`.

- **Diagnóstico (cadena completa, backend congelado leído solo-lectura + BD viva):**
  * `opc=13` (case 13, `Proyecto.java:647-653`) → `MemoriaDJpaController.Cambiar_estado_actividad`:
    `UPDATE memoria_d SET estado=<e> WHERE id_memoria_d=<param>`. Persiste bien (BD: proyectos con todas est3).
  * `cba_num` = `id_memoria_d` (confirmado: SP `sp_m_d_t_memoria` devuelve `m.id_memoria_d` como columna [0]).
  * Estado en `Tag_memoria`: estado 1 → `<b class="text-info">EN PROCESO</b>`; estado 3 → `<b class="text-success">FINALIZADO</b>`.
  * **CAUSA:** `opParseMemoriaMetrics` clasificaba finalizada con `t.querySelector('.text-success') || /FINALIZAD/i.test(txt)`.
    La regex `/FINALIZAD/i` sobre TODO el texto de la tabla es demasiado laxa: matchea "FINALIZAD" en respuestas/adjuntos/
    nombre de fase → marca pendientes como finalizadas → `pendingIds` incompleto/vacío → el loop no emite opc=13 para ellas
    → quedan en estado 1, pero el proyecto cierra igual.
- **Fix:**
  * Clasificación precisa: finalizada solo si existe el marcador REAL `t.querySelector('b.text-success')` cuyo texto contiene
    FINALIZAD. Sin la regex laxa. Así las pendientes se detectan y entran a `pendingIds` → se finalizan (opc=11 + opc=13).
  * Guarda honesta: si aún quedaran pendientes cuyo `id_memoria_d` no está en el DOM (links permission-gated a otros
    responsables), NO cierra en silencio; informa "puedo finalizar N de M; el resto requiere su responsable/backend" y el
    usuario decide (`ctx.pendientes` agregado al contexto de cierre).
- **Límite (escalado, §8.9):** finalizar server-side TODAS las pendientes (incl. de otros responsables, sin depender del DOM)
  requiere un endpoint batch en el backend. Para admin (`id_cargo==6`) todos los ids están visibles → cobertura 100% client-side.
- **Commit DHF:** `a1311a0`.
- **Estado:** IMPLEMENTADO, compilado y desplegado. Verificación funcional (autocompletar → recargar → badges verdes
  TERMINADA en la previsualización) a cargo del usuario en proyecto sandbox.

### 8.15 Blindaje del login contra caídas del widget :8080 (defer) + recuperación del contenedor Docker

Incidente: el login (`index.jsp`) quedaba en pantalla azul en blanco, colgado varios minutos. Único archivo DHF: `web/Contenedor_head.jsp`.

- **Causa raíz (Network tab del usuario + verificado con curl):** el `<script src="http://localhost:8080/office-platform-widget.js">`
  (inyectado por `Contenedor_head.jsp`, presente en TODA página incl. el login) era **bloqueante** (sin defer/async). El
  contenedor `office-platform-app` (:8080) estaba **caído** (postgres/minio/onlyoffice seguían up; solo el app murió), así
  que el navegador esperaba el script indefinidamente → bloqueaba el render → pantalla azul. Confirmado: :8080 daba HTTP 000
  (timeout ~8s).
- **Fix (síntoma):** se agregó **`defer`** al `<script>` del widget. Descarga en paralelo y ejecuta tras el parse -> el DOM se
  pinta SIEMPRE, aunque :8080 esté caído/lento. El widget igual auto-monta en DOMContentLoaded (defer corre antes de DCL).
  Degradación limpia: si :8080 cae, el login carga normal y solo el gestor de archivos queda inoperante hasta que vuelva.
  (El widget del embed `OfficePlatformEmbed.jsp` va dentro del modal on-demand, no afecta al login; se dejó igual.)
- **Recuperación (causa):** `docker compose up -d` en `office-platform` levantó el `office-platform-app` caído. Verificado:
  `:8080/office-platform-widget.js` -> 200; login/index -> 200 en ~5 ms.
- **Nota operativa:** Tomcat se gestiona detached con `Start-Process cmd /c start_tomcat_daemon.bat` (sobrevive la sesión;
  NO usar run_in_background del harness, que lo mata). MySQL de la app corre en :3307 (no :3306). Si el login vuelve a
  colgarse, revisar primero que `office-platform-app` (:8080) esté arriba (`docker ps`) y `docker logs office-platform-app`
  por si murió (OOM/excepción). `iniciar_servicios.bat` levanta MySQL + Docker + Tomcat al inicio de jornada.
- **Commit DHF:** `599c8e1`.
- **Estado:** RESUELTO y VERIFICADO por el usuario (login carga de inmediato, entra a la app sin bloqueos).
