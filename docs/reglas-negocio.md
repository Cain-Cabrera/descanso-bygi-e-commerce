# Reglas de negocio — Descanso by Gi

## 1. Reglas de negocio

### RN01 — Registro de usuarios
Cada usuario debera registrarse con un correo electronico unico dentro del sistema.

### RN02 — Roles de usuario
Cada usuario debera tener asignado un rol que determine los permisos disponibles dentro del sistema.

### RN03 — Gestion de productos
Solo los usuarios con rol administrador podran registrar, modificar y eliminar productos.

### RN04 — Categoria de producto
Cada producto debera pertenecer a una categoria.

### RN05 — Marca de producto
Cada producto debera pertenecer a una marca.

### RN06 — Imagenes de productos
Un producto podra tener una o varias imagenes asociadas.

### RN07 — Precio de producto
Cada producto debera tener un precio mayor o igual a cero.

### RN08 — Stock de productos
El stock de un producto no podra ser menor que cero.

### RN09 — Carrito de usuario
Cada usuario podra tener un unico carrito activo.

### RN10 — Productos del carrito
Un carrito podra contener uno o varios productos.

### RN11 — Cantidad de productos
La cantidad de un producto dentro del carrito debera ser mayor que cero.

### RN12 — Pedido
Cada pedido debera estar asociado a un usuario.

### RN13 — Detalle de pedido
Cada pedido debera contener uno o varios detalles de pedido.

### RN14 — Producto en pedido
Cada detalle de pedido debera estar asociado a un producto.

### RN15 — Precio del producto en el pedido
El detalle de pedido debera conservar el precio del producto al momento de realizar el pedido.

### RN16 — Forma de pago
Cada pedido debera tener una forma de pago definida entre las opciones disponibles.

### RN17 — Estado del pedido
Cada pedido debera tener un estado que permita conocer su situacion dentro del proceso de compra.

### RN18 — Actualizacion del estado del pedido
Solo los usuarios con rol administrador podran actualizar el estado de un pedido.

### RN19 — Gestion de categorias
Solo los usuarios con rol administrador podran registrar, modificar y eliminar categorias.

### RN20 — Gestion de marcas
Solo los usuarios con rol administrador podran registrar, modificar y eliminar marcas.

### RN21 — Gestion de stock
Solo los usuarios con rol administrador podran modificar el stock de los productos.

### RN22 — Eliminacion de registros
Los registros eliminados deberan conservarse mediante el mecanismo de eliminacion logica definido por el sistema.
