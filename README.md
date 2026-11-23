# Rol de misiones para los Avengers – Taller Semana 7

Programación III (ISWZ-2102) – UDLA

Aplicación en Java con interfaz gráfica (Swing) que registra, modifica y lista Avengers, y calcula su aporte al Fondo de Héroes, el impuesto del gobierno y el pago neto a recibir.

## Formulación del problema

**Entradas:** ID (único), nombre, misión, nivel de peligrosidad (1 a 5) y pago mensual (en dólares).

**Procesos:** registrar, modificar por ID, listar y calcular fondo, impuesto y pago neto.

**Salida:** informe por Avenger con nombre, pago mensual, aporte al Fondo de Héroes, impuesto del gobierno y pago neto.

### Reglas de cálculo

| Concepto | Fórmula |
|---|---|
| Fondo de Héroes | pago mensual × 0.08 |
| Pago anual | pago mensual × 12 |
| Impuesto anual | porcentaje del tramo aplicado solo sobre el exceso (ver tabla) |
| Impuesto mensual | impuesto anual / 12 |
| Pago neto | pago mensual − fondo − impuesto mensual |

| Pago anual | Impuesto |
|---|---|
| <= 50,000 | 0 % |
| > 50,000 y <= 100,000 | 10 % del exceso a 50,000 |
| > 100,000 y <= 200,000 | 20 % del exceso a 100,000 |
| > 200,000 | 30 % del exceso a 200,000 |

Todos los valores monetarios se redondean a 2 decimales.

### Validaciones

- El ID no puede repetirse al registrar y debe existir para modificar.
- El nombre y la misión no pueden estar vacíos.
- La peligrosidad debe estar entre 1 y 5.
- El pago mensual debe ser numérico y mayor a 0.

## Estructura del proyecto

| Archivo | Descripción |
|---|---|
| `Avenger.java` | Entidad con sus atributos y validaciones. |
| `CalculadoraPagos.java` | Cálculo del fondo, el impuesto y el pago neto. |
| `RepositorioAvengers.java` | Interfaz de almacenamiento (registrar, buscar, modificar, listar). |
| `RepositorioArrayList.java` | Alternativa 1: ArrayList con búsqueda lineal. |
| `RepositorioHashMap.java` | Alternativa 2: acceso directo por ID con tabla hash. |
| `VentanaPrincipal.java` | Interfaz gráfica en Swing (programa principal). |
| `PruebasLogica.java` | Casos de prueba CP-01 a CP-16. |
| `ComparacionRendimiento.java` | Comparación de tiempos entre las dos alternativas. |

## Cómo compilar y ejecutar

Requisito: JDK 11 o superior.

### Desde IntelliJ IDEA

1. Abrir la carpeta del proyecto (File → Open).
2. Abrir `src/VentanaPrincipal.java` y pulsar ▶ junto a `main` para iniciar la aplicación.
3. Para los casos de prueba o la comparación de rendimiento, ejecutar de la misma forma `PruebasLogica.java` o `ComparacionRendimiento.java`.

### Desde la terminal

```bash
javac -d out src/*.java
java -cp out VentanaPrincipal
java -cp out PruebasLogica
java -cp out ComparacionRendimiento
```

## Integrantes

- Viteri Carlos – Formulación del problema
- Cevallos Emily – Alternativa 1 (ArrayList)
- Aniceto Martín – Alternativa 2 (HashMap)
- Sebastian – Lógica en Java
- Quinapallo Isaac – Interfaz gráfica
- Besade – Análisis y fundamentación
# Avengers-Taller-semana-7