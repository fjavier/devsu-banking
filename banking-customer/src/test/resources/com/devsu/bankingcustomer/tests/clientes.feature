Feature: Pruebas del API de Clientes (CRUD)

Background:
    # url 'http://localhost:8081/clientes'
    * url 'http://banking-customer:8080/clientes'
    * configure charset = 'UTF-8'

Scenario: CRUD CLIENTES
  * def random = java.util.UUID.randomUUID().toString().substring(0, 4)
  * def randomsufix = java.util.UUID.randomUUID().toString().substring(0, 4)
  * def identification = random + "-" + randomsufix

  Given request
      """
      {
        "nombre": "Jose Lema",
        "genero": "M",
        "edad": 30,
        "identificacion": "#(identification)",
        "direccion": "Managua",
        "telefono": "88888888",
        "contrasena": "1234"
      }
      """
    When method post
    Then status 201
    And match $.nombre == 'Jose Lema'
    And match $.direccion == 'Managua'
    And match $.edad == 30

    * def clienteId = $.clienteId
    * print 'ClienteId ', clienteId

  #Obtiene cliente
    Given path clienteId
    When method get
    Then status 200
    And match $.clienteId == clienteId

  #Actualizar cliente
    Given path clienteId
    And request { direccion: 'León', telefono: '77777777' }
    When method put
    Then status 200
    And match $.direccion == 'León'

  # Eliminar cliente
    Given path clienteId
    When method delete
    Then status 204
