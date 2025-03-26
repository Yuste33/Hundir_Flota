# Juego de Batalla Naval

Un clásico juego de batalla naval implementado en Java, con funcionalidad para dos jugadores.

## Características principales

- 🚢 **Sistema de flotas** con diferentes tipos de barcos
- 🎯 **Tableros interactivos** para cada jugador
- ⚔️ **Mecánica de turnos** alternados
- 💥 **Detección de impactos** y hundimientos
- 📊 **Seguimiento de estado** del juego

## Tipos de barcos disponibles

1. **Acorazado** - Tamaño: 5 celdas
2. **Fragata** - Tamaño: 3 celdas  
3. **Canoa** - Tamaño: 1 celda

## Cómo jugar

1. **Configuración inicial**:
   - Cada jugador coloca sus 3 barcos en el tablero
   - Selecciona posición (X,Y) y orientación (horizontal/vertical)

2. **Durante el juego**:
   - Por turnos, los jugadores atacan coordenadas
   - El sistema indica "Impacto" o "Agua"
   - Gana quien hunda primero toda la flota enemiga

3. **Fin del juego**:
   - Muestra el tablero completo de ambos jugadores
   - Anuncia al ganador

## Requisitos del sistema

- Java JDK 11 o superior
- Maven (para gestión de dependencias)

## Estructura del proyecto
