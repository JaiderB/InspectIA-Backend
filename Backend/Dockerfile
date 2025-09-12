FROM eclipse-temurin:21

WORKDIR /app
COPY app-0.0.1-SNAPSHOT.jar /app
COPY serviceAccountKey.json /app

ENV FIREBASE_SERVICE_ACCOUNT_KEY=/app/serviceAccountKey.json
ENV JWT_REFRESH_TOKEN_EXPIRATION=259200000


CMD [ "java", "-jar", "/app/app-0.0.1-SNAPSHOT.jar" ]