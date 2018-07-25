# Java WS Chat
Application is built using Spring, Docker, Kubernetes, MySQL, Liquibase.
The app aims to show the way you can built and use WS in Spring, how to put it into Docker images and deploys it to Kubernetes.

## Getting Started

These instructions will help you start and deploy this app to Kubernetes.

### Installing steps

In order to run this app on Kubernetes, you would need to follow next steps:
- Build docker images (application, DB, liquibase)
	```
	docker build -t my_image:<version> .
	docker tag my_image <DOCKER_ID_USER>/my_image
	docker login
	docker push <DOCKER_ID_USER>/my_image:<version>
	```
- Create cluster on kubernetes
- Create Kubernetes objects
