cd API
docker build -t api-prod:v1 . -f Dockerfile-prod 
cd .. 
cd VMS
docker build -t vms-prod:v1 . -f Dockerfile-prod 
cd .. 
cd VPS
docker build -t vps-prod:v1 . -f Dockerfile-prod 
cd ..
docker-compose -f docker-compose-prod.yml up 
