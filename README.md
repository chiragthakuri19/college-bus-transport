# College Bus Transport Management System

A web-based system for managing college bus transportation built with Spring Boot and React.

## Features

- User Authentication (Admin/Student)
- Bus Route Management
- Schedule Management
- User Profile Management
- Transport Fee Management
- Real-time Notifications

## Tech Stack

- Backend: Spring Boot 3.2.3
- Database: MySQL 8.0
- Security: JWT Authentication
- Frontend: React (TypeScript)

## Local Development

### Prerequisites

- Java 21
- Maven
- Docker and Docker Compose
- Node.js and npm (for frontend development)

### Running the Application

1. Clone the repository
```bash
git clone <repository-url>
cd college-bus-transport
```

2. Start the application using Docker Compose
```bash
docker-compose up --build
```

The application will be available at:
- Backend API: http://localhost:8080
- Database: localhost:3307

## API Endpoints

### Authentication
- POST /api/auth/register - Register new user
- POST /api/auth/login - Login user

### Bus Routes
- GET /api/routes - Get all routes
- GET /api/routes/{id} - Get route by ID
- POST /api/routes - Create new route (Admin only)
- PUT /api/routes/{id} - Update route (Admin only)
- DELETE /api/routes/{id} - Delete route (Admin only)

### User Management
- GET /api/users/profile - Get user profile
- PUT /api/users/profile - Update user profile

## Deployment

The application is containerized and can be deployed to any cloud platform that supports Docker containers.

### Environment Variables

The following environment variables need to be set in production:

- JDBC_DATABASE_URL
- JDBC_DATABASE_USERNAME
- JDBC_DATABASE_PASSWORD
- JWT_SECRET

## License

MIT License 