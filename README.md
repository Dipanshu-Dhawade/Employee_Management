# Employee Management System

A secure and scalable Employee Management System built using Java and Spring Boot. 
The application provides employee management, authentication, authorization, role and permission management, leave management, document management, education and experience tracking through RESTful APIs.

## 🚀 Features

- JWT-based Authentication
- Role-Based Authorization (RBAC)
- Role and Permission Management
- Secure REST APIs
- Employee CRUD Operations
- Department Management
- Designation Management
- Employee Education Management
- Employee Experience Management
- Employee Document Management
- Leave Management
- Leave Status Management
- Input Validation
- Pagination
- Exception Handling
- Excel Export
- Customer Management
- Database Relationship Management

## 🛠️ Technologies Used

### Backend
- Java
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- REST API
- Maven

### Database
- MySQL

### Tools
- IntelliJ IDEA
- Postman
- Git
- GitHub

## 🔐 Authentication & Authorization

The application uses Spring Security and JWT for authentication.

After successful login, the server generates a JWT token. The client sends the token with requests to protected APIs.

```text
Client
   ↓
Login
   ↓
Spring Security
   ↓
JWT Token
   ↓
Protected API Request
   ↓
JWT Validation
   ↓
Role / Permission Check
   ↓
API Response
