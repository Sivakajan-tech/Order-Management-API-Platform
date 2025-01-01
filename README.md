# Order Management API Platform

## Overview
The Order Management API Platform is a backend service built using Java Spring Boot. This platform provides functionalities for user authentication, order management, and order dispatching. The API is secured with JWT-based authentication, and features include user registration, login, order placement, order cancellation, and fetching order history.

---

## Features
1. **User Authentication and Authorization**
   - User signup with email, password, first name, and last name.
   - User login with email and password.
   - JWT-based security for all APIs (except signup and login).

2. **Order Management**
   - Place an order by specifying item name, quantity, and shipping address.
   - Cancel orders in the `NEW` state using the order reference number.
   - Fetch order history with pagination (page number and record size).

3. **Background Job**
   - An hourly background job updates the status of `NEW` orders to `DISPATCHED`.

4. **Database**
   - MySQL database with JPA repository for persistence.

5. **Dockerized Application**
   - Fully containerized backend application for easy deployment.

---

## Additional Documentation
- [Database Design](Docs/DatabaseDesign.md)
- [API Documentation](Docs/APIDocumentation.md)

--- 

## Technologies Used
- **Java Spring Boot** for backend development.
- **MySQL** as the relational database.
- **JPA** for ORM and repository handling.
- **JWT** for securing APIs.
- **Cron Jobs** for background task scheduling.
- **Docker** for containerizing the application.
- **JUnit** and **Mockito** for unit testing.

---

## How to Run

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Sivakajan-tech/Order-Management-API-Platform.git
   cd Order-Management-API-Platform
   ```

2. **Set Up MySQL**:
   ```bash
   CREATE DATABASE oma;
   ```

3. **Configure the Application:**:
   
   Edit the application.properties file to configure your MySQL credentials:
   ```bash
   spring.datasource.url=jdbc:mysql://<hostname>:<port>/oma
   spring.datasource.username=your-<username> 
   spring.datasource.password=your-<password>
   ```
4. **Run the Application**:
   - To run the application using Maven:
     ```bash
     ./mvnw spring-boot:run
     ```
   - To run the application using Docker:
     ```bash
     docker-compose up
     ```
5. **Access the API**:
   - API will be available at `http://localhost:8080`.

---

Made with 💗  by S.Sivakajan😊
