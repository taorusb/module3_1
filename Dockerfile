FROM openjdk:11.0.15
ENV IEXAPIS_TOKEN=$IEXAPIS_TOKEN
ENV APP_THREAD_COUNT=$APP_THREAD_COUNT
COPY . ./app

WORKDIR ./app
RUN chmod +x gradlew && ./gradlew build
WORKDIR ./build/libs/
ENTRYPOINT ["java", "-jar", "module3_1-0.0.1-SNAPSHOT.war"]