#Creating secret for DB
kubectl create secret generic chat-db-secret \
--from-literal=db.user=user \
--from-literal=db.password=pass \
--from-literal=db.root.password=root_pass;

#Creating secret for Docker private registry
kubectl create secret docker-registry docker-registry-secret \
--docker-server="https://index.docker.io/v1/" \
--docker-username=docker_username \
--docker-email=docker.email@gmail.com \
--docker-password=password;

#Creating db
kubectl create -f chat-db.yml;
#Creating db service
kubectl create -f chat-db-service.yml;

sleep 5000;

#Executing liquibase job to create DB structure
kubectl create -f liquibase-chat-db-job.yml
#Executing liquibase job to create DB data
kubectl create -f liquibase-chat-db-job-data.yml

sleep 5000;

#Creating app
kubectl create -f chat.yml
#Creating app service
kubectl create -f chat-service.yml