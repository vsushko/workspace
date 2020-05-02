### Definitions
#### Pod
*Pods* are the smallest deployable units of computing that can be created and managed in Kubernetes

A *Pod* (as in a pod of whales or pea pod) is a group of one or more containers (such as Docker containers), with shared storage/network, and a specification for how to run the containers

#### ReplicaSet
A ReplicaSet’s purpose is to maintain a stable set of replica Pods running at any given time. As such, it is often used to guarantee the availability of a specified number of identical Pods.

Основной метод управления репликами Pod и их жизненным циклом. 
Обеспечивает необходимое количество запущенных реплик.
