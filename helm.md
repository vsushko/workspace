# Helm
Using helm:
```
https://helm.sh/docs/intro/using_helm/
```
bitnami charts:
```
https://github.com/bitnami/charts
```
install helm via brew:
```
brew install helm
```
list helm charts:
```
helm ls
```
search artifact hub:
```
helm search hub spring
```
Application Dashboard for Kubernetes:
```
https://kubeapps.dev/
```
add repo repo:
```
helm repo add bitnami https://repo.com/chart
```
Installs chart from repo:
```
helm install <name> repo/chart
```
display environment variables:
```
helm env
```
remove release helm chart:
```
helm uninstall helm-chart-name
```
create chart:
```
helm create vsushko-chart
```
compile helm chart:
```
cd vsushko-chart
helm dependencies build
```
check for communication with EKS:
```
kubectl config current-context
```
install application with helm:
```
cd chart-folder
helm install vsushko-chart .
```
takes a Helm chart in the current directory and renders its templates using the values specified in the values.yaml file:
```
helm template .
```
helm upgrade:
```
helm upgrade release prod-env
```
Upgrade chart with modifying the service type from ClusterIP to LoadBalanceer in valuees.yaml file.
Make the application publicly accesseble and save file:
```
vim values.yaml
... change:
service:
  type: ClusterIP -> LoadBalancer
helm upgrade vsushko-chart .
(vim templates/deployment.yml
http port -> targetPort containerPort: {{ .Values.service.targetPort }}
)
```
get domain name:
```
kubectl get svc
```
What is HELM | Why We need HELM | Create HELM Chart?:
```
https://www.fosstechnix.com/what-is-helm-why-we-need-helm-helm-charts/
```
helm history:
```
helm history release
```
rollback:
```
helm rollback release 1
```
helm uninstall:
```
helm ls
helm uninstall release
helm uninstall <chart-name>
```
