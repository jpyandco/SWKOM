# Document Management System

## Sprint 4 Updates

### Key Changes:
- **Backend**: First implementation of RabbitMQ
- **Backend**: Implemented logging in critical positions
- **Docker**: Updated `docker-compose` for creation of a separate RabbitMQ container.

## Setup

1. **Run Application**:
   ```bash
   docker-compose up --build
   ```

2. **Access**:
   - **Frontend**: [http://localhost](http://localhost)
   - **Backend API**: [http://localhost:8081/api/](http://localhost:8081/api/)

3. **Testing**:
   - Execute backend tests with:
     ```bash
     mvn test
     ```
   - Validate functionality with valid and invalid inputs.

Sprint 4 lays the groundwork for further implementation of queues and message sending.