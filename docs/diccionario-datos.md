### Tabla: pedidos

| Campo | Tipo | Restricciones | Descripcion |
|---|---|---|---|
| id | BIGINT | PK | Identificador unico del pedido |
| fecha_alta | TIMESTAMP | NOT NULL | Fecha y hora de creacion |
| eliminado | BOOLEAN | NOT NULL | Indica si el registro fue eliminado logicamente |
| estado | VARCHAR(20) | NOT NULL | Estado actual del pedido |
| total | NUMERIC(10,2) | NOT NULL | Importe total del pedido |
| forma_de_pago | VARCHAR(20) | NOT NULL | Forma de pago seleccionada |
| usuario_id | BIGINT | FK, NOT NULL | Usuario que realizo el pedido |

Valores permitidos para `estado`:

- PENDIENTE
- CONFIRMADO
- CANCELADO

Valores permitidos para `forma_de_pago`:

- EFECTIVO
- TARJETA
- TRANSFERENCIA

Relaciones:

- `usuario_id` referencia a `usuarios.id`.
