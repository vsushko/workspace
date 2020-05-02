### Definitions
#### Pod
*Pods* are the smallest deployable units of computing that can be created and managed in Kubernetes

A *Pod* (as in a pod of whales or pea pod) is a group of one or more containers (such as Docker containers), with shared storage/network, and a specification for how to run the containers

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
