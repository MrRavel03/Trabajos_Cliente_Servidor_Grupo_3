# Sistema de Gestión de Biblioteca (Cliente-Servidor)

Autores:
Gabriel Osorio Valerin
Dylan Madrid Velez
Fabian Lopez Carballo


## Configuración de la Base de Datos 

2.  **Configurar Credenciales:**
    * El proyecto viene configurado por defecto con:
        * **Usuario:** `root`
        * **Contraseña:** `gabriel`

    * Ve al archivo: `BibliotecaServidor/src/main/java/biblioteca/config/ConexionDB.java`
    * Modifica las constantes `USER` y `PASS`:
        ```java
        private static final String USER = "tu_usuario";
        private static final String PASS = "tu_contraseña";
        ```

## Usuarios de Prueba

Puedes utilizar las siguientes credenciales para probar los diferentes roles del sistema:

**Rol: ADMINISTRADOR / BIBLIOTECARIO**
* **Email:** `admin@biblio.com`
* **Contraseña:** `admin`

**Rol: ESTUDIANTE**
* **Email:** `ana@est.com`
* **Contraseña:** `1234`