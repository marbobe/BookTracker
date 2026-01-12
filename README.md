# 📚 BookTracker: Sistema de Gestión de Lecturas Proporcional

**BookTracker** es una aplicación web full-stack diseñada para centralizar y puntuar el historial de lecturas personales. Este proyecto nació de la necesidad de aplicar arquitecturas robustas en un entorno real, priorizando la experiencia de usuario (UX) y la integridad de los datos.

**Estado del Proyecto:** Versión 1.0 (Thymeleaf Edition) - Estable.

---

## Características Destacadas (Implementadas)

### Gestión Completa (CRUD)
- Registro detallado de libros: título, autor, fecha de finalización, reseña y puntuación.
- Edición y borrado con validaciones en tiempo real para evitar datos inconsistentes.

### Navegación Avanzada y UX
- **Buscador Dinámico:** Filtro de libros por título o autor para facilitar la localización en colecciones extensas.
- **Paginación Inteligente:** Implementación de navegación por páginas para optimizar la carga y el rendimiento del frontend.
- **UI de Alto Impacto:** Interfaz responsiva construida con **Bootstrap 5**, incluyendo un selector de estrellas visual para las puntuaciones y confirmaciones de seguridad antes de acciones críticas (borrado).

### Solidez Técnica
- **Arquitectura en Capas:** Separación estricta entre Controlador, Servicio, Repositorio y Modelo (MVC).
- **Validación de Datos:** Uso de anotaciones JPA y Bean Validation (`@NotBlank`, `@Min`, `@Max`) para garantizar la calidad de la información.

---

## Stack Tecnológico

| Tecnología | Uso |
| :--- | :--- |
| **Java 21** | Lenguaje principal (Backend) |
| **Spring Boot 3.3.6** | Framework de aplicación |
| **Spring Data JPA** | Gestión de persistencia y abstracción de base de datos |
| **Thymeleaf** | Motor de plantillas para renderizado server-side |
| **Bootstrap 5** | Framework de estilos y diseño responsivo |
| **H2 Database** | Base de datos en memoria para desarrollo y tests |
| **Maven** | Gestión de dependencias y construcción del proyecto |

---

## Arquitectura y Patrones
Para este proyecto se han aplicado buenas prácticas de ingeniería de software que lo hacen escalable y fácil de mantener:
- **Patrón Repositorio:** Abstracción completa del acceso a datos.
- **Capa de Servicio:** Desacoplamiento de la lógica de negocio del controlador.
- **Inyección de Dependencias:** Gestión eficiente de componentes mediante el contenedor de Spring.

---

## Próximos Pasos (Roadmap)

### Mejoras Inmediatas (v1.x):
- [ ] **Migración a Producción:** Configurar **PostgreSQL** como base de datos persistente.
- [ ] **Gestión de Medios:** Implementación de subida de imágenes para las portadas de los libros (Multipart Files).
- [ ] **Seguridad:** Añadir Spring Security para permitir múltiples usuarios con perfiles privados.

### Evolución del Proyecto (v2.0):
- [ ] **Migración a Arquitectura Desacoplada:** Transformar el backend en una **API REST** pura.
- [ ] **Frontend Moderno:** Reconstrucción total de la interfaz utilizando **React**, permitiendo una experiencia de Single Page Application (SPA) más fluida y moderna.

---

## Instalación y Uso

1. Clonar el repositorio.
2. Asegurarse de tener instalado **JDK 21**.
3. Ejecutar desde la terminal:
   ```bash
   ./mvnw spring-boot:run ```
4. Abrir en el navegador: http://localhost:8080/books
