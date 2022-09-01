# syntax=docker/dockerfile:1
#Which "official Java image" ?
FROM maven
#working directory
WORKDIR /app
#copy from your Host(PC, laptop) to container
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
#Run this inside the image
RUN mvn dependency:go-offline
COPY src ./src
CMD ["mvn", "spring-boot:run"]