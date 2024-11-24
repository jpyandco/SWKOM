# Document Management System

## Sprint 3 Updates

### Key Changes:
- **Backend**: Added validators for all Business Layer (BL) entities. Validations are enforced for incoming data.
- **Frontend**: Improved UI with a better layout and added a delete button for document entries.
- **Testing**: Achieved >70% test coverage with unit tests for valid and invalid data for all API methods.
- **Docker**: Updated `docker-compose` for streamlined development and testing.

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

Sprint 3 ensures robust validations, improved UI, comprehensive testing, and smooth integration for a production-ready system.