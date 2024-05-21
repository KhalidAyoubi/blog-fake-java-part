FROM tomee:9-alpine-plume
COPY target/blog.war /usr/local/tomee/webapps/
