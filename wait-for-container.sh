#!/usr/bin/env bash


# shellcheck disable=SC2212
while [ ]; do
  sleep 1
  curl --request GET -sL \
    --url 'eleasticserver:9200' && break || echo "ElasticSearch server is not responding"
done

echo "Running jar"
java  -Dnetworkaddress.cache.ttl=60 -jar target/elasticsearch-practices-1.0.jar

