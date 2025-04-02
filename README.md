# IsFootball

Este proyecto incluye una parte frontend desarrollada con **Vue.js 3** y una parte backend gestionada con **Spring Boot**. A continuación, se proporcionan las instrucciones para configurar y ejecutar ambas partes del proyecto.

## Configuración del cliente (Frontend)

### Requisitos previos
Antes de comenzar, asegúrate de tener **Node.js** y **npm** instalados en tu máquina. Si no los tienes, puedes descargarlos desde [su sitio oficial](https://nodejs.org/).

### Instalación de dependencias

1. **Clona el repositorio** o **descarga los archivos del proyecto**.
2. **Navega hasta la carpeta del cliente del proyecto:**

```bash
 cd client

```
**Instala las dependencias de Vue.js**:
```bash
npm install
```

Inicia el servidor de desarrollo:
```bash
npm run dev
```

Esto hará que tu aplicación Vue.js se ejecute en el puerto configurado (por defecto, el puerto 3000).

## Configuración del servidor (Backend)
### Requisitos previos

Para ejecutar el servidor backend en Spring Boot, necesitas tener Java 8 o superior (se recomienda Java 21) y Maven instalados. Si no tienes Java o Maven instalados, puedes obtenerlos desde sus respectivos sitios oficiales:

- **Java**: Sitio oficial de Oracle o OpenJDK.

- **Maven**: Sitio oficial de Apache Maven.

**Iniciar el servidor con Spring Boot**

Compila y ejecuta el backend con Maven:

Navega a la carpeta del servidor y ejecuta el siguiente comando para compilar el proyecto y crear el archivo .jar del backend:
```bash
mvn clean install
```

Luego, para iniciar la aplicación:
```bash
mvn spring-boot:run
```

Esto iniciará el servidor en http://localhost:8080 por defecto.

## Configuración completa

Arranca primero el backend (Spring Boot), asegurándote de que está corriendo en el puerto 8080 (por defecto).

Luego inicia el frontend con el comando:
```bash
npm run dev
```


Accede al frontend en http://localhost:3000 y al backend en http://localhost:8080.

## Personalizar configuración

Si necesitas modificar el comportamiento del proyecto (por ejemplo, cambiar el puerto en el que corre el frontend o el backend), consulta la documentación de Spring Boot y Vue CLI para más detalles.