# Document Management System

## Current Progress (Sprint 2)

- **Frontend**: Nginx serves an HTML page displaying "Hello World".
- **Backend API**: Spring Boot provides an endpoint with a basic message.
- **Integration**: The frontend fetches data from the backend API.
- **Docker**: Both frontend and backend run in containers managed by `docker-compose`.

## Setup

1. Build and run with Docker Compose:

   ```bash
   docker-compose up --build
   ```

2. Access the frontend at [http://localhost](http://localhost) and the backend API at [http://localhost:8081/api/message](http://localhost:8081/api/message).
