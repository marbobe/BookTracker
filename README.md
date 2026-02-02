# 📚 BookTracker: Personal Reading Management System

**BookTracker** is a full-stack web application designed to centralize, review, and rate your personal reading history. This project demonstrates the implementation of robust architectures in a real-world environment, prioritizing User Experience (UX), security, and data integrity.

**Project Status:** Version 1.1 (Security & Demo Edition) - Stable.

---

## 📸 App Screenshots

<img width="1390" height="658" alt="image" src="https://github.com/user-attachments/assets/a47f9eee-15e5-4810-a825-7bbe14c2ebd6" />
<img width="1373" height="812" alt="image" src="https://github.com/user-attachments/assets/e1675d07-9319-415b-98eb-9371d223c1cc" />
<img width="581" height="517" alt="image" src="https://github.com/user-attachments/assets/9ea842a6-f25b-4193-b32e-74c7cdbd3052" />
<img width="1358" height="725" alt="image" src="https://github.com/user-attachments/assets/43da7e60-c396-43dd-93cf-fa521a4d2590" />




---

## 🌟 Key Features

### Full CRUD Management
- Detailed book logging: Title, author, genre, finish date, review, and star rating.
- Advanced editing and deletion with real-time validations.

### Security & Multi-user Support
- **Spring Security Integration:** Secure authentication and authorization flow.
- **Custom User Profiles:** Each user manages their own private collection.
- **Data Protection:** Passwords encrypted using **BCrypt**.

### Professional Demo Mode
- **Guest Access:** Recreuiters can explore the app with a single click via "Live Demo".
- **Read-Only Restrictions:** Logic-level protection that allows guests to see the UI and test forms while preventing database modifications.
- **Automated Data Seeding:** Database pre-populated with curated book data for immediate evaluation.

### Advanced Navigation & UX
- **Dynamic Search:** Filter books by title, author, date, or score with persistent state across pages.
- **Smart Pagination:** Optimized server-side navigation for performance.
- **Responsive UI:** Built with **Bootstrap 5**, featuring custom star-rating components and safe-action confirmations.

---

## 🛠 Tech Stack

| Technology | Purpose |
| :--- | :--- |
| **Java 21** | Backend Language |
| **Spring Boot 3.3.6** | Core Framework |
| **Spring Security** | Authentication & Authorization |
| **Spring Data JPA** | Data Persistence & Abstraction |
| **Thymeleaf** | Server-side Template Engine |
| **MySQL / Docker** | Production Database |
| **H2 Database** | Testing & Development |
| **Bootstrap 5** | Responsive Frontend Design |
| **Maven** | Dependency Management |

---

## 🏗 Architecture & Patterns
The project follows clean code principles and modern engineering patterns:
- **Layered Architecture:** Strict separation between Controller, Service, Repository, and Model (MVC).
- **Repository Pattern:** Complete abstraction of data access.
- **Dependency Injection:** Efficient component management via Spring IoC.
- **Programmatic Authentication:** Custom logic for seamless "Guest" login experiences.

---

## 🚀 Roadmap

### Immediate Enhancements (v1.x):
- [ ] **Cloud Deployment:** Live version hosted on Render/Railrail with PostgreSQL.
- [ ] **Media Management:** Book cover uploads using Multipart Files and Cloudinary.
- [ ] **Statistics Dashboard:** Visual charts showing reading habits by genre and time.

### Future Evolution (v2.0):
- [ ] **REST API Migration:** Decoupling the backend into a pure RESTful service.
- [ ] **Modern Frontend:** Complete UI rebuild using **React** for a Single Page Application (SPA) experience.
- [ ] **AI Integration:** Personalized reading recommendations using **Spring AI and Google Gemini API**.

---

## ⚙️ Installation & Setup

1. Clone the repository:
   ```bash
   git clone [https://github.com/your-user/book-tracker.git](https://github.com/your-user/book-tracker.git)
2. Setup Database: The project is configured to use MySQL. Ensure you have a local instance or use the provided docker-compose if available
3. Build and Run:
   ```bash
   ./mvnw spring-boot:run ```
4. Access the App: Open http://localhost:8080 in your browser.
