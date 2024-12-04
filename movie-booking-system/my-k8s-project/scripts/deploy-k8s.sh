#!/bin/bash
kubectl apply -f k8s-manifests/mysql/
kubectl apply -f k8s-manifests/prometheus/
kubectl apply -f k8s-manifests/grafana/
kubectl apply -f k8s-manifests/zipkin/
kubectl apply -f k8s-manifests/kibana/
kubectl apply -f k8s-manifests/elasticsearch/
