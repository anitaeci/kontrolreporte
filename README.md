# kontrolreporte
API REST para administrar ordenes de trabajo para el matenimiento de equipos.

## Descripción
* Proyecto maven
* Los servicios rest se construyen con el framework jakarta version 9.1
* Base de datos mongo db 
* Inyección de dependencias
* Uso de streams
* Desplegado sobre payara 

## Uso
* Configurar un fichero microprofile-config.properties con la información de conexión a la bd.
* Construir el war con la instrucción mvn clean package.
* desplegar el war en payara server.

## Endpoints
### GET http://localhost:8080/kontrolreporte/kontrol/orders/

Retrona todas las ordenes de trabajo.

### GET http://localhost:8080/kontrolreporte/kontrol/orders/{id}

Retorna el detalle de una orden de trabajo

### POST http://localhost:8080/kontrolreporte/kontrol/orders/

Registra una nueva orden de trabajo

### DELETE http://localhost:8080/kontrolreporte/kontrol/orders/{id}

### PATH http://localhost:8080/kontrolreporte/kontrol/orders/

Actualiza los atributos enviados en el json

Elimina la orden de trabajo

## TODO
* Casos de prueba
* Autenticación y autorización