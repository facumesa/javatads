# Sistema de Viajes

[![Java](https://img.shields.io/badge/Java-8%2B-ED8B00?logo=openjdk&logoColor=white)](https://www.oracle.com/java/technologies/downloads/)
[![JUnit](https://img.shields.io/badge/tests-JUnit%204-25A162?logo=junit5&logoColor=white)](https://junit.org/junit4/)
[![Build](https://img.shields.io/badge/build-Apache%20Ant-A81C7D?logo=apache-ant&logoColor=white)](https://ant.apache.org/)

Sistema académico de gestión aeroportuaria desarrollado en Java. Permite administrar pasajeros, aeropuertos, vuelos, reservas, check-in, embarques y disponibilidad de asientos, utilizando estructuras de datos implementadas desde cero.

El proyecto fue realizado como obligatorio de **Algoritmos y Estructuras de Datos** y evita depender del framework de colecciones de Java para resolver el núcleo del sistema.

## Funcionalidades

- Registro, búsqueda y listado de pasajeros por cédula.
- Listados ascendentes, descendentes y filtrados por categoría.
- Registro y consulta de aeropuertos y vuelos.
- Ciclo de estados del vuelo: `PROGRAMADO → ABIERTO → CERRADO → FINALIZADO`.
- Reservas con hasta un 10 % de sobreventa.
- Check-in limitado por la capacidad real del vuelo.
- Cola FIFO de vuelos cerrados por aeropuerto para embarque y despegue.
- Consulta de grupos de asientos contiguos por clase.
- Validación de datos y resultados tipados mediante `Retorno`.

## Estructuras de datos

Las estructuras utilizadas por la lógica de negocio están implementadas en el paquete `tads`:

| Estructura | Implementación | Uso principal |
| --- | --- | --- |
| Lista simplemente enlazada | `Lista<T>` y `Nodo<T>` | Pasajeros, aeropuertos, vuelos y reservas |
| Pila (LIFO) | `Pila<T>` | Recorrido descendente de listas |
| Cola (FIFO) | `Cola<T>` | Orden de embarque de vuelos por aeropuerto |

Las entidades implementan `Comparable` para mantener los datos ordenados. Los pasajeros se ordenan numéricamente por cédula, y los vuelos y aeropuertos por código.

## Arquitectura

```text
.
├── src/
│   ├── dominio/          # Pasajero, Aeropuerto, Vuelo y Reserva
│   ├── sistemaViajes/    # API pública, implementación, enums y retornos
│   └── tads/             # Lista, pila, cola, nodo e interfaces
├── test/
│   └── sistemaViajes/    # Pruebas unitarias JUnit 4
├── nbproject/            # Configuración del proyecto NetBeans
└── build.xml             # Automatización con Apache Ant
```

La interfaz `Sistema` define las operaciones públicas y `ImplementacionSistema` concentra las reglas de negocio. Cada operación devuelve un objeto `Retorno`, cuyo resultado puede ser `OK` o uno de los errores numerados definidos por el contrato.

## Requisitos

- JDK 8 o superior.
- Apache Ant para compilar y ejecutar las pruebas desde la terminal.
- JUnit 4 y Hamcrest. NetBeans resuelve estas bibliotecas mediante sus librerías globales.

También puede abrirse directamente como proyecto de Apache NetBeans.

## Instalación

```bash
git clone https://github.com/facumesa/javatads.git
cd javatads
```

Para compilar:

```bash
ant compile
```

Para ejecutar todas las pruebas:

```bash
ant test
```

Para limpiar los artefactos generados:

```bash
ant clean
```

> El proyecto expone una API de dominio y no incluye una clase `main` ni una interfaz gráfica. Su comportamiento se ejecuta y valida mediante las pruebas unitarias.

## Ejemplo de uso

```java
Sistema sistema = new ImplementacionSistema();

sistema.inicializarSistema();
sistema.registrarAeropuerto("MVD", "Carrasco");
sistema.registrarAeropuerto("EZE", "Ezeiza");

sistema.registrarPasajero(
    "3.335.321-2",
    "Juan",
    45,
    Categoria.FRECUENTE
);

sistema.registrarVuelo("MVD", "EZE", "VU001", 100, 250);
sistema.realizarReserva("VU001", "3.335.321-2");
sistema.abrirVuelo("VU001");
sistema.realizarCheckIn("VU001", "3.335.321-2");
sistema.cerrarVuelo("VU001");
sistema.embarqueYDespegueDeVuelo("MVD");
```

## Reglas de negocio destacadas

- La cédula uruguaya se recibe con formato `X.XXX.XXX-X` o `XXX.XXX-X`.
- Un pasajero, aeropuerto o vuelo no puede registrarse dos veces.
- Las reservas se aceptan mientras el vuelo esté programado o abierto.
- El check-in solo puede realizarse con el vuelo abierto y una reserva previa.
- Al cerrar un vuelo, este ingresa en la cola del aeropuerto de origen.
- Los vuelos despegan respetando el orden de cierre de esa cola.
- La cabina se divide en primera clase (columnas 1–3), ejecutiva (4–8) y turista (9–26).

## Pruebas

El repositorio incluye **118 casos de prueba** distribuidos en 16 suites. Cubren caminos exitosos, validaciones, estados inválidos y casos límite de todas las operaciones de la interfaz `Sistema`.

Entre los escenarios comprobados se encuentran el orden de los listados, la sobreventa, el límite de check-in, el orden FIFO de embarque y la búsqueda de asientos contiguos.

## Autor

**Facundo Mesa** — proyecto académico de Algoritmos y Estructuras de Datos.
