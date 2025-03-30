# IsFootball

Este es un proyecto que incluye una parte frontend desarrollada con **Vue.js** y una parte backend gestionada con **Struts 2**. A continuación, te proporcionamos las instrucciones para configurar y ejecutar ambas partes del proyecto.

## Configuración del cliente (Frontend)

### Requisitos previos
Antes de comenzar, asegúrate de tener **Node.js** y **npm** instalados en tu máquina. Si no los tienes, puedes descargar **Node.js** desde [su sitio oficial](https://nodejs.org/).

### Instalación de dependencias

1. **Clona el repositorio** o **descarga los archivos del proyecto**.
2. **Navega hasta la carpeta del cliente del proyecto:**

   ```bash
   cd client

## Configuración del servidor (Backend)

## Requisitos previos

Para ejecutar el servidor backend en Struts 2, necesitas tener **Apache Tomcat** y **Java** instalados. Asegúrate de tener Java 8 o superior.

### Iniciar el servidor con Tomcat

1. **Crea el archivo .war del servidor usando Maven**:

Navega a la raíz del proyecto y ejecuta el siguiente comando para crear el archivo .war del backend:
```
mvn clean install
```

Esto creará el archivo target/your-project.war, que contiene todo el código del backend.

2. Despliega el archivo .war en Tomcat:

Copia el archivo .war generado y pégalo en la carpeta webapps de tu instalación de Tomcat.

3. Inicia Tomcat:

Puedes iniciar el servidor de Tomcat desde la línea de comandos:
```
    cd /ruta/a/tomcat/bin
    ./startup.sh  # En Linux/macOS
    startup.bat   # En Windows
```

Accede al backend:

El servidor debería estar disponible en http://localhost:8080 (o el puerto que hayas configurado en Tomcat).

### Configuración completa

Arranca primero el backend (Tomcat), asegurándote de que está corriendo en el puerto que has configurado (por defecto en 8080).

Después inicia el frontend con el comando npm run dev. Esto hará que tu aplicación Vue.js se ejecute en el puerto que hayas configurado.

Ahora podrás acceder al frontend en http://localhost:3000 y el backend en http://localhost:8080.

### Personalizar configuración

Si necesitas personalizar el comportamiento del proyecto (por ejemplo, cambiar el puerto en el que corre el frontend), consulta la referencia de configuración de Vue CLI para más detalles.