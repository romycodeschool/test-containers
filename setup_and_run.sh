#!/bin/bash

# Stop on any error
set -e

# Name of the Docker Compose file
COMPOSE_FILE="docker-compose.yaml"

# Check if docker-compose.yml exists in the current directory
if [ ! -f "$COMPOSE_FILE" ]; then
    echo "docker-compose.yaml file not found in the current directory."
    exit 1
fi

# Starting the containers using Podman Compose
echo "Starting containers..."
podman-compose up -d

# List running containers
echo "Listing running containers..."
podman ps

# Add any additional commands you might need here
# ...

echo "Containers are up and running!"

# End of the script
