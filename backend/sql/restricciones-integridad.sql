-- Agrega las restricciones CHECK del diccionario a bases existentes.
-- El script puede ejecutarse más de una vez sin duplicar restricciones.
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint
        WHERE conname = 'ck_productos_precio_stock'
          AND conrelid = 'public.productos'::regclass
    ) THEN
        ALTER TABLE public.productos
            ADD CONSTRAINT ck_productos_precio_stock
            CHECK (precio >= 0 AND stock >= 0);
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint
        WHERE conname = 'ck_items_carrito_cantidad_subtotal'
          AND conrelid = 'public.items_carrito'::regclass
    ) THEN
        ALTER TABLE public.items_carrito
            ADD CONSTRAINT ck_items_carrito_cantidad_subtotal
            CHECK (cantidad > 0 AND subtotal >= 0);
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint
        WHERE conname = 'ck_detalles_pedido_importes_cantidad'
          AND conrelid = 'public.detalles_pedido'::regclass
    ) THEN
        ALTER TABLE public.detalles_pedido
            ADD CONSTRAINT ck_detalles_pedido_importes_cantidad
            CHECK (cantidad > 0 AND precio_unitario >= 0 AND subtotal >= 0);
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint
        WHERE conname = 'ck_pedidos_total'
          AND conrelid = 'public.pedidos'::regclass
    ) THEN
        ALTER TABLE public.pedidos
            ADD CONSTRAINT ck_pedidos_total
            CHECK (total >= 0);
    END IF;
END $$;
