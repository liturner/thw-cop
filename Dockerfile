FROM eclipse-temurin:17-jre

EXPOSE 8080

COPY boscop-main/target/boscop-release /opt/boscop
RUN mkdir -p /opt/boscop/conf
RUN mkdir -p /opt/boscop/data

WORKDIR /opt/boscop
ENTRYPOINT ["sh", "/opt/boscop/BOSCOP.sh"]