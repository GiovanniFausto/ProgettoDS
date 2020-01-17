# ProgettoDS
PR5) (a).DB1.GW2.STATS1
For more info see relazione.pdf in the root dir.
## Homework 1:

Build dev docker image
```bash
docker build -t api:v1 . -f Dockerfile-dev
docker build -t vms:v1 . -f Dockerfile-dev 
docker build -t vps:v1 . -f Dockerfile-dev
```
Run docker-compose dev
```bash
docker-compose -f docker-compose-dev.yml up
```
Running the production environment or run the run.sh file on consegna1 dir:
```bash
docker build -t api-prod:v1 . -f Dockerfile-prod
docker build -t vms-prod:v1 . -f Dockerfile-prod 
docker build -t vps-prod:v1 . -f Dockerfile-prod
```
Run docker-compose prod
```bash
docker-compose -f docker-compose-prod.yml up
```


## Homework 2:

Launch Minikube:
```bash
minikube start --memory=5120 --vm-driver=kvm2
minikube status
minikube dashboard
```

Set environment variables for Docker cli:
```bash
eval $(minikube docker-env)
```
Pull the necessary image
```bash
docker pull prom/prometheus
docker pull mjhea0/spark-hadoop:2.2.1
```
Build dev image:
```bash
docker build -t api-service:v1 . -f Dockerfile-dev
docker build -t vms-service:v1 . -f Dockerfile-dev 
docker build -t vps-service:v1 . -f Dockerfile-dev
docker build -t spout:v1 . -f Dockerfile-dev
docker build -t sparkstream:v1. -f Dockerfile-dev 
```
Build production image:
```bash
docker build -t api-prod:v1 . -f Dockerfile-prod
docker build -t vms-prod:v1 . -f Dockerfile-prod
docker build -t vps-prod:v1 . -f Dockerfile-prod
docker build -t spout-prod:v1 . -f Dockerfile-prod
docker build -t sparkstream-prod:v1 . -f Dockerfile-prod
```
ConfigMaps prometheus:
```bash
kubectl create configmap prometheus-example-cm --from-file prometheus.yml
```
To deploy:
```bash
kubectl apply -f kafka.yml
kubectl apply -f api-service.yml
kubectl apply -f vms-service.yml
kubectl apply -f vps-service.yml
kubectl apply -f vol.yml
kubectl apply -f prom.yml
kubectl apply -f spout-service.yml
kubectl apply -f spark-master-deployment.yml
kubectl apply -f spark-workers-deployment.yml
kubectl apply -f spark-service.yml
```
Show ip:
```bash
minikube service --url api-service-external
minikube service --url prometheus-external
minikube service --url spark-master-external
```
Query prometheus:
```html
http://prometheus-external/api/v1/query?query=(sum(gateway_requests_seconds_count)/sum(process_uptime_seconds))
http://prometheus-external/api/v1/query?query=(sum(gateway_requests_seconds_sum))/(sum(gateway_requests_seconds_count))
```
To "un"-deploy:
```bash
kubectl delete -f kafka.yml
kubectl delete -f api-service.yml
kubectl delete -f vms-service.yml
kubectl delete -f vps-service.yml
kubectl delete -f vol.yml
kubectl delete -f prom.yml
kubectl delete -f spout-service.yml
kubectl delete -f spark-master-deployment.yml
kubectl delete -f spark-workers-deployment.yml
kubectl delete -f spark-service.yml
```
