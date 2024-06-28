# Trabajo Práctico Final de Backend

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)

## Descripción

Este proyecto es el trabajo práctico final de backend, donde se demuestra el manejo de APIs con autenticación mediante tokens y documentación con Swagger. El proyecto fue desarrollado por **Enrique Espinosa** **Y** **Nadia Postigo**.

## Características

- **API Segura**: Implementación de autenticación y autorización mediante tokens JWT.
- **Documentación con Swagger**: Documentación interactiva de las APIs utilizando Swagger.
- **Arquitectura Moderna**: Uso de Spring Boot para el desarrollo del backend.
- **Pruebas Unitarias**: Pruebas unitarias con JUnit y Mockito.

## Tecnologías Utilizadas

- **Java**
- **Spring Boot**
- **Spring Security**
- **JWT (JSON Web Tokens)**
- **Swagger**
- **JUnit**
- **Mockito**

## Instalación

1. Clona este repositorio:
    ```bash
    git clone https://github.com/tuusuario/turepositorio.git
    ```

2. Navega al directorio del proyecto:
    ```bash
    cd turepositorio
    ```

3. Construye el proyecto:
    ```bash
    ./mvnw clean install
    ```

4. Ejecuta la aplicación:
    ```bash
    ./mvnw spring-boot:run
    ```

## Uso

- Accede a la documentación de la API en `http://localhost:8083/swagger-ui.html` para product
- Accede a la documentación de la API en `http://localhost:8082/swagger-ui.html` para Orders
- Utiliza las herramientas de desarrollo como Postman para interactuar con las APIs protegidas mediante tokens JWT.

## Contribuciones

Las contribuciones son bienvenidas. Por favor, sigue los pasos a continuación para contribuir:

1. Haz un fork del proyecto.
2. Crea una nueva rama (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza los cambios necesarios y commitea (`git commit -m 'Añadir nueva funcionalidad'`).
4. Sube los cambios (`git push origin feature/nueva-funcionalidad`).
5. Abre un Pull Request.

## Autor

**Enrique Espinosa**

![Java](https://data:image/png;base64,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)

