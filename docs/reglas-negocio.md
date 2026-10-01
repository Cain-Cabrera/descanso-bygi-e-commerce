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

Un producto podra tener cero, una o varias imagenes asociadas.

### RN07 — Precio de producto

Cada producto debera tener un precio mayor o igual a cero.

### RN08 — Stock de productos

El stock de un producto no podra ser menor que cero.

### RN09 — Carrito de usuario

Cada usuario podra tener un unico carrito activo.

Los carritos que dejen de estar activos deberan conservarse mediante eliminacion logica, permitiendo mantener el historial de carritos del usuario.

### RN10 — Productos del carrito

Un carrito podra contener cero, uno o varios productos.

### RN11 — Cantidad de productos

La cantidad de un producto dentro del carrito debera ser mayor que cero.

### RN12 — Pedido

Cada pedido debera estar asociado a un usuario.

### RN13 — Detalle de pedido

Cada pedido confirmado debera contener uno o varios detalles de pedido.

### RN14 — Producto en pedido

Cada detalle de pedido debera estar asociado a un producto.

### RN15 — Precio del producto en el pedido

El detalle de pedido debera conservar el precio del producto al momento de realizar el pedido.

### RN16 — Forma de pago

Cada pedido debera tener una forma de pago definida entre las opciones disponibles.

Las formas de pago disponibles seran:

- EFECTIVO
- TARJETA
- TRANSFERENCIA

### RN17 — Gestion de categorias

Solo los usuarios con rol administrador podran registrar, modificar y eliminar categorias.

### RN18 — Gestion de marcas

Solo los usuarios con rol administrador podran registrar, modificar y eliminar marcas.

### RN19 — Gestion de stock

Solo los usuarios con rol administrador podran modificar el stock de los productos.

### RN20 — Eliminacion de registros

Los registros eliminados deberan conservarse mediante el mecanismo de eliminacion logica definido por el sistema.

### RN21 — Verificacion de stock

Antes de confirmar un pedido, el sistema debera verificar que exista stock suficiente para cada producto solicitado.

### RN22 — Cantidad solicitada superior al stock

Si la cantidad solicitada de un producto es superior al stock disponible, el sistema no debera permitir la confirmacion del pedido hasta que la cantidad solicitada sea igual o inferior al stock disponible.

### RN23 — Descuento de stock

Al confirmar un pedido, el sistema debera descontar del stock disponible de cada producto la cantidad incluida en el pedido.

### RN24 — Reposicion de stock

Si un pedido que ya produjo un descuento de stock es cancelado, el sistema debera reintegrar al stock la cantidad de productos correspondiente.

### RN25 — Calculo del subtotal

El subtotal de cada detalle de pedido debera calcularse multiplicando la cantidad solicitada por el precio unitario del producto al momento de realizar el pedido.

### RN26 — Calculo del total del pedido

El total del pedido debera calcularse como la suma de los subtotales correspondientes a todos sus detalles de pedido.

### RN27 — Confirmacion del pedido

Un pedido solo podra confirmarse cuando todos los productos solicitados tengan stock suficiente, contenga al menos un detalle de pedido y se haya seleccionado una forma de pago valida.

### RN28 — Carrito luego de confirmar el pedido

Una vez confirmado el pedido, los productos incluidos en el carrito utilizado para realizar la compra deberan dejar de formar parte del carrito activo.

### RN29 — Cancelacion del pedido

Un pedido podra ser cancelado antes de finalizar la operacion de compra. Si la cancelacion se produce luego del descuento de stock, el sistema debera reintegrar al stock la cantidad de productos correspondiente.
