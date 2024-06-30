# Build stage
# Step 1: Build the application
FROM maven:3.9.6-amazoncorretto-21 AS  build
# Set the working directory
WORKDIR /build
# Copy the pom.xml file
COPY pom.xml .
# Install the dependencies
RUN  mvn dependency:go-offline
# Copy the source code
COPY src ./src
# Package the application without running the tests to speed up the build
RUN mvn clean package -DskipTests

# Runtime stage
# Step 1: to run the application we need JDK 21
FROM amazoncorretto:21
# Set the arguements ( docker specific) if not provided default values will be used
ARG PROFILE=dev
ARG APP_VERSION=1.0.0
# Set the working directory
WORKDIR /app
# Copy the packaged JAR file from the build stage
COPY --from=build /build/target/bsn-*.jar /app/
#Expose the port
EXPOSE 8088

# Environment variables
ENV DB_URL=jdbc:postgresql://postgres-sql-bsn:5432/book_social_network
ENV ACTIVE_PROFILE=${PROFILE}
ENV JAR_VERSION=${APP_VERSION}
ENV EMAIL_USERNAME = missing_username
ENV EMAIL_PASSWORD = missing_password

CMD java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} -Dspring.datasource.url=${DB_URL} bsn-${JAR_VERSION}.jar

# End of Dockerfile
# to build the image run the following command: docker build -t bsn/bsn:1.0.0 -f ../docker/backend/Dockerfile .
# -t is used to tag the image with the name bsn/bsn and version 1.0.0
# -f is used to specify the path of the Dockerfile
