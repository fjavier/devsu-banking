# devsu-banking
## Prueba Técnica  / Practica de ingreso a DEVSU

Este proyecto implementa dos microservicios independientes, siguiendo arquitectura hexagonal y comunicación asincrónica mediante RabbitMQ.
Permite gestionar clientes, cuentas bancarias, movimientos y reportes consolidados.

Se crea la comunicación entre los microservicios mediante mensajes en cola, al crearse
un cliente por primera vez se genera una cuenta bancaria por defecto, esto para demostrar
el comportamiento de la comunicación asincrona entre microservicios.

## Stack de Técnologias:
1. Java 
2. Spring Boot
3. RabbitMQ
4. Spring Data
5. Mockito
6. Junit
7. Karate
8. PostgresQL
9. Docker

## Este proyecto muestra:
    1. Arquitectura Hexagonal    
    2. Microservicios independientes 
    3. Comunicación asíncrona con RabbitMQ   
    4. Persistencia con PostgreSQL
    5. Reportes consolidados complejos
    6. Docker Compose para fácil despliegue
    7. Pruebas automáticas con Karate y JUnit

## Funcionalidades
    1. Recurso Clientes (CRUD)
    2. Recurso Cuentas Bancarias (CRU)
    3. Recurso Movimientos (POST)
    4. Reporte Consolidado (GET)

## Base de datos:
Se utiliza una base de datos PostgreSQL, el script se encuentra en la raiz del proyecto.

## Despliegue con Docker compose:

Ubicarse en la raiz del proyecto y ejecutar:

```bash
docker compose up --build
```

Este comando ejecutara los contenedores postgresql, rabbitmq, banking-account y banking-customer

<p>El contenedor banking-account contiene todo lo relacionado a cuentas, movimientos y reportes y se expone en el puerto 8082</p>
<p>El contenedor banking-customer contiene todo lo relacionado a clientes y se expone en el puerto 8081</p>
<p>El contenedor karate-test contiene las pruebas automatizadas con Karate y JUnit, este genera un reporte</p>
<p>En la raiz del proyecto llamado karate-reports.</p>

## Pruebas en postman>
### Clientes
1. Creación de cliente:

```curl
   curl --location 'http://localhost:8081/clientes' \
   --header 'Content-Type: application/json' \
   --header 'Accept: */*' \
   --data '{
   "nombre": "Jaylin Funk",
   "genero": "M",
   "edad": 26,
   "identificacion": "ddd95a8e-8432-4b64-a2e2-29ee76595f06",
   "direccion": "Lithuania 0293 Jed Harbor",
   "telefono": "802-913-4875",
   "contrasena": "QepUMz2uPw90KGs"
   }'
```

2. Obtener cliente:
```curl
   curl --location 'http://localhost:8081/clientes/1' \
   --header 'Accept: */*'
```

3. Actualiza Cliente:
```curl
curl --location --request PUT 'http://localhost:8081/clientes/1' \
--header 'Content-Type: application/json' \
--header 'Accept: */*' \
--data '{
    "direccion": "León",
    "telefono": "77777777"
}'
```
4. Elimina Cliente:
 ```curl
   curl --location --request DELETE 'http://localhost:8081/clientes/1'
```

### Cuentas:

1. Crear cuenta:
```curl
   curl --location 'http://localhost:8082/cuentas' \
   --header 'Content-Type: application/json' \
   --header 'Accept: */*' \
   --data '{
   "numeroCuenta": "73084277",
   "tipoCuenta": "Auto Loan Account",
   "saldoInicial": 50,

"clienteId": 2
}'
```

2. Actualizar cuenta:
```curl
curl --location --request PUT 'http://localhost:8082/cuentas/73084277' \
--header 'Content-Type: application/json' \
--header 'Accept: */*' \
--data '{
  "tipoCuenta": "DIGITAL",
  "estado": true,
}'
```

3. Obtener cuenta:
```curl
curl --location 'http://localhost:8082/cuentas/73084277' \
--header 'Accept: */*'
```

### Movimientos:
1. Crear movimiento:
```curl
curl --location 'http://localhost:8082/movimientos' \
--header 'Content-Type: application/json' \
--header 'Accept: */*' \
--data '{
  "numeroCuenta": "73084277",
  "tipoMovimiento": "PRUEBA",
  "valor": 80
}'

```

### Reportes:

```curl
curl --location 'http://localhost:8082/reportes?clienteId=1&fechaDesde=2025-11-20&fechaHasta=2025-11-20' \
--header 'Accept: */*'
```

### Estructura de respuesta para reportes:
```json
{
    "clienteId": 2,
    "persona": {
        "nombre": "Isai Johns",
        "genero": "M",
        "telefono": "442-705-4845",
        "edad": 26
    },
    "cuentas": [
        {
            "numeroCuenta": "17170065",
            "saldoDisponible": 130.00,
            "estado": true,
            "tipoCuenta": "Savings Account",
            "saldoInicial": 50.00,
            "movimientos": [
                {
                    "fecha": "2025-11-20T15:07:20.447611",
                    "valor": 50.00,
                    "tipoMovimiento": "CREACION",
                    "saldo": 50.00
                },
                {
                    "fecha": "2025-11-20T15:07:58.212622",
                    "valor": 80.00,
                    "tipoMovimiento": "PRUEBA",
                    "saldo": 130.00
                }
            ]
        },
        {
            "numeroCuenta": "15551609",
            "saldoDisponible": 180.00,
            "estado": true,
            "tipoCuenta": "Auto Loan Account",
            "saldoInicial": 50.00,
            "movimientos": [
                {
                    "fecha": "2025-11-20T12:40:06.074479",
                    "valor": 50.00,
                    "tipoMovimiento": "CREACION",
                    "saldo": 50.00
                },
                {
                    "fecha": "2025-11-20T12:42:28.362619",
                    "valor": 50.00,
                    "tipoMovimiento": "CREDITO",
                    "saldo": 100.00
                },
                {
                    "fecha": "2025-11-20T15:05:57.849460",
                    "valor": 80.00,
                    "tipoMovimiento": "PRUEBA",
                    "saldo": 180.00
                }
            ]
        }
    ]
}
```

### Consideraciones:

Para poder ejecutar las pruebas se necesita tener instalado docker y docker compose.

Para la construcción de este proyecto se consideraron los siguientes puntos:
### F1: Generación de CRUD (Crear, leer, actualizar y eliminar) en Entidades: Cliente. Generación de CRU (Crear, leer y actualizar) en Entidades: Cuenta y Movimiento.

Los nombres de los endpoints a generar son:

· /cuentas
· /clientes
. /movimientos

### F2: Registro de movimientos: al registrar un movimiento en la cuenta se debe tener en cuenta lo siguiente:

· Para un movimiento se pueden tener valores positivos o negativos.
· Al realizar un movimiento se debe actualizar el saldo disponible.
· Se debe llevar el registro de las transacciones realizadas.

### F3: Registro de movimientos: Al realizar un movimiento el cual no cuente con saldo, debe alertar mediante el siguiente mensaje "Saldo no disponible"

· Defina, según su expertise, la mejor manera de capturar y mostrar el error.

### F4: Reportes: Generar un reporte de "Estado de cuenta" especificando un rango de
fechas y cliente.

· Este reporte debe contener:
1. Cuentas asociadas con sus respectivos saldos  o Detalle de movimientos de las cuentas
2. El endpoint que se debe utilizar para esto debe ser el siguiente:

    2.1 (/reportes?fecha=rango fechas & cliente)

    2.2 El servicio del reporte debe retornar la informacion en formato JSON

    2.3 Defina, según su expertise, la mejor manera de solicitar y retornar esta
información.

### F5: Pruebas unitarias: Implementar 1 prueba unitaria para la entidad de dominio Cliente.

### F6: Pruebas de Integración: Implementar 1 prueba de integración.

### F7: Despliegue de la solución en contenedores.
