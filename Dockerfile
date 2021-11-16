FROM tomcat:latest
LABEL maintainer="Ravi Singh Rajput <ravisinghrajput005@gmail.com>"
COPY target/**.war /usr/local/tomcat/webapps
