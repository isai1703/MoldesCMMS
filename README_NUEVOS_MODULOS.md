# Nuevos Módulos - Sistema CMMS Moldes

## Versión de Base de Datos: 4

## Módulos Implementados

### 1. Gestión de Operadores
**Ubicación:** `OperadoresActivity`, `OperadorFormActivity`

**Características:**
- CRUD completo de operadores
- Información personal (nombre, número de empleado, contacto)
- Asignación laboral (departamento, turno, nivel de experiencia)
- Certificaciones
- Estado activo/inactivo
- Filtros por turno y departamento

**Entidad:** `Operador`
**DAO:** `OperadorDao`

---

### 2. Registro de Producción
**Ubicación:** `RegistroProduccionActivity`, `RegistroProduccionFormActivity`

**Características:**
- Registro de producción por turno
- Asignación de operador, máquina y molde
- Control de piezas producidas y defectuosas
- Cálculo automático de eficiencia
- Registro de tiempos (inicio, fin, paros)
- Motivos de paro
- Observaciones del turno

**Entidad:** `RegistroProduccion`
**DAO:** `RegistroProduccionDao`

**Cálculos automáticos:**
- Tiempo total de producción
- Porcentaje de eficiencia
- Identificación de defectos

---

### 3. Requerimientos de Insumos
**Ubicación:** `RequerimientosInsumoActivity`, `RequerimientoInsumoFormActivity`, `RequerimientoInsumoDetailActivity`

**Características:**
- Solicitud de insumos por departamento
- Tipos: Materia Prima, Material de Empaque, Insumos de Producción, etc.
- Sistema de prioridades (Urgente, Alta, Media, Baja)
- Flujo de aprobación
- Estados: Pendiente → Aprobado → Comprado → Entregado
- Opción de rechazo con motivo
- Información de proveedor sugerido
- Costo estimado
- Especificaciones técnicas

**Entidad:** `RequerimientoInsumo`
**DAO:** `RequerimientoInsumoDao`

**Flujo de trabajo:**
1. Supervisor solicita insumo
2. Sistema marca como "Pendiente"
3. Aprobación o rechazo por autorizado
4. Compras marca como "Comprado"
5. Almacén marca como "Entregado"

---

### 4. Refacciones de Máquinas
**Ubicación:** `RefaccionesMaquinaActivity`, `RefaccionMaquinaFormActivity`

**Características:**
- Inventario de refacciones para máquinas
- Identificación: código, número de parte
- Compatibilidad con modelos de máquinas
- Control de stock (actual, mínimo, máximo)
- Alertas de bajo stock
- Categorías: Eléctricos, Mecánicos, Hidráulicos, Neumáticos, etc.
- Información de compra (precio, proveedor, tiempo de entrega)
- Ubicación en almacén
- Especificaciones técnicas

**Entidad:** `RefaccionMaquina`
**DAO:** `RefaccionMaquinaDao`

**Alertas:**
- Stock agotado (rojo)
- Stock bajo (naranja)
- Stock suficiente (verde)

---

## Integración con Módulos Existentes

### Solicitudes de Mantenimiento
Se actualizó `SolicitudMantenimiento` para:
- Recibir solicitudes del departamento "Mantenimiento Máquinas"
- Nuevos subtipos:
  - Cambio de Conexión Rápida (agua, aire, ambas)
  - Cambio de Manguera (calibres 8, 10, 12)
  - Conexión tipo Y
- Campos adicionales para especificar detalles técnicos

### Base de Datos
**AppDatabase versión 4** incluye todas las entidades:
- Usuario
- Molde
- Mantenimiento
- SolicitudMantenimiento
- Herramienta
- Refaccion
- Departamento
- Maquina
- MantenimientoMaquina
- RefaccionMaquina *(nueva)*
- Producto
- InspeccionCalidad
- OrdenCompra
- Operador *(nueva)*
- RegistroProduccion *(nueva)*
- RequerimientoInsumo *(nueva)*

---

## Pantallas Implementadas

### Operadores
1. `activity_operadores.xml` - Lista de operadores
2. `activity_operador_form.xml` - Formulario CRUD
3. `item_operador.xml` - Item del RecyclerView

### Registro de Producción
1. `activity_registro_produccion.xml` - Lista de registros
2. `activity_registro_produccion_form.xml` - Formulario de registro
3. `item_registro_produccion.xml` - Item del RecyclerView

### Requerimientos de Insumos
1. `activity_requerimientos_insumo.xml` - Lista de requerimientos
2. `activity_requerimiento_insumo_form.xml` - Formulario de solicitud
3. `activity_requerimiento_detail.xml` - Detalle y gestión
4. `item_requerimiento_insumo.xml` - Item del RecyclerView

### Refacciones de Máquinas
1. `activity_refacciones_maquina.xml` - Lista de refacciones
2. `activity_refaccion_maquina_form.xml` - Formulario CRUD
3. `item_refaccion_maquina.xml` - Item del RecyclerView

---

## Menús Implementados
- `menu_operadores.xml` - Filtros por turno y departamento
- `menu_registros.xml` - Filtros y estadísticas
- `menu_requerimientos.xml` - Filtros por estado
- `menu_refacciones_maquina.xml` - Filtro de bajo stock

---

## Uso del Sistema

### Para Operadores
1. Acceder a "Gestión de Operadores"
2. Registrar operadores con sus datos
3. Asignar turnos y departamentos

### Para Registro de Producción
1. Al inicio del turno, crear nuevo registro
2. Seleccionar operador, máquina y molde
3. Al finalizar, ingresar piezas producidas y defectuosas
4. Sistema calcula automáticamente la eficiencia

### Para Requerimientos
1. Supervisor crea requerimiento
2. Personal autorizado aprueba/rechaza
3. Compras marca como comprado
4. Almacén marca como entregado

### Para Refacciones
1. Registrar refacciones en inventario
2. Establecer stocks mínimos
3. Sistema alerta cuando stock es bajo
4. Consultar compatibilidad con máquinas

---

## Notas Técnicas

### Corrutinas y Flow
Todos los módulos usan:
- `lifecycleScope.launch` para operaciones asíncronas
- `Flow<List<T>>` para observar cambios en tiempo real
- `suspend fun` para operaciones de base de datos

### Validaciones
- Campos obligatorios marcados con *
- Validación de números y cantidades
- Validación de estados en flujos de aprobación

### Compatibilidad
- Compatible con Android API 24+
- Material Design Components
- Room Database con migración

---

## Próximas Mejoras Sugeridas

1. **Reportes y Estadísticas**
   - Eficiencia por operador
   - Consumo de insumos por departamento
   - Rotación de refacciones

2. **Notificaciones**
   - Alertas de bajo stock
   - Recordatorios de requerimientos pendientes
   - Notificaciones de producción

3. **Exportación de Datos**
   - Exportar registros a Excel
   - Generar reportes PDF
   - Compartir información

4. **Dashboard**
   - Pantalla principal con indicadores
   - Gráficas de eficiencia
   - Estado general del sistema

---

## Compilación

```bash
# Limpiar proyecto
./gradlew clean

# Compilar
./gradlew build

# Instalar en dispositivo
./gradlew installDebug
Autor
Ing. Oscar Isai Izquierdo Garcia
Sistema CMMS para Gestión de Moldes
Versión 4.0
Fecha: Octubre 2024
Contacto
Contacto
Email: tu.email@ejemplo.com
GitHub: tu-usuario-github
Desarrollado con Kotlin, Room Database y Material Design
