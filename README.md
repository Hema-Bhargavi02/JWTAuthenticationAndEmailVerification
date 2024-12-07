# JWT Authentication and Email Verification in Spring Boot

This project demonstrates how to implement **JWT (JSON Web Token) Authentication** in a Spring Boot application. It includes the following features:
- **Sign-In**: User registration.
- **Email Verification**: Sends a verification email to the user upon registration.
- **Login**: Authenticates the user and provides a JWT token for secure access to protected endpoints.

## Features

1. **Sign-In**  
   - Users can register by providing their details (e.g., email, username, password).
   - The application stores the user details securely in the database.

2. **Email Verification**  
   - After registration, the user receives an email with a verification link.
   - The user must verify their email to activate their account.

3. **Login**  
   - Users can log in with their email and password.
   - Upon successful login, the application generates and returns a JWT token.
   - The token is used for authenticating requests to protected endpoints.

---

## Technologies Used

- **Spring Boot**: Backend framework.
- **Spring Security**: For authentication and authorization.
- **JWT (JSON Web Tokens)**: For stateless authentication.
- **Java Mail Sender**: For sending verification emails.
- **MySQL (or your preferred database)**: For storing user data.
- **Hibernate**: For ORM (Object-Relational Mapping).
