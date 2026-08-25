<div align="center">

# 🛏️ Descanso by Gi — E-commerce

**Plataforma de e-commerce para una colchonería real, desarrollada como Proyecto Final Integrador.**

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4-brightgreen?logo=springboot)
![React](https://img.shields.io/badge/React-TypeScript-blue?logo=react)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?logo=postgresql&logoColor=white)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

</div>

---

## 📋 Índice

- [Sobre el proyecto](#-sobre-el-proyecto)
- [Funcionalidades](#-funcionalidades)
- [Stack tecnológico](#-stack-tecnológico)
- [Estructura del repositorio](#-estructura-del-repositorio)
- [Cómo empezar](#-cómo-empezar)
- [Roadmap](#-roadmap)
- [Equipo](#-equipo)
- [Licencia](#-licencia)

---

## 📖 Sobre el proyecto

**Descanso by Gi** es una colchonería real que opera hoy únicamente a través de redes sociales y WhatsApp, sin un canal digital propio. Esto limita su alcance: no hay catálogo disponible las 24 horas, no hay forma de comparar productos sin contacto directo, no hay indexación en buscadores y no se recopilan datos sobre el comportamiento de compra.

Este proyecto construye una plataforma web que resuelve esos problemas, permitiendo a los clientes explorar el catálogo, registrarse, armar pedidos y hacer seguimiento de su compra, y a la administración gestionar productos, stock y pedidos desde un panel propio.

> Desarrollado como Proyecto Final Integrador — Tecnicatura Universitaria en Programación a Distancia (TUPaD), UTN Facultad Regional San Nicolás.

## ✨ Funcionalidades

- 🛒 Catálogo público con categorías, búsqueda y detalle de producto
- 👤 Registro, login y cuenta de cliente
- 🧺 Carrito de compras persistente
- ✅ Confirmación de pedido con coordinación de pago por WhatsApp/transferencia
- ⚙️ Panel administrativo para gestión de pedidos y stock

## 🧱 Stack tecnológico

| Capa | Tecnología |
|---|---|
| Frontend | React + TypeScript |
| Backend | Java + Spring Boot |
| Base de datos | MySQL |

## 📁 Estructura del repositorio

```
.
├── backend/     # API REST en Spring Boot
├── frontend/    # Aplicación React + TypeScript
└── README.md
```

## 🚀 Cómo empezar

> ⚠️ Proyecto en etapa inicial de desarrollo — las instrucciones se irán completando a medida que avancen los sprints.

### Requisitos previos

- Java 21+
- Node.js 18+
- PostgreSQL 

### Backend

```bash
cd backend
./mvnw spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm run dev
```

## 🗺️ Roadmap

- [ ] Diseño y setup del proyecto
- [ ] Backend base + catálogo
- [ ] Autenticación + carrito
- [ ] Checkout + panel admin
- [ ] Integración final y documentación


## 👥 Equipo

| Nombre |
|---|
| Cain Cabrera Bertolazzi |
| Leoel Jesus Allabay |
| Alex Nahuel Austin |

## 📄 Licencia

Este proyecto se distribuye bajo licencia MIT. Ver [LICENSE](LICENSE) para más detalles.
