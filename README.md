# Demo TVMaze + MongoDB

## Descripción

Proyecto de demostración desarrollado en Java que consume la API pública de **TVMaze**, 
procesa las respuestas JSON utilizando **Gson** y almacena informaci贸n de rese帽as en **MongoDB Atlas**.

El flujo principal de la aplicación es:

1. Consultar TVMaze mediante el endpoint `search/shows?q=girls`.
2. Convertir la respuesta JSON a objetos Java mediante Gson.
3. Obtener el identificador del programa encontrado.
4. Consultar el detalle del programa mediante `shows/{id}`.
5. Guardar la respuesta detallada en `repuestaDetalle.json`.
6. Conectarse a MongoDB Atlas.
7. Verificar la conexión mediante `ping`.
8. Insertar una reseña en la colección `reviews`.

---

## Autor

**Alfredo Campos Hernández**  
Arquitecto de Software, Lider Técnico
- Teléfono / WhatsApp: +52 55 9193 2843
- Correo: alfredo.campos.hernandez@gmail.com

---

## Tecnologías

- Java
- Maven
- Gson 2.14.0
- MongoDB
- MongoDB Atlas
- MongoDB Java Sync Driver 5.6.3
- JUnit 4.13.2
- Git / GitHub
- Visual Studio Code
- Postman
- API REST de TVMaze
- JSON

---

# Estructura del proyecto

demo/
* Postman/
*   New Collection.postman_collection.json
* src/
*   main/
*  *   DBConnect.java
*  *       java/
*  *       com/
*  *           example/
*  *               App.java
*  *               TVSearch.java
* pom.xml
* repuestaDetalle.json
* README.md


---

# Descripción de las carpetas

---

## `src/main/`

Contiene el código fuente principal del proyecto.

### `src/main/java/`

Contiene las clases Java organizadas mediante paquetes.

La ruta principal del código es:

src/main/java/com/example/

---


## `Postman/`

Contiene la colección de Postman utilizada para probar los endpoints de TVMaze.

Archivo:

Postman/New Collection.postman_collection.json

La colección incluye actualmente:

### Buscar programas

```http
GET https://api.tvmaze.com/search/shows?q=girls
```

### Consultar detalle de un programa

```http
GET https://api.tvmaze.com/shows/139
```

La colección utiliza el formato Postman Collection v2.1.

---

# Archivos principales

## `pom.xml`

Es el archivo principal de configuración de Maven.

Define:

- Identidad del proyecto.
- Versión del proyecto.
- Codificaci贸n UTF-8.
- Versión de compilación configurada.
- Dependencias.
- Plugins de Maven.

### Dependencias principales

#### Gson

<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.14.0</version>
</dependency>

Se utiliza para convertir JSON en objetos Java.

#### MongoDB Java Driver

<dependency>
    <groupId>org.mongodb</groupId>
    <artifactId>mongodb-driver-sync</artifactId>
    <version>5.6.3</version>
</dependency>

Permite conectarse y trabajar con MongoDB desde Java.

#### JUnit

<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.2</version>
    <scope>test</scope>
</dependency>

Se utiliza para pruebas unitarias.

---

# Código Java

## `src/main/java/com/example/App.java`

Es la clase principal de la aplicación.

### Responsabilidades principales

- Crear y ejecutar peticiones HTTP.
- Consumir la API de TVMaze.
- Procesar respuestas JSON.
- Convertir JSON a objetos Java mediante Gson.
- Guardar una respuesta JSON en archivo.
- Conectarse a MongoDB Atlas.
- Ejecutar un `ping` para comprobar la conexión.
- Insertar una reseña en MongoDB.

### M茅todos principales

GET(String endpoint)

Realiza una petición HTTP GET.

---

POST(String endpoint, String json)

Realiza una petición HTTP POST enviando JSON.

---

abrirConexion(String endpoint, String metodo)

Configura la conexión HTTP utilizada por las peticiones GET y POST.

---

leerRespuesta(HttpURLConnection conexion)

Lee la respuesta de la API y la devuelve como `JSon - String`.

---

saveReponse(String respuesta, String nombreArchivo)

Guarda una respuesta en un archivo.

---

# `TVSearch.java`

Contiene los modelos Java utilizados para representar la información devuelta por TVMaze.

La clase principal contiene:

public Double score;
public Show show;

La clase `Show` contiene información como:

- ID
- Nombre
- URL
- Tipo
- Idioma
- G茅neros
- Estado
- Duraci贸n
- Fecha de estreno
- Sitio oficial
- Horario
- Rating
- Network
- WebChannel
- Informaci贸n externa
- Imágenes
- Resumen
- Enlaces

Tambión contiene clases internas para representar estructuras anidadas:

Show
* Schedule
* Rating
* Network
* WebChannel
* Country
* Externals
* Image
* Links
* Link

Esto permite que Gson convierta directamente la respuesta JSON de TVMaze a objetos Java.

---

# MongoDB

La aplicación utiliza MongoDB Atlas.

La base de datos utilizada actualmente es:

ratings

Y la colección utilizada es:

reviews

La estructura del documento insertado es conceptualmente:

json
{
    "show_id": "139",
    "comment": "Girls es un gran show!",
    "rating": 5
}

MongoDB genera autom谩ticamente el campo `_id` para el documento.


# API utilizada

El proyecto utiliza:

https://api.tvmaze.com/

### Endpoint de búsqueda

GET /search/shows?q=girls

### Endpoint de detalle

GET /shows/{id}

Ejemplo:

GET /shows/139

---

# `repuestaDetalle.json`

Este archivo contiene una respuesta de ejemplo obtenida desde TVMaze.

Actualmente corresponde al programa:

	Girls

con:

	ID: 139

El archivo permite conservar localmente una respuesta JSON real para revisar la estructura de datos.

---


# Requisitos

Para trabajar con el proyecto se requiere:

- JDK compatible con la versión configurada en `pom.xml`.
- Maven.
- Acceso a Internet para consumir TVMaze.
- Cuenta/base de datos MongoDB Atlas con permisos adecuados.
- Visual Studio Code, IntelliJ IDEA, Eclipse u otro IDE Java.
- Postman, opcionalmente, para probar los endpoints.

> El `pom.xml` del proyecto actualmente tiene configurado `maven.compiler.source` y `maven.compiler.target` en **25**. 
Aunque el entorno de desarrollo puede utilizar un JDK más reciente, 
esta configuración debe tomarse en cuenta al compilar el proyecto.

---

