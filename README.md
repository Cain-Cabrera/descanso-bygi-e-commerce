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
- [Módulos funcionales](#-módulos-funcionales)
- [Módulo backend](#-módulo-backend)
- [Módulo frontend](#-módulo-frontend)
- [Cómo empezar](#-cómo-empezar)
- [Variables de entorno](#-variables-de-entorno)
- [Roadmap](#-roadmap)
- [Equipo](#-equipo)

---

## 📖 Sobre el proyecto

**Descanso by Gi** es una colchonería real que opera hoy únicamente a través de redes sociales y WhatsApp, sin un canal digital propio. Esto limita su alcance: no hay catálogo disponible las 24 horas, no hay forma de comparar productos sin contacto directo, no hay indexación en buscadores y no se recopilan datos sobre el comportamiento de compra.

Este proyecto construye una plataforma web que resuelve esos problemas, permitiendo a los clientes explorar el catálogo, registrarse, armar pedidos y ver historial de compras, y a la administración gestionar productos, stock y pedidos desde un panel propio.

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

- [Ver diagrama entidad-relación (ER)](./docs/Diagramas/ER_PostgreSQL_Descanso_by_Gi.png)
- [Ver diagrama UML](./docs/Diagramas/UML-Descanso-by.png)

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
| Frontend | React + TypeScript + Tailwind CSS 4 |
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
│   ├── Diagramas/
│   │   ├── ER_PostgreSQL_Descanso_by_Gi.png   # Diagrama entidad-relación
│   │   └── UML-Descanso-by.png                # Diagrama UML del sistema
│   ├── diccionario-datos.md                   # Diccionario de datos
│   ├── reglas-negocio.md                      # Reglas de negocio
│   └── requisitos.md                          # Requisitos del sistema
└── README.md
```

---

## 🧩 Módulos funcionales

El sistema se organiza en los siguientes módulos, definidos a partir de los [requisitos funcionales](./docs/requisitos.md):

| Módulo | Descripción | Requisitos |
|---|---|---|
| 👤 **Usuarios** | Registro, inicio de sesión y gestión de la cuenta (consulta y modificación de datos personales). | RF01 – RF03 |
| 🛒 **Catálogo** | Consulta de productos, agrupación por categorías, búsqueda, filtros y detalle de producto con imágenes. | RF04 – RF08 |
| 🧺 **Carrito** | Consulta del carrito, agregar productos con cantidad, modificar cantidades y eliminar productos. | RF09 – RF12 |
| 📦 **Pedidos** | Confirmación del carrito como pedido y consulta del historial y detalle de pedidos del cliente. | RF13 – RF15 |
| ⚙️ **Administración** | Gestión de productos, categorías, marcas, imágenes, stock, pedidos y usuarios. Acceso restringido por rol (RNF06). | RF16 – RF22 |

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

### Requisitos previos

- Java 21+
- Node.js 22.12+
- PostgreSQL

### Backend

```bash
cd backend
./gradlew bootRun
```

Antes de iniciarlo, configurá las variables de PostgreSQL y `JWT_SECRET` indicadas abajo. En desarrollo, Hibernate crea o actualiza las tablas al iniciar el backend (`spring.jpa.hibernate.ddl-auto=update`).

### Frontend

```bash
cd frontend
cp .env.example .env
pnpm install
pnpm dev
```

En Windows PowerShell, usá `Copy-Item .env.example .env` en lugar de `cp`.

### Base de datos

Creá una base PostgreSQL vacía llamada `descanso_by_gi` (o usá el nombre que definas en `DB_URL`). El backend crea las tablas de las entidades al iniciarse. El repositorio no incluye archivos `schema.sql` ni `data.sql`; para agregar los `CHECK` del diccionario a una base que ya tenía las tablas, ejecutá una vez `backend/sql/restricciones-integridad.sql`.
## 🔐 Variables de entorno

> ⚠️ **Importante:** El backend no arranca sin estas cuatro variables.  
> **Nunca subas credenciales reales al repositorio.**

| Variable | Descripción | Ejemplo |
|:---|:---|:---|
| `DB_URL` | URL de conexión a PostgreSQL | `jdbc:postgresql://<host>:<puerto>/<base_de_datos>` |
| `DB_USERNAME` | Usuario de la base de datos | `<usuario>` |
| `DB_PASSWORD` | Contraseña de la base de datos | `<contraseña>` |
| `JWT_SECRET` | Clave para firmar los tokens JWT (mínimo 32 caracteres, distinta en cada entorno) | `<clave>` |
| `CORS_ALLOWED_ORIGINS` | Origen(es) del frontend permitidos, separados por coma; opcional en local | `http://localhost:5173` |

El frontend usa `VITE_API_URL`, definido en `frontend/.env.example` para desarrollo local.

### ☁️ Preparación para despliegue

La configuración de despliegue está preparada en `render.yaml`, `backend/Dockerfile` y `netlify.toml`. El backend se ejecuta en Render con Java 21; el frontend se construye en Netlify con Node.js 22.12 y pnpm. La regla de redirección de Netlify permite que React Router abra directamente las rutas de la aplicación.

El despliegue real queda pendiente de configurar las cuentas y credenciales del equipo:

1. Crear la base PostgreSQL en Supabase y copiar los datos de conexión. En Render, completar `DB_URL` con una URL JDBC (`jdbc:postgresql://...`) y el modo SSL requerido por la conexión de Supabase; completar también `DB_USERNAME` y `DB_PASSWORD`. Si Supabase ya tenía las tablas antes de esta corrección, ejecutar `backend/sql/restricciones-integridad.sql` en su editor SQL.
2. Crear el servicio web de Render desde el repositorio usando el Blueprint `render.yaml`. Configurar `JWT_SECRET` con una clave aleatoria de al menos 32 caracteres y `CORS_ALLOWED_ORIGINS` con el origen del sitio Netlify (sin rutas ni barra final).
3. Importar el mismo repositorio en Netlify. La configuración toma `frontend/` como directorio base y `frontend/dist` como publicación. Definir `VITE_API_URL` como la URL HTTPS pública del backend de Render, sin agregar `/api`.
4. Cuando ambos servicios estén publicados, verificar registro e inicio de sesión, catálogo, operaciones del carrito, creación de pedidos y acceso administrativo en el entorno desplegado.

No guardes credenciales en estos archivos ni en el repositorio; ingresalas en las variables de entorno de cada servicio. Para desarrollo local, `frontend/.env.example` conserva la URL local.

#### Crear el primer administrador

El registro público crea usuarios con rol `USUARIO`; no permite elegir ni asignarse el rol administrador. Para habilitar el panel en un entorno nuevo:

1. Registrá la cuenta que usará el administrador desde la página. Así la contraseña queda almacenada con el cifrado que usa la aplicación.
2. Desde el editor SQL de Supabase, con una cuenta autorizada para administrar la base, promovela indicando el correo registrado:

```sql
UPDATE public.usuarios
SET rol = 'ADMINISTRADOR'
WHERE mail = 'correo-del-administrador@ejemplo.com'
  AND eliminado = FALSE;
```

3. Confirmá que la operación afectó una fila. Cerrá sesión y volvé a ingresar para que el nuevo token incluya el rol actualizado.


### 🖥️ Configuración local

En **Windows PowerShell**, ejecutá:

```powershell
$env:DB_URL="jdbc:postgresql://<host>:<puerto>/<base_de_datos>"
$env:DB_USERNAME="<usuario>"
$env:DB_PASSWORD="<contraseña>"
$env:JWT_SECRET="<clave-de-al-menos-32-caracteres>"
$env:CORS_ALLOWED_ORIGINS="http://localhost:5173"

```
---

## 🗺️ Roadmap

- [x] Análisis y diseño (requisitos, DB, arquitectura)
- [x] Setup inicial del proyecto (repos, dependencias)
- [x] Backend base + APIs de productos y categorías
- [x] Autenticación (login/registro) + carrito
- [x] Checkout + panel administrativo
- [x] Integración, pruebas automatizadas y documentación
- [ ] Pruebas manuales de los flujos en el navegador
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






