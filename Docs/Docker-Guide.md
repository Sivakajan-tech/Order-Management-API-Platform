# Docker Guide

## Prerequisites

Before you begin, make sure you have the following installed:
- [Docker](https://www.docker.com/products/docker-desktop) (for building images and running containers)
- [Docker Compose](https://docs.docker.com/compose/) (for managing multi-container applications)

## Dockerizing the Application

You can find the [Dockerfile](/Dockerfile) in the root directory of the project. This file contains the instructions to build a Docker image for the application. To build the Docker image for your application, run the following command:

```bash
docker build -t ordermanagementapi .
```
This command will create an image named `ordermanagementapi` based on the `Dockerfile` located in the current directory.


## Running the Application with Docker Compose

Docker Compose allows you to define and run multi-container Docker applications. The `docker-compose.yml` file describes the services (such as your app and database) and how they interact. You can find the [docker-compose.yml](/docker-compose.yaml) file in the root directory of the project.

### Start the Application with Docker Compose

To start both the application and the MySQL database using Docker Compose, run the following command:

```bash
docker-compose up -d
```

This command will build the images (if not already built) and start the containers as defined in the `docker-compose.yml` file. It will run the application on port 8080 and expose MySQL on port 3306.

## Environment Variables

The `SPRING_DATASOURCE_URL` in the `docker-compose.yml` is dynamically set to connect to the MySQL container.

- `SPRING_DATASOURCE_URL`: The JDBC URL for the database (set to `jdbc:mysql://mysqldb:3306/oma` for the Docker container setup).
- `SPRING_DATASOURCE_USERNAME`: The database username (e.g., `root`).
- `SPRING_DATASOURCE_PASSWORD`: The database password (e.g., `password`).

## Common Issues and Troubleshooting

- **Database Connection Failures**: If the application cannot connect to the MySQL database, ensure that the MySQL container is running and the environment variables are set correctly.
- **Healthcheck Failures**: If the health check fails, check the logs to troubleshoot the specific error. Run `docker logs <container_id>` to view logs.
