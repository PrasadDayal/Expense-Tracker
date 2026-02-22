# 💰 Expense Tracker System

A Spring Boot backend application for managing and splitting shared expenses between users.

This project allows users to:
- Create users
- Add shared expenses
- Split expenses among participants
- Track expense records

Built using **Spring Boot, JPA (Hibernate), and MySQL**.

---

## 📌 Features

- Create and manage users
- Add expenses
- Split expenses equally among participants
- Store expense splits in database
- RESTful API architecture
- Layered project structure

---

## 🏗️ Architecture

The application follows a clean layered architecture:

Client → Controller → Service → Repository → Database

### Layers:

- **Controller Layer** → Handles HTTP requests
- **Service Layer** → Business logic
- **Repository Layer** → Database access using JPA
- **Entity Layer** → Database table mapping

---
## ⚙️ Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Lombok

---

## 🧠 Business Logic: The Split Flow

The core of the **Expense Tracker System** is the automated splitting logic. When an expense is recorded, the system ensures data integrity and mathematical accuracy through the following workflow:

[Image of expense splitting logic flow diagram]

### 1. Validation Phase
* **User Verification:** The system checks if the `paidByUserId` (the payer) exists in the database.
* **Participant Verification:** It validates that every ID in the `participantIds` list corresponds to a registered user.
* **Constraint Check:** Ensures the total amount is greater than zero and the participant list is not empty.

### 2. Calculation Phase
To prevent financial discrepancies (like the $0.01$ cent rounding error), the system uses **Java BigDecimal**:
* **Formula:** `Total Amount / Number of Participants`
* **Rounding:** It utilizes `RoundingMode.HALF_UP` to 2 decimal places.
* **Logic:** ```java
  BigDecimal splitAmount = totalAmount.divide(
      BigDecimal.valueOf(participants.size()), 2, RoundingMode.HALF_UP
  );

  ---

  ## 🛠️ Future Enhancements

The current system provides a solid foundation for expense tracking. The following features are planned to evolve the project into a production-ready financial tool:

### 🔐 Security & Access Control
- **[ ] JWT Authentication:** Implement Spring Security with JSON Web Tokens (JWT). This will allow users to sign in and receive a secure token for subsequent API calls, ensuring they can only view or edit their own expenses.

### 🧮 Advanced Financial Logic
- **[ ] Debt Settlement Optimization:** Implement a "Simplify Debts" algorithm (similar to Splitwise). This reduces the total number of transactions between a group by using a graph-based clearing house approach.
  

### 📖 Developer Experience
- **[ ] Swagger UI (OpenAPI 3):** Integrate `springdoc-openapi` to provide interactive documentation. Developers will be able to test endpoints directly from the browser at `/swagger-ui.html`.
  

### 📊 Reporting & Exports
- **[ ] Monthly Summaries:** Generate automated spending reports.
- **[ ] PDF/Excel Export:** Add a service layer using **iText** or **Apache POI** to allow users to download their expense history for personal accounting.

### 🐳 Infrastructure
- **[ ] Dockerization:** Create a `Dockerfile` and `docker-compose.yml` to containerize the Spring Boot app and MySQL database for "one-click" deployment.
- **[ ] Microservices Transition:** Split the User Management and Expense Logic into separate services to practice distributed system design.

---

### 🤝 How to Contribute
1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

---

## 👨‍💻 Authors

**Sarthak Bhoknal** *Backend Developer & Frontend Developer*
---
**Prasad Dayal** *Backend Developer & Frontend Developer*
---
**Rohan Bhandare** *Database Designer*
--


If you have any questions, feedback, or want to collaborate on this project, feel free to reach out!
---


> "Clean code always looks like it was written by someone who cares." — *Robert C. Martin*
