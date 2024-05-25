ARG ELASTIC_VERSION

# https://www.docker.elastic.co/
FROM docker.elastic.co/logstash/logstash:${ELASTIC_VERSION}
RUN apk add tzdata
# Add your logstash plugins setup here
# Example: RUN logstash-plugin install logstash-filter-json