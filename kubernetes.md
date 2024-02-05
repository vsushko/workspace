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
List all namespaces:
```
kubectl get deploy -A
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
|docker|Kubernetes|
| --- | --- |
|ENTRYPOINT|command|
|CMD|args|

##### Kind
Kind Quick start:
```
https://kind.sigs.k8s.io/docs/user/quick-start/
```
Installation via brew (kind):
```
brew install kind
```
Check kind version:
```
kind version
```
creates cluster:
```
kind create cluster --config conig.yaml
```
kind get kubeconfig gives unclear error:
```
https://github.com/kubernetes-sigs/kind/issues/2205
```
### Nodes
Kubernetes runs your workload by placing containers into Pods to run on Nodes. 
A node may be a virtual or physical machine, depending on the cluster. 
Each node is managed by the control plane and contains the services necessary to run Pods.

Get nodes:
```
kubectl get nodes
```
Go inside node:
```
docker exec -it 4ed2802b3308 bash
cd /etc/kubernetes/manifests/
ls -l
-rw------- 1 root root 2419 Jan  9 18:56 etcd.yaml
-rw------- 1 root root 3896 Jan  9 18:56 kube-apiserver.yaml
-rw------- 1 root root 3435 Jan  9 18:56 kube-controller-manager.yaml
-rw------- 1 root root 1463 Jan  9 18:56 kube-scheduler.yaml
```
Delete cluster:
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

## Kubectl
Kubectl - a CLI tool to talk to api-server

Kubectl create:
```
kubectl create -f <file_name>
kubectl create -f .
kubectl create -f http://mywebkube.com/k8s.yaml
```
Kubectl apply (create/update idempotent):
```
kubectl create -f <file_name>
kubectl create -f .
kubectl create -f http://mywebkube.com/k8s.yaml
```

Kind - a tool to create kubernetes cluster for learning

## Pod
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
Delete resource:
```
kubectl delete -f pod.yaml
```
Describe pod:
```
kubectl describe pod
```
Pod Container Logs:
```
kubectl logs my-pod
```
Check the specific container log for a pod:
```
kubectl logs my-pod -c <MY_CONTAINER>
```

List all the pods in all namespaces of a Kubernetes cluster:
```
kubectl get pod -A
```
Get pod with specified namespace:
```
kubectl get pod -n kube-system 
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
Get more information about pod:
```
kubectl get pod -o wide
```
Get information in yml format:
```
kubectl get pod pod1 -o yaml
```
Port forwarding:
```
kubectl port-forward my-pod 8080:80
```
Delete pod:
```
kubectl delete pod pod1
```
Delete all pods:
```
kubectl delete pod --all
```
Change restart policy:
```
kubectl get pod -o yaml | grep restartPolicy
restartPolicy: Always
```
Exploring Pod Container:
```
kubectl exec -it my-pod bash (deprecated)
kubectl exec -it my-pod -- bash
kubectl exec -it my-pod -c my-container -- bash
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
- Manages Pod
- It ensures that our desired replicas for the given pod spec are running
- ReplicaSet -> restartPolicy:Always
```
https://kubernetes.io/docs/concepts/workloads/controllers/replicaset/
```
Get ReplicaSet:
```
kubectl get rs
```
Describe ReplicaSet:
```
kubectl describe rs/my-rs-1
```
ReplicaSet Match Expressions:
```
spec:
  selector:
    matchExpessions:
    - key: "team"
      operator: In
      values: [ "team-a", "team-b" ]
```

### Deployment
A *Deployment* provides declarative updates for Pods and ReplicaSets.

You describe a *desired state* in a Deployment, and the Deployment Controller changes the actual state to the desired state at a controlled rate. You can define Deployments to create new ReplicaSets, or to remove existing Deployments and adopt all their resources with new Deployments.

- Manages ReplicaSet
- 1 Deployment - 1 Microservice
- Deployment List<ReplicaSet> -> List<Pod>

Get deployment:
```
kubectl get deploy
```
Describe Deploy:
```
kubectl describe deploy
```
Get deploy logs:
```
kubectl logs deploy/my-deploy
```
Go to deploy's pod:
```
kubectl exec -it deploy/my-deploy -- bash
```
Port forwarding to 8080:
```
kubectl port-forward deploy/my-deploy 8080:80
```
Delete deploy:
```
kubectl delete deploy/my-deploy
```
Port forward of deployment:
```
kubectl port-forward deploy/order-service-deploy 8080:80
```
Get deployment rollout history:
```
kubectl rollout history deploy
```
Rolling out the deployment:
```
kubectl rollout undo deploy/order-service-deploy
```
Rolling out the deployment to proper version:
```
kubectl rollout undo deploy/order-service-deploy
```
Go to specific version:
```
kubectl rollout undo deploy/order-service-deploy --to-revision=1
```
Checking Rollout Changes:
```
kubectl rollout history deploy --revision=5
```
### Deployment Strategy

**reacreate** - terminate the old pods and create the new pods.

**rolling update** - gradually roll out the changes. We can have a mix of old and new pods temporarily.
- maxSurge - max number of additional pods that can be created
- maxUnavailable - max number of pods that can be terminated

## Service
- Logical abstractiion for a set of Pods
- A single reliable network endpoint to access Pods
  - stable IP address
  - DNS name

Describe service:
```
kubectl describe svc <service-name>
```
### Service / kube-proxy
- A simple proxy
- k8s resource Service does not consume CPU/memory
- Not round robin LB. It is random
- Do not expect URL rewriting / path based routing .. etc.. (It is Ingress in k8s)

### Service types
**ClusterIP** - for communication within the k8s cluster. Can not be accessed from outside the cluster (AWS/GCP cloud - private subnet communication).
This is the default option if we do not specify. Mostly this is what we use.

**NodePort** - Can be accessed from outsiide via k8s master/nodes via Specific port (can be used for testing)
- Allowed node port ranges are 30000-32767
- Each node is set to listen on specific port
- any request to one of the nodes on the port is forwarded to the pod

**LoadBalancer** - To be used AWS/ GCP / Azure .. cloud providers. Can be used to receive traffic from outside.
- To be used in the cloud provider
- To receive external traffiic and foward to pods

Send curl request 1000 times and grep string:
```sh
for i in {1..1000}; do curl -s http://my-app | grep -o "<title>[^<]*" | tail -c+8; done
```

## Namespace
Virtual cluster / Partitioning within a cluster
- Isolating a group of resources within a cluster

Use Cases
- Dev / QA environments
- Isolating separate team resources

Get namespaces:
```
kubectl get namespace
# or
kubectl get ns
```
Create namespace:
```
kubectl create ns dev
```
Get running pod in specific namespace:
```
kubectl get pod -n dev
```
Delete namespace:
```
kubectl delete ns dev
```
Create services with specifying namespace:
```
kubectl apply -f my-file.yaml -n my-namespace
```
Get all within namespace:
```
kubectl get all -n qa
```
Port forwarding with ns:
```
kubectl port-forward svc/my-app 8080:80 -n qa
```
Specify namespece via metadata:
```
metadata
  namespace: my-ns
```

## Probes
Problem:
- Pods are considered to be live & ready as soon as the containers are started
- if the Pod is ready,
  - the Service will send the requests to the pod
  - rolling update will terminate old pods
- We should ensuree that our Pods are live and ready to avoid surprises!

Probes are tools/utilitiies to measure the health of the pod.
- Has it started?
- Is it alive?
- Is it ready to serve requests?

Terms:
- Live -> Is the Pod alive?
- Ready -> can the Pod serve the request?

#### Probe Types:
|Probes|Description|Action If Fails|
|---|---|---|
|startupProbe|To check if the application inside the container has started|restart|
|livenessProbe|To check if the application is still alive|restart|
|readinessProbe|To check if the application is ready to take the requests from service|remove from service|

|Probes|Phase|
|---|---|
|startupProbe|It starts as soon as container started<br/>If the check passes, startupProbe stops|
|livenessProbe|It starts once startupProbe completes<br/>It is executed throught the pod lifecycle|
|readinessProbe|It starts once startupProbe completes<br/>It is executed throught the pod lifecycle|

|Options|Description|
|---|---|
|exec|Execute any command to check. for ex: cat /tmp/app.log|
|httpGet|To invoke a http endpoint. for ex: /health|
|tcpSocket|To check if the app started listening on specific port|

#### Probe Properties:
|Options|Description|
|---|---|
|initialDelaySeconds|0|
|perioSeconds|10|
|timeoutSeconds|1|
|successThreshold|1|
|failureThreshold|3|

## ConfigMap & Secret
To keep the configuration data separately from the application
- ConfigMap
- Secret

ConfigMap -> non-sensitive data
- application.properties
Secret -> sensitive data
- credentials

### ConfigMap
- Properites as Key/Value
- Properties as file
- Store any binary file
- Max size 1MB

Get ConfigMap:
```
kubectl get cm
```
Describe ConfigMap:
```
kubectl describe cm <cm-name>
```
Get kube-root-ca.crt:
```
kubectl get cm kube-root-ca.crt -o yaml
```

### Secret
- Same as ConfigMap - but for sensitive data
- Value is base64 encoded
- Use Cases:
  - ssh key files
  - basic credentials
  - servicee accounts

Get secret:
```
kubectl get secret
```
Create secret via command line:
```
kubectl create secret generic my-secret --from-literal=username=vsushko --from
-literal=password=admin
```
Get secret:
```
kubectl get secret -o yaml
```
Base64 string:
```
echo vsushko | base64
```
Mount filed to docker container:
```
docker run -it -v $PWD:/ws ubuntu
cd ws
cat 01-simple-configuration.yaml | base64
```

## Persistent Volume, Claim & Stateful Set

#### Cluster
Workload requires
- Compute instances
- Storage
  - Life cycle of storage should be separated from Pod life cycle

#### Persistent Volumes
- Storage abstraction / Volume plugins
- Provides storage - Similar to node in the cluster which provides CPU/Memory

|Term|Description|Example|
|---|---|---|
|Storage class|Type of storage|AWS EBS SSD - super fast<br/>AWS EBS disk based - slow <br/>GCP-PD standard<br/>GCP-PD ssd<br/>GCP-PD extreme|
|Persistent Volume Claim|Request to create PV. Resource which links PV and Pod|Request to create 5GB of GCP PD sdd for the application|
|Persistent Volume|Actual storage created for a specific storage class|5GB of GCP PD ssd<br/>100GB of GCP PD example|

A **StorageClass** provides a way for administrators to describe the classes of storage they offer.

List all Storage Classes (SCs):
```
kubectl get sc
```

#### Access Modes
- ReadWriteOnce (per node)
- ReadWriteOncePod (per pod)
- ReadOnlyMany
- ReadWriteMany

List all Persistent Volumes (PVs):
```
kubectl get pv
```
Describe Persistent Volumes (PVs):
```
kubectl describe pv
```
Describe PersistentVolumeClaim (PVC):
```
kubectl describe pvc
```
Delete Persistent Volumes (PVs):
```
kubectl delete -f <file.yaml>
# pv will be deleted automatically
```
Delete all PersistentVolumeClaim (PVCs):
```
kubectl delete pvc --all
```
#### StatefulSet
- Same as Deployment - but for a Stateful workload
- Each pod will have unique / stable hostname
- StatefulSet is NOT just for databases. Instead, it is for any workload which wants sticky identity
- Does not have any ReplicaSet

#### Headless Service
- Service will not have any IP & Kube-proxy does NOT do any load balancing
- DNS entries would be created for <pod-name>.<svc-name>

## Auto Scaling

#### Cluster
Resources
- CPU
- Memory
Range
- min -> request
- max -> limit

#### Resource Units
| Resource | Units |
| --- | --- |
| Memory | 1M, 50M, 1G, 1Mi, 50Mi, 1Gi |
| CPU | 1, 100m, 500m |

#### Consequences of Exceeding Limit
| Resource | Action |
| --- | --- |
| Memory | Kubelet will kill the container and restart |
| CPU | Container will NOT be killed. Throttled |

Display CPU and memory usage:
```
kubectl top
```
Install Metrics Server:
```
kubectl apply -f resources/metrics-server.yaml
```
List all Horizontal Pod Autoscalers (HPAs):
```
kubectl get hpa
```
Test nginx:
```
ab -n 20000 -c -5 http://nginx/
```

## Ingress
An API object that manages external access to the services in a cluster, typically HTTP and HTTPS. It may provide load balancing, SSL, teermination and name-based virtual hosting.

- Smart Router/Proxy to bring traffic into the cluster
- Contains a set of routing rules
- We need Ingress Controller to manage Ingress

#### Key concepts:
#### **Ingress Controller**
- Manages Ingress resources (like Deployment Controller)
- Implements the Ingress Rules
- There aree multiple implementations (AWS, GCP, ...)
- Routes traffic to the services withint the namespace
 
#### **Ingress Resource**
Contains rules to route external HTTP(S) traffic to internal services.

#### **Path-Based Routing**
Different paths can be routed to different services

#### **Host-Based Routing**
Different subdomains can be routed to different services

Nginx Ingress Controller:
```
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/kind/deploy.yaml
```
Check ingress on cluster:
```
kubectl get ns
```
List all Ingress resources:
```
kubectl get ing
```
Check ingress on cluster with specifying namespace:
```
kubectl get ns -n <my-namespace>
```

#### Host
|OS|Host File Path|
|---|---|
|Mac/Linux|/etc/hots|
|Windows|c:\Winows\System32\Drivers\etc\hosts|

