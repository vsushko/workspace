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
```
kubectl version --client
kubectl version --output=yaml
```
List all the pods in all namespaces of a Kubernetes cluster:
```
kubectl get pod -A
```
Get pod:
```
kubectl get pod <POD_NAME>
```
Kubectl describe pod:
```
kubectl describe pod <POD_NAME>
```
List pods with labels labels:
```
kubectl get pod --show-labels
```
Get pod labels:
```
kubectl get pod --show-labels
```
Get pods with label:
```
kubectl get pod -l <LABE>=<LABEL_VALUE> (!= could be used as condition, separation with using comma)
```
creates deployment on cluster:
```
kubectl create deployment hello-node --image=k8s.gcr.io/echoserver:1.4
```
check deployment on cluster:
```
kubectl get deployment
```
expose deployment using serrvicee on cluster:
```
kubectl expose deployment hello-node --type=NodePort --port=8080
```
check service on cluster using kubectl:
```
kubectl get svc
```
test:
```
curl -v <CLUSTER-IP>:<PORT>
```

##### Kind
Kind Quick start:
```
https://kind.sigs.k8s.io/docs/user/quick-start/
```
Installation via brew (kind):
```
brew install kind
```
check kind version:
```
kind version
```
creates cluster:
```
kind create cluster --config conig.yaml
```
get nodes:
```
kubectl get nodes
```
go inside node:
```
docker exec -it 4ed2802b3308 bash
cd /etc/kubernetes/manifests/
ls -l
-rw------- 1 root root 2419 Jan  9 18:56 etcd.yaml
-rw------- 1 root root 3896 Jan  9 18:56 kube-apiserver.yaml
-rw------- 1 root root 3435 Jan  9 18:56 kube-controller-manager.yaml
-rw------- 1 root root 1463 Jan  9 18:56 kube-scheduler.yaml
```
delete cluster:
```
kind delete cluster --name dev-cluster
cat ~/.kube/config
apiVersion: v1
kind: Config
preferences: {}
```
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
create token for k8s dashboard:
```
kubectl -n kubernetes-dashboard create token admin-user
```
### Minikube
install minikube:
```
brew install minikube
```
remove old minikube:
```
brew unlink minikube
brew link minikube
```
for troubleshooting:
```
minikube delete
```
and execute the command to install kubernetes into an existing docker install:
```
minikube start --driver=docker
```
To make docker the default driver:
```
minikube config set driver docker
```
print docker environment:
```
minikube docker-env
```
get minikube's status:
```
minikube status
```
shows what we have in cluster:
```
kubectl get all
```
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
```
run kubectl apply -f first-pod.yaml
```
get information about pod:
```
kubectl describe pod webapp
```
list root of the container:
```
kubectl exec webapp ls
```
run container sh:
```
kubectl -it exec webapp sh
```
list all services:
```
minikube service list
```
Kubernetes dashboard sign in:
```
https://stackoverflow.com/questions/46664104/how-to-sign-in-kubernetes-dashboard
```
Kubernetess Cluster:
* master / control-plane (1 or more)
  * api-server
  * etcd
  * scheeduler
  * controllere-manager
* node / worker (1 or more)
  * container runtime
  * kubelet
  * kube-proxy
 
Kubectl - a CLI tool to talk to api-server
Kind - a tool to create kubernetes cluster for learning

### Pod
Pod is a collection of containers that can run on a host. This resource is created by clients and scheduled onto hosts.
```
https://kubernetes.io/docs/reference/kubernetes-api/workload-resources/pod-v1/
```
Workload is an application running on your k8s cluster
Pod is the basic building block to create workload
* Pod can run one or more containers
  * Only one of the containers is your app container
  * other containers a helpers
* Pod represents a VM and containers represeent the proces

Kubernetes Resource Yaml format:
```
apiVersion: [api version]
kind: [Kubernetes workload type]
metadata:
 [name for your resource, additional labels]
spec:
 [this will change depends on the workload type]
 [refer to the documentation]
```
Watch pods creation:
```
watch -t -x kubectl get pod
```
Creates Pod:
```
kubectl create -f pod.yaml
kubectl apply -f pod.yaml (for updating pod)
```
Delete resource:
```
kubectl delete -f pod.yaml
```
Describe pod:
```
kubectl describe pod
```
Pod Status:
Pending - node is yet to be assigned
Container Creating - Kubelet is working on creating container
Running - Kubelet started the container
ErrImagePull/ImagePullBackOff - Failed pulling image. Kubelet will retry with some delay
Completed - Container exited successfully
Error - Container exited with error
CrashLoopBackOff - There is a problem is running the container. Kubelet is retrying with delay (no issues in puulling the image)
Terminating - Pod is getting deleted
 
### ReplicaSet
A ReplicaSet’s purpose is to maintain a stable set of replica Pods running at any given time. As such, it is often used to guarantee the availability of a specified number of identical Pods.

Основной метод управления репликами Pod и их жизненным циклом. 
Обеспечивает необходимое количество запущенных реплик.

replicas: требуемое количество экземпляров Pod
selector: определяет все Pod-ы, управляемые этим ReplicaSet
### Deployment
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
