FROM tomcat:10-jdk17-corretto
VOLUME /tmp

EXPOSE 8080
COPY target/jax-rs-project-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/app.war