# Diccionario de datos — Descanso by Gi

## 1. Descripcion

El presente documento describe las tablas, campos, tipos de datos y relaciones que conforman la base de datos del sistema Descanso by Gi.

La base de datos utiliza PostgreSQL y sigue un modelo relacional.

---

## 2. Tabla: usuarios

Almacena la informacion de los usuarios registrados en el sistema.

| Campo | Tipo | Clave | Nulo | Descripcion |
|---|---|---|---|---|
| id | BIGINT | PK | No | Identificador unico del usuario |
| fecha_alta | TIMESTAMP | - | No | Fecha y hora de alta del registro |
| eliminado | BOOLEAN | - | No | Indica si el registro fue eliminado logicamente |
| nombre | VARCHAR(100) | - | No | Nombre del usuario |
| apellido | VARCHAR(100) | - | No | Apellido del usuario |
| mail | VARCHAR(150) | UNIQUE | No | Correo electronico del usuario |
| contrasena | VARCHAR(255) | - | No | Contrasena del usuario |
| celular | VARCHAR(30) | - | Si | Numero de celular del usuario |
| rol | VARCHAR(20) | - | No | Rol del usuario dentro del sistema |

### Valores de rol

- ADMINISTRADOR
- USUARIO

---

## 3. Tabla: categorias

Almacena las categorias utilizadas para clasificar los productos.

| Campo | Tipo | Clave | Nulo | Descripcion |
|---|---|---|---|---|
| id | BIGINT | PK | No | Identificador unico de la categoria |
| fecha_alta | TIMESTAMP | - | No | Fecha y hora de alta del registro |
| eliminado | BOOLEAN | - | No | Indica si el registro fue eliminado logicamente |
| nombre | VARCHAR(100) | - | No | Nombre de la categoria |
| descripcion | VARCHAR(255) | - | Si | Descripcion de la categoria |

---

## 4. Tabla: marcas

Almacena las marcas asociadas a los productos.

| Campo | Tipo | Clave | Nulo | Descripcion |
|---|---|---|---|---|
| id | BIGINT | PK | No | Identificador unico de la marca |
| fecha_alta | TIMESTAMP | - | No | Fecha y hora de alta del registro |
| eliminado | BOOLEAN | - | No | Indica si el registro fue eliminado logicamente |
| nombre | VARCHAR(100) | - | No | Nombre de la marca |

---

## 5. Tabla: productos

Almacena la informacion de los productos disponibles en el catalogo.

| Campo | Tipo | Clave | Nulo | Descripcion |
|---|---|---|---|---|
| id | BIGINT | PK | No | Identificador unico del producto |
| fecha_alta | TIMESTAMP | - | No | Fecha y hora de alta del registro |
| eliminado | BOOLEAN | - | No | Indica si el registro fue eliminado logicamente |
| nombre | VARCHAR(150) | - | No | Nombre del producto |
| descripcion | VARCHAR(500) | - | Si | Descripcion del producto |
| precio | NUMERIC(10,2) | - | No | Precio actual del producto |
| stock | INTEGER | - | No | Cantidad disponible del producto |
| marca_id | BIGINT | FK | No | Identificador de la marca del producto |
| categoria_id | BIGINT | FK | No | Identificador de la categoria del producto |

### Relaciones

- `marca_id` referencia a `marcas.id`.
- `categoria_id` referencia a `categorias.id`.

---

## 6. Tabla: imagenes

Almacena las imagenes asociadas a los productos.

| Campo | Tipo | Clave | Nulo | Descripcion |
|---|---|---|---|---|
| id | BIGINT | PK | No | Identificador unico de la imagen |
| fecha_alta | TIMESTAMP | - | No | Fecha y hora de alta del registro |
| eliminado | BOOLEAN | - | No | Indica si el registro fue eliminado logicamente |
| url | VARCHAR(500) | - | No | URL de la imagen almacenada |
| producto_id | BIGINT | FK | No | Identificador del producto al que pertenece la imagen |

### Relacion

- `producto_id` referencia a `productos.id`.
- Un producto puede tener varias imagenes.

---

## 7. Tabla: carritos

Almacena los carritos de compra asociados a los usuarios.

| Campo | Tipo | Clave | Nulo | Descripcion |
|---|---|---|---|---|
| id | BIGINT | PK | No | Identificador unico del carrito |
| fecha_alta | TIMESTAMP | - | No | Fecha y hora de alta del registro |
| eliminado | BOOLEAN | - | No | Indica si el registro fue eliminado logicamente |
| estado | VARCHAR(20) | - | No | Estado actual del carrito |
| usuario_id | BIGINT | FK | No | Identificador del usuario propietario del carrito |

### Valores de estado

- ACTIVO
- ELIMINADO

### Relacion

- `usuario_id` referencia a `usuarios.id`.
- Un usuario puede tener varios carritos a lo largo del tiempo.
- Un usuario puede tener un unico carrito activo.

---

## 8. Tabla: items_carrito

Almacena los productos incluidos dentro de los carritos.

| Campo | Tipo | Clave | Nulo | Descripcion |
|---|---|---|---|---|
| id | BIGINT | PK | No | Identificador unico del item |
| fecha_alta | TIMESTAMP | - | No | Fecha y hora de alta del registro |
| eliminado | BOOLEAN | - | No | Indica si el registro fue eliminado logicamente |
| cantidad | INTEGER | - | No | Cantidad del producto dentro del carrito |
| subtotal | NUMERIC(10,2) | - | No | Subtotal correspondiente al producto |
| carrito_id | BIGINT | FK | No | Identificador del carrito |
| producto_id | BIGINT | FK | No | Identificador del producto |

### Relaciones

- `carrito_id` referencia a `carritos.id`.
- `producto_id` referencia a `productos.id`.
- Un carrito puede contener varios items.
- Cada item corresponde a un producto.

---

## 9. Tabla: pedidos

Almacena los pedidos realizados por los usuarios.

| Campo | Tipo | Clave | Nulo | Descripcion |
|---|---|---|---|---|
| id | BIGINT | PK | No | Identificador unico del pedido |
| fecha_alta | TIMESTAMP | - | No | Fecha y hora de alta del pedido |
| eliminado | BOOLEAN | - | No | Indica si el registro fue eliminado logicamente |
| total | NUMERIC(10,2) | - | No | Importe total del pedido |
| forma_de_pago | VARCHAR(20) | - | No | Forma de pago seleccionada |
| usuario_id | BIGINT | FK | No | Identificador del usuario que realiza el pedido |

### Valores de forma de pago

- EFECTIVO
- TARJETA
- TRANSFERENCIA

### Relacion

- `usuario_id` referencia a `usuarios.id`.
- Un usuario puede realizar varios pedidos.

---

## 10. Tabla: detalles_pedido

Almacena el detalle de los productos incluidos en cada pedido.

| Campo | Tipo | Clave | Nulo | Descripcion |
|---|---|---|---|---|
| id | BIGINT | PK | No | Identificador unico del detalle |
| fecha_alta | TIMESTAMP | - | No | Fecha y hora de alta del registro |
| eliminado | BOOLEAN | - | No | Indica si el registro fue eliminado logicamente |
| cantidad | INTEGER | - | No | Cantidad del producto solicitado |
| precio_unitario | NUMERIC(10,2) | - | No | Precio del producto al momento del pedido |
| subtotal | NUMERIC(10,2) | - | No | Subtotal correspondiente al producto |
| pedido_id | BIGINT | FK | No | Identificador del pedido |
| producto_id | BIGINT | FK | No | Identificador del producto |

### Relaciones

- `pedido_id` referencia a `pedidos.id`.
- `producto_id` referencia a `productos.id`.
- Un pedido puede contener varios detalles.
- Cada detalle corresponde a un producto.

---

## 11. Resumen de relaciones

| Tabla origen | Cardinalidad | Tabla destino | Descripcion |
|---|---|---|---|
| usuarios | 1 : N | carritos | Un usuario puede tener varios carritos a lo largo del tiempo, pero solo uno activo |
| carritos | 1 : N | items_carrito | Un carrito contiene varios items |
| productos | 1 : N | items_carrito | Un producto puede aparecer en varios items |
| usuarios | 1 : N | pedidos | Un usuario puede realizar varios pedidos |
| pedidos | 1 : N | detalles_pedido | Un pedido contiene varios detalles |
| productos | 1 : N | detalles_pedido | Un producto puede aparecer en varios detalles |
| categorias | 1 : N | productos | Una categoria puede tener varios productos |
| marcas | 1 : N | productos | Una marca puede tener varios productos |
| productos | 1 : N | imagenes | Un producto puede tener varias imagenes |

---

## 12. Convencion de nombres

Las tablas y columnas de la base de datos utilizan una convencion de nombres consistente:

- Nombres en minusculas.
- Sin tildes.
- Tablas en plural.
- Palabras compuestas utilizando guion bajo.
- Claves foraneas utilizando el formato `entidad_id`.

Ejemplos:

- `usuarios`
- `categorias`
- `marcas`
- `productos`
- `imagenes`
- `carritos`
- `items_carrito`
- `pedidos`
- `detalles_pedido`
- `categoria_id`
- `marca_id`
- `producto_id`
- `usuario_id`
