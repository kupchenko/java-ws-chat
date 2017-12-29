FROM gradle
USER root
COPY build.gradle /
COPY migrations /migrations/
WORKDIR /
ENTRYPOINT ["gradle", "update"]