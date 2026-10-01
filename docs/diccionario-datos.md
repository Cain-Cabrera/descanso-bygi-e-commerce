# Diccionario de datos — Descanso by Gi

## 1. Descripcion general

La base de datos del sistema Descanso by Gi utiliza PostgreSQL.

La base de datos esta compuesta por las tablas usuarios, categorias, marcas, productos, imagenes, carritos, items_carrito, pedidos y detalles_pedido.

Las tablas utilizan claves primarias para identificar de forma unica cada registro y claves foraneas para establecer las relaciones entre las entidades.

Los registros utilizan eliminacion logica mediante el campo `eliminado`.

---

## 2. Diccionario de datos

### Tabla: usuarios

| Campo | Tipo | Restricciones | Descripcion |
|---|---|---|---|
| id | BIGINT | PK | Identificador unico del usuario |
| fecha_alta | TIMESTAMP | NOT NULL | Fecha y hora de creacion del registro |
| eliminado | BOOLEAN | NOT NULL | Indica si el registro fue eliminado logicamente |
| nombre | VARCHAR(100) | NOT NULL | Nombre del usuario |
| apellido | VARCHAR(100) | NOT NULL | Apellido del usuario |
| mail | VARCHAR(150) | NOT NULL, UNIQUE | Correo electronico unico del usuario |
| contrasena | VARCHAR(255) | NOT NULL | Contrasena del usuario |
| celular | VARCHAR(30) | | Numero de celular |
| rol | VARCHAR(20) | NOT NULL, CHECK | Rol asignado al usuario |

Valores permitidos para `rol`:

- ADMINISTRADOR
- USUARIO

---

### Tabla: categorias

| Campo | Tipo | Restricciones | Descripcion |
|---|---|---|---|
| id | BIGINT | PK | Identificador unico de la categoria |
| fecha_alta | TIMESTAMP | NOT NULL | Fecha y hora de creacion del registro |
| eliminado | BOOLEAN | NOT NULL | Indica si el registro fue eliminado logicamente |
| nombre | VARCHAR(100) | NOT NULL | Nombre de la categoria |
| descripcion | VARCHAR(255) | | Descripcion de la categoria |

---

### Tabla: marcas

| Campo | Tipo | Restricciones | Descripcion |
|---|---|---|---|
| id | BIGINT | PK | Identificador unico de la marca |
| fecha_alta | TIMESTAMP | NOT NULL | Fecha y hora de creacion del registro |
| eliminado | BOOLEAN | NOT NULL | Indica si el registro fue eliminado logicamente |
| nombre | VARCHAR(100) | NOT NULL | Nombre de la marca |

---

### Tabla: productos

| Campo | Tipo | Restricciones | Descripcion |
|---|---|---|---|
| id | BIGINT | PK | Identificador unico del producto |
| fecha_alta | TIMESTAMP | NOT NULL | Fecha y hora de creacion del registro |
| eliminado | BOOLEAN | NOT NULL | Indica si el registro fue eliminado logicamente |
| nombre | VARCHAR(150) | NOT NULL | Nombre del producto |
| descripcion | VARCHAR(500) | | Descripcion del producto |
| precio | NUMERIC(10,2) | NOT NULL, CHECK | Precio actual del producto |
| stock | INTEGER | NOT NULL, CHECK | Cantidad disponible del producto |
| marca_id | BIGINT | FK, NOT NULL | Identificador de la marca del producto |
| categoria_id | BIGINT | FK, NOT NULL | Identificador de la categoria del producto |

Relaciones:

- `marca_id` referencia a `marcas.id`.
- `categoria_id` referencia a `categorias.id`.

---

### Tabla: imagenes

| Campo | Tipo | Restricciones | Descripcion |
|---|---|---|---|
| id | BIGINT | PK | Identificador unico de la imagen |
| fecha_alta | TIMESTAMP | NOT NULL | Fecha y hora de creacion del registro |
| eliminado | BOOLEAN | NOT NULL | Indica si el registro fue eliminado logicamente |
| url | VARCHAR(500) | NOT NULL | URL donde se encuentra almacenada la imagen |
| producto_id | BIGINT | FK, NOT NULL | Identificador del producto al que pertenece la imagen |

Relaciones:

- `producto_id` referencia a `productos.id`.

---

### Tabla: carritos

| Campo | Tipo | Restricciones | Descripcion |
|---|---|---|---|
| id | BIGINT | PK | Identificador unico del carrito |
| fecha_alta | TIMESTAMP | NOT NULL | Fecha y hora de creacion del registro |
| eliminado | BOOLEAN | NOT NULL | Indica si el registro fue eliminado logicamente |
| estado | VARCHAR(20) | NOT NULL, CHECK | Estado actual del carrito |
| usuario_id | BIGINT | FK, NOT NULL | Identificador del usuario propietario del carrito |

Valores permitidos para `estado`:

- ACTIVO
- ELIMINADO

Relaciones:

- `usuario_id` referencia a `usuarios.id`.
- Un usuario puede tener varios carritos historicos.
- El sistema permite mantener conceptualmente un unico carrito activo por usuario.

---

### Tabla: items_carrito

| Campo | Tipo | Restricciones | Descripcion |
|---|---|---|---|
| id | BIGINT | PK | Identificador unico del item del carrito |
| fecha_alta | TIMESTAMP | NOT NULL | Fecha y hora de creacion del registro |
| eliminado | BOOLEAN | NOT NULL | Indica si el registro fue eliminado logicamente |
| cantidad | INTEGER | NOT NULL, CHECK | Cantidad del producto agregado al carrito |
| subtotal | NUMERIC(10,2) | NOT NULL, CHECK | Importe correspondiente a la cantidad del producto |
| carrito_id | BIGINT | FK, NOT NULL | Identificador del carrito al que pertenece el item |
| producto_id | BIGINT | FK, NOT NULL | Identificador del producto agregado al carrito |

Relaciones:

- `carrito_id` referencia a `carritos.id`.
- `producto_id` referencia a `productos.id`.

---

### Tabla: pedidos

| Campo | Tipo | Restricciones | Descripcion |
|---|---|---|---|
| id | BIGINT | PK | Identificador unico del pedido |
| fecha_alta | TIMESTAMP | NOT NULL | Fecha y hora de creacion del registro |
| eliminado | BOOLEAN | NOT NULL | Indica si el registro fue eliminado logicamente |
| total | NUMERIC(10,2) | NOT NULL, CHECK | Importe total del pedido |
| forma_de_pago | VARCHAR(20) | NOT NULL, CHECK | Forma de pago seleccionada |
| usuario_id | BIGINT | FK, NOT NULL | Identificador del usuario que realizo el pedido |

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
| id | BIGINT | PK | Identificador unico del detalle del pedido |
| fecha_alta | TIMESTAMP | NOT NULL | Fecha y hora de creacion del registro |
| eliminado | BOOLEAN | NOT NULL | Indica si el registro fue eliminado logicamente |
| cantidad | INTEGER | NOT NULL, CHECK | Cantidad de unidades del producto |
| precio_unitario | NUMERIC(10,2) | NOT NULL, CHECK | Precio del producto al momento de realizar el pedido |
| subtotal | NUMERIC(10,2) | NOT NULL, CHECK | Importe correspondiente a la cantidad y precio unitario |
| pedido_id | BIGINT | FK, NOT NULL | Identificador del pedido al que pertenece el detalle |
| producto_id | BIGINT | FK, NOT NULL | Identificador del producto incluido en el pedido |

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

---

## 4. Restricciones CHECK

Las restricciones CHECK permiten validar determinadas reglas de negocio directamente desde la base de datos.

### Productos

- `precio >= 0`: evita registrar precios negativos.
- `stock >= 0`: evita registrar cantidades de stock negativas.

### Items del carrito

- `cantidad > 0`: evita registrar cantidades iguales o menores a cero.
- `subtotal >= 0`: evita registrar subtotales negativos.

### Detalles del pedido

- `cantidad > 0`: evita registrar cantidades iguales o menores a cero.
- `precio_unitario >= 0`: evita registrar precios unitarios negativos.
- `subtotal >= 0`: evita registrar subtotales negativos.

### Pedidos

- `total >= 0`: evita registrar totales negativos.
- `forma_de_pago` solo puede contener los valores definidos por el sistema.

### Usuarios

- `rol` solo puede contener los valores definidos por el sistema.

### Carritos

- `estado` solo puede contener los valores ACTIVO o ELIMINADO.

---

## 5. Indices

Los indices se utilizan para mejorar el rendimiento de las consultas y facilitar las operaciones sobre las relaciones entre tablas.

Los indices definidos en la base de datos son:

| Indice | Tabla | Campo |
|---|---|---|
| idx_productos_marca | productos | marca_id |
| idx_productos_categoria | productos | categoria_id |
| idx_imagenes_producto | imagenes | producto_id |
| idx_carritos_usuario | carritos | usuario_id |
| idx_items_carrito | items_carrito | carrito_id |
| idx_items_producto | items_carrito | producto_id |
| idx_pedidos_usuario | pedidos | usuario_id |
| idx_detalles_pedido | detalles_pedido | pedido_id |
| idx_detalles_producto | detalles_pedido | producto_id |

Estos indices permiten optimizar principalmente las consultas que utilizan claves foraneas para relacionar los registros.

---

## 6. Campos derivados

### Subtotal de items_carrito

El campo `subtotal` representa el importe correspondiente a la cantidad de un producto dentro del carrito.

Se considera un campo derivado porque su valor se obtiene a partir de la cantidad y el precio del producto.

Su almacenamiento permite consultar directamente el importe correspondiente al item del carrito.

### Subtotal de detalles_pedido

El campo `subtotal` representa el importe correspondiente a la cantidad del producto multiplicada por el precio unitario registrado en el detalle.

Se almacena para conservar el importe calculado al momento de realizar el pedido.

### Total de pedidos

El campo `total` representa la suma de los subtotales de los detalles asociados al pedido.

Se almacena para facilitar la consulta del importe total del pedido y conservar el valor correspondiente al momento de realizar la compra.

---

## 7. Eliminacion logica

Las tablas utilizan el campo `eliminado` para implementar eliminacion logica.

Cuando un registro deja de estar disponible, no se elimina fisicamente de la base de datos. En su lugar, el campo `eliminado` se establece en `TRUE`.

Esto permite conservar el historial de los registros y mantener la integridad de las relaciones existentes.

---

## 8. Herencia y clase Base

Las entidades del sistema heredan los atributos definidos en la clase abstracta `Base`.

Los atributos heredados son:

- `id`
- `fecha_alta`
- `eliminado`

La clase `Base` no representa una tabla independiente en el modelo, sino que sus atributos forman parte de las tablas correspondientes.

---

## 9. Persistencia de enumeraciones

Debido a que el sistema utiliza PostgreSQL, los valores que en el UML se representan como enumeraciones se almacenan en la base de datos mediante campos `VARCHAR` con restricciones `CHECK`.

Las enumeraciones del modelo son:

### Rol

Valores:

- ADMINISTRADOR
- USUARIO

Se almacena en `usuarios.rol`.

### EstadoCarrito

Valores:

- ACTIVO
- ELIMINADO

Se almacena en `carritos.estado`.

### FormaDePago

Valores:

- EFECTIVO
- TARJETA
- TRANSFERENCIA

Se almacena en `pedidos.forma_de_pago`.

No se utiliza un ENUM propio de PostgreSQL para estos campos; se utiliza `VARCHAR` con `CHECK` para mantener una estructura simple y controlada desde la base de datos.
