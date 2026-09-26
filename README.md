<div align="center">

# 🛏️ Descanso by Gi — E-commerce

**Plataforma de e-commerce para una colchonería real, desarrollada como Proyecto Final Integrador.**

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4-brightgreen?logo=springboot)
![React](https://img.shields.io/badge/React-TypeScript-blue?logo=react)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?logo=mysql&logoColor=white)


</div>

---

## 📋 Índice

- [Sobre el proyecto](#sobre-el-proyecto)
- [Alcance](#alcance)
- [Funcionalidades](#funcionalidades)
- [Stack tecnológico](#stack-tecnológico)
- [Estructura del repositorio](#estructura-del-repositorio)
- [Modulo backend](#Modulo-backend)
- [Modulo frontend](#Modulo-frontend)
- [Cómo empezar](#cómo-empezar)
- [Roadmap](#roadmap)
- [Equipo](#equipo)
- [Licencia](#licencia)

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



## 📊 Alcance

### Alcance del Producto

**Características principales que se van a construir:**

- 🛒 Catálogo público con categorías, búsqueda y detalle de producto
- 👤 Registro, login y gestión de cuenta
- 🧺 Carrito de compras persistente
- ✅ Confirmación de pedido (coordinación de pago por WhatsApp/transferencia)
- 📦 Seguimiento de pedidos en tiempo real
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
- Base de datos: Setup MySQL, scripts DDL/DML, migraciones
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
- Deploy frontend en Vercel/Netlify
- Deploy backend en Render/Railway
- Deploy DB en servicios cloud (Supabase, AWS RDS, etc.)
- Testing en ambiente de producción

**6. Video explicativo** 
- Video demostrando el proyecto 

---

## ✨ Funcionalidades

- 🛒 Explorar catálogo con búsqueda y filtros
- 👤 Crear cuenta y login
- 🧺 Carrito de compras
- ✅ Realizar pedido
- 📦 Ver estado de pedidos
- ⚙️ Panel administrativo (productos, stock, órdenes)
- 📊 Reportes básicos de ventas

---

## 🧱 Stack tecnológico

| Capa | Tecnología |
|---|---|
| Frontend | React + TypeScript |
| Backend | Java + Spring Boot |
| Base de datos | MySQL |

---

## 📁 Estructura del repositorio

```
descanso-by-gi/
├── backend/                 # API REST en Spring Boot
│   ├── src/
│   ├── pom.xml
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
La siguiente estructura corresponde a la **planificacion de los modulos y funcionalidades previstas para el frontend** del proyecto. Su implementacion se realizara durante las etapas posteriores del desarrollo.


```
Frontend
├── Inicio
│   ├── Presentación
│   └── Productos destacados
│
├── Catálogo
│   ├── Listado de productos
│   ├── Categorías
│   ├── Búsqueda
│   ├── Filtros
│   └── Detalle de producto
│       └── Galería de imágenes
│
├── Usuario
│   ├── Registro
│   ├── Inicio de sesión
│   └── Mi cuenta
│
├── Carrito
│   ├── Visualizar carrito
│   ├── Agregar productos
│   ├── Modificar cantidades
│   ├── Eliminar productos
│   └── Resumen
│
├── Pedidos
│   ├── Confirmar pedido
│   ├── Historial de pedidos
│   └── Estado del pedido
│
└── Administración
    ├── Gestión de productos
    ├── Gestión de categorías
    ├── Gestión de stock
    └── Gestión de pedidos
```


## 🚀 Cómo empezar

> ⚠️ Proyecto en etapa inicial de desarrollo — las instrucciones se irán completando a medida que avancen los sprints.

### Requisitos previos

- Java 21+
- Node.js 18+
- MySQL

### Backend

```bash
cd backend
./mvnw spring-boot:run
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
mysql -u root -p < schema.sql

# Insertar datos iniciales
mysql -u root -p descanso_by_gi < data.sql
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

## 📄 Licencia

Este proyecto se distribuye bajo licencia MIT. Ver [LICENSE](LICENSE) para más detalles.






