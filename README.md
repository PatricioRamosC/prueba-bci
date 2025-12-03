# Prueba BCI - API de Usuarios

API REST para gestión de usuarios con autenticación JWT desarrollada en Spring Boot.

## Tecnologías

- **Java 8**
- **Spring Boot 2.7.18**
- **Spring Security**
- **Spring Data JPA**
- **H2 Database** (en memoria)
- **JWT** (JSON Web Tokens)
- **Maven**
- **Lombok**

## Configuración

### Requisitos
- JDK 8
- Maven 3.6+

### Base de Datos
La aplicación usa H2 en memoria. Configuración en `application.properties`:
```properties
spring.datasource.url=jdbc:h2:mem:usuariosdb
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### Validación de Contraseña
Expresión regular configurada en `application.properties`:
```properties
app.password.regex=^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$
```
**Requisitos**: Mínimo 8 caracteres, al menos una mayúscula, una minúscula, un número y un símbolo especial.

## Instalación y Ejecución

```bash
# Clonar el repositorio
git clone <repository-url>
cd prueba-bci

# Compilar y ejecutar
mvn clean install
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## Endpoints

### 1. Crear Usuario
```http
POST /usuarios
Content-Type: application/json

{
  "nombre": "Juan Pérez",
  "correo": "juan@example.com",
  "contraseña": "Password123!",
  "telefonos": [
    {
      "numero": "123456789",
      "codigoCiudad": "1",
      "codigoPais": "57"
    }
  ]
}
```

**Respuesta exitosa (201):**
```json
{
  "id": "uuid-generado",
  "creado": "2025-01-01T10:00:00",
  "modificado": "2025-01-01T10:00:00",
  "ultimoLogin": "2025-01-01T10:00:00",
  "token": "jwt-token-generado",
  "activo": true
}
```

### 2. Listar Usuarios
```http
GET /usuarios
```

### 3. Actualizar Usuario
```http
PUT /usuarios
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "nombre": "Juan Carlos Pérez",
  "correo": "juan@example.com"
  "contraseña": "Password123!",
  "telefonos": [
    {
      "numero": "123456789",
      "codigoCiudad": "1",
      "codigoPais": "57"
    }
  ]
}
```

### 4. Eliminar Usuario
```http
DELETE /usuarios
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "correo": "juan@example.com"
}
```

## Validaciones

### Correo Electrónico
- Formato válido (regex estándar)
- No puede estar duplicado

### Contraseña
- Mínimo 8 caracteres
- Al menos una letra mayúscula
- Al menos una letra minúscula
- Al menos un número
- Al menos un símbolo especial (@$!%*?&)

### Validaciones de Negocio
- Validación de formato de correo con regex
- Validación de contraseña con expresión regular
- Verificación de correo duplicado

## Seguridad

### JWT (JSON Web Tokens)
- **Algoritmo**: HMAC256
- **Expiración**: 24 horas
- **Header**: `Authorization: Bearer <token>`

### Encriptación
- Contraseñas encriptadas con **BCrypt**
- No se almacenan contraseñas en texto plano

## Códigos de Error

| Código | Descripción                                                |
|--------|------------------------------------------------------------|
| 204    | Usuario inexistente                                        |
| 400    | Datos inválidos (formato correo, contraseña, validaciones) |
| 401    | Token inválido o expirado                                  |
| 409    | Correo ya registrado                                       |
| 500    | Error interno del servidor                                 |

## Tabla

| Izquierda    | Centro        | Derecha      |
|--------------|---------------|--------------|
| texto        | texto         | texto        |
| más texto    | más texto     | más texto    |

## Ejemplos de Uso

### Crear Usuario y Obtener Token
```bash
curl -X POST http://localhost:8080/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Test User",
    "correo": "test@example.com",
    "contraseña": "Password123!",
    "telefonos": [{"numero": "123456789", "codigoCiudad": "1", "codigoPais": "57"}]
  }'
```

### Actualizar Usuario con Token
```bash
curl -X PUT http://localhost:8080/usuarios \
  -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Updated Name",
    "correo": "test@example.com"
  }'
```

### Eliminar Usuario con Token
```bash
curl -X DELETE http://localhost:8080/usuarios \
  -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "correo": "test@example.com"
  }'
```

## Consola H2

Para ver la base de datos en desarrollo:
1. Ir a: `http://localhost:8080/h2-console`
2. JDBC URL: `jdbc:h2:mem:usuariosdb`
3. Usuario: `sa`
4. Contraseña: (vacía)

## Estructura del Proyecto

```
src/main/java/cl/nttdata/prueba_bci/
├── controller/          # Controladores REST
├── dto/                # Data Transfer Objects
├── entity/             # Entidades JPA
├── exception/          # Excepciones personalizadas
├── repository/         # Repositorios JPA
├── service/            # Lógica de negocio (JWT, Validación, Usuario)
├── utils/              # Utilidades y handlers
└── config/             # Configuraciones de seguridad
```

## Servicios Implementados

- **UsuarioService**: Lógica de negocio para CRUD de usuarios
- **JwtService**: Generación y validación de tokens JWT
- **ValidacionService**: Validaciones de correo y contraseña

## Autenticación

Todos los endpoints excepto POST (crear usuario) requieren token JWT en el header:
```
Authorization: Bearer <jwt-token>
```

## Autor

**Patricio Ramos** - NTTDATA