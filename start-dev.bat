@echo off

echo Iniciando Angular...
start cmd /k "cd frontend && ng serve"

echo Iniciando Spring Boot...
start cmd /k "cd backend && mvnw.cmd spring-boot:run"

echo Todo iniciado