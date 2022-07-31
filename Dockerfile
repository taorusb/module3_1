FROM openjdk:11.0.15
ENV SERVER_PORT=$SERVER_PORT
ENV IEXAPIS_TOKEN=$IEXAPIS_TOKEN
ENV APP_THREAD_COUNT=$APP_THREAD_COUNT
ENV DB_USER=$DB_USER
ENV DB_URL=$DB_URL
ENV DB_PASSWORD=$DB_PASSWORD
ENV DB_NAME=$DB_NAME
ENV DB_SCHEMA=$DB_SCHEMA
COPY . ./app

WORKDIR ./app
RUN chmod +x gradlew && ./gradlew build
WORKDIR ./build/libs/
ENTRYPOINT ["java", "-jar", "module3_1-0.0.1-SNAPSHOT.war"]