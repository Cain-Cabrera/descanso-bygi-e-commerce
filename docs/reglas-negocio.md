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

Un pedido podra ser cancelado de acuerdo con las condiciones definidas por el sistema. Si la cancelacion se produce luego del descuento de stock, debera aplicarse la reposicion correspondiente definida en la RN24.
