# Bank Statement File Processing Application

## Overview

This project is a **Bank Statement File Processing Application** that allows users to upload their monthly bank statements in CSV or JSON format, process the transactions, and store relevant data in a database. The application also includes user authentication and authorization, ensuring secure access to the features. This project aims to implement best practices in software design, including SOLID principles, design patterns, and CI/CD pipelines.

## Features

- **User Authentication & Authorization**: Secure user registration and login using JWT (JSON Web Tokens).
- **File Upload**: Users can upload bank statements in CSV or JSON format.
- **Data Processing**: The application processes uploaded files, extracting relevant transaction details such as date, amount, and transaction type.
- **Data Storage**: Processed transaction data is stored in a database.
- **Logging & Monitoring**: Detailed logs of user actions and application performance.
- **CI/CD Integration**: Continuous integration and delivery setup for automated testing and deployment.
- **Dockerization**: Containerized application for easy deployment and scalability.

## Technology Stack

- **Backend**: Java, Spring Boot
- **Database**: PostgreSQL (or your choice of database)
- **Frontend**: (Optional: If a frontend is added)
- **Authentication**: JWT (JSON Web Tokens)
- **Testing**: JUnit, Mockito
- **Logging**: SLF4J with Logback
- **Build Tool**: Maven
- **Containerization**: Docker
- **CI/CD**: GitHub Actions (or your choice of CI/CD tools)

## Prerequisites

Before you begin, ensure you have the following installed:

- Java JDK 17 or higher
- Maven
- Docker (for containerization)
- PostgreSQL (or your preferred database)
- Git (for version control)

## Getting Started

### Clone the Repository

```
git clone https://github.com/Obaid-Ghafoori/bank-statement-processor.git
cd bank-statement-processing-app
```
## Build the Project
Navigate to the project directory and run:

```
mvn clean install
```
## Set Up Database
- Create a new PostgreSQL database (or your chosen database).
- Update src/main/resources/application.properties with your database credentials:
````
spring.datasource.url=jdbc:postgresql://localhost:5432/yourdbname
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
````

## Run the Application
You can run the application using:

````
mvn spring-boot:run
````
