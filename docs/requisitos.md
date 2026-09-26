# Requisitos del sistema — Descanso by Gi

## 1. Requisitos funcionales

### RF01 — Registro de usuario
El sistema debera permitir que un usuario se registre proporcionando sus datos personales y credenciales de acceso

### RF02 — Inicio de sesion
El sistema debera permitir que los usuarios registrados inicien sesion mediante sus credenciales

### RF03 — Gestion de cuenta
El sistema debera permitir al usuario consultar y modificar sus datos personales

### RF04 — Consulta de productos
El sistema debera permitir a los usuarios consultar el catalogo de productos disponibles

### RF05 — Consulta por categorias
El sistema debera permitir consultar los productos agrupados por categorias

### RF06 — Busqueda de productos
El sistema debera permitir buscar productos mediante criterios de busqueda

### RF07 — Filtrado de productos
El sistema debera permitir aplicar filtros sobre el catalogo de productos

### RF08 — Consulta del detalle de producto
El sistema debera permitir consultar la informacion detallada de un producto, incluyendo sus imagenes

### RF09 — Gestion de carrito
El sistema debera permitir al usuario consultar su carrito de compras

### RF10 — Agregar productos al carrito
El sistema debera permitir agregar productos al carrito e indicar la cantidad deseada.

### RF11 — Modificar cantidades del carrito
El sistema debera permitir modificar la cantidad de productos agregados al carrito.

### RF12 — Eliminar productos del carrito
El sistema debera permitir eliminar productos del carrito.

### RF13 — Realizar pedido
El sistema debera permitir al usuario confirmar los productos de su carrito y generar un pedido.

### RF14 — Consulta de pedidos
El sistema debera permitir al usuario consultar el historial de sus pedidos.

### RF15 — Consulta del estado del pedido
El sistema debera permitir al usuario consultar el estado de sus pedidos.

### RF16 — Gestion de productos
El sistema debera permitir al administrador registrar, modificar, consultar y eliminar productos.

### RF17 — Gestion de categorias
El sistema debera permitir al administrador registrar, modificar, consultar y eliminar categorias.

### RF18 — Gestion de marcas
El sistema debera permitir al administrador registrar, modificar, consultar y eliminar marcas.

### RF19 — Gestion de imagenes
El sistema debera permitir asociar imagenes a los productos y gestionar dichas imagenes.

### RF20 — Gestion de stock
El sistema debera permitir al administrador consultar y actualizar el stock de los productos.

### RF21 — Gestion de pedidos
El sistema debera permitir al administrador consultar los pedidos y actualizar su estado.

### RF22 — Gestion de usuarios
El sistema debera permitir al administrador consultar y gestionar los usuarios registrados.

## 2. Requisitos no funcionales

### RNF01 — Arquitectura
El sistema debera utilizar una arquitectura separada en frontend y backend.

### RNF02 — Frontend
El frontend debera desarrollarse utilizando React y TypeScript.

### RNF03 — Backend
El backend debera desarrollarse utilizando Java y Spring Boot.

### RNF04 — Base de datos
El sistema debera utilizar MySQL para la persistencia de los datos.

### RNF05 — Persistencia
La informacion del sistema debera almacenarse de forma persistente en la base de datos.

### RNF06 — Seguridad
El sistema debera controlar el acceso a las funcionalidades segun el rol del usuario.

### RNF07 — Interfaz adaptable
La interfaz debera adaptarse a diferentes tamanos de pantalla, incluyendo dispositivos moviles y de escritorio.

### RNF08 — Mantenibilidad
El codigo debera organizarse en modulos y capas que faciliten su mantenimiento y evolucion.

### RNF09 — Integridad de datos
El sistema debera mantener la integridad y consistencia de la informacion almacenada.

### RNF10 — Disponibilidad
El sistema debera permitir el acceso a las funcionalidades disponibles durante el funcionamiento normal de la aplicacion.
