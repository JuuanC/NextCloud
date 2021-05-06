## Construido con

* [Maven] (https://maven.apache.org/) - Gestion de dependencias
* [JDK] (https://www.oracle.com/java/technologies/javase-jdk11-downloads.html): plataforma Java ™, kit de desarrollo de edicion estandar
* [Quarkus] (https://quarkus.io/) - Marco para facilitar el arranque y el desarrollo de nuevas aplicaciones
* [PostgreSQL] (https://www.postgresql.org/) - Sistema de gestion de bases de datos relacionales de codigo abierto
* [git] (https://git-scm.com/) - Sistema de control de versiones distribuido gratuito y de codigo abierto
* [OpenAPI] (https://swagger.io/specification/): marco de software de codigo abierto respaldado por un gran ecosistema de herramientas que ayuda a los desarrolladores a diseñar, construir, documentar y consumir servicios web RESTful.
* [OkHttoClient] (https://square.github.io/okhttp/4.x/okhttp/okhttp3/-ok-http-client/): Librería para realizar peticiones htpp y webdav.

## Herramientas externas utilizadas

* [Postman] (https://www.getpostman.com/) - Entorno de desarrollo de API (documentacion de prueba)

## Que hacer
Poder obtener los datos para las siguientes graficas:
* [x] Obtener un archivo específico.
* [x] Subir un archivo específico.
* [x] Listar los archivos de una carpeta.

# NextCloud Project

Este proyecto utiliza Quarkus, el Supersonic Subatomic Java Framework.

Si desea obtener mas informacion sobre Quarkus, visite su sitio web: https://quarkus.io/.

## Ejecutando la aplicacion en modo dev

Puede ejecutar su aplicacion en modo dev que habilita la codificacion en vivo usando:
``mvn compile quarkus:dev``

## Empaquetar y ejecutar la aplicacion

La aplicacion se puede empaquetar usando:
``mvn package -Dquarkus-profile=dev``

Produce el archivo `nextcloud-1.0.0-SNAPSHOT-runner` en el directorio `/ target`.
Tenga en cuenta que las dependencias se copian en el directorio `target / lib`.

La aplicacion ahora se puede ejecutar usando `java -jar /target/nextcloud-1.0.0-SNAPSHOT-runner`.

##Cuando el servicio este corriendo

*NOTA: Estos endpoints no funcionaran debido a que la conexion a la base de datos solo funcionan con la maquina virtual.

openAPI definition
* Por favor ir a http://localhost:8087/services donde encontrara lo siguiente:
```
POST /v1/getFile - para obtene un archivo.
```
```
PUT /v1/upload - para subir un archivo.
```
```
POST /v2/listFiles - para obtener la lista de archivos de determinada carpeta.
```
### Pruebas con postman

### URLs

|  URL |  Metodo |
|----------|--------------|
|`http://localhost:8080/v1/getFile`   | POST |
|`http://localhost:8080/v1/upload`    | PUT  |
|`http://localhost:8080/v2/listFiles` | POST |


### Estructura del paquete

## Archivos y directorios

```texto.
├── src
│ └── main
│   └── docker
│       ├── Dcokerfile.fast-jar
│       ├── Dockerfile.jvm
│       └── Dockerfile.native
│
├── src
│ └── main
│   └── java
│       ├── dto
│       ├── exception
│       ├── model
│       ├── repository
│       ├── resource
│       ├── service
│       └── util
│           
├── src
│ └── main
│   └── resources
│       ├── application.yml
│       └── banner.txt
│
├── src
│ └── test
│   └── java
│ 
├── nextcloud.iml
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## Paquetes

* `dto` - para convertir objetos
* `exception` - para manejar las excepciones
* `model` - para contener nuestras entidades
* `repository` - para comunicarse con la base de datos
* `resource` - para los endpoint que consumira el cliente
* `service` - para mantener nuestra logica del negocio
* `util` - para metodos de utilidad, como validaciones
* `test ` - contiene pruebas unitarias
* `pom.xml` - contiene todas las dependencias del proyecto
