# start by pulling the python image
FROM openjdk:17-oracle

# copy the requirements file into the image
COPY ./target/mymarketplace-0.0.1-SNAPSHOT.jar /app/

# switch working directory
WORKDIR /app


CMD ["java", "-jar", "mymarketplace-0.0.1-SNAPSHOT.jar" ]