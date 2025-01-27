#!/bin/bash

run_if_non_empty() {
  local cmd="$1"
  local items="$2"
  if [[ ! -z "$items" ]]; then
    echo "$items" | xargs $cmd
  fi
}

echo "Stopping all running Docker containers..."
containers=$(docker ps -q)
run_if_non_empty "docker stop" "$containers"

echo "Removing all containers..."
containers=$(docker ps -aq)
run_if_non_empty "docker rm" "$containers"

echo "Removing all images..."
images=$(docker images -q)
run_if_non_empty "docker rmi -f" "$images"

echo "Removing all volumes..."
volumes=$(docker volume ls -q)
run_if_non_empty "docker volume rm" "$volumes"

echo "Removing all networks (ignoring default ones)..."
networks=$(docker network ls --filter "type=custom" -q)
run_if_non_empty "docker network rm" "$networks"

echo "Removing all Docker build cache..."
docker builder prune -a --force

echo "Performing a full system-wide cleanup..."
docker system prune --all --volumes --force

echo "Docker cleanup completed. Verifying resources..."

echo "Containers:"
docker ps -a

echo "Images:"
docker images

echo "Volumes:"
docker volume ls

echo "Networks:"
docker network ls

echo "All Docker resources have been purged."

echo "Creating shared network..."
docker network create shared-network
