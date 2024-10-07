# Use the official Tomcat image
FROM tomcat:9.0

# Remove default webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the application files to the Tomcat webapps directory
COPY src/main/webapp /usr/local/tomcat/webapps/ROOT/

# Copy the Hibernate configuration file
COPY src/main/resources/hibernate.cfg.xml /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/

# Copy compiled classes
COPY target/classes /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/

# Expose the Tomcat port
EXPOSE 8080