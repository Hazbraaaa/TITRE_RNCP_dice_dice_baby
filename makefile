# ==============================================================================
# VARIABLES
# ==============================================================================

ifneq (,$(wildcard ./.env))
    include .env
    export
endif

DC = docker compose
FRONT_SERVICE = frontend
BACK_SERVICE = backend
DB_SERVICE = db


# ==============================================================================
# MAIN RULES (Docker)
# ==============================================================================

.PHONY: up down restart build status logs reset

# Launch the application in detached mode
up:
	$(DC) up -d

# Stop the containers
down:
	$(DC) down

# Restart the application
restart: down up

# Force the reconstruction of all images and launch the containers
build:
	$(DC) up -d --build

# Display the status of the containers
status:
	$(DC) ps

# Display the logs in real-time
logs:
	$(DC) logs -f

# Destroy all volumes and restart the application
reset:
	$(DC) down -v
	$(DC) up -d --build

# ==============================================================================
# SHORTCUTS FOR CONTAINERS (Logs, Shell)
# ==============================================================================

.PHONY: logs-front logs-back logs-db shell-front shell-back shell-db

# Display the logs of a specific container
logs-front:
	$(DC) logs -f $(FRONT_SERVICE)

logs-back:
	$(DC) logs -f $(BACK_SERVICE)

logs-db:
	$(DC) logs -f $(DB_SERVICE)

# Enter the interior of a container (Shell terminal)
shell-front:
	$(DC) exec -it $(FRONT_SERVICE) sh

shell-back:
	$(DC) exec -it $(BACK_SERVICE) bash

shell-db:
	$(DC) exec -it $(DB_SERVICE) bash


# ==============================================================================
# SPECIFIC COMMANDS FOR TECH STACK
# ==============================================================================

.PHONY: mvn-clean mvn-spotless test-back npm-lint npm-lint-fix test-front psql check-commit

# Launch a Clean & Package Maven directly in the Back container
mvn-clean:
	$(DC) exec $(BACK_SERVICE) ./mvnw clean

# Run Spotless to format code directly in the Back container
mvn-spotless:
	$(DC) exec $(BACK_SERVICE) ./mvnw spotless:apply

# Run tests directly in the Back container
test-back:
	$(DC) exec -e SPRING_DATASOURCE_URL="jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL" \
	           -e SPRING_DATASOURCE_DRIVERCLASSNAME="org.h2.Driver" \
	           -e SPRING_DATASOURCE_USERNAME="sa" \
	           -e SPRING_DATASOURCE_PASSWORD="" \
	           -e SPRING_JPA_HIBERNATE_DDL_AUTO="create-drop" \
	           -e SPRING_FLYWAY_ENABLED="false" \
	           $(BACK_SERVICE) ./mvnw test

# Run ESLint directly in the Front container
npm-lint:
	$(DC) exec $(FRONT_SERVICE) npm run lint

# Run ESLint with auto-fix directly in the Front container
npm-lint-fix:
	$(DC) exec $(FRONT_SERVICE) npm run lint:fix

# Run tests directly in the Front container
test-front:
	$(DC) exec $(FRONT_SERVICE) npm run test

# Connect directly to the Postgres database
psql:
	$(DC) exec -it $(DB_SERVICE) psql -U $(POSTGRES_USER) -d $(POSTGRES_DB)

# Run all checks before committing code
check-commit: mvn-spotless npm-lint test-back test-front