# Introduction

This project allow users to get a forecast of the current temperature in different latitudes and longitudes.

# How to run

1. Generate docker image: **docker build -t vf-assessment** .

2. Run docker-compose: **docker-compose up -d**

It includes the spring application, the mongo database and the kafka image.


Alternatively, you can run the docker image by itself, but needs to be in the same
network as the mongo database.

You must have a mongo instance on port **27017**.

**docker run -p 8080:8080 --name vf-assessment --network my_network -d vf-assessment**

# Swagger

http://localhost:8080/doc/swagger-ui/index.html

# Kafdrop

http://localhost:9000/

# Endpoints

GET localhost:8080/forecast?latitude=,longitude=

DELETE localhost:8080/forecast?latitude=,longitude=





