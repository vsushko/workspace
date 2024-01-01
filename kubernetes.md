### Installation
https://kubernetes.io/docs/tasks/tools/install-kubectl/

install kubectl on macOS:
```
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/darwin/amd64/kubectl"
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/darwin/arm64/kubectl"
```
Make the kubectl binary executable.
`chmod +x ./kubectl`
Move the kubectl binary to a file location on your system `PATH`.
```
sudo mv ./kubectl /usr/local/bin/kubectl
sudo chown root: /usr/local/bin/kubectl
```
Test to ensure the version you installed is up-to-date:
`kubectl version --client`
Install Kubernetes Dashboard:
```
kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.4.0/aio/deploy/recommended.yaml
kubectl patch deployment kubernetes-dashboard -n kubernetes-dashboard --type 'json' -p '[{"op": "add", "path": "/spec/template/spec/containers/0/args/-", "value": "--enable-skip-login"}]'
kubectl proxy
```
Access the k8s dashboard:
```
http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/
```

install minikube:
`brew install minikube`
remove old minikube:
```
brew unlink minikube
brew link minikube
```
for troubleshooting:
`minikube delete`
and execute the command to install kubernetes into an existing docker install:
`minikube start --driver=docker`
To make docker the default driver:
```
minikube config set driver docker
```
print docker environment:
`minikube docker-env`
get minikube's status:
`minikube status`
shows what we have in cluster:
`kubectl get all`
create pod from file(first-pod.yaml):
```yaml
apiVersion: v1

kind: Pod

metadata:
  name: webapp
spec:
  containers:
  - name: webapp
    image: container-name:tag
```
run the pod: 
`run kubectl apply -f first-pod.yaml`
get information about pod:
`kubectl describe pod webapp`
list root of the container:
`kubectl exec webapp ls`
run container sh:
`kubectl -it exec webapp sh`
list all services:
`minikube service list`

Kubernetes dashboard:
```
https://stackoverflow.com/questions/46664104/how-to-sign-in-kubernetes-dashboard
```





#### ReplicaSet
A ReplicaSet’s purpose is to maintain a stable set of replica Pods running at any given time. As such, it is often used to guarantee the availability of a specified number of identical Pods.

Основной метод управления репликами Pod и их жизненным циклом. 
Обеспечивает необходимое количество запущенных реплик.

replicas: требуемое количество экземпляров Pod
selector: определяет все Pod-ы, управляемые этим ReplicaSet
#### Deployment
A *Deployment* provides declarative updates for Pods and ReplicaSets.

You describe a *desired state* in a Deployment, and the Deployment Controller changes the actual state to the desired state at a controlled rate. You can define Deployments to create new ReplicaSets, or to remove existing Deployments and adopt all their resources with new Deployments.

Основной контроллер для управления Pods
Управляет ReplicaSet
Предоставляют возможность управления обновлениями и функциональность rollback-a
* strategy: описывает метод обновления Pods на основе type
* recreate: все существующие Pods убиваются до запуска новых
* rollingUpdate: циклическое обновление Pods на основе maxSurge и maxUnavailable
* maxSurge: определяет количество дополнительных реплик
* maxUnavailable: количество возможно недоступных реплик
