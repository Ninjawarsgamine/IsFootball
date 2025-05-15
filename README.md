
# IsFootball

**IsFootball** es una aplicación full-stack diseñada para ofrecer estadísticas detalladas y actualizadas del mundo del fútbol. La plataforma permite a los usuarios:

- Consultar estadísticas y clasificaciones de competiciones.
- Visualizar información detallada de equipos, incluyendo plantillas y partidos.
- Acceder a estadísticas individuales de jugadores.

Desarrollada con **Vue.js 3** en el frontend y **Spring Boot** en el backend, IsFootball proporciona una experiencia interactiva y dinámica para los aficionados al fútbol.

## Configuración de API-FOOTBALL

### 1. Registro en API-FOOTBALL
Para obtener datos en tiempo real de partidos y estadísticas, necesitarás una clave de API de [API-FOOTBALL](https://www.api-football.com/):

1. **Crear una cuenta**:
   - Visita [API-FOOTBALL.com](https://www.api-football.com/)
   - Haz clic en _"Sign Up"_ y completa el formulario de registro
   - Verifica tu cuenta a través del correo electrónico recibido

2. **Obtener tu API Key**:
   - Inicia sesión en tu cuenta
   - Navega a la sección **Dashboard** o **API-Key** en tu panel de control
   - Encontrarás tu clave bajo el título **Your API Key** o similar

### 2. Configurar la API Key en el proyecto

1. **En el backend**:
    - Si no lo has creado aún, creamos el archivo `application.properties` en el siguiente directorio:
    ```
        src/main/resources
    ```
    
    Aquí vamos a encontrar un archivo `application.properties.sample`, que vamos a usar para crear `application.properties`.

   - Una vez creado el archivo `application.properties`, en `api.key=tu_clave_api` cambiamos el texto por defecto por nuestra api-key.

2. **Consideraciones importantes**:
   - Nunca compartas tu API Key públicamente.
   - La clave es personal e intransferible.
   - La versión gratuita tiene límites de solicitudes (consultar plan actual en API-FOOTBALL).

## Configuración del cliente (Frontend)

### Requisitos previos
Antes de comenzar, asegúrate de tener **Node.js** y **npm** instalados en tu máquina. Si no los tienes, puedes descargarlos desde [su sitio oficial](https://nodejs.org/).

### Instalación de dependencias

1. **Clona el repositorio** o **descarga los archivos del proyecto**.
2. **Navega hasta la carpeta del cliente del proyecto:**

```bash
cd client
````

**Instala las dependencias de Vue.js**:

```bash
npm install
```

### Iniciar el servidor de desarrollo

#### Para Windows

En **Windows**, puedes iniciar el servidor de desarrollo usando el siguiente comando:

```bash
npm run dev
```

Esto ejecutará la aplicación Vue.js en el puerto configurado por defecto, que generalmente es el puerto **3000**.

#### Para Linux y macOS

En **Linux** y **macOS**, también puedes usar el siguiente comando para iniciar el servidor:

```bash
npm run dev
```

Este comando ejecutará la aplicación Vue.js de la misma manera, escuchando en el puerto **3000** por defecto.

#### Nota Adicional

Si quieres cambiar el puerto en el que corre la aplicación, puedes modificar la configuración de Vue.js en el archivo `package.json` o en los archivos de configuración de Vue CLI (`vue.config.js` si lo tienes).

### Comprobaciones adicionales

Si enfrentas problemas con permisos al ejecutar los comandos en **Linux** o **macOS**, puedes intentar ejecutar el comando con `sudo`:

```bash
sudo npm run dev
```

En **Windows**, asegúrate de estar ejecutando el terminal con privilegios de administrador si encuentras algún problema relacionado con permisos.

### Acceso a la aplicación

Una vez que el servidor de desarrollo esté en ejecución, accede al frontend en:

```
http://localhost:3000
```

---

## Configuración del servidor (Backend)

### Requisitos previos

Para ejecutar el servidor backend en Spring Boot, necesitas tener Java 8 o superior (se recomienda Java 21) instalado. Si no tienes Java o Maven instalados, puedes obtenerlos desde sus respectivos sitios oficiales:

* **Java**: Sitio oficial de Oracle o OpenJDK.

### Iniciar el servidor con Spring Boot

Compila y ejecuta el backend con Maven:

1. Navega a la carpeta del servidor y ejecuta el siguiente comando para limpiar dependencias locales, compilar e instalar el proyecto, generando el archivo .jar del backend:

```bash
 ./mvnw dependency:purge-local-repository compile clean install -DskipTests
```

2. Luego, para iniciar la aplicación:

```bash
./mvnw spring-boot:run
```

Esto iniciará el servidor en **[http://localhost:8080](http://localhost:8080)** por defecto.

## Instalación de Redis

Debido a que con una cuenta gratuita de API-FOOTBALL no podemos acceder a la temporada actual, podemos guardar datos en el caché (no van  a variar). O incluso, si tienes una cuenta premium, pero quieres ahorrar peticiones en el desarrollo, lo mejor es guardar los resultados en caché. Para ello, vamos a usar Redis. 

Para instalarlo, tendremos que hacer lo siguiente según el Sistema Operativo:

### Windows

1. **Instalar WSL** (si no está instalado):

   ```bash
   wsl --install
   ```

2. **Instalar Redis dentro de WSL**:

   ```bash
   sudo apt update && sudo apt install redis-server
   ```

3. **Verificar instalación**:

   ```bash
   redis-server
   redis-cli ping  # Debe responder PONG
   ```

### Linux

```bash
sudo apt update && sudo apt install redis-server
redis-server
redis-cli ping
```

### macOS

```bash
brew install redis
redis-server
redis-cli ping
```
---
## Iniciar la Aplicación Completa

1. **Primero el backend**:

   ```bash
   ./mvnw spring-boot:run
   ```

2. **Luego el frontend**:

   ```bash
   cd client
   npm run dev
   ```

3. Accede a:

   * **Frontend**: [http://localhost:3000](http://localhost:3000)
   * **Backend**: [http://localhost:8080](http://localhost:8080)

---
## Personalizar configuración

Si necesitas modificar el comportamiento del proyecto (por ejemplo, cambiar el puerto en el que corre el frontend o el backend), consulta la documentación de Spring Boot y Vue CLI para más detalles.

---

## Solución de Problemas Comunes

* **Error 403 Forbidden**: Verifica que la API Key en `application.properties` sea correcta y esté activa.
* **Problemas con Redis**: Asegúrate de que el servidor Redis esté corriendo (`redis-server`).
* **Datos no actualizados**: Verifica tu plan de API-FOOTBALL y los límites de solicitudes.

---

## Referencias

* Documentación oficial de [API-FOOTBALL](https://www.api-football.com/documentation-v3)

---

```
```