<div align="center">

# 🛏️ Descanso by Gi — E-commerce

**Plataforma de e-commerce para una colchonería real, desarrollada como Proyecto Final Integrador.**

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4-brightgreen?logo=springboot)
![React](https://img.shields.io/badge/React-TypeScript-blue?logo=react)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?logo=postgresql&logoColor=white)


</div>

---

## 📋 Índice

- [Sobre el proyecto](#-sobre-el-proyecto)
- [Funcionalidades](#-funcionalidades)
- [Documentación](#-documentación)
- [Diagramas](#-diagramas)
- [Alcance](#-alcance)
- [Stack tecnológico](#-stack-tecnológico)
- [Estructura del repositorio](#-estructura-del-repositorio)
- [Módulo backend](#-módulo-backend)
- [Módulo frontend](#-módulo-frontend)
- [Cómo empezar](#-cómo-empezar)
- [Variables de entorno](#-variables-de-entorno)
- [Roadmap](#-roadmap)
- [Equipo](#-equipo)

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

## 📚 Documentación

Toda la documentación técnica del proyecto se encuentra centralizada en la carpeta [`docs/`](./docs/).

### 📄 Documentos

- [Requisitos](./docs/requisitos.md)  
  Requisitos funcionales y no funcionales del sistema.

- [Reglas de negocio](./docs/reglas-negocio.md)  
  Reglas que definen el comportamiento y las restricciones del sistema.

- [Diccionario de datos](./docs/diccionario-datos.md)  
  Descripción de las entidades, atributos y datos utilizados por el sistema.

## 📐 Diagramas

La carpeta [`docs/Diagramas/`](./docs/Diagramas/) contiene los principales diagramas utilizados durante el análisis y diseño del proyecto:

- [Diagrama ERR - Base de datos](./docs/Diagramas/Diagrama%20ERR-Base%20de%20datos.jpeg)
- [Diagrama UML](./docs/Diagramas/UML.png)

Los diagramas permiten visualizar la estructura de la base de datos y el diseño de las principales entidades y relaciones del sistema.

## 📊 Alcance

### Alcance del Producto

**Características principales que se van a construir:**

- 🛒 Catálogo público con categorías, búsqueda y detalle de producto
- 👤 Registro, login y gestión de cuenta
- 🧺 Carrito de compras persistente
- ✅ Confirmación de pedido (coordinación de pago por WhatsApp/transferencia)
- ⚙️ Panel administrativo para gestión de productos, stock y órdenes
- 📱 Diseño responsivo (mobile, desktop)

### Alcance del Proyecto

**Trabajo que hay que realizar para entregar el producto:**

**1. Análisis** 
- Análisis de requisitos funcionales y no-funcionales
- Diseño de arquitectura del sistema
- Diseño de base de datos (modelo relacional, tablas, índices)
- Especificación de APIs REST (endpoints, métodos, respuestas)

**2. Desarrollo** 
- Backend: APIs REST en Java Spring Boot (productos, carrito, pedidos, usuarios)
- Frontend: Interfaz React + TypeScript (catálogo, carrito, checkout, admin, cliente)
- Base de datos: PostgreSQL, scripts DDL/DML, migraciones
- Integración frontend-backend

**3. Pruebas** 
- Testing unitario (backend)
- Testing de integración (APIs + DB)
- Testing funcional (casos de uso principales)
- Testing de seguridad (autenticación, validaciones)

**4. Documentación** 
- README completo con instrucciones de instalación
- Documentación técnica (arquitectura, APIs, DB)
- Diagramas (ER diagram)

**5. Deployment** 
- Deploy frontend en Netlify
- Deploy backend en Render
- Deploy DB en Supabase
- Testing en ambiente de producción

**6. Video explicativo** 
- Video demostrando el proyecto 

---

## 🧱 Stack tecnológico

| Capa | Tecnología |
|---|---|
| Frontend | React + TypeScript |
| Backend | Java + Spring Boot |
| Base de datos | PostgreSQL |

---

## 📁 Estructura del repositorio

```
descanso-by-gi/
├── backend/                 # API REST en Spring Boot
│   ├── src/
│   ├── build.gradle
│   └── README.md
├── frontend/                # Aplicación React + TypeScript
│   ├── src/
│   ├── package.json
│   └── README.md
├── docs/                    # Documentación técnica
│   ├── arquitectura.md
│   ├── api.md
│   └── db.md
└── README.md
```

---

## Modulo backend

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/descansobygi/
│   │   │   ├── config/
│   │   │   ├── controllers/
│   │   │   ├── enums/
│   │   │   ├── exception/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   └── service/
│   │   └── resources/
│   └── test/
├── gradle/
├── build.gradle
├── gradlew
├── gradlew.bat
├── settings.gradle
├── .gitattributes
├── .gitignore
└── README.md

```

---

## Modulo frontend
El frontend será desarrollado utilizando React + TypeScript.

```
frontend/
├── src/
│   ├── components/
│   ├── pages/
│   ├── services/
│   ├── types/
│   ├── hooks/
│   ├── App.tsx
│   ├── main.tsx
│   └── index.css
├── public/
├── .env
├── .gitignore
├── package.json
├── tsconfig.json
├── vite.config.ts
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
./gradlew bootRun
```

### Frontend

```bash
cd frontend
pnpm install
pnpm dev
```

### Base de datos

```bash
# Crear base de datos
psql -U postgres -f schema.sql

# Insertar datos iniciales
psql -U postgres -d descanso_by_gi -f data.sql
```
## 🔐 Variables de entorno

> ⚠️ **Importante:** El backend requiere estas tres variables para conectarse a PostgreSQL.  
> **Nunca subas credenciales reales al repositorio.**

| Variable | Descripción | Ejemplo |
|:---|:---|:---|
| `DB_URL` | URL de conexión a PostgreSQL | `jdbc:postgresql://<host>:<puerto>/<base_de_datos>` |
| `DB_USERNAME` | Usuario de la base de datos | `<usuario>` |
| `DB_PASSWORD` | Contraseña de la base de datos | `<contraseña>` |


### 🖥️ Configuración local

En **Windows PowerShell**, ejecutá:

```powershell
$env:DB_URL=jdbc:postgresql://<host>:<puerto>/<base_de_datos>
$env:DB_USERNAME=<usuario>
$env:DB_PASSWORD=<contraseña>

```
---

## 🗺️ Roadmap

- [ ] Análisis y diseño (requisitos, DB, arquitectura)
- [ ] Setup inicial del proyecto (repos, dependencias)
- [ ] Backend base + APIs de productos y categorías
- [ ] Autenticación (login/registro) + carrito
- [ ] Checkout + panel administrativo
- [ ] Integración final, pruebas y documentación
- [ ] Deployment en la nube
- [ ] Video explicativo

---

## 👥 Equipo

| Nombre |
|---|
| Cain Cabrera Bertolazzi |
| Leonel Jesús Allabay |
| Alex Nahuel Austin |

**Tutor:** Sofia Raia.
**Materia:** Trabajo Final Integrador  
**Facultad:** UTN Facultad Regional San Nicolás

---






