# Docker multi-stage build

# 1. Building the App with Maven
FROM maven:3.8.6-amazoncorretto-17

ADD . /bioskop-team-b-6
WORKDIR /bioskop-team-b-6

# Just echo so we can see, if everything is there :)
RUN ls -l

# Run Maven build
RUN mvn clean install


# 2. Just using the build artifact and then removing the build-container
FROM openjdk:17-jdk

MAINTAINER aris kusnul widitama

VOLUME /tmp

# Add Spring Boot app.jar to Container
COPY --from=0 "/bioskop-team-b-6/target/Bioskop6-0.0.1-SNAPSHOT.jar" app.jar

# Fire up our Spring Boot app by default
#CMD [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]

# Fire up our Spring Boot app by default
CMD [ "sh", "-c", "java $JAVA_OPTS -XX:+UseContainerSupport -Djava.security.egd=file:/dev/./urandom -Dserver.port=$PORT -jar /app.jar" ]


