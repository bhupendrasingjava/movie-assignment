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



#Scale deployment commands



C:\bhupendra\softwares\elasticsearch-8.9.1\bin>kubectl scale deployment theater-service --replicas=0 -n default
deployment.apps/theater-service scaled

C:\bhupendra\softwares\elasticsearch-8.9.1\bin>kubectl scale deployment api-gateway --replicas=1 -n default
deployment.apps/api-gateway scaled

C:\bhupendra\softwares\elasticsearch-8.9.1\bin>kubectl scale deployment movie-service --replicas=0 -n default
deployment.apps/movie-service scaled

C:\bhupendra\softwares\elasticsearch-8.9.1\bin>kubectl scale deployment theater-service --replicas=0 -n default
deployment.apps/theater-service scaled

C:\bhupendra\softwares\elasticsearch-8.9.1\bin>kubectl scale deployment eureka-server --replicas=1 -n default
deployment.apps/eureka-server scaled

C:\bhupendra\softwares\elasticsearch-8.9.1\bin>kubectl scale deployment prometheus-deployment --replicas=0 -n default
deployment.apps/prometheus-deployment scaled

C:\bhupendra\softwares\elasticsearch-8.9.1\bin>kubectl scale deployment grafana-deployment --replicas=0 -n default
deployment.apps/grafana-deployment scaled

C:\bhupendra\softwares\elasticsearch-8.9.1\bin>kubectl scale deployment zipkin --replicas=0 -n default
deployment.apps/zipkin scaled

kubectl scale deployment zipkin --replicas=1 -n default





Create a topic in Kafka:

Navigate to bin directry (Got to root and then go to bin):

kafka-topics --create --topic email --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
kafka-topics --create --topic sms --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1

To Publish a message:

kafka-console-producer --broker-list localhost:9092 --topic email

Hello How r u

To read a message from a topic email:

kafka-console-consumer --bootstrap-server localhost:9092 --topic email --from-beginning

