
# Stage 1 : Build the application
# Specify the base image
FROM node:20.9.0 AS build-stage

# Set the working directory
WORKDIR /app

# Copy the package.json and package-lock.json files
COPY package*.json ./

# Install the dependencies
RUN npm install

# Copy the source code
COPY . .

# Build the application
RUN npm run build --prod


# Stage 2 :  Use nginx to serve the application because angular app needs a web server to run
# Specify the base image
FROM  nginx:alpine

# Copy the nginx configuration file
COPY nginx.conf /etc/nginx/nginx.conf

# Copy the build output to the default nginx public folder
COPY --from=build-stage /app/dist/book-network-ui/browser /usr/share/nginx/html

EXPOSE 80

# to build the image run the following command: docker build -t bsn/bsn-ui:1.0.0 -f ../docker/frontend/Dockerfile .