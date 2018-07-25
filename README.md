# Java WS Chat
Application is built using Spring, Docker, Kubernetes, MySQL, Liquibase.
The app aims to show the way you can built and use WS in Spring, how to put it into Docker images and deploys it to Kubernetes.

## Getting Started

These instructions will help you start and deploy this app to Kubernetes.

### Installing steps

In order to run this app on Kubernetes, you would need to follow next steps:
- Build docker images (application, DB, liquibase):
	
	```
	docker build -t my_image:<version> .
	docker tag my_image <DOCKER_ID_USER>/my_image
	docker login
	docker push <DOCKER_ID_USER>/my_image:<version>
	```
- Create cluster on kubernetes
- Create Kubernetes objects
	
	Creating secret for DB:
		
	```
	kubectl create secret generic chat-db-secret \
		--from-literal=db.user=user \
		--from-literal=db.password=pass \
		--from-literal=db.root.password=root_pass;
	```
	
	Creating secret for Docker private registry:
	
	```
	kubectl create secret docker-registry docker-registry-secret \
		--docker-server="https://index.docker.io/v1/" \
		--docker-username=docker_username \
		--docker-email=docker.email@gmail.com \
		--docker-password=password;
	```
	
	Create DB deployment and service:
	
	```
		kubectl create -f chat-db.yml;
		kubectl create -f chat-db-service.yml;
	```
	
	Create Application deployment and service:
	```
		kubectl create -f chat.yml
		kubectl create -f chat-service.yml
	```
	
	Create liquibase Jobs to create and fill db with test data:
	```
		kubectl create -f liquibase-chat-db-job.yml
		kubectl create -f liquibase-chat-db-job-data.yml
	```
	
