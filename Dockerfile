FROM maven:3-jdk-11

VOLUME /var/log

COPY pom.xml elasticsearch-practices/

COPY src/ elasticsearch-practices/src/

COPY wait-for-container.sh /wait-for-container.sh

WORKDIR elasticsearch-practices/

RUN mvn clean package

EXPOSE 8080

ENTRYPOINT ["bash", "/wait-for-container.sh"]
