#!/usr/bin/env bash
mvn clean package
echo "Packaging"
docker-compose -f docker-compose.yml up --build -d

