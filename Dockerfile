FROM tomcat:latest
LABEL maintainer="Ravi Singh Rajput <ravisinghrajput005@gmail.com>"
COPY /simple.*/.war /usr/local/tomcat/webapps
