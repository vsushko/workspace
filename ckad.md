
Shortcuts / shortnames
Kubernetes defines shortnames for most resources:
* po → pods
* deploy → deployments
* svc → services
* ns → namespaces
* cm → configmaps
* sa → serviceaccounts
* ing → ingresses
* ds → daemonsets
* sts → statefulsets

k run —help

k get ns --show-labels -n <namespace>

k label nodes bryan-node-1 environment=production

k run hello-minikube

k cluster-info

k get nodes

k run nginx —image nginx

k get pods

k get deploy app -o yaml > app.yaml

ingress pvcs, jobs, network policies are important

vim, man, wget, curl  

https://killercoda.com/

echo 'alias kn="kubectl config set-context --current --namespace"' >> ~/.bashrc

c/pricing

k get pods
k describe pod mypod-app

k create … –dry-run=client -o yaml

k explain

environment variables
grep, awk, sed
searching logs
filtering output
directory navigation

Generators:
k create ... --dry-run=client -o yaml

Pod

k explain pod
k explain pod.spec.containers

create pod from existing one:
k get pod webapp-color -o yaml > pod.yaml

create pod with label:
k run nginx --image=nginx -l tier=msg

delete all pods with label:
delete pod -l name=busybox-pod

Create an NGINX Pod
k run nginx --image=nginx

Generate POD Manifest YAML file (-o yaml). Don't create it(--dry-run)
k run nginx --image=nginx --dry-run=client -o yaml

k run webapp-color --image=kodekloud/webapp-color --dry-run=client -o yaml > webapp-color.yaml

with label:
k run redis --image=redis:alpine --labels='tier=db'

k run mypod \
  --image=nginx \
  --dry-run=client -o yaml > pod.yaml

k run mypod --image=nginx \
  --dry-run=client -o yaml > mypod.yaml

k run pod redis --image=redis --dry-run=client -n finance -o yaml > pod.yaml

With env variables:

k run api \
  --image=myapi:1.0 \
  --env=MODE=prod \
  --env=LOG=true \
  --dry-run=client -o yaml > api-pod.yaml

With resource limits:

k run busy \
  --image=busybox \
  --requests=cpu=100m,memory=100Mi \
  --limits=cpu=200m,memory=200Mi \
  --dry-run=client -o yaml > busy.yaml

With commands or args:

k run toolbox \
  --image=busybox \
  --command -- sleep 3600 \
  --dry-run=client -o yaml > toolbox.yaml

Deployment

Create a deployment
k create deployment nginx --image=nginx nginx

Generate Deployment YAML file (-o yaml). Don't create it(--dry-run)
k create deployment --image=nginx nginx --dry-run -o yaml

Generate Deployment with 4 Replicas
k create deployment nginx --image=nginx --replicas=4

You can also scale deployment using the k scale command.
k scale deployment nginx --replicas=4

k create deploy webapp --image=kodekloud/webapp-color --replicas=3

or

k create deployment nginx --image=nginx --dry-run=client -o yaml > nginx-deployment.yaml

k create deployment web  --image=nginx -replicas=3 --dry-run=client -o yaml > web-deploy.yaml

k create deployment web --image=nginx --dry-run=client -o yaml | k apply -f -

Service

Create a Service named redis-service of type ClusterIP to expose pod redis on port 6379
k expose pod redis --port=6379 --name redis-service --dry-run=client -o yaml

This will not use the pods' labels as selectors
k create service clusterip redis --tcp=6379:6379 --dry-run=client -o yaml

Service (ClusterIP):
k expose deployment web \
  --port=80 --target-port=80 \
  --dry-run=client -o yaml > web-svc.yaml

Service (NodePort):
k expose deployment web \
  --port=80 --target-port=80 \
  --type=NodePort \
  --dry-run=client -o yaml > web-svc-nodeport.yaml

This will not use the pods' labels as selectors
k create service nodeport nginx --tcp=80:80 --node-port=30080 --dry-run=client -o yaml

Create a Service named nginx of type NodePort to expose pod nginx's port 80 on port 30080 on the nodes:
k expose pod nginx --port=80 --name nginx-service --type=NodePort --dry-run=client -o yaml

Expose Pod
k run httpd --image=httpd:alpine --restart=Never

k expose pod httpd --port=80 --target-port=80 --type=ClusterIP

OR

k run httpd --image=httpd:alpine --port=80 --expose=true

Environment Variables

env:
  - name: APP_COLOR
     value: pink

env:
  - name: APP_COLOR
     valueFrom:
       configMapKeyRef: 

env:
  - name: APP_COLOR
     valueFrom:
       secretKeyRef:

ConfigMap

k describe cm

k create configmap app-config \
  --from-literal=ENV=prod \
  --from-literal=FEATURE_X=true \
  --dry-run=client -o yaml > cm.yaml

k create configmap myconfigmap \
  --from-literral=<key>=<value>

k create configmap webapp-config-map --from-literal=AP
P_COLOR=darkblue --from-literal=APP_OTHER=disregard

apiVersion: v1
kind: Pod
metadata:
  labels:
    name: webapp-color
  name: webapp-color
spec:
  containers:
  - image: kodekloud/webapp-color
    name: webapp-color
    env: 
    - name: APP_COLOR
      valueFrom:
        configMapKeyRef:
          name: webapp-config-map
          key: APP_COLOR
    resources: {}
  dnsPolicy: ClusterFirst
  restartPolicy: Always
status: {}

apiVersion: v1
data:
   APP_COLOR: darkblue
   APP_OTHER: disregard
kind: ConfigMap
metadata:
  name: webapp-config-map

Add to Pod

ENV 

env:
  - name: APP_COLOR
     valueFrom:
       configMapKeyRef: 
         name: app-config
         key: APP_COLOR

Single ENV

envFrom:
  - configMapKeyRef: 
       name: app-config

Volume

volumes:
- name: app-config-volumeconfigMap:  name: app-config

Secret

k create secret generic db-secret \
  --from-literal=USER=admin \
  --from-literal=PASSWORD=pass123 \
  --dry-run=client -o yaml > secret.yaml

k create secret generic --help

echo -n "mysql" | base64
echo -n "bXlzcWw=" | base64 --decode

k create secret generic db-secret --from-literal=DB_Host=sql01 --from-literal=DB_User=root --from-literal=DB_Password=password123

k get secrets

k get secret app-secret -o yaml

apiVersion: v1
kind: Pod
metadata:
  labels:
    name: webapp-pod
  name: webapp-pod
spec:
  containers:
  - image: kodekloud/simple-webapp-mysql
    name: webapp-pod
    envFrom:
      - secretRef:
          name: db-secret
    resources: {}
  dnsPolicy: ClusterFirst
  restartPolicy: Always
status: {}

apiVersion: v1
data:
  DB_Host: c3FsMDE=
  DB_Password: cGFzc3dvcmQxMjM=
  DB_User: cm9vdA==
kind: Secret
metadata:
  creationTimestamp: "2025-11-24T20:37:16Z"
  name: db-secret
  namespace: default
  resourceVersion: "1103"
  uid: 033cf466-aaaa-4506-8ee4-0bdfdd25990e
type: Opaque

Single ENV

env:
  - name: DB_PASSWORD
     valueFrom:
       secretKeyRef: 
         name: app-secret
         key: DB_PASSWORD

ENV

envFrom:
  - SecretRef: 
       name: app-secret

Volume

volumes:
- name: app-secret-volumesecret:  secretName: app-secret
Inside container
ls /opt/app-secret-volumes
cat /opt/app-secret-volumes/DB_PASSWORD

Namespace
k create ns demo --dry-run=client -o yaml > ns-demo.yaml

A few flags you should use everywhere:
* -n / --namespace — pick namespace quickly:k get po -n kube-system
* -o wide — more info (node, IP, etc.):k get po -o wide
* -o yaml — show full manifest:k get deploy web -o yaml
* -A — all namespaces:k get po -A
* --field-selector / -l — select by field/label:k get po -l app=web

ResourceQuota

k create quota my-quota \
  --hard=cpu=2,memory=4Gi \
  --dry-run=client -o yaml > my-quota.yaml

Pod

in Pod

command overrides entrypoint

args overrides cmd

simple Pod example with args

apiVersion: v1
kind: Pod
metadata:
  name: arg-pod
spec:
  containers:
    - name: demo
      image: busybox
      command: ["sh", "-c"]
      args: ["echo Hello from args && sleep 3600"]

you CANNOT edit specifications of an existing POD other than the below.
* spec.containers[*].image
* spec.initContainers[*].image
* spec.activeDeadlineSeconds
* spec.tolerations

Edit Pod (option 1)

k delete pod webapp
k create -f /tmp/kubectl-edit-ccvrq.yaml

Edit Pod (option 2)

k get pod webapp -o yaml > my-new-pod.yaml

vi my-new-pod.yaml

k delete pod webapp

k create -f my-new-pod.yaml

Create an NGINX Pod
k run nginx --image=nginx

k run custom-nginx --image=nginx --port=8080

Check the nodes info
k get pods -o wide

k run nginx —image=nginx 

what images are used in the new pod:
k get pod webapp -o jsonpath='{.spec.containers[*].name}'

state of the container agentx in the pod webapp
k get pod webapp -o jsonpath='{.status.containerStatuses[?(@.name=="agentx")].state}'

set image to the pod:
k set image pod/redis redis=redis

If you are not given a pod definition file, you may extract the definition to a file using the below command:
k get pod <pod-name> -o yaml > pod-definition.yaml

To modify the properties of the pod, you can utilize the k edit pod <pod-name> command. Please note that only the properties listed below are editable.
    * spec.containers[*].image
    * spec.initContainers[*].image
    * spec.activeDeadlineSeconds
    * spec.tolerations
    * spec.terminationGracePeriodSeconds

Pod with Resource Requests and Limits:

Resource and Limits:

apiVersion: v1
kind: Pod
metadata:
  name: frontend
spec:
  containers:
  - name: app
    image: images.my-company.example/app:v4
    resources:
      requests:
        memory: "64Mi"
        cpu: "250m"
      limits:
        memory: "128Mi"
        cpu: "500m"

ReplicaSet

k create deployment rs-temp \
  --image=nginx \
  --dry-run=client -o yaml > rs.yaml

1. kind: Deployment -> ReplicaSet
2. Remove selector mismatch or add it if needed
3. Remove fields not vavlid for ReplicaSet (strategy, etc.)

scale command:
k scale --replicas=6 -f replicaset-definition.yml

k scale --replicas=6 replicaset myapp-replicaset

k get replicaset

k delete replicaset myapp-replicaset
k replace -f replicaset-definition.yml

k scale --replicas=6 -f replicaset=definition.yml

k explain replicaset

Deployments

Edit deployment:k edit deployment my-deployment

k create deployment web \
  --image=nginx \
  --replicas=3 \
  --dry-run=client -o yaml > web-deploy.yaml

k get deploy

k describe deploy frontend-deployment

k create deploy httpd-frontend --image=httpd:2.4-alpine --replicas=3 --dry-run=client -o yaml > httpd-frontend.yaml

k create deploy redis-deploy -n dev-ns --image=redis --replicas=2

k create deployment --help

Namespaces
k create namespace test-123 --dry-run -o yaml

apiVersion: v1kind: Namespace
metadata:
  creationTimestamp: null
  name: test-123
spec: {}
status: {}

db-service.dev.svc.cluster.local

k get pods --namespace=kube-system

k -f pod-definition.yml --namespace=dev

k create ns demo --dry-run=client -o yaml > ns-demo.yaml

k create namespace dev

set namespace as default namespace:
k config set-context $(k config current-context) --namespace=dev

k get pods --all-namespaces
k get pods -A

Services

k get svc

k edit svc kubernetes

endpoints info:
k get endpoints kubernetes

k describe endpoints kubernetes

expose nodePort service deployment:
k expose deploy simple-webapp-deployment \
--port=8080 \
--target-port=8080 \
--type=NodePort \
-- selector="simple-webapp"
--dry-run=client -o yaml > service-definition-1.yaml

apiVersion: v1
kind: Service
metadata:
  name: webapp-service
spec:
  ports:
  - port: 8080
     protocol: TCP
     targetPort: 8080
     nodePort: 30080
  selector:
    name: simple-webapp
  type: NodePort
status:
  loadBalancer: {}

k explain command

k api-resources

k explain deployments
k explain deployments.spec

k explain pods
k explain pods.spec

k explain pods --recursive

EXAM TIPS:
k config use-context k8s

k config view

vim:
:set paste

select and indent

k get events -n dev

use kubernetes.io

Docker

1. command overrides entrypoint

2. args overrides cmd

docker build -t image_name .

-p <host_port>:<container_port>
docker run -p 8282:8080 image_name

docker inspect image_name

get os:
docker run --rm python:3.6 cat /etc/os-release

run --rm python:3.6 cat /etc/os-release

approximate size
docker image image_name

Simple Dockerfile with args

FROM python:3.6-alpine

RUN pip install flask

COPY . /opt/

EXPOSE 8080

WORKDIR /opt

ENTRYPOINT ["python", "app.py"]

CMD ["--color", "red"]

apiVersion: v1
kind: Pod
metadata:
  name: webapp-green
spec:
  containers:
    - image: kodekloud/webapp-color
      args: ["--color", "green"]
      command: ["python", "app.py"]
      name: webapp-green
  dnsPolicy: ClusterFirst
  restartPolicy: Always

k run webapp-green —image=kodekloud/webapp-color -- --color green

# Start a busybox pod and keep it in the foreground, don't restart it if it exits
k run -i -t busybox --image=busybox --restart=Never
  
# Start the nginx pod using the default command, but use custom arguments
(arg1 .. argN) for that command
k run nginx --image=nginx -- <arg1> <arg2> ... <argN>
  
# Start the nginx pod using a different command and custom arguments
k run nginx --image=nginx --command -- <cmd> <arg1> ... <argN>

deletes the existing resource and immediately recreates it from the provided YAML file
k replace --force -f /tmp/kubectl-edit-123456.yaml

Encrypting Secret Data at Rest

k create secret generic my-secret --from-literal=key1=value

install etcdctl

k get pods -n kube-system

ETCDCTL_API=3 etcdctl \
   --cacert=/etc/kubernetes/pki/etcd/ca.crt   \
   --cert=/etc/kubernetes/pki/etcd/server.crt \
   --key=/etc/kubernetes/pki/etcd/server.key  \
   get /registry/secrets/default/my-secret 

ETCDCTL_API=3 etcdctl \
   --cacert=/etc/kubernetes/pki/etcd/ca.crt   \
   --cert=/etc/kubernetes/pki/etcd/server.crt \
   --key=/etc/kubernetes/pki/etcd/server.key  \
   get /registry/secrets/default/my-secret | hexdump -C

ps -aux | grep kube-api | grep "encryption-provider-config"

check manifests:

ls /etc/kubernetes/manifests/

--encryption-provider-config should be present:
cat /etc/kubernetes/manifests/kube-apiserver.yaml

Add configuration:

Generate a 32-byte random key and base64 encode it. You can use this command:
head -c 32 /dev/urandom | base64

apiVersion: apiserver.config.k8s.io/v1
kind: EncryptionConfiguration
resources:
  - resources:
      - secrets
      - configmaps
      - pandas.awesome.bears.example
    providers:
      - aescbc:
          keys:
            - name: key1
              # See the following text for more details about the secret value
              secret: <BASE 64 ENCODED SECRET>
      - identity: {} # this fallback allows reading unencrypted secrets;
                     # for example, during initial migration

1. Save the new encryption config file to /etc/kubernetes/enc/enc.yaml on the control-plane node.
2. Edit the manifest for the kube-apiserver static pod: /etc/kubernetes/manifests/kube-apiserver.yaml so that it is similar to:

#
# This is a fragment of a manifest for a static Pod.
# Check whether this is correct for your cluster and for your API server.
#
apiVersion: v1
kind: Pod
metadata:
  annotations:
    kubeadm.kubernetes.io/kube-apiserver.advertise-address.endpoint: 10.20.30.40:443
  creationTimestamp: null
  labels:
    app.kubernetes.io/component: kube-apiserver
    tier: control-plane
  name: kube-apiserver
  namespace: kube-system
spec:
  containers:
  - command:
    - kube-apiserver
    ...
    - --encryption-provider-config=/etc/kubernetes/enc/enc.yaml  # add this line
    volumeMounts:
    ...
    - name: enc                           # add this line
      mountPath: /etc/kubernetes/enc      # add this line
      readOnly: true                      # add this line
    ...
  volumes:
  ...
  - name: enc                             # add this line
    hostPath:                             # add this line
      path: /etc/kubernetes/enc           # add this line
      type: DirectoryOrCreate             # add this line
  ...

crictrl pods

ps aux | grep kube-api | grep “--encryption-provider-config”

k create secret generic my-secret --from-literal=key1=value

Docker Security & Kubernetes Security

docker run --user=1001 ubuntu sleep 3600

docker run --cap-drop KILL

docker run --priviledges 

The setting on the container will override the setting on the pod

under spec in pod:

apiVersion: v1
kind: Pod
metadata:
  labels:
    name: webapp-color
  name: webapp-color
spec:
  securityContext:
    runAsUser: 1000
  containers:
  - image: kodekloud/webapp-color
    name: webapp-color

on container level:

apiVersion: v1
kind: Pod
metadata:
  labels:
    name: webapp-color
  name: webapp-color
spec:
  securityContext:
    runAsUser: 1000
  containers:
  - image: kodekloud/webapp-color
    name: webapp-color
    securityContext:
      runAsUser: 1000
      capabilities:
        add:  [“MAC_ADMIN”]

k delete pod ubunty-sleeperr --force

Service Accounts

k create serviceaccount dashboard-sa

k create token dashboard-sa (should have expiry time)

Old way (use annotations):

In Secret:

metadata:
  annotations:
    kubernetes.io/service-account.name: dashboard-sa

k get sa

k describe serviceaccount dashboard-sa

k describe secret dashboard-sa-token-kbbdm

curl https://192.168.56.70:6443/api -insecure -- "Authorization: Bearer <TOKEN>"

k exec -it my-kubernetes-dashboard -- ls /var/run/secrets/kubernetes.io/serviceaccount

apiVersion: v1
kind: Pod
metadata:
  name: my-pod
spec:
  serviceAccountName: build-robot
  automountServiceAccountToken: false <— not to apply service account token 

Bound Service Account Tokens

TokenRequest API (Audience bound, Time Bound, Object Bound)

set sa:

k set serviceaccount deploy/web-dashboard dashboard-sa

Taints - Node

k taint --help

k taint nodes node-name key=value:taint-effect

taint effects:
- noSchedule
- PreferNoSchedule
- NoExecute

k taint nodes node1 app=myapp:NoSchedule

k taint nodes node01 spray=mortein:NoSchedule

k taint nodes node01 app_type=alpha:NoSchedule

Tolerations

k get nodes

k taint nodes node1 app=blue:noSchedule

apiVersion: v1
kind: Pod
metadata:
  name: nginx
  labels:
    env: test
spec:
  containers:
  - name: nginx
    image: nginx
    imagePullPolicy: IfNotPresent
  tolerations:
  - key: "app"
    operator: “Equal”
    value: “blue”
    effect: "NoSchedule"

see master taint:

k describe node node01 | grep Taint

check that pod is scheduled on node:k get pod bee -o wide

inspect pod’s tolerations:k describe pod bee | grep -A5 Tolerations

To remove the taint:
k taint nodes node1 key1=value1:NoSchedule-

k taint nodes controlplane node-role.kubernetes.io/control-plane:NoSchedule-

Node Selectors

apiVersion: v1
kind: Pod
metadata:
  name: mypod
spec:
  nodeSelector:
    size: Large
  containers:
    - name: nginx
      image: nginx

k label nodes <node-name> label-key=label-value
k label nodes node01 color=blue

k label nodes node-1 size=Large

Node Affinity

to check the existing labels:
k describe node node01

apiVersion: v1
kind: Pod
metadata:
  name: with-node-affinity
spec:
  affinity:
    nodeAffinity:
      requiredDuringSchedulingIgnoredDuringExecution:
        nodeSelectorTerms:
        - matchExpressions:
          - key: size
            operator: In (Exists | NotIn)
            values:
            - Large
            - Medium

Init containers

apiVersion: v1
kind: Pod
metadata:
  name: yellow
spec:
  containers:
  - image: redis
    name: gold
  - image: busybox
    name: lemon
    command: ["sleep", "1000"]
  dnsPolicy: ClusterFirst
  restartPolicy: Always
status: {}
~                 

exec into containers

k exec -it app -n elastic-stack  -- cat /log/app.log
________________________________________________________
Pod with init container

apiVersion: v1
kind: Pod
metadata:
  labels:
    name: app
  name: app
  namespace: elastic-stack
spec:
  initContainers:
  - name: sidecar
    image: kodekloud/filebeat-configured
    restartPolicy: Always
    volumeMounts:
      - name: log-volume
        mountPath: /var/log/event-simulator

  containers:
  - image: kodekloud/event-simulator
    name: app
    resources: {}
    volumeMounts:
    - mountPath: /log
      name: log-volume

  volumes:
  - hostPath:
      path: /var/log/webapp
      type: DirectoryOrCreate
    name: log-volume

https://kubernetes.io/docs/concepts/workloads/pods/init-containers/

An initContainer is configured in a pod like all other containers, except that it is specified inside a initContainers section, like this:

apiVersion: v1
kind: Pod
metadata:
  name: myapp-pod
  labels:
    app: myapp
spec:
  containers:
  - name: myapp-container
    image: busybox:1.28
    command: ['sh', '-c', 'echo The app is running! && sleep 3600']
  initContainers:
  - name: init-myservice
    image: busybox
    command: ['sh', '-c', 'git clone <some-repository-that-will-be-used-by-application> ;']

You can configure multiple such initContainers as well, like how we did for multi-pod containers. In that case each init container is run one at a time in sequential order.
If any of the initContainers fail to complete, Kubernetes restarts the Pod repeatedly until the Init Container succeeds.

apiVersion: v1
kind: Pod
metadata:
  name: myapp-pod
  labels:
    app: myapp
spec:
  containers:
  - name: myapp-container
    image: busybox:1.28
    command: ['sh', '-c', 'echo The app is running! && sleep 3600']
  initContainers:
  - name: init-myservice
    image: busybox:1.28
    command: ['sh', '-c', 'until nslookup myservice; do echo waiting for myservice; sleep 2; done;']
  - name: init-mydb
    image: busybox:1.28
    command: ['sh', '-c', 'until nslookup mydb; do echo waiting for mydb; sleep 2; done;']

init container example:---
apiVersion: v1
kind: Pod
metadata:
  name: red
  namespace: default
spec:
  containers:
  - command:
    - sh
    - -c
    - echo The app is running! && sleep 3600
    image: busybox:1.28
    name: red-container
  initContainers:
  - image: busybox
    name: red-initcontainer
    command: 
      - "sleep"
      - "20"

Rediness Probe

readinessProbe:
  httpGet:
    path: /api/ready
    port: 8080
  initialDelaySeconds
  periodSeconds: 5
  failureThreshold: 8

readinessProbe:
  tcpSocket:
    port: 3306

readinessProbe:
  exec: command:
   - cat
   - /app/is_ready

Liveness Probes

livenessProbe:
  httpGet:
    path: /api/ready
    port: 8080
  initialDelaySeconds
  periodSeconds: 5
  failureThreshold: 8

livenessProbe:
  tcpSocket:
    port: 3306

livenessProbe:
  exec: command:
   - cat
   - /app/is_ready

for i in {1..20}; do
   k exec --namespace=kube-public curl -- sh -c 'test=`wget -qO- -T 2  http://webapp-service.default.svc.cluster.local:8080/ready 2>&1` && echo "$test OK" || echo "Failed"';
   echo ""
done

k replace -f simple-webapp-1.yaml --force

Logging

show logs for the container:

k logs -f event-simulator-pod <container-name>

Monitor and Debug Solution

minikube addons eenable metrics-server

k apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml

k create -f deploy/1.8+/

k top node

k top pod

Labels and Selectors

k get pods --selector app=App1

k get pods --selector env=dev | wc -l

k get pods --selector env=dev | --no-headers

k get all --selector env=prod,bu=finance,tier=frontend 

Rolling Updates & Rollbacks in Deployments

k rollout status deployment/myapp-deployment

history:
k rollout history deployment/myapp-deployment

k set image deployment/myapp-deployment \
  nginx=nginx:1.9.1

k rollout undo deployment myapp-deployment

k create deployment nginx --image=nginx:1.16
k rollout status deployment nginx
k rollout history deployment nginx

k rollout history deployment nginx --revision=1

k set image deployment nginx nginx=nginx:1.17 --record
k rollout history deployment nginx
k set image deployment nginx nginx=nginx:1.17 --record=true

k set image deploy frontend simple-webapp=kodecloud/webapp-color:v3

k edit deployments. nginx --record
k rollout history deployment nginx
k rollout history deployment nginx --revision=3

k rollout history deployment nginx
k rollout history deployment nginx --revision=3
k describe deployments. nginx | grep -i image:

k rollout history deployment nginx --revision=1
k rollout undo deployment nginx --to-revision=1
k describe deployments. nginx | grep -i image:

k edit deployment frontend -> change deployment


Rolling update strategy

spec:
  strategy;
  rollingUpdate:
    maxSurge: 25%
    maxUnavailable: 25%
  type: RollingUpdate

Blue green: deploy v1 and v2, once v2 is deployed - change label to v2 in service

k scale deployment --replicas=1 frontend-v2

Jobs

k get jobs

k get cronjob

Simple Job:

apiVersion: batch/v1
kind: Job
metadata:
  name: throw-dice-job
spec:
  completions: 2
  backoffLimit: 15 # This is so the job does not quit before it succeeds.
  template:
    spec:
      containers:
      - name: throw-dice
        image: kodekloud/throw-dice
      restartPolicy: Never

create a job from pod:
k create job throw-dice-job --image=kodekloud/throw-dice --dry-run=client -o yaml > throw-dice-job.yaml		

apiVersion: batch/v1
kind: Job
metadata:
  name: throw-dice-job
spec:
  completions: 3
  parallelism: 3
  backoffLimit: 25 # This is so the job does not quit before it succeeds.
  template:
    spec:
      containers:
      - name: throw-dice
        image: kodekloud/throw-dice
      restartPolicy: Never

With cron:

apiVersion: batch/v1
kind: CronJob
metadata:
  name: throw-dice-cron-job
spec:
  schedule: "30 21 * * *"
  jobTemplate:
    spec:
      completions: 3
      backoffLimit: 15
      parallelism: 3
      template:
        metadata: {}
        spec:
          containers:
          - image: kodekloud/throw-dice
            name: throw-dice
            resources: {}
          restartPolicy: Never
status: {}
~            

Network Policies

Pod
labels:
  role: db

Network
podSelector:
  matchLabels:
    role: db

policyTypes:
- Ingress
ingress
- from:
  - podSelector:
       matchLabels:
         role: db
  ports:
  - protocol: TCP
     port: 3306

apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: test-network-policy
  namespace: default
spec:
  podSelector:
    matchLabels:
      role: db
  policyTypes:
  - Ingress
  - Egress
  ingress:
  - from:
    - ipBlock:
        cidr: 172.17.0.0/16
        except:
        - 172.17.1.0/24
    - namespaceSelector:
        matchLabels:
          project: myproject
    - podSelector:
        matchLabels:
          role: frontend
    ports:
    - protocol: TCP
      port: 6379
  egress:
  - to:
    - ipBlock:
        cidr: 10.0.0.0/24
    ports:
    - protocol: TCP
      port: 5978

k get netpol

apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: test-network-policy
  namespace: default
spec:
  podSelector:
    matchLabels:
      role: db
  policyTypes:
  - Ingress
  - Egress
  ingress:
    - {}
  egress:
  - to:
    - podSelector:
        matchLabels:
          name: mysql
    ports:
    - protocol: TCP
      port: 3306
  - to:
    - podSelector:
        matchLabels:
          name: payroll
    ports:
    - protocol: TCP
      port: 8080
  - ports:
    - port: 53
      protocol: UDP
    - port: 53
      protocol: TCP

how to check generator existence:
k create --help | grep -i networkpolicy
 

apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: internal-policy
  namespace: default
spec:
  podSelector:
    matchLabels:
      role: db
  policyTypes:
  - Ingress
  - Egress
  ingress:
    - {}
  egress:
  - to:
    - podSelector:
        matchLabels:
          name: mysql
    ports:
    - protocol: TCP
      port: 3306
  - to:
    - podSelector:
        matchLabels:
          name: payroll
    ports:
    - protocol: TCP
      port: 8080
  - ports:
    - port: 53
      protocol: UDP
    - port: 53
      protocol: TCP

Ingress

imperative way:
k create ingress <ingress-name> --rule="host/path=service:port"

k create ingress ingress-test --rule="wear.my-online-store.com/wear*=wear-service:80"

Cofigmap for ingress

apiVersion: v1
kind: ConfigMap
metadata:
  name: nginx-configuration

Deployment

apiVersion: extensions/v1beta1
kind: Deployment
metadata:
  name: nginx-ingress-controller
spec:
  replicas: 1
  selector:
    matchLabels:
      name: nginx-ingress
  template:
    metadata:
      labels:
        name: nginx-ingress
    spec:
      containers:
        - name: nginx-ingress-controller
          image: quay.io/kubernetes-ingress-controller/nginx-ingress-controller:0.21.0
          args:
            - /nginx-ingress-controller
            - --configmap=$(POD_NAMESPACE)/nginx-configuration
          env:
            - name: POD_NAME
              valueFrom:
                fieldRef:
                  fieldPath: metadata.name
            - name: POD_NAMESPACE
              valueFrom:
                fieldRef:
                  fieldPath: metadata.namespace
          ports:
            - name: http
              containerPort: 80
            - name: https
              containerPort: 443

Service

apiVersion: v1
kind: Service
metadata:
  name: nginx-ingress
spec:
  type: NodePort
  ports:
    - port: 80
      targetPort: 80
      protocol: TCP
      name: http
    - port: 443
      targetPort: 443
      protocol: TCP
      name: https
  selector:
    name: nginx-ingress

Service Account

apiVersion: v1
kind: ServiceAccount
metadata:
  name: nginx-ingress-serviceaccount

k create serviceaccount ingress-nginx --namespace ingress-nginx

k create serviceaccount ingress-nginx-admission --namespace ingress-nginx

Ingress Resource

apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  name: ingress-wear
spec:
  backend:
    serviceName: wear-service
    servicePort: 80

￼

apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  name: ingress-wear
spec:
  backend:
    serviceName: wear-service
    servicePort: 80


apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  name: ingress-wear-watch
spec:
  rules:
    - http:
        paths:
          - path: /wear
            backend:
              service:
                name: wear-service
                port: 80
          - path: /watch
            service:
              backend:
                  name: watch-service
                  port: 80

k describe ingress ingress-wear-watch

2 Rules

apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  name: ingress-wear-watch
spec:
  rules:
    - host: wear.my-online-store.com
      http:
        paths:
          - backend:
              serviceName: wear-service
              servicePort: 80

    - host: watch.my-online-store.com
      http:
        paths:
          - backend:
              serviceName: watch-service
              servicePort: 80

k describe ing ingress-wear-watch -n app-space

k get deploy ingress-nginx-controller -n ingress-nginx -o yaml | grep default

Edit ingress

k edit ingress -n app-space

k create ingress critical-ingress -n critical-space --default-backend=critical-service:80

apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
    nginx.ingress.kubernetes.io/ssl-redirect: "false"
  creationTimestamp: "2025-11-30T12:02:42Z"
  generation: 1
  name: ingress-wear-watch
  namespace: app-space
  resourceVersion: "1262"
  uid: 24c2dc0f-5e6b-468e-8007-b711bc20bd1c
spec:
  rules:
  - http:
      paths:
      - backend:
          service:
            name: wear-service
            port:
              number: 8080
        path: /wear
        pathType: Prefix
      - backend:
          service:
            name: video-service
            port:
              number: 8080
        path: /watch
        pathType: Prefix
status:
  loadBalancer:
    ingress:
    - ip: 172.20.185.87


---
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: critical-ingress
  namespace: critical-space
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
    nginx.ingress.kubernetes.io/ssl-redirect: "false"
spec:
  rules:
  - http:
      paths:
      - path: /pay
        pathType: Prefix
        backend:
          service:
           name: pay-service
           port:
            number: 8282

k create ing ingress-pay -n critical-space --rule=“pay=pay-service:8282"

apiVersion: apps/v1
kind: Deployment
metadata:
  labels:
    app.kubernetes.io/component: controller
    app.kubernetes.io/instance: ingress-nginx
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: ingress-nginx
    app.kubernetes.io/part-of: ingress-nginx
    app.kubernetes.io/version: 1.1.2
    helm.sh/chart: ingress-nginx-4.0.18
  name: ingress-nginx-controller
  namespace: ingress-nginx
spec:
  minReadySeconds: 0
  revisionHistoryLimit: 10
  selector:
    matchLabels:
      app.kubernetes.io/component: controller
      app.kubernetes.io/instance: ingress-nginx
      app.kubernetes.io/name: ingress-nginx
  template:
    metadata:
      labels:
        app.kubernetes.io/component: controller
        app.kubernetes.io/instance: ingress-nginx
        app.kubernetes.io/name: ingress-nginx
    spec:
      containers:
      - args:
        - /nginx-ingress-controller
        - --publish-service=$(POD_NAMESPACE)/ingress-nginx-controller
        - --election-id=ingress-controller-leader
        - --watch-ingress-without-class=true
        - --default-backend-service=app-space/default-http-backend
        - --controller-class=k8s.io/ingress-nginx
        - --ingress-class=nginx
        - --configmap=$(POD_NAMESPACE)/ingress-nginx-controller
        - --validating-webhook=:8443
        - --validating-webhook-certificate=/usr/local/certificates/cert
        - --validating-webhook-key=/usr/local/certificates/key
        env:
        - name: POD_NAME
          valueFrom:
            fieldRef:
              fieldPath: metadata.name
        - name: POD_NAMESPACE
          valueFrom:
            fieldRef:
              fieldPath: metadata.namespace
        - name: LD_PRELOAD
          value: /usr/local/lib/libmimalloc.so
        image: registry.k8s.io/ingress-nginx/controller:v1.1.2@sha256:28b11ce69e57843de44e3db6413e98d09de0f6688e33d4bd384002a44f78405c
        imagePullPolicy: IfNotPresent
        lifecycle:
          preStop:
            exec:
              command:
              - /wait-shutdown
        livenessProbe:
          failureThreshold: 5
          httpGet:
            path: /healthz
            port: 10254
            scheme: HTTP
          initialDelaySeconds: 10
          periodSeconds: 10
          successThreshold: 1
          timeoutSeconds: 1
        name: controller
        ports:
        - name: http
          containerPort: 80
          protocol: TCP
        - containerPort: 443
          name: https
          protocol: TCP
        - containerPort: 8443
          name: webhook
          protocol: TCP
        readinessProbe:
          failureThreshold: 3
          httpGet:
            path: /healthz
            port: 10254
            scheme: HTTP
          initialDelaySeconds: 10
          periodSeconds: 10
          successThreshold: 1
          timeoutSeconds: 1
        resources:
          requests:
            cpu: 100m
            memory: 90Mi
        securityContext:
          allowPrivilegeEscalation: true
          capabilities:
            add:
            - NET_BIND_SERVICE
            drop:
            - ALL
          runAsUser: 101
        volumeMounts:
        - mountPath: /usr/local/certificates/
          name: webhook-cert
          readOnly: true
      dnsPolicy: ClusterFirst
      nodeSelector:
        kubernetes.io/os: linux
      serviceAccountName: ingress-nginx
      terminationGracePeriodSeconds: 300
      volumes:
      - name: webhook-cert
        secret:
          secretName: ingress-nginx-admission

---

apiVersion: v1
kind: Service
metadata:
  creationTimestamp: null
  labels:
    app.kubernetes.io/component: controller
    app.kubernetes.io/instance: ingress-nginx
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: ingress-nginx
    app.kubernetes.io/part-of: ingress-nginx
    app.kubernetes.io/version: 1.1.2
    helm.sh/chart: ingress-nginx-4.0.18
  name: ingress-nginx-controller
  namespace: ingress-nginx
spec:
  ports:
  - port: 80
    protocol: TCP
    targetPort: 80
    nodePort: 30080
  selector:
    app.kubernetes.io/component: controller
    app.kubernetes.io/instance: ingress-nginx
    app.kubernetes.io/name: ingress-nginx
  type: NodePort


---
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: ingress-wear-watch
  namespace: app-space
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
    nginx.ingress.kubernetes.io/ssl-redirect: "false"
spec:
  rules:
  - http:
      paths:
      - path: /wear
        pathType: Prefix
        backend:
          service:
           name: wear-service
           port: 
            number: 8080
      - path: /watch
        pathType: Prefix
        backend:
          service:
           name: video-service
           port:
            number: 8080

k get roles -n ingress-space
k get rolebindinigs -n ingress-space
k describe ingress-role -n ingress-space

State Persistence

volumes:
- name: data-volume
  awsElasticBlockStore
    volumeID: <volume-id>
    fsType: ext4

Persistent Volume

apiVersion: v1
kind: PersistentVolume
metadata:
  name: simple-pv
spec:
  capacity:
    storage: 1Gi
  volumeMode: Filesystem
  accessModes:
    - ReadWriteOnce
  persistentVolumeReclaimPolicy: Retain
  storageClassName: manual
  hostPath:
    path: /tmp/data

apiVersion: v1
kind: PersistentVolume
metadata:
  name: pv-vol1
spec:
  capacity:
    storage: 1Gi
  accessModes:
    - ReadWriteOnce
    awsElasticBlockStore
      volumeID: <volume-id>
      fsType: ext4

k get persistentvolume


  persistentVolumeReclaimPolicy: Retain
  storageClassName: manual
  hostPath:
    path: /tmp/data


Persistent Volume Claims

apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: myclaim
spec:
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 500Mi

k get persistentvolumeclaim

PVC Reclaim Policies

1. Retain. Keeps PV and it’s data 
2. Delete Deletes PV
3. Recycle Data is scrrubbed and PV is made available for caims again (rm -rf /scrub/*)

apiVersion: v1
kind: Pod
metadata:
  name: mypod
spec:
  containers:
    - name: myfrontend
      image: nginx
      volumeMounts:
      - mountPath: "/var/www/html"
        name: mypd
  volumes:
    - name: mypd
      persistentVolumeClaim:
        claimName: myclaim

k exec webapp -- cat /log/app.log

k get po webapp -o yaml > webapp.yaml
edit pod and then:
k replace -f webapp.yaml --force

Create simple pod with volume:

apiVersion: v1
kind: Pod
metadata:
  name: webapp
spec:
  containers:
  - name: event-simulator
    image: kodekloud/event-simulator
    env:
    - name: LOG_HANDLERS
      value: file
    volumeMounts:
    - mountPath: /log
      name: log-volume
  volumes:
  - name: log-volume
    hostPath:
      path: /var/log/webapp
      type: Directory

cat app.log


apiVersion: v1
kind: PersistentVolume
metadata:
  name: pv-log
spec:
  capacity:
    storage: 100Mi
  accessModes:
  - ReadWriteMany
  hostPath:
    path: /pv/log
  persistentVolumeReclaimPolicy: Retain

apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: claim-log-1
spec:
  accessModes:
    - ReadWriteMany
  resources:
    requests:
      storage: 50Mi

k get pvc claim-log-1

updated pod:

apiVersion: v1
kind: Pod
metadata:
  name: webapp
spec:
  containers:
  - name: event-simulator
    image: kodekloud/event-simulator
    env:
    - name: LOG_HANDLERS
      value: file
    volumeMounts:
    - mountPath: /log
      name: log-volume
  volumes:
  - name: log-volume
    persistentVolumeClaim:
      claimName: claim-log-1

k replace —force -f /tmp/kubectl-edit-4194189248.yaml

Storage Class

k get sc

k get sc portworx-io-priority-high -o yaml

apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: local-pvc
spec:
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 500Mi
  storageClassName: local-path

k describe pvc local-pvc

k run nginx --image=nginx:alpine --dry-run=client -oyaml > nginx.yaml

apiVersion: v1
kind: Pod
metadata:
  labels:
    run: nginx
  name: nginx
spec:
  containers:
  - image: nginx:alpine
    name: nginx
    volumeMounts:
      - name: local-persistent-storage
        mountPath: /var/www/html
    resources: {}
  volumes:
    - name: local-persistent-storage
      persistentVolumeClaim:
        claimName: local-pvc
  dnsPolicy: ClusterFirst
  restartPolicy: Always
status: {}

k get pvc

ubernetes controller will automatically bind a PVC to a PV if:
They match on:
1. Storage size (PV capacity.storage ≥ PVC requests.storage)
2. Access modes (PV supports PVC's requested mode)
3. StorageClass (same value, or both empty)
4. VolumeMode (e.g., Filesystem / Block)
If these match → binding happens automatically.


Storage Class Creation

---
apiVersion: storage.k8s.io/v1
kind: StorageClass
metadata:
  name: delayed-volume-sc
provisioner: kubernetes.io/no-provisioner
volumeBindingMode: WaitForFirstConsumer

Article on Setting up Basic Authentication

Create a file with user details locally at /tmp/users/user-details.csv

# User File Contents
password123,user1,u0001
password123,user2,u0002
password123,user3,u0003
password123,user4,u0004
password123,user5,u0005

Edit the kube-apiserver static pod configured by kubeadm to pass in the user details. The file is located at /etc/kubernetes/manifests/kube-apiserver.yaml

apiVersion: v1
kind: Pod
metadata:
  name: kube-apiserver
  namespace: kube-system
spec:
  containers:
  - command:
    - kube-apiserver
      <content-hidden>
    image: k8s.gcr.io/kube-apiserver-amd64:v1.11.3
    name: kube-apiserver
    volumeMounts:
    - mountPath: /tmp/users
      name: usr-details
      readOnly: true
  volumes:
  - hostPath:
      path: /tmp/users
      type: DirectoryOrCreate
    name: usr-details

Modify the kube-apiserver startup options to include the basic-auth file

apiVersion: v1
kind: Pod
metadata:
  creationTimestamp: null
  name: kube-apiserver
  namespace: kube-system
spec:
  containers:
  - command:
    - kube-apiserver
    - --authorization-mode=Node,RBAC
      <content-hidden>
    - --basic-auth-file=/tmp/users/user-details.csv

---
kind: Role
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  namespace: default
  name: pod-reader
rules:
- apiGroups: [""] # "" indicates the core API group
  resources: ["pods"]
  verbs: ["get", "watch", "list"]
 
---
# This role binding allows "jane" to read pods in the "default" namespace.
kind: RoleBinding
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: read-pods
  namespace: default
subjects:
- kind: User
  name: user1 # Name is case sensitive
  apiGroup: rbac.authorization.k8s.io
roleRef:
  kind: Role #this must be Role or ClusterRole
  name: pod-reader # this must match the name of the Role or ClusterRole you wish to bind to
  apiGroup: rbac.authorization.k8s.io


Once created, you may authenticate into the kube-api server using the users credentials

curl -v -k https://localhost:6443/api/v1/pods -u "user1:password123"

k Config

k config view 
k config view - kubeconfig=my-custom-config

k config use-context prrod-user@production


howw many clusters are in kubeconfig:
k config view

k config view --kubeconfig my-kube-config
k config current-context --kubeconfig my-kube-config

use the dev-user to access test-cluster-1
k config --kubeconfig=/root/my-kube-config use-context research

To know the current context, run the command: k config --kubeconfig=/root/my-kube-config current-context

you cant get the pods in the cluster :

controlplane ~ ➜  k get pods
error: unable to read client-cert /etc/kubernetes/pki/users/dev-user/developer-user.crt for dev-user due to open /etc/kubernetes/pki/users/dev-user/developer-user.crt: no such file or directory

1. Identify the Current Certificate Path
k config view --kubeconfig=/root/my-kube-config | grep -A5 "name: dev-user"
2. Verify the Actual Certificate Location
ls -l /etc/kubernetes/pki/users/dev-user/
3. Edit the Kubeconfig File
k config set-credentials dev-user \
  --client-certificate=/etc/kubernetes/pki/users/dev-user/dev-user.crt \
  --client-key=/etc/kubernetes/pki/users/dev-user/dev-user.key \
  --kubeconfig=/root/my-kube-config
4. Test Cluster Access
controlplane ~ ➜  k get pods
No resources found in default namespace.

k get roles
k get rolebindings
k describe role developer
k describe rolebinding devuser-developer-binding

k auth can-i create deploymens
k auth can-i create delete nodes -as dev-user -n test

Role based  access control

authorization modes configured on the cluster:

k describe pod kube-apiserver-controlplane -n kube-system

and look for --authorization-mode

How many roles exist in the default namespace:
k get roles

all namespaces:k get roles --all-namespaces

What are the resources the kube-proxy role in the kube-system namespace is given access to:
k describe role kube-proxy -n kube-system

Actions:
k describe role -n kube-system kube-proxy

Which account is the kube-proxy role assigned to:k describe rolebinding kube-proxy -n kube-system

k get pods --as dev-user

to create role:k create role developer --namespace=default --verb=list,create,delete --resource=pods

to create RoleBinding;
k create rolebinding dev-user-binding --namespace=default --role=developer --user=dev-user

kind: Role
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  namespace: default
  name: developer
rules:
- apiGroups: [""]
  resources: ["pods"]
  verbs: ["list", "create","delete"]

---
kind: RoleBinding
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: dev-user-binding
subjects:
- kind: User
  name: dev-user
  apiGroup: rbac.authorization.k8s.io
roleRef:
  kind: Role
  name: developer
  apiGroup: rbac.authorization.k8s.io

k edit role developer -n blue

add a role:

k edit role developer -n blue

- apiGroups:
  - apps
  resources:
  - deployments
  verbs:
  - create

apiVersion: rbac.authorization.k8s.io/v1
kind: Role
metadata:
  name: developer
  namespace: blue
rules:
- apiGroups:
  - apps
  resourceNames:
  - dark-blue-app
  resources:
  - pods
  verbs:
  - get
  - watch
  - create
  - delete
- apiGroups:
  - apps
  resources:
  - deployments
  verbs:
  - create

k get roles -A —no-headers  | wc -l

k as dev-user create deployment nginx --image=nginx -n blue

Cluster Roles

k api-resources —namespaced=true

How many ClusterRoles do you see defined in the cluster:
k get clusterroles --no-headers | wc -l
k get clusterroles --no-headers -o json | jq '.items | length'

k get clusterrolebindings --no-headers | wc -l
k get clusterrolebindings --no-headers -o json | jq '.items | length'

What namespace is the cluster-admin clusterrole part of:
k api-resources --namespaced=false

What user/groups are the cluster-admin role bound to:
k describe clusterrolebinding cluster-admin

What level of permission does the cluster-admin role grant:
k describe clusterrole cluster-admin


---
kind: ClusterRole
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: node-admin
rules:
- apiGroups: [""]
  resources: ["nodes"]
  verbs: ["get", "watch", "list", "create", "delete"]

---
kind: ClusterRoleBinding
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: michelle-binding
subjects:
- kind: User
  name: michelle
  apiGroup: rbac.authorization.k8s.io
roleRef:
  kind: ClusterRole
  name: node-admin
  apiGroup: rbac.authorization.k8s.io


---
kind: ClusterRole
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: storage-admin
rules:
- apiGroups: [""]
  resources: ["persistentvolumes"]
  verbs: ["get", "watch", "list", "create", "delete"]
- apiGroups: ["storage.k8s.io"]
  resources: ["storageclasses"]
  verbs: ["get", "watch", "list", "create", "delete"]

---
kind: ClusterRoleBinding
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: michelle-storage-admin
subjects:
- kind: User
  name: michelle
  apiGroup: rbac.authorization.k8s.io
roleRef:
  kind: ClusterRole
  name: storage-admin
  apiGroup: rbac.authorization.k8s.io

Admission Controllers

viewing enabled admission controllers
kube-apiserver -h | grep enable-admission plugins
k exec kube-apiseerverr-controlplane -n kube-system — kube-apiiserver -h | grep enable-admission-plugins

ps -ef | grep kube-apiserver | grep admission-plugins

Validating and mutating Admission Controllers:

k create ns webhook-demo

Create a TLS secret named webhook-server-tls in the webhook-demo namespace.
k -n webhook-demo create secret tls webhook-server-tls \
    --cert "/root/keys/webhook-server-tls.crt" \
    --key "/root/keys/webhook-server-tls.key"

webhook server:apiVersion: apps/v1
kind: Deployment
metadata:
  name: webhook-server
  namespace: webhook-demo
  labels:
    app: webhook-server
spec:
  replicas: 1
  selector:
    matchLabels:
      app: webhook-server
  template:
    metadata:
      labels:
        app: webhook-server
    spec:
      securityContext:
        runAsNonRoot: true
        runAsUser: 1234
      containers:
      - name: server
        image: stackrox/admission-controller-webhook-demo:latest
        imagePullPolicy: Always
        ports:
        - containerPort: 8443
          name: webhook-api
        volumeMounts:
        - name: webhook-tls-certs
          mountPath: /run/secrets/tls
          readOnly: true
      volumes:
      - name: webhook-tls-certs
        secret:
          secretName: webhook-server-tls

apiVersion: v1
kind: Service
metadata:
  name: webhook-server
  namespace: webhook-demo
spec:
  selector:
    app: webhook-server
  ports:
    - port: 443
      targetPort: webhook-api


apiVersion: admissionregistration.k8s.io/v1
kind: MutatingWebhookConfiguration
metadata:
  name: demo-webhook
webhooks:
  - name: webhook-server.webhook-demo.svc
    clientConfig:
      service:
        name: webhook-server
        namespace: webhook-demo
        path: "/mutate"
      caBundle: LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tCk1JSURQekNDQWllZ0F3SUJBZ0lVQk84SThhcGJtMFlVSGxPdTltblUwZ1ZSNXBvd0RRWUpLb1pJaHZjTkFRRUwKQlFBd0x6RXRNQ3NHQTFVRUF3d2tRV1J0YVhOemFXOXVJRU52Ym5SeWIyeHNaWElnVjJWaWFHOXZheUJFWlcxdgpJRU5CTUI0WERUSTFNVEl3TXpJd01ERTFNRm9YRFRJMk1ERXdNakl3TURFMU1Gb3dMekV0TUNzR0ExVUVBd3drClFXUnRhWE56YVc5dUlFTnZiblJ5YjJ4c1pYSWdWMlZpYUc5dmF5QkVaVzF2SUVOQk1JSUJJakFOQmdrcWhraUcKOXcwQkFRRUZBQU9DQVE4QU1JSUJDZ0tDQVFFQTBUcmFVUzE5ZG5CdGlBc3EvMDVYdUx4a3ZmRDZRckNEeWgzYQoyNGQyaWFlOGFCZDJqSTVuZjFtRXlzMWFrUzlnZXUxSGw0MmhpWHpkRS9Vd29wd3kvWXgyZTZzdVNLS1pnYjlTClVzeWFLMnhmK2UwRDNZS2Z4WUFsTC9BdlJCWE4zVFlCaWxVWHZUR092U2lwbUV2NXB2T3h6LzUxaThVbzdWK1YKNTVhd1FlWVpjU1dvUlBFTVkxK0lzbHVVUkM3OWhnazNyZmIyWXVReDRGL1RwS043WnZESGI3TFR3YmtCYXhVbwpwS24rMk9VOWxvcElDVmhuZUtSN21ldUF4eHdQOEdwbnZlUW5hb2RJSG9aaXV3T3lFQisrSWZMYlV6d0JBbStTCmdlNDhydG9VS1dIdStWT0xXRGdSVW05ZUJNVG94MWMzZ2k2ZVNVbk05UVNnSmJXZk9RSURBUUFCbzFNd1VUQWQKQmdOVkhRNEVGZ1FVYlpCak10N0U5Q0ZnQStpN1pXR0pyN1htbGNjd0h3WURWUjBqQkJnd0ZvQVViWkJqTXQ3RQo5Q0ZnQStpN1pXR0pyN1htbGNjd0R3WURWUjBUQVFIL0JBVXdBd0VCL3pBTkJna3Foa2lHOXcwQkFRc0ZBQU9DCkFRRUFnRE4zcERONWJpZHI3YXpxN2xVRGwzNkxRdDE5aWc3Qm51SWVXLzlNaHhYRTBUbEkzTkEwNUZ0UVFUYWQKRDBpTjJ6MHV0N0JVZlRiV2o2RGwvOWdvWGI5VU5PT3hlQnloWXBQQ0ZwVFlRc0pPMGhrTURTcEZIQkF5d1FOWApBNzJ1UlRBOGpGMHZtMjF6SzNMV3ZNQkRRSGpheHcxV0dyRVIvZk1WSzBZSWpJWDAzK2tCMkFqT2Eyd1VKaEtlCnkrTHAwMHUzZU04QmVvclJCT0Y2TlpZQnVkOGxJcDBSTHpqK1dlZkxzTytjNFBJdDFBdDFOY0hNRHZjOVBrLzgKd2R5eHRKNUdSUHRLSFY4d0E4OTFNeVhqcGQ1b0VLWTVBT0UzVWZ3MFFNL1RJVUV4MzVHckhCTTlvNGZNTk5vLwoxbXg2Y2hjQjh5b3p3THNJb0JjUmQyQjFKUT09Ci0tLS0tRU5EIENFUlRJRklDQVRFLS0tLS0K
    rules:
      - operations: [ "CREATE" ]
        apiGroups: [""]
        apiVersions: ["v1"]
        resources: ["pods"]
    admissionReviewVersions: ["v1beta1"]
    sideEffects: None


 k create -f /root/webhook-configuration.yaml


pod-with-defaults.yaml

# A pod with no securityContext specified.
# Without the webhook, it would run as user root (0). The webhook mutates it
# to run as the non-root user with uid 1234.
apiVersion: v1
kind: Pod
metadata:
  name: pod-with-defaults
  labels:
    app: pod-with-defaults
spec:
  restartPolicy: OnFailure
  containers:
    - name: busybox
      image: busybox
      command: ["sh", "-c", "echo I am running as user $(id -u)"]

Check the securityContext of the pod created in the previous step (pod-with-defaults:
k get pod pod-with-defaults -o yaml | grep -A2 "securityContext:"

# A pod with a securityContext explicitly allowing it to run as root.
# The effect of deploying this with and without the webhook is the same. The
# explicit setting however prevents the webhook from applying more secure
# defaults.
apiVersion: v1
kind: Pod
metadata:
  name: pod-with-override
  labels:
    app: pod-with-override
spec:
  restartPolicy: OnFailure
  securityContext:
    runAsNonRoot: false
  containers:
    - name: busybox
      image: busybox
      command: ["sh", "-c", "echo I am running as user $(id -u)"]

pod with conflict:
# A pod with a conflicting securityContext setting: it has to run as a non-root
# user, but we explicitly request a user id of 0 (root).
# Without the webhook, the pod could be created, but would be unable to launch
# due to an unenforceable security context leading to it being stuck in a
# 'CreateContainerConfigError' status. With the webhook, the creation of
# the pod is outright rejected.
apiVersion: v1
kind: Pod
metadata:
  name: pod-with-conflict
  labels:
    app: pod-with-conflict
spec:
  restartPolicy: OnFailure
  securityContext:
    runAsNonRoot: true
    runAsUser: 0
  containers:
    - name: busybox
      image: busybox
      command: ["sh", "-c", "echo I am running as user $(id -u)"]

It is a separate plugin
k convert -f old-file —output-version new-api

k converrt -f nginx.yaml —output-version apps/v1

cp -v /etc/kubernetes/manifests/kube-apiserver.yaml /root/kube-apiserver.yaml.backup

--runtime-config

 - command:
    - kube-apiserver
    - --advertise-address=10.18.17.8
    - --allow-privileged=true
    - --authorization-mode=Node,RBAC
    - --client-ca-file=/etc/kubernetes/pki/ca.crt
    - --enable-admission-plugins=NodeRestriction
    - --enable-bootstrap-token-auth=true
    - --runtime-config=rbac.authorization.k8s.io/v1alpha1 --> This one

kubectl-convert command installation:
curl -LO https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl-convert

chmod +x kubectl-convert

mv kubectl-convert /usr/local/bin/kubectl-convert

kubectl-convert --help

kubectl-convert -f ingress-old.yaml --output-version networking.k8s.io/v1

apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
  name: ingress-space
spec:
  rules:
  - http:
      paths:
      - backend:
          service:
            name: ingress-svc
            port:
              number: 80
        path: /video-service
        pathType: Prefix
status:
  loadBalancer: {}

kubectl-convert -f ingress-old.yaml --output-version networking.k8s.io/v1 > ingress-new.yaml

k create -f ingress-new.yaml

k get ing ingress-space -oyaml | grep apiVersion

apiVersion: networking.k8s.io/v1

Custom Resource Definition

Custom Resource It is an extension of the Kubernetes API that is not necessarily available in a default Kubernetes installation.

It can be namespaced:

---
apiVersion: apiextensions.k8s.io/v1
kind: CustomResourceDefinition
metadata:
  name: internals.datasets.kodekloud.com 
spec:
  group: datasets.kodekloud.com
  versions:
    - name: v1
      served: true
      storage: true
      schema:
        openAPIV3Schema:
          type: object
          properties:
            spec:
              type: object
              properties:
                internalLoad:
                  type: string
                range:
                  type: integer
                percentage:
                  type: string
  scope: Namespaced 
  names:
    plural: internals
    singular: internal
    kind: Internal
    shortNames:
    - int

Creation:
apiVersion: datasets.kodekloud.com/v1
kind: Internal
metadata:
  name: my-internal
  namespace: default
spec:
  internalLoad: "high"
  range: 10
  percentage: "50%"

kind: Global
apiVersion: traffic.controller/v1
metadata:
  name: datacenter
spec:
  dataField: 2
  access: true

Helm

helm install wordpress
helm upgrade wordpress
helm rollback wordpress
helm uninstall wordpress

sudo snap install helm —classic

curl

orr pkg install helm

identify OS:
cat /etc/*release*

Helm installation on Ubuntu:

root@controlplane:~# curl https://baltocdn.com/helm/signing.asc | apt-key add -
root@controlplane:~# apt-get install apt-transport-https --yes
root@controlplane:~# echo "deb https://baltocdn.com/helm/stable/debian/ all main" | tee /etc/apt/sources.list.d/helm-stable-debian.list
root@controlplane:~# apt-get update
root@controlplane:~# apt-get install helm

helm env

helm version --debug

helm list

helm search hub wordpress

helm repo add bitnami https://charts.bitnami.com/bitnami

helm search repo wordpress

helm repo list

helm intall release-name chart-name 

helm insatll reelease-1 bitnami/wordpress

helm uninstal my-release

helm pull --untal bitnami/wordpress

ls wordpress

helm uninstall release-4 ./wordpress

helm repo list

helm install bravo bitnami/drupal
helm uninstall bravo

helm pull --untar bitnami/apache

helm install mywebapp ./apache

helm list

Kustomization:

apiVersion: kustomize.config.k8s.io/v1beta1
kind: Kustomization

# kubernetes resources to be managed by kustomize
resources:
  - db/db-config.yaml
  - db/db-depl.yaml
  - db/db-service.yaml
  - message-broker/rabbitmq-config.yaml
  - message-broker/rabbitmq-depl.yaml
  - message-broker/rabbitmq-service.yaml
  - nginx/nginx-depl.yaml
  - nginx/nginx-service.yaml

controlplane ~/code/k8s ➜  pwd
/root/code/k8s

controlplane ~/code/k8s ➜  k apply -k .
-----------OR---------------
controlplane ~/code ➜  kustomize build k8s/ | k apply -f -


controlplane code/k8s/message-broker ➜  cat rabbitmq-service.yaml 
apiVersion: v1
kind: Service
metadata:
  name: rabbit-cluster-ip-service
spec:
  type: ClusterIP
  selector:
    component: redis
  ports:
    - port: 5672
      targetPort: 5672

controlplane ~ ➜  k get svc

controlplane ~/code ➜  tree
.
└── k8s
    ├── db
    │   └── kustomization.yaml
    ├── kustomization.yaml
    ├── message-broker
    │   └── kustomization.yaml
    └── nginx
        └── kustomization.yaml
4 directories, 12 files

resources:
  - db-depl.yaml
  - db-service.yaml
  - db-config.yaml

resources:
  - db-depl.yaml
  - db-service.yaml
  - db-config.yaml


resources:
  - rabbitmq-config.yaml
  - rabbitmq-depl.yaml
  - rabbitmq-service.yaml

resources:
  - nginx-depl.yaml
  - nginx-service.yaml

apiVersion: kustomize.config.k8s.io/v1beta1
kind: Kustomization

# kubernetes resources to be managed by kustomize
resources:
  - db/
  - message-broker/
  - nginx/
# Customizations that need to be made

controlplane ~/code ➜  k apply -k /root/code/k8s/
--------------OR---------------
controlplane ~/code ➜  kustomize build /root/code/k8s/ | k apply -f -


Transformers

Patches

task:
1. Create a new Deployment called nginx-deploy, with:
    * One container called nginx
    * Image: nginx:1.16
    * 4 replicas
    * RollingUpdate strategy with:
        * maxSurge=1
        * maxUnavailable=2
2. Upgrade the Deployment to version 1.17.
3. Once all pods are updated, undo the update and roll back to the previous version.



k create deploy nginx-deploy --image=nginx:1.16 --replicas=4 --dry-run=client -o yaml > deploy.yaml

add strategy:
 strategy:
   type: RollingUpdate
   rollingUpdate:
     maxSurge: 1
      maxUnavailable: 2

k set image deployment nginx-deploy nginx=nginx:1.17 --record=true

k get pods

k rollout history deploy nginx-deploy

REVISION  CHANGE-CAUSE
1         <none>
2         k set image deployment nginx-deploy nginx=nginx:1.17 --record=true

k rollout undo deploy nginx-deploy --to-revision=1

k get pods

k describe deploy

task:Create a Redis Deployment
Create a deployment in the default namespace with the following specifications:
* Name: redis
* Image: redis:alpine
* Replicas: 1
* Labels: app=redis
* CPU Request: 0.2 CPU (200m)
* Container Port: 6379
Volumes:
1. An emptyDir volume named data, mounted at /redis-master-data.
2. A ConfigMap volume named redis-config, mounted at /redis-master.
    * The ConfigMap has already been created for you. Do not create it again.
Note: All resources should be created in the default namespace (unless specified otherwise).

k create deploy redis --image=redis:alpine --replicas=1 --port=6379 --dry-run=client -o yaml > deploy.yaml

apiVersion: apps/v1
kind: Deployment
metadata:
  labels:
    app: redis
  name: redis
spec:
  replicas: 1
  selector:
    matchLabels:
      app: redis
  strategy: {}
  template:
    metadata:
      labels:
        app: redis
    spec:
      containers:
      - image: redis:alpine
        name: redis
        ports:
        - containerPort: 6379
        resources:
          requests:
            cpu: "200m"
        volumeMounts:
         - mountPath: /redis-master-data
           name: data
         - mountPath: /redis-master
           name: redis-config
      volumes:
       - name: data
         emptyDir: {}
       - name: redis-config
         configMap:
           name: redis-config
status: {}


k expose deployment redis --port=6379 --name messaging-service --namespace marketing


check that env was passed to pod:
k exec pod1 -- env | grep "TREE1=trauerweide"

Create the /root/Dockerfile :

FROM bash
CMD ["ping", "killercoda.com"]

Build the image:

podman build -t pinger .

podman image ls

Run the image:

podman run --name my-ping pinger

_____

podman tag pinger local-registry:5000/pinger

podman image ls

podman push local-registry:5000/pinger

___

podman tag pinger pinger:v1

podman tag pinger local-registry:5000/pinger:v1

podman image ls

podman push local-registry:5000/pinger:v1

_____

Why can you call curl wonderful:30080 and it works?

There is a NodePort Service wonderful which listens on port 30080 . It has the Pods of Deployment of app "wonderful" as selector.

We can reach the NodePort Service via the K8s Node IP:

curl 172.30.1.2:30080

And because of an entry in /etc/hosts we can call:

curl wonderful:30080

_______
Perform a Green-Blue rollout of an application


Create a new Deployment and watch these places:

apiVersion: apps/v1
kind: Deployment
metadata:
  labels:
    app: wonderful
  name: wonderful-v2
spec:
  replicas: 4
  selector:
    matchLabels:
      app: wonderful
      version: v2
  template:
    metadata:
      labels:
        app: wonderful
        version: v2
    spec:
      containers:
      - image: nginx:alpine
        name: nginx

Wait till all Pods are running, then switch the Service selector:

apiVersion: v1
kind: Service
metadata:
  labels:
    app: wonderful
  name: wonderful
spec:
  ports:
  - port: 80
    protocol: TCP
    targetPort: 80
    nodePort: 30080
  selector:
    app: wonderful
    version: v2
  type: NodePort

Confirm with curl wonderful:30080 , all requests should hit the new image.

Finally scale down the original Deployment:

k scale deploy wonderful-v1 --replicas 0

_____


Perform a Canary rollout of an application
Reduce the replicas of the old deployment to 8:

apiVersion: apps/v1
kind: Deployment
metadata:
  labels:
    app: wonderful
  name: wonderful-v1
spec:
  replicas: 8
  selector:
    matchLabels:
      app: wonderful
  template:
    metadata:
      labels:
        app: wonderful
    spec:
      containers:
      - image: httpd:alpine
        name: httpd

Create a new Deployment and watch these places:

apiVersion: apps/v1
kind: Deployment
metadata:
  labels:
    app: wonderful
  name: wonderful-v2
spec:
  replicas: 2
  selector:
    matchLabels:
      app: wonderful
  template:
    metadata:
      labels:
        app: wonderful
    spec:
      containers:
      - image: nginx:alpine
        name: nginx

Verify

Call the app with curl wonderful:30080 , around 20% should hit the new image.

___________

apiVersion: apiextensions.k8s.io/v1
kind: CustomResourceDefinition
metadata:
  name: shopping-items.beta.killercoda.com
spec:
  group: beta.killercoda.com
  versions:
    - name: v1
      served: true
      storage: true
      schema:
        openAPIV3Schema:
          type: object
          properties:
            spec:
              type: object
              properties:
                description:
                  type: string
                dueDate:
                  type: string
  scope: Namespaced
  names:
    plural: shopping-items
    singular: shopping-item
    kind: ShoppingItem

k get crd | grep shopping


apiVersion: "beta.killercoda.com/v1"
kind: # TODO fill out
metadata:
  name: bananas
spec:
  # TODO fill out

k -f /code/crd.yaml apply

k get crd | grep shopping

And we can create a new object of it:

apiVersion: "beta.killercoda.com/v1"
kind: ShoppingItem
metadata:
  name: bananas
spec:
  description: buy yellow ones
  dueDate: tomorrow

k get shopping-item
k get shopping-item bananas -oyaml
k delete crd shopping-items.beta.killercoda.com


_________

Helm charts can be installed in any Namespaces, so here we have to look in all.

helm ls -A > /root/releases

k get ingressclass lists all IngressClass resources in the cluster.
IngressClass defines which Ingress controller should handle a given Ingress resource.
In other words, this command shows you the available controllers that can process Ingress rules, for example:
* nginx
* traefik
* alb
* haproxy
* custom controllers

apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: world
  namespace: world
  annotations:
    # this annotation removes the need for a trailing slash when calling urls
    # but it is not necessary for solving this scenario
    nginx.ingress.kubernetes.io/rewrite-target: /
spec:
  ingressClassName: nginx # k get ingressclass
  rules:
  - host: "world.universe.mine"
    http:
      paths:
      - path: /europe
        pathType: Prefix
        backend:
          service:
            name: europe
            port:
              number: 80
      - path: /asia
        pathType: Prefix
        backend:
          service:
            name: asia
            port:
              number: 80

https://editor.networkpolicy.io/?id=JNhu8kykvrmkoZeW

Network Policy

apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: np
  namespace: space1
spec:
  podSelector: {}
  policyTypes:
  - Egress
  egress:
  - ports:
    - port: 53
      protocol: TCP
    - port: 53
      protocol: UDP
  - to:
     - namespaceSelector:
        matchLabels:
         kubernetes.io/metadata.name: space2

# these should work
k -n space1 exec app1-0 -- curl -m 1 microservice1.space2.svc.cluster.local
k -n space1 exec app1-0 -- curl -m 1 microservice2.space2.svc.cluster.local
k -n space1 exec app1-0 -- nslookup tester.default.svc.cluster.local
k -n space1 exec app1-0 -- nslookup killercoda.com

# these should not work
k -n space1 exec app1-0 -- curl -m 1 tester.default.svc.cluster.local
k -n space1 exec app1-0 -- curl -m 1 killercoda.com


Admission Controllers

We can check the kube-apiserver manifest:


cat /etc/kubernetes/manifests/kube-apiserver.yaml

Or the running process:

ps aux | grep kube-apiserver

We filter for the argument:

cat /etc/kubernetes/manifests/kube-apiserver.yaml | grep admission-plugins

And then create a new file in the requested location /root/admission-plugins with content:

NodeRestriction
LimitRanger
Priority


We need to edit the apiserver manifest:


# ALWAYS make a backup
cp /etc/kubernetes/manifests/kube-apiserver.yaml ~/kube-apiserver.yaml

vim /etc/kubernetes/manifests/kube-apiserver.yaml

And add the new plugin to the list:


--enable-admission-plugins=NodeRestriction,LimitRanger,Priority,MutatingAdmissionWebhook

Now we wait till the apiserver restarted using watch crictl ps .

For speed, we could also move the kube-apiserver.yaml manifest out of the directory, wait till the process ended and then move it back it.


k run redis-pod --image=redis:7 --port 6379 --command 'redis-server' '/redis-master/redis.conf' --dry-run=client -o yaml > redis-pod.yaml

cat <<'EOF' > redis-pod.yaml
apiVersion: v1
kind: Pod
metadata:
  name: redis-pod
spec:
  initContainers:
  - name: init-redis-config
    image: busybox:1.36
    command:
    - sh
    - -c
    - |
      set -e
      cat <<EOF >/redis-master/redis.conf
      maxmemory $(cat /configmap/maxmemory)
      maxmemory-policy $(cat /configmap/maxmemory-policy)
      EOF
    volumeMounts:
    - name: redis-config
      mountPath: /redis-master
    - name: redis-config-map
      mountPath: /configmap
      readOnly: true
  containers:
  - name: redis
    image: redis:7
    command:
    - redis-server
    - /redis-master/redis.conf
    ports:
    - containerPort: 6379
    volumeMounts:
    - name: redis-config
      mountPath: /redis-master
    - name: redis-data
      mountPath: /redis-data
  volumes:
  - name: redis-config-map
    configMap:
      name: redis-config
  - name: redis-config
    emptyDir: {}
  - name: redis-data
    emptyDir: {}
EOF

k apply -f redis-pod.yaml

k get pod redis-pod -o wide

k logs redis-pod

Connect to the redis-pod container using the Redis CLI and inspect the configuration values.

Open the Redis shell:

k exec -it redis-pod -- redis-cli
From the Redis prompt, retrieve the maxmemory setting:

CONFIG GET maxmemory
In the same session, check the maxmemory-policy setting:

CONFIG GET maxmemory-policy

——____


Run the command to list the pods and their labels

Run the command to list the pods by their label

Run a command to get the pod IP addresses

Run the command to view which port the pods are exposed on.

Use this command to run busybox pod and get a shell to it for troubleshooting:

k run -it --rm --restart=Never busybox --image=gcr.io/google-containers/busybox sh
From a shell to a pod, see if the pods are listening on the correct port.
You should get something similar to the following output:

hostnames-54b9d67f64-khfsj
hostnames-54b9d67f64-zq79d
hostnames-54b9d67f64-trl7r

Solution

# get the pod labels
k get po --show-labels
# list the pods by their label
k get pods -l app=hostnames
# get the pod IPs
k get po -o wide
for ep in 192.168.0.8:9376 192.168.0.7:9376 192.168.0.9:9376; do
    wget -qO- $ep
done

_______
Check services by DNS and IP

Run the command to list the services
Use this command to run busybox pod and get a shell to it for troubleshooting:

k run -it --rm --restart=Never busybox --image=gcr.io/google-containers/busybox sh
From the busybox pod, see if the service is responding via its DNS name

From the busybox pod, see if the service is responding via its FQDN

View the kubernetes.io documentation for more info on the service FQDN

Check that your /etc/resolv.conf file in your Pod is correct

Check if the kubernetes service is responding via DNS from inside the busybox pod

Test whether your Service works by its IP address


Solution

# list the services
k get svc
# from 'busybox' pod, contact service via DNS
nslookup hostnames
# from 'busybox' pod, contact service via FQDN
nslookup hostnames.default.svc.cluster.local
# from 'busybox' pod, check /etc/resolv.conf
cat /etc/resolv.conf
# from 'busybox' pod, contact 'kubernetes' service
nslookup kubernetes.default
# from 'busybox' pod, check the IP of the service
for i in $(seq 1 3); do 
    wget -qO- 10.0.1.175:80
done

Solution

k get service hostnames -o yaml

k get endpoints hostnames

Apply node affinity to a pod

k create namespace 012963bd

k label node controlplane availability-zone=zone1 --overwrite
k label node node01 availability-zone=zone2 --overwrite

cat <<'EOF_MANIFEST' > az1-pod.yaml
apiVersion: v1
kind: Pod
metadata:
  name: az1-pod
  namespace: 012963bd
spec:
  affinity:
    nodeAffinity:
      preferredDuringSchedulingIgnoredDuringExecution:
      - weight: 80
        preference:
          matchExpressions:
          - key: availability-zone
            operator: In
            values:
            - zone1
      - weight: 20
        preference:
          matchExpressions:
          - key: availability-zone
            operator: In
            values:
            - zone2
  containers:
  - name: main
    image: busybox:1.28
    command: ["sh", "-c", "sleep 3600"]
  restartPolicy: Never
EOF_MANIFEST

k apply -f az1-pod.yaml

k -n 012963bd get pod az1-pod -o wide

Create Dockerfile with Args and Run

cat Dockerfile
FROM busybox

ENTRYPOINT ["sleep"]

CMD ["3600"]

docker build -t busybox-sleep .

docker tag busybox-sleep localhost:5000/busybox-sleep

docker images

Create a job and cronJob in Kubernetes

cat job-countdown.yaml
apiVersion: batch/v1
kind: Job
metadata:
  name: countdown
spec:
  template:
    metadata:
      name: countdown
    spec:
      containers:
      - name: counter
        image: centos:7
        command:
         - "bin/bash"
         - "-c"
         - "for i in 9 8 7 6 5 4 3 2 1 ; do echo $i ; done"
      restartPolicy: Never

k create -f job-countdown.yaml

k get job,pods

k logs countdown-ggb4g

cronjob:

apiVersion: batch/v1
kind: CronJob
metadata:
  name: hello
spec:
  schedule: "* * * * *"
  jobTemplate:
    spec:
      template:
        spec:
          containers:
          - name: hello
            image: busybox:1.28
            imagePullPolicy: IfNotPresent
            command:
            - /bin/sh
            - -c
            - date; echo Hello from the Kubernetes cluster
          restartPolicy: OnFailure

k get cj,job,po

Create a Pod with emptyDir volume

k create namespace volumes


cat <<'EOF_POD' > shared-pod.yaml
apiVersion: v1
kind: Pod
metadata:
  name: shared-pod
  namespace: volumes
spec:
  containers:
  - name: writer
    image: busybox:1.36
    command:
      - sh
      - -c
      - |
        while true; do
          date >> /data/out.log
          sleep 2
        done
    volumeMounts:
    - name: shared
      mountPath: /data
  - name: reader
    image: busybox:1.36
    command:
      - sh
      - -c
      - tail -f /data/out.log
    volumeMounts:
    - name: shared
      mountPath: /data
  volumes:
  - name: shared
    emptyDir: {}
EOF_POD

k apply -f shared-pod.yaml

k logs shared-pod -f -c reader -n volumes
k -n volumes logs shared-pod -c reader --tail=10

Create a custom nginx container image

Create a deployment from your new customer nginx container image with the command k create deploy custom-nginx --image chadmcrowell/nginx-custom:v1

List the deployments and pods in your Kubernetes cluster with the command k get deploy,po

Create the service that will make the deployment available from the web with the command k expose deploy custom-nginx --type=NodePort --port=80 --name=nginx-service

List the services in your cluster with the command k get svc

Copy the NodePort number (e.g. 31221) in the "PORT(S)" column (the number next to 80)

To view your new nginx web page, click on the hamburger menu in the upper right corner (below "Logout"), and select "Traffic / Ports".

Under "Custom Ports" paste in the node port that you copied from the service named "nginx-service"



NFS Volumes in Kubernetes Pods

Install an NFS server on node01
ssh to the worker node with the command ssh node01

run the script located in the current directory with the command ./nfs-server-install.sh

NOTE: Select the defaults when prompted. This scropt will take about 3 minutes to complete. You will only be prompted once.
When the script is finished, exit from the node01 server with the command exit

cat ./nfs-server-install.sh
#/bin/bash

apt update && apt -y upgrade

apt install -y nfs-server

mkdir /data

cat << EOF >> /etc/exports
/data *(rw,no_subtree_check,no_root_squash,insecure)
EOF

# /data *(rw,sync,no_root_squash,insecure)
# /data *(rw,no_subtree_check,no_root_squash,insecure)

# This was the original
# /data 172.30.2.2(rw,no_subtree_check,no_root_squash) 

systemctl enable --now nfs-server

exportfs -ar

# exportfs -rav



# to mount this from another server (specific to killercoda)

# sudo apt update && sudo apt install nfs-common

# sudo mkdir /var/data

# sudo mount -t nfs -o nfsvers=3 172.30.2.2:/data /var/data


look at the pod yaml for the pod that will mount the nfs volume from the nfs server we just setup on node01 with the command cat pod-nfs-vol.yaml

The contents should look like the following:

apiVersion: v1
kind: Pod
metadata:
  name: test
spec:
  containers:
    - name: alpine
      image: alpine:latest
      command: [ 'sh', '-c', 'while true; do echo "some text" >> /data/test; sleep 3600; done' ]
      volumeMounts:
        - name: nfs-volume
          mountPath: /data
  volumes:
    - name: nfs-volume
      nfs:
        server: 172.30.2.2
        path: /data
        readOnly: no
Now create the pod with the command k create -f pod-nfs-vol.yaml

List the pods in the default namespace with the command k get po

The output should look like similar to the following:

NAME   READY   STATUS    RESTARTS   AGE
test   1/1     Running   0          118s


cat ./nfs-helper.sh
#!/bin/bash

sudo apt update && sudo apt install nfs-common

# to mount this from another server (specific to killercoda)

sudo mkdir /var/data

sudo mount -t nfs -o nfsvers=3 172.30.2.2:/data /var/data


Create a pod with an additional sidecar container

cat pod-logging.yaml 
apiVersion: v1
kind: Pod
metadata:
  name: pod-logging
spec:
  containers:
  - name: main
    image: busybox
    args: [/bin/sh, -c, 'while true; do echo $(date); sleep 1; done']

k create -f pod-logging.yaml

cat pod-logging-sidecar.yaml

apiVersion: v1
kind: Pod
metadata:
  name: pod-logging-sidecar
spec:
  containers:
  - image: busybox
    name: main
    args: [ 'sh', '-c', 'while true; do echo "$(date)\n" >> /var/log/main-container.log; sleep 5; done' ]
    volumeMounts:
      - name: varlog
        mountPath: /var/log
  - name: sidecar
    image: busybox
    args: [ /bin/sh, -c, 'tail -f /var/log/main-container.log' ]
    volumeMounts:
      - name: varlog
        mountPath: /var/log
  volumes:
    - name: varlog
      emptyDir: {}

Monitoring Kubernetes with Metrics Server

view the CPU and memory usage on the control plane node:
k top node controlplane

show the metrics for a pod and it's containers:
k top po emptydir-simple --containers

k top po --sort-by=cpu
k top po --sort-by=memory
k top po php-apache --containers --sort-by=memory

Blue Green Deployments in Kubernetes

k create deploy java-hello --image chadmcrowell/hello-world-java --dry-run=client -o yaml > deploy.yaml

apiVersion: apps/v1
kind: Deployment
metadata:
  creationTimestamp: null
  labels:
    app: java-hello
  name: java-hello
spec:
  replicas: 1
  selector:
    matchLabels:
      app: java-hello
  strategy: {}
  template:
    metadata:
      creationTimestamp: null
      labels:
        app: java-hello
        version: 3
    spec:
      containers:
      - image: chadmcrowell/hello-world-java
        name: hello-world-java
        resources: {}
status: {}

k expose deploy java-hello --port 8080 --target-port 8080 --type=nodePort

apiVersion: v1
kind: Service
metadata:
  labels:
    app: hello-java-blue
    version: v1
  name: svc-java-blue
spec:
  ports:
  - port: 8080
    protocol: TCP
    targetPort: 8080
    nodePort: 30001
  selector:
    app: hello-java-green
    version: v2
  type: NodePort

Save the file and exit out of vim. (wq! )
Apply the YAML file and change the existing service with the command k apply -f svc-spring-boot-blue.yaml


# open the file named svc-spring-boot-green.yaml
vim svc-spring-boot-green.yaml

The final result of the YAML file should look like this:
apiVersion: v1
kind: Service
metadata:
  labels:
    app: hello-java-green
    version: v2
  name: svc-java-green
spec:
  ports:
  - port: 8080
    protocol: TCP
    targetPort: 8080
    nodePort: 30000
  selector:
    app: hello-java-blue
    version: v1
  type: NodePort

# modify the existing service by applying the YAML file
k apply -f svc-spring-boot-green.yaml 

Create and Update Deployments
Scale the deployment to 3 replicas

k -n session283884 scale deploy scaler --replicas 3

k -n session283884 get po

k -n session283884 set image deploy scaler nginx-for-k8s=chadmcrowell/nginx-for-k8s:v2

Create a simple nginx pod
k -n session283884 run nginx --image=nginx:1.17 --restart=Never

k -n session283884 get pod nginx

k -n session283884 describe pod nginx

Create a deployment for nginx


k -n session283884 create deployment web --image=nginx:1.18
k -n session283884 scale deployment web --replicas=3
k -n session283884 get deploy,rs,po

Scale a deployment

k -n session283884 scale deployment web --replicas=5

k -n session283884 get deploy,rs,po

Update deployment image

k -n session283884 set image deployment/web nginx=nginx:1.19

k -n session283884 rollout status deployment/web

Expose deployment with ClusterIP

k -n session283884 expose deployment web --port=80 --target-port=80 --type=ClusterIP

k -n session283884 get svc

Deploy a pod to a new namespace
k create ns devteam283884

k -n devteam283884 run nginx --image=nginx --restart=Never

k get pods -n devteam283884

Delete all pods in a namespace

k -n session283884 delete pods --all

Delete whatever is causing the pods to regenerate so the namespace stays empty without deleting the namespace itself.
k -n session283884 delete deployment demo-app

k -n session283884 get pods

Sort pods by restart count 

k -n session283884 get pods --sort-by=.status.containerStatuses[0].restartCount

Run busybox with sleep

k -n session283884 run sleeper --image=busybox --restart=Never -- sleep 3600

k -n session283884 get pod sleeper

Add a preStop hook

cat <<EOF | k -n session283884 apply -f -
apiVersion: v1
kind: Pod
metadata:
  name: prestop
spec:
  containers:
  - name: nginx
    image: nginx
    lifecycle:
      preStop:
        exec:
          command: ["sleep", "10"]
EOF

k -n session283884 describe pod prestop

Label a pod

k -n session283884 label pod nginx app=frontend tier=web

k -n session283884 get pod nginx --show-labels


Explore pod spec with explain

k explain pod.spec.containers

Patch deployment to change port

k -n session283884 patch deployment web --type=json \
  -p='[{"op": "replace", "path": "/spec/template/spec/containers/0/ports/0/containerPort", "value":8080}]'

k -n session283884 get deploy web -o yaml

Group pods by node
k -n session283884 get pods -o wide --sort-by=.spec.nodeName

Create a job that runs once

k -n session283884 create job oneshot --image=busybox -- /bin/sh -c 'echo Hello CKAD'

k -n session283884 get jobs

Mount a ConfigMap as volume

echo "max_players=100" > config.txt
k -n session283884 create configmap game-config --from-file=config.txt

cat <<EOF | k -n session283884 apply -f -
apiVersion: v1
kind: Pod
metadata:
  name: cm-vol-pod
spec:
  containers:
  - name: busybox
    image: busybox
    command: ["sleep", "3600"]
    volumeMounts:
    - name: config
      mountPath: /etc/config
  volumes:
  - name: config
    configMap:
      name: game-config
EOF

Set env vars from ConfigMap
k -n session283884 create configmap env-config --from-literal=ENV=production

cat <<EOF | k -n session283884 apply -f -
apiVersion: v1
kind: Pod
metadata:
  name: cm-env-pod
spec:
  containers:
  - name: busybox
    image: busybox
    command: ["env"]
    envFrom:
    - configMapRef:
        name: env-config
EOF

Mount Secret into a pod

k -n session283884 create secret generic app-secret --from-literal=password=pass123

cat <<EOF | k -n session283884 apply -f -
apiVersion: v1
kind: Pod
metadata:
  name: secret-vol
spec:
  containers:
  - name: busybox
    image: busybox
    command: ["sleep", "3600"]
    volumeMounts:
    - name: secrets
      mountPath: /etc/secrets
  volumes:
  - name: secrets
    secret:
      secretName: app-secret
EOF

Use Secret for env vars

k -n session283884 create secret generic db-secret \
  --from-literal=username=admin \
  --from-literal=password=secret123

cat <<EOF | k -n session283884 apply -f -
apiVersion: v1
kind: Pod
metadata:
  name: db-env-pod
spec:
  containers:
  - name: busybox
    image: busybox
    command: ["env"]
    env:
    - name: DB_USER
      valueFrom:
        secretKeyRef:
          name: db-secret
          key: username
    - name: DB_PASS
      valueFrom:
        secretKeyRef:
          name: db-secret
          key: password
EOF

Set CPU and memory limits

cat <<EOF | k -n session283884 apply -f -
apiVersion: v1
kind: Pod
metadata:
  name: limited
spec:
  containers:
  - name: busybox
    image: busybox
    command: ["sleep", "3600"]
    resources:
      requests:
        memory: "64Mi"
        cpu: "100m"
      limits:
        memory: "128Mi"
        cpu: "200m"
EOF


HTTP Readiness Probe

cat <<EOF | k -n session283884 apply -f -
apiVersion: v1
kind: Pod
metadata:
  name: nginx-ready
spec:
  containers:
  - name: nginx
    image: nginx
    readinessProbe:
      httpGet:
        path: /
        port: 80
      initialDelaySeconds: 5
      periodSeconds: 10
EOF

k -n session283884 get pod nginx-ready

Liveness Probe Restart

cat <<EOF | k -n session283884 apply -f -
apiVersion: v1
kind: Pod
metadata:
  name: nginx-live
spec:
  containers:
  - name: nginx
    image: nginx
    livenessProbe:
      httpGet:
        path: /
        port: 80
      initialDelaySeconds: 5
      periodSeconds: 10
EOF
k -n session283884 get pod nginx-live

TCP Liveness Probe

cat <<EOF | k -n session283884 apply -f -
apiVersion: v1
kind: Pod
metadata:
  name: tcp-check
spec:
  containers:
  - name: nginx
    image: nginx
    livenessProbe:
      tcpSocket:
        port: 80
      initialDelaySeconds: 5
      periodSeconds: 10
EOF

Startup Probe

cat <<EOF | k -n session283884 apply -f -
apiVersion: v1
kind: Pod
metadata:
  name: startup-probe-pod
spec:
  containers:
  - name: nginx
    image: nginx
    startupProbe:
      httpGet:
        path: /
        port: 80
      failureThreshold: 10
      periodSeconds: 5
EOF

Port Forward to Pod

k -n session283884 run nginx-pf --image=nginx --port=80

k -n session283884 port-forward pod/nginx-pf 8080:80

Logs from CrashLoop Pod

k -n session283884 run crashme --image=busybox --restart=Never -- /bin/sh -c 'sleep 10; exit 1'

k -n session283884 logs crashme --previous

Describe Pod Events

k -n session283884 run badpod --image=doesnotexist/broken

k -n session283884 describe pod badpod

Ephemeral Debug Container

k run nginx --image=nginx

k debug -it nginx --image=busybox --target=nginx

Logs from Sidecar

cat <<EOF | k -n session283884 apply -f -
apiVersion: v1
kind: Pod
metadata:
  name: dual-logger
spec:
  containers:
  - name: main
    image: busybox
    command: ["sh", "-c", "while true; do echo main; sleep 5; done"]
  - name: sidecar
    image: busybox
    command: ["sh", "-c", "while true; do echo sidecar; sleep 5; done"]
EOF

k -n session283884 logs dual-logger -c sidecar

Update ConfigMap

k -n session283884 create configmap app-config \
  --from-literal=version=1.0

cat <<EOF | k -n session283884 apply -f -
apiVersion: v1
kind: Pod
metadata:
  name: configpod
spec:
  containers:
    - name: busybox
      image: busybox
      command: ["sleep", "3600"]
      volumeMounts:
        - name: config
          mountPath: /etc/config
  volumes:
    - name: config
      configMap:
        name: app-config
EOF

k -n session283884 create configmap app-config --from-literal=version=2.0 -o yaml --dry-run=client | k apply -f -

Rotate Secret

k -n session283884 create secret generic db-pass \
  --from-literal=password=oldpass

cat <<EOF | k -n session283884 apply -f -
apiVersion: v1
kind: Pod
metadata:
  name: secretpod
spec:
  containers:
    - name: busybox
      image: busybox
      command: ["sleep", "3600"]
      volumeMounts:
        - name: secret
          mountPath: /etc/secret
  volumes:
    - name: secret
      secret:
        secretName: db-pass
EOF

k -n session283884 create secret generic db-pass \
  --from-literal=password=newpass \
  --dry-run=client -o yaml | k apply -f -

Default CPU/Memory Limits

cat <<EOF | k -n session283884 apply -f -
apiVersion: v1
kind: LimitRange
metadata:
  name: default-limits
spec:
  limits:
    - type: Container
      default:
        cpu: 200m
        memory: 128Mi
      defaultRequest:
        cpu: 100m
        memory: 64Mi
EOF

k -n session283884 run limits-pod \
  --image=busybox \
  --restart=Never \
  -- sleep 3600

LimitRange for Namespace

cat <<EOF | k -n session283884 apply -f -
apiVersion: v1
kind: LimitRange
metadata:
  name: mem-cpu-limit
spec:
  limits:
    - type: Container
      max:
        memory: "512Mi"
        cpu: "1"
EOF

Create ServiceAccount

k -n session283884 create serviceaccount build-bot

cat <<EOF | k -n session283884 apply -f -
apiVersion: v1
kind: Pod
metadata:
  name: sa-pod
spec:
  serviceAccountName: build-bot
  containers:
    - name: busybox
      image: busybox
      command: ["sleep", "3600"]
EOF

Role and RoleBinding

k -n session283884 create role pod-reader --verb=get,list,watch --resource=pods

k -n session283884 create rolebinding read-pods-binding --role=pod-reader --serviceaccount=session283884:build-bot

Restrict Pod Deletes with RBAC

k -n session283884 create role no-delete-pods --verb=get,list --resource=pods

k -n session283884 create rolebinding deny-delete-binding --role=no-delete-pods --serviceaccount=session283884:build-bot

k -n session283884 auth can-i delete pods --as=system:serviceaccount:session283884:build-bot

Drop Linux Capabilities

cat <<EOF | k -n session283884 apply -f -
apiVersion: v1
kind: Pod
metadata:
  name: drop-cap
spec:
  containers:
    - name: nginx
      image: nginx
      securityContext:
        capabilities:
          drop:
            - ALL
EOF

Read-Only Root Filesystem

cat <<EOF | k -n session283884 apply -f -
apiVersion: v1
kind: Pod
metadata:
  name: readonly-pod
spec:
  containers:
  - name: nginx
    image: nginx
    securityContext:
      readOnlyRootFilesystem: true
EOF

PodSecurity Restricted Namespace

k label ns session283884 \
  pod-security.kubernetes.io/enforce=restricted \
  --overwrite

cat <<EOF | k -n session283884 apply -f -
apiVersion: v1
kind: Pod
metadata:
  name: privileged
spec:
  containers:
    - name: busybox
      image: busybox
      command: ["sleep", "3600"]
      securityContext:
        privileged: true
EOF

Deny All Ingress

# namespace and workloads
cat <<'EOF' | k apply -f -
apiVersion: v1
kind: Namespace
metadata:
  name: payments
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: api
  namespace: payments
spec:
  replicas: 2
  selector:
    matchLabels:
      app: api
  template:
    metadata:
      labels:
        app: api
    spec:
      containers:
      - name: nginx
        image: nginx:1.25
        ports:
        - containerPort: 80
---
apiVersion: v1
kind: Service
metadata:
  name: api
  namespace: payments
spec:
  selector:
    app: api
  ports:
  - port: 80
    targetPort: 80
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: worker
  namespace: payments
spec:
  replicas: 1
  selector:
    matchLabels:
      app: worker
  template:
    metadata:
      labels:
        app: worker
    spec:
      containers:
      - name: busybox
        image: busybox:1.36
        command:
        - sh
        - -c
        - sleep 3600
EOF

# tester pod for validation
k -n payments run tester --image=busybox:1.36 --restart=Never --command -- sh -c "sleep 3600"

# default deny policy
cat <<'EOF' | k apply -f -
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: default-deny
  namespace: payments
spec:
  podSelector: {}
  policyTypes:
  - Ingress
  - Egress
  ingress: []
  egress: []
EOF

# verify ingress to api is blocked
k -n payments exec tester -- wget -qO- http://api

# verify egress from api pods is blocked
k -n payments exec deploy/api -c nginx -- wget -qO- http://example.com

Allow Namespace Traffic

# namespace, deployment, and service
cat <<'EOF' | k apply -f -
apiVersion: v1
kind: Namespace
metadata:
  name: allow-ns
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: web
  namespace: allow-ns
spec:
  replicas: 2
  selector:
    matchLabels:
      app: web
  template:
    metadata:
      labels:
        app: web
    spec:
      containers:
      - name: nginx
        image: nginx:1.25
        ports:
        - containerPort: 80
---
apiVersion: v1
kind: Service
metadata:
  name: web
  namespace: allow-ns
spec:
  selector:
    app: web
  ports:
  - port: 80
    targetPort: 80
EOF

# prepare namespaces and test pods
k create namespace trusted

k create namespace untrusted

k label namespace trusted access=granted

k -n trusted run client-trusted --image=busybox:1.36 --restart=Never --command -- sh -c "sleep 3600"

k -n untrusted run client-untrusted --image=busybox:1.36 --restart=Never --command -- sh -c "sleep 3600"

# network policy allowing only the labelled namespace
cat <<'EOF' | k apply -f -
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: allow-namespace-traffic
  namespace: allow-ns
spec:
  podSelector:
    matchLabels:
      app: web
  policyTypes:
  - Ingress
  ingress:
  - from:
    - namespaceSelector:
        matchLabels:
          access: granted
    ports:
    - protocol: TCP
      port: 80
EOF

# verify trusted namespace traffic (returns HTML)
k -n trusted exec client-trusted -- wget -qO- http://web.allow-ns

# verify other namespaces are blocked (fails or times out)
k -n untrusted exec client-untrusted -- wget -qO- http://web.allow-ns

Allow Label Traffic

# create namespace, deployment, and service
cat <<'EOF' | k apply -f -
apiVersion: v1
kind: Namespace
metadata:
  name: allow-label
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: web
  namespace: allow-label
spec:
  replicas: 2
  selector:
    matchLabels:
      app: web
  template:
    metadata:
      labels:
        app: web
    spec:
      containers:
      - name: nginx
        image: nginx:1.25
        ports:
        - containerPort: 80
---
apiVersion: v1
kind: Service
metadata:
  name: web
  namespace: allow-label
spec:
  selector:
    app: web
  ports:
  - port: 80
    targetPort: 80
EOF

# launch test pods
k -n allow-label run bb-allowed --image=busybox:1.36 --labels=access=granted --restart=Never --command -- sh -c "sleep 3600"

k -n allow-label run bb-denied --image=busybox:1.36 --restart=Never --command -- sh -c "sleep 3600"

# apply the network policy allowing only labelled pods to reach web on port 80
cat <<'EOF' | k apply -f -
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: allow-label-traffic
  namespace: allow-label
spec:
  podSelector:
    matchLabels:
      app: web
  policyTypes:
  - Ingress
  ingress:
  - from:
    - podSelector:
        matchLabels:
          access: granted
    ports:
    - protocol: TCP
      port: 80
EOF

# verify allowed traffic (should return HTML)
k -n allow-label exec bb-allowed -- wget -qO- http://web

# verify denied traffic (command should time out or fail)
k -n allow-label exec bb-denied -- wget -qO- http://web

Allow Ingress Only on Port 80

# namespace, deployment, and service
cat <<'EOF' | k apply -f -
apiVersion: v1
kind: Namespace
metadata:
  name: allow-port
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: api
  namespace: allow-port
spec:
  replicas: 2
  selector:
    matchLabels:
      app: api
  template:
    metadata:
      labels:
        app: api
    spec:
      containers:
      - name: nginx
        image: nginx:1.25
        ports:
        - containerPort: 80
---
apiVersion: v1
kind: Service
metadata:
  name: api
  namespace: allow-port
spec:
  selector:
    app: api
  ports:
  - name: http
    port: 80
    targetPort: 80
EOF

# test pods
k -n allow-port run bb-http --image=busybox:1.36 --restart=Never --command -- sh -c "sleep 3600"

k -n allow-port run bb-ssh --image=busybox:1.36 --restart=Never --command -- sh -c "sleep 3600"

# network policy allowing only TCP 80
cat <<'EOF' | k apply -f -
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: allow-only-port
  namespace: allow-port
spec:
  podSelector:
    matchLabels:
      app: api
  policyTypes:
  - Ingress
  ingress:
  - ports:
    - protocol: TCP
      port: 80
    from:
    - podSelector: {}
EOF

# confirm http (port 80) is allowed; expect HTML
k -n allow-port exec bb-http -- wget -qO- http://api

# confirm other ports (22) are blocked; expect timeout or failure
k -n allow-port exec bb-ssh -- nc -z api 22

Block Egress

# namespace, deployment, and service
cat <<'EOF' | k apply -f -
apiVersion: v1
kind: Namespace
metadata:
  name: block-egress
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: api
  namespace: block-egress
spec:
  replicas: 2
  selector:
    matchLabels:
      app: api
  template:
    metadata:
      labels:
        app: api
    spec:
      containers:
      - name: nginx
        image: nginx:1.25
        ports:
        - containerPort: 80
---
apiVersion: v1
kind: Service
metadata:
  name: api
  namespace: block-egress
spec:
  selector:
    app: api
  ports:
  - port: 80
    targetPort: 80
EOF

# client pod for testing ingress
k -n block-egress run bb-client --image=busybox:1.36 --restart=Never --command -- sh -c "sleep 3600"

# network policy denying all egress while permitting ingress on port 80
cat <<'EOF' | k apply -f -
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: block-egress
  namespace: block-egress
spec:
  podSelector:
    matchLabels:
      app: api
  policyTypes:
  - Ingress
  - Egress
  ingress:
  - from:
    - podSelector: {}
    ports:
    - protocol: TCP
      port: 80
  egress: []
EOF

# verify ingress still works (returns HTML)
k -n block-egress exec bb-client -- wget -qO- http://api

# verify egress is blocked (should fail)
k -n block-egress exec deploy/api -c nginx -- wget -qO- http://example.com

Create a Headless Service

k create namespace stateful-demo

k create serviceaccount web-sa -n stateful-demo

cat <<'STATEFUL' | k apply -f -
apiVersion: apps/v1
kind: StatefulSet
metadata:
  name: web
  namespace: stateful-demo
spec:
  serviceName: web
  replicas: 3
  selector:
    matchLabels:
      app: web
  template:
    metadata:
      labels:
        app: web
    spec:
      serviceAccountName: web-sa
      containers:
      - name: nginx
        image: nginx:1.25
        ports:
        - containerPort: 80

STATEFUL

cat <<'SERVICE' | k apply -f -
apiVersion: v1
kind: Service
metadata:
  name: web
  namespace: stateful-demo
spec:
  clusterIP: None
  selector:
    app: web
  ports:
  - port: 80
    targetPort: 80

SERVICE

k -n stateful-demo run dns-client --image=busybox:1.36 --
restart=Never --command -- sh -c "sleep 3600"

k -n stateful-demo exec dns-client -- nslookup web.stateful-demo.svc.cluster.local

Test DNS Resolution

k create namespace dns-lab

k create namespace web

k -n dns-lab run dns-client --image=busybox:1.36 --restart=Never --command -- sh -c "sleep 3600"

cat <<'EOF' | k apply -f -
apiVersion: apps/v1
kind: Deployment
metadata:
  name: web
  namespace: web
spec:
  replicas: 2
  selector:
    matchLabels:
      app: web
  template:
    metadata:
      labels:
        app: web
    spec:
      containers:
      - name: nginx
        image: nginx:1.25
        ports:
        - containerPort: 80
---
apiVersion: v1
kind: Service
metadata:
  name: web
  namespace: web
spec:
  selector:
    app: web
  ports:
  - port: 80
    targetPort: 80
EOF

# short name (fails because namespaces differ)
k -n dns-lab exec dns-client -- nslookup web

# FQDN succeeds
k -n dns-lab exec dns-client -- nslookup web.web.svc.cluster.local

# convert to headless service and resolve pod endpoints
k -n web delete service web

k -n web expose deployment web --name=web --port=80 --cluster-ip=None

k -n dns-lab exec dns-client -- nslookup web.web.svc.cluster.local

Externalname Service

k create namespace externalname-demo

cat <<'EOF' | k apply -f -
apiVersion: v1
kind: Service
metadata:
  name: external-search
  namespace: externalname-demo
spec:
  type: ExternalName
  externalName: www.google.com
EOF

k -n externalname-demo run dns-tool --image=busybox:1.36 --restart=Never --command -- sh -c "sleep 3600"

k -n externalname-demo exec dns-tool -- nslookup external-search.externalname-demo.svc.cluster.local

Nodeport Service

k create deploy web --image=nginx:1.25 --replicas=3

k expose deploy web --port=80 --target-port=80 --type=NodePort --name=web-nodeport --overrides='{"spec":{"ports":[{"port":80,"targetPort":80,"nodePort":32080}]}}'

k get svc web-nodeport

k get nodes -o wide

# just the INTERNAL-IP column for quick reference
NODE_IP=$(k get nodes -o jsonpath='{range .items[*]}{.status.addresses[?(@.type=="InternalIP")].address}{"\n"}{end}')

curl -I http://$NODE_IP:32080

Test Service Connectivity

k create namespace svc-test

k -n svc-test create deploy web --image=nginx:1.25 --replicas=2 --port=80

k -n svc-test expose deploy web --name=web --port=80 --target-port=80

k -n svc-test run tester --image=busybox:1.36 --restart=Never --command -- sh -c "sleep 3600"

k -n svc-test exec tester -- nslookup web

k -n svc-test exec tester -- wget -qO- http://web

k -n svc-test get endpoints web

Ingress Controller


# apply the official manifest
k apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/cloud/deploy.yaml

# wait for the controller pod to be ready
k -n ingress-nginx rollout status deploy/ingress-nginx-controller --timeout=180s

# list controller pods
k -n ingress-nginx get pods -l app.kubernetes.io/component=controller

# inspect the service exposing the controller
k -n ingress-nginx get svc ingress-nginx-controller
Note: In the Killercoda environment the EXTERNAL-IP column displays <pending> permanently, because there is no external load balancer available. Use the NodePort listed in the service instead when you need to access the controller.

# change the service type to NodePort so it is reachable
k -n ingress-nginx patch svc ingress-nginx-controller -p '{"spec":{"type":"NodePort","ports":[{"name":"http","port":80,"protocol":"TCP","targetPort":80,"nodePort":30000},{"name":"https","port":443,"protocol":"TCP","targetPort":443,"nodePort":30443}]}}'

# confirm the NodePort that was allocated
k -n ingress-nginx get svc ingress-nginx-controller -o wide

# just the INTERNAL-IP column for quick reference
NODE_IP=$(k get nodes -o jsonpath='{range .items[*]}{.status.addresses[?(@.type=="InternalIP")].address}{"\n"}{end}')

# sample backend to exercise the controller
k create deploy demo --image=nginx:1.25 --port=80

k expose deploy demo --port=80 --target-port=80

cat <<'EOF' | k apply -f -
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: demo
  namespace: default
spec:
  ingressClassName: nginx
  rules:
  - host: demo.local
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: demo
            port:
              number: 80
EOF

# hit the ingress with the Host header
curl -I -H 'Host: demo.local' http://$NODE_IP:30000/

Ingress Host Routing

k create namespace ingress-host-routing

k -n ingress-host-routing create deploy web --image=nginx:1.25 --port=80

k -n ingress-host-routing expose deploy web --port=80 --target-port=80

cat <<'EOF' | k apply -f -
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: web-ingress
  namespace: ingress-host-routing
spec:
  ingressClassName: nginx
  rules:
  - host: web.example.com
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: web
            port:
              number: 80
EOF

k -n ingress-host-routing get ingress web-ingress

# just the INTERNAL-IP column for quick reference
NODE_IP=$(k get nodes -o jsonpath='{range .items[*]}{.status.addresses[?(@.type=="InternalIP")].address}{"\n"}{end}')

curl -I -H 'Host: web.example.com' http://$NODE_IP:30000/

Ingress Tls

k create namespace ingress-tls

openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout tls.key -out tls.crt \
  -subj "/CN=tls.example.com/O=Example Org"

k -n ingress-tls create secret tls tls-secret --cert=tls.crt --key=tls.key

k -n ingress-tls create deploy web --image=nginx:1.25 --port=80

k -n ingress-tls expose deploy web --port=80 --target-port=80

cat <<'EOF2' | k apply -f -
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: web-ingress
  namespace: ingress-tls
spec:
  ingressClassName: nginx
  tls:
  - hosts:
    - tls.example.com
    secretName: tls-secret
  rules:
  - host: tls.example.com
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: web
            port:
              number: 80
EOF2

k -n ingress-tls get ingress web-ingress -o yaml | sed -n '1,40p'

# just the INTERNAL-IP column for quick reference
NODE_IP=$(k get nodes -o jsonpath='{range .items[*]}{.status.addresses[?(@.type=="InternalIP")].address}{"\n"}{end}')

curl -kI -H 'Host: tls.example.com' https://$NODE_IP:30443/

Canary Ingress Deployment

# Solution commands for canary-ingress
cat <<'EOF' | k apply -f -
apiVersion: v1
kind: Namespace
metadata:
  name: canary-ingress
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: web
  namespace: canary-ingress
spec:
  replicas: 2
  selector:
    matchLabels:
      app: web
  template:
    metadata:
      labels:
        app: web
    spec:
      containers:
      - name: echo
        image: hashicorp/http-echo:0.2.3
        args:
        - "-text=web"
        ports:
        - containerPort: 5678
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: web-v2
  namespace: canary-ingress
spec:
  replicas: 1
  selector:
    matchLabels:
      app: web-v2
  template:
    metadata:
      labels:
        app: web-v2
    spec:
      containers:
      - name: echo
        image: hashicorp/http-echo:0.2.3
        args:
        - "-text=web-v2"
        ports:
        - containerPort: 5678
---
apiVersion: v1
kind: Service
metadata:
  name: web
  namespace: canary-ingress
spec:
  selector:
    app: web
  ports:
  - port: 80
    targetPort: 5678
---
apiVersion: v1
kind: Service
metadata:
  name: web-v2
  namespace: canary-ingress
spec:
  selector:
    app: web-v2
  ports:
  - port: 80
    targetPort: 5678
EOF

# primary ingress sending 100% of traffic to web
cat <<'EOF' | k apply -f -
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: web
  namespace: canary-ingress
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
spec:
  ingressClassName: nginx
  rules:
  - host: canary.local
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: web
            port:
              number: 80
EOF

# canary ingress sending 10% of traffic to web-v2
cat <<'EOF' | k apply -f -
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: web-canary
  namespace: canary-ingress
  annotations:
    nginx.ingress.kubernetes.io/canary: "true"
    nginx.ingress.kubernetes.io/canary-weight: "10"
spec:
  ingressClassName: nginx
  rules:
  - host: canary.local
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: web-v2
            port:
              number: 80
EOF

# busybox client to probe ingress responses
k -n canary-ingress run curl --image=busybox:1.36 --restart=Never --command -- sh -c "while true; do wget -qO- http://canary.local && sleep 1; done"

# stream several responses to observe ~90/10 split
k -n canary-ingress logs -f curl

Patch Deployment Image with Kustomize

mkdir -p kustomize-patch-image/base

mkdir -p kustomize-patch-image/overlays/stage

cat <<'EOF_DEP' > kustomize-patch-image/base/deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: web
spec:
  replicas: 2
  selector:
    matchLabels:
      app: web
  template:
    metadata:
      labels:
        app: web
    spec:
      containers:
      - name: nginx
        image: nginx:1.21
        ports:
        - containerPort: 80
EOF_DEP

cat <<'EOF_BASE_K' > kustomize-patch-image/base/kustomization.yaml
resources:
  - deployment.yaml
EOF_BASE_K

cat <<'EOF_OVERLAY_K' > kustomize-patch-image/overlays/stage/kustomization.yaml
resources:
  - ../../base
images:
  - name: nginx
    newTag: "1.23"
EOF_OVERLAY_K

k apply -k kustomize-patch-image/overlays/stage

k get deployment web -o 

jsonpath='{.spec.template.spec.containers[0].image}'

Kustomize Env Overlay

Create two Kustomize overlays (dev and prod ) from a base deployment.

directory layout:

my-kustomize-project/
├── base/
│   ├── deployment.yaml
│   ├── service.yaml
│   └── kustomization.yaml
├── overlays/
│   ├── dev/
│   │   ├── kustomization.yaml
│   │   └── patch-deployment.yaml
│   └── prod/
│       ├── kustomization.yaml
│       └── patch-deployment.yaml

base/kustomization.yaml

resources:
  - deployment.yaml
  - service.yaml

overlays/dev/kustomization.yaml

resources:
  - ../../base

patchesStrategicMerge:
  - patch-deployment.yaml

namePrefix: dev-
namespace: dev

overlays/prod/kustomization.yaml

resources:
  - ../../base

patchesStrategicMerge:
  - patch-deployment.yaml

namePrefix: prod-
namespace: prod

overlays/prod/patch-deployment.yaml

apiVersion: apps/v1
kind: Deployment
metadata:
  name: my-app
spec:
  replicas: 3
  template:
    spec:
      containers:
        - name: my-app
          image: my-app:prod
          env:
            - name: ENV
              value: "production"

Apply with Kustomize:

# Apply dev
k apply -k overlays/dev/

# Apply prod
k apply -k overlays/prod/


Kustomize Configmap Secret

mkdir -p kustomize-configmap-secret/base

cat <<'EOF_POD' > kustomize-configmap-secret/base/pod.yaml
apiVersion: v1
kind: Pod
metadata:
  name: app
spec:
  containers:
  - name: app
    image: busybox:1.36
    command: ["sh", "-c", "env && sleep 3600"]
    envFrom:
    - configMapRef:
        name: app-config
    - secretRef:
        name: app-secret
EOF_POD

cat <<'EOF_K' > kustomize-configmap-secret/base/kustomization.yaml
resources:
  - pod.yaml
configMapGenerator:
  - name: app-config
    literals:
      - LOG_LEVEL=debug
      - FEATURE_FLAG=true
secretGenerator:
  - name: app-secret
    literals:
      - API_TOKEN=supersecret
      - API_URL=https://api.internal
EOF_K

k apply -k kustomize-configmap-secret/base

k exec app -- printenv LOG_LEVEL FEATURE_FLAG API_TOKEN API_URL

Kustomize Common Labels

mkdir -p kustomize-common-labels/base

cat <<'EOF_DEP' > kustomize-common-labels/base/deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: api
spec:
  replicas: 2
  selector:
    matchLabels:
      app: api
  template:
    metadata:
      labels:
        app: api
    spec:
      containers:
      - name: api
        image: nginx:1.25
        ports:
        - containerPort: 80
EOF_DEP

cat <<'EOF_SVC' > kustomize-common-labels/base/service.yaml
apiVersion: v1
kind: Service
metadata:
  name: api
spec:
  selector:
    app: api
  ports:
  - port: 80
    targetPort: 80
EOF_SVC

cat <<'EOF_K' > kustomize-common-labels/base/kustomization.yaml
resources:
  - deployment.yaml
  - service.yaml
commonLabels:
  env: dev
  team: platform
EOF_K

k apply -k kustomize-common-labels/base

k get deploy,svc -l team=platform -o custom-columns=KIND:.kind,NAME:.metadata.name,ENV:.metadata.labels.env

Apply Resources with Kustomize

mkdir -p kustomize-demo/base

mkdir -p kustomize-demo/overlays/prod

cat <<'EOF_BASE_DEP' > kustomize-demo/base/deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: web
spec:
  replicas: 2
  selector:
    matchLabels:
      app: web
  template:
    metadata:
      labels:
        app: web
    spec:
      containers:
      - name: nginx
        image: nginx:1.25
        ports:
        - containerPort: 80
EOF_BASE_DEP

cat <<'EOF_BASE_SVC' > kustomize-demo/base/service.yaml
apiVersion: v1
kind: Service
metadata:
  name: web
spec:
  selector:
    app: web
  ports:
  - port: 80
    targetPort: 80
EOF_BASE_SVC

cat <<'EOF_BASE_K' > kustomize-demo/base/kustomization.yaml
resources:
  - deployment.yaml
  - service.yaml
EOF_BASE_K

cat <<'EOF_OVERLAY_K' > kustomize-demo/overlays/prod/kustomization.yaml
resources:
  - ../../base
nameSuffix: -prod
commonLabels:
  env: prod
patches:
  - target:
      kind: Deployment
      name: web
    patch: |
      - op: replace
        path: /spec/replicas
        value: 3
      - op: replace
        path: /spec/template/spec/containers/0/image
        value: nginx:1.25-alpine
EOF_OVERLAY_K

k apply -k kustomize-demo/overlays/prod

k get deploy,svc -l env=prod

Ingress Path Rewriting

k create namespace ingress-path-rewrite

k -n ingress-path-rewrite create deploy web --image=nginx:1.25 --port=80

k -n ingress-path-rewrite expose deploy web --port=80 --target-
port=80

cat <<'EOF2' | k apply -f -
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: web-ingress
  namespace: ingress-path-rewrite
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
spec:
  ingressClassName: nginx
  rules:
  - host: rewrite.example.com
    http:
      paths:
      - path: /app
        pathType: Prefix
        backend:
          service:
            name: web
            port:
              number: 80
EOF2

k -n ingress-path-rewrite get ingress web-ingress
# just the INTERNAL-IP column for quick reference
NODE_IP=$(k get nodes -o jsonpath='{range .items[*]}{.status.addresses[?(@.type=="InternalIP")].address}{"\n"}{end}')

curl -I -H 'Host: rewrite.example.com' http://$NODE_IP:30000/app























