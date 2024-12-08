@echo off
start /B kubectl port-forward svc/mysql-service 3306:3306
start /B kubectl port-forward svc/keycloak 8080:8080
start /B kubectl port-forward svc/theater-service 8081:8081
start /B kubectl port-forward svc/eureka-service 8761:8761
start /B kubectl port-forward svc/booking-service 8084:8084
start /B kubectl port-forward svc/movie-service 8083:8083

