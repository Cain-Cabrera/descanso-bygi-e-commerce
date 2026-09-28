# Diccionario de datos — Descanso by Gi

## 1. Descripcion general

La base de datos del sistema Descanso by Gi utiliza PostgreSQL. Las tablas se encuentran relacionadas mediante claves primarias y claves foraneas. Los registros utilizan eliminacion logica mediante el campo `eliminado`.

## 2. Diccionario de datos

### Tabla: usuarios

| Campo | Tipo | Restricciones | Descripcion |
|---|---|---|---|
| id | BIGINT | PK | Identificador unico del usuario |
| fecha_alta | TIMESTAMP | NOT NULL | Fecha y hora de creacion |
| eliminado | BOOLEAN | NOT NULL | Indica si el registro fue eliminado logicamente |
| nombre | VARCHAR(100) | NOT NULL | Nombre del usuario |
| apellido | VARCHAR(100) | NOT NULL | Apellido del usuario |
| mail | VARCHAR(150) | NOT NULL, UNIQUE | Correo electronico del usuario |
| contrasena | VARCHAR(255) | NOT NULL | Contrasena del usuario |
| celular | VARCHAR(30) | | Numero de celular |
| rol | VARCHAR(20) | NOT NULL | Rol del usuario |

Valores permitidos para `rol`:

- ADMINISTRADOR
- USUARIO

---

### Tabla: categorias

| Campo | Tipo | Restricciones | Descripcion |
|---|---|---|---|
| id | BIGINT | PK | Identificador unico de la categoria |
| fecha_alta | TIMESTAMP | NOT NULL | Fecha y hora de creacion |
| eliminado | BOOLEAN | NOT NULL | Indica si el registro fue eliminado logicamente |
| nombre | VARCHAR(100) | NOT NULL | Nombre de la categoria |
| descripcion | VARCHAR(255) | | Descripcion de la categoria |

---

### Tabla: marcas

| Campo | Tipo | Restricciones | Descripcion |
|---|---|---|---|
| id | BIGINT | PK | Identificador unico de la marca |
| fecha_alta | TIMESTAMP | NOT NULL | Fecha y hora de creacion |
| eliminado | BOOLEAN | NOT NULL | Indica si el registro fue eliminado logicamente |
| nombre | VARCHAR(100) | NOT NULL | Nombre de la marca |

---

### Tabla: productos

| Campo | Tipo | Restricciones | Descripcion |
|---|---|---|---|
| id | BIGINT | PK | Identificador unico del producto |
| fecha_alta | TIMESTAMP | NOT NULL | Fecha y hora de creacion |
| eliminado | BOOLEAN | NOT NULL | Indica si el registro fue eliminado logicamente |
| nombre | VARCHAR(150) | NOT NULL | Nombre del producto |
| descripcion | VARCHAR(500) | | Descripcion del producto |
| precio | NUMERIC(10,2) | NOT NULL | Precio actual del producto |
| stock | INTEGER | NOT NULL | Cantidad disponible del producto |
| marca_id | BIGINT | FK, NOT NULL | Marca a la que pertenece el producto |
| categoria_id | BIGINT | FK, NOT NULL | Categoria a la que pertenece el producto |

Relaciones:

- `marca_id` referencia a `marcas.id`.
- `categoria_id` referencia a `categorias.id`.

---

### Tabla: imagenes

| Campo | Tipo | Restricciones | Descripcion |
|---|---|---|---|
| id | BIGINT | PK | Identificador unico de la imagen |
| fecha_alta | TIMESTAMP | NOT NULL | Fecha y hora de creacion |
| eliminado | BOOLEAN | NOT NULL | Indica si el registro fue eliminado logicamente |
| url | VARCHAR(500) | NOT NULL | URL de la imagen almacenada |
| producto_id | BIGINT | FK, NOT NULL | Producto al que pertenece la imagen |

Relaciones:

- `producto_id` referencia a `productos.id`.

---

### Tabla: carritos

| Campo | Tipo | Restricciones | Descripcion |
|---|---|---|---|
| id | BIGINT | PK | Identificador unico del carrito |
| fecha_alta | TIMESTAMP | NOT NULL | Fecha y hora de creacion |
| eliminado | BOOLEAN | NOT NULL | Indica si el registro fue eliminado logicamente |
| estado | VARCHAR(20) | NOT NULL | Estado actual del carrito |
| usuario_id | BIGINT | FK, NOT NULL | Usuario propietario del carrito |

Valores permitidos para `estado`:

- ACTIVO
- ELIMINADO

Relaciones:

- `usuario_id` referencia a `usuarios.id`.
- No posee restriccion `UNIQUE`, permitiendo conservar carritos historicos.

---

### Tabla: items_carrito

| Campo | Tipo | Restricciones | Descripcion |
|---|---|---|---|
| id | BIGINT | PK | Identificador unico del item |
| fecha_alta | TIMESTAMP | NOT NULL | Fecha y hora de creacion |
| eliminado | BOOLEAN | NOT NULL | Indica si el registro fue eliminado logicamente |
| cantidad | INTEGER | NOT NULL | Cantidad del producto en el carrito |
| subtotal | NUMERIC(10,2) | NOT NULL | Subtotal correspondiente a la cantidad del producto |
| carrito_id | BIGINT | FK, NOT NULL | Carrito al que pertenece el item |
| producto_id | BIGINT | FK, NOT NULL | Producto agregado al carrito |

Relaciones:

- `carrito_id` referencia a `carritos.id`.
- `producto_id` referencia a `productos.id`.

---

### Tabla: pedidos

| Campo | Tipo | Restricciones | Descripcion |
|---|---|---|---|
| id | BIGINT | PK | Identificador unico del pedido |
| fecha_alta | TIMESTAMP | NOT NULL | Fecha y hora de creacion |
| eliminado | BOOLEAN | NOT NULL | Indica si el registro fue eliminado logicamente |
| total | NUMERIC(10,2) | NOT NULL | Importe total del pedido |
| forma_de_pago | VARCHAR(20) | NOT NULL | Forma de pago seleccionada |
| usuario_id | BIGINT | FK, NOT NULL | Usuario que realizo el pedido |

Valores permitidos para `forma_de_pago`:

- EFECTIVO
- TARJETA
- TRANSFERENCIA

Relaciones:

- `usuario_id` referencia a `usuarios.id`.

> Nota: La tabla `pedidos` no contiene un campo `estado`.

---

### Tabla: detalles_pedido

| Campo | Tipo | Restricciones | Descripcion |
|---|---|---|---|
| id | BIGINT | PK | Identificador unico del detalle |
| fecha_alta | TIMESTAMP | NOT NULL | Fecha y hora de creacion |
| eliminado | BOOLEAN | NOT NULL | Indica si el registro fue eliminado logicamente |
| cantidad | INTEGER | NOT NULL | Cantidad de unidades del producto |
| precio_unitario | NUMERIC(10,2) | NOT NULL | Precio del producto al momento de realizar el pedido |
| subtotal | NUMERIC(10,2) | NOT NULL | Subtotal del detalle |
| pedido_id | BIGINT | FK, NOT NULL | Pedido al que pertenece el detalle |
| producto_id | BIGINT | FK, NOT NULL | Producto incluido en el pedido |

Relaciones:

- `pedido_id` referencia a `pedidos.id`.
- `producto_id` referencia a `productos.id`.

---

## 3. Resumen de relaciones

| Relacion | Cardinalidad |
|---|---|
| Usuario - Carrito | 1:N |
| Usuario - Pedido | 1:N |
| Categoria - Producto | 1:N |
| Marca - Producto | 1:N |
| Producto - Imagen | 1:N |
| Carrito - ItemCarrito | 1:N |
| Producto - ItemCarrito | 1:N |
| Pedido - DetallePedido | 1:N |
| Producto - DetallePedido | 1:N |

## 4. Consideraciones

- `Base` no constituye una tabla independiente; sus atributos se heredan en las entidades correspondientes.
- Las relaciones se implementan mediante claves foraneas.
- Los campos monetarios utilizan `NUMERIC(10,2)`.
- Los campos `cantidad` y `stock` utilizan `INTEGER`.
- Los valores que anteriormente podrian representarse mediante ENUM se almacenan como `VARCHAR` y pueden restringirse mediante `CHECK` en PostgreSQL.
- La tabla `imagenes` almacena unicamente la URL asociada al producto, sin campo `descripcion`.
- La relacion entre `usuarios` y `carritos` es 1:N para permitir conservar carritos historicos, manteniendo conceptualmente un unico carrito activo por usuario.
