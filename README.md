# ✈️ Sistema de Gestión & Estructuras de Datos (TADs)

![Java](https://img.shields.io/badge/Java-17%2B-orange?style=for-the-badge&logo=java)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Completado-brightgreen?style=for-the-badge)

Proyecto de implementación y desarrollo de **Tipos de Datos Abstractos (TADs)** personalizados y estructuras de datos dinámicas en **Java**, aplicado a la resolución de algoritmos y optimización de datos.

---

## 📌 Tabla de Contenidos

- [Acerca del Proyecto](#-acerca-del-proyecto)
- [Estructuras de Datos Implementadas (TADs)](#-estructuras-de-datos-implementadas-tads)
- [Requisitos Previos](#-requisitos-previos)
- [Instalación y Ejecución](#-instalación-y-ejecución)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Pruebas y Evaluación](#-pruebas-y-evaluación)
- [Autores](#-autores)

---

## 🛠️ Acerca del Proyecto

El objetivo principal de este proyecto es la implementación **desde cero** (sin el uso del framework de colecciones estándar de Java) de las principales estructuras de datos dinámicas y lineales. 

Se hace énfasis en:
- Uso correcto de **memoria dinámica** y manejo de punteros/referencias.
- Implementación de **contratos e interfaces** para asegurar la abstracción.
- Análisis de **complejidad temporal y espacial** (Notación Big-O) en cada operación.

---

## 📐 Estructuras de Datos Implementadas (TADs)

Entre las estructuras desarrolladas en el proyecto se destacan:

| Estrategia / TAD | Tipo | Operaciones Principales | Complejidad Promedio |
| :--- | :--- | :--- | :---: |
| **Lista Enlazada** | Lineal | `insertar`, `eliminar`, `buscar`, `obtener` | $O(1)$ / $O(n)$ |
| **Pila (Stack)** | LIFO | `push`, `pop`, `top`, `isEmpty` | $O(1)$ |
| **Cola (Queue)** | FIFO | `enqueue`, `dequeue`, `front`, `isEmpty` | $O(1)$ |
| **Tabla Hash / Mapeo** | No Lineal | `insertar`, `obtener`, `eliminar` | $O(1)$ |

---

## 💻 Requisitos Previos

Asegúrate de tener instalado en tu entorno local:

- **JDK 17** o superior
- Un IDE compatible (IntelliJ IDEA, Eclipse, VS Code o NetBeans)
- **Git** para clonar el repositorio

---

## ⚙️ Instalación y Ejecución

1. **Clonar el repositorio:**
   ```bash
   git clone [https://github.com/facumesa/javatads.git]
   cd proyecto-tads