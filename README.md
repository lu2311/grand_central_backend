# 🍽️ Grand Central – Backend  
Backend del sistema de gestión del restaurante Grand Central.  
Este servicio expone la API para autenticación, gestión de platos, votaciones del menú diario, subida de imágenes y más.

---

## 🚀 Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 3.3.5**
- **Spring Security + JWT**
- **Spring Data JPA**
- **SQLite**
- **Hibernate**
- **Cloudinary** (subida de imágenes)
- **Springdoc OpenAPI** (Swagger UI)
- **Maven**

---

## 📦 Requisitos Previos

Asegúrate de tener instalado:

- **Java 21**  
- **Maven 3.8+**
- (Opcional) Postman o navegador para probar endpoints

---

## ⚙️ Configuración del Proyecto

### 1️⃣ Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/restaurant-backend.git
cd restaurant-backend

### 2️⃣ Variables de entorno

El proyecto utiliza Cloudinary y JWT.
Crea un archivo .env (o agrégalo en las variables del sistema):

CLOUDINARY_URL=cloudinary://<api_key>:<api_secret>@<cloud_name>
JWT_SECRET=una_clave_secreta_segura

### 3️⃣ Configuración de la base de datos

Este backend usa SQLite, por lo que no necesitas instalar nada extra.
Puedes encontrar la base en:
src/main/resources/database.db
Se creará automáticamente si no existe.

### ▶️ Ejecución del Proyecto
🔧 Usando Maven
mvn spring-boot:run

### 📚 Documentación de la API (Swagger UI)

Una vez iniciado el servidor, visita:
👉 http://localhost:8080/swagger-ui.html
Aquí podrás ver y probar todos los endpoints del backend.
