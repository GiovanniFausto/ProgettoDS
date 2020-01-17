docker build -t api-prod:v1 ./API -f API/Dockerfile-prod 
docker build -t vms-prod:v1 ./VMS -f VMS/Dockerfile-prod 
docker build -t vps-prod:v1 ./VPS -f VPS/Dockerfile-prod 
docker-compose -f docker-compose-prod.yml up 
