@echo off
start /B kubectl port-forward svc/mysql-service 3306:3306
start /B kubectl port-forward svc/keycloak 8080:8080
start /B kubectl port-forward svc/theater-service 8081:8081
start /B kubectl port-forward svc/eureka-service 8761:8761
start /B kubectl port-forward svc/booking-service 8084:8084
start /B kubectl port-forward svc/movie-service 8083:8083
start /B kubectl port-forward svc/prometheus-service 9090:9090
start /B kubectl port-forward svc/grafana-service 3000:3000
start /B kubectl port-forward svc/api-gateway-service 7000:7000

kubectl port-forward deploy/api-gateway 7000:7000
kubectl port-forward svc/elasticsearch-service 9200:9200
kubectl port-forward svc/logstash-service 5000:5000
kubectl port-forward svc/kibana-service 5601:5601
kubectl port-forward deploy/elasticsearch-deployment 9200:9200
kubectl port-forward svc/zipkin 9411:9411

