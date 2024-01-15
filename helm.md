install helm via brew:
```
brew install hel
```
create chart:
```
helm create vsushko-chart
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
Upgrade chart with modifying the service type from ClusterIP to LoadBalanceer in valuees.yaml file.
Make the application publicly accesseble and save file:
```
vim values.yaml
... change:
service:
  type: ClusterIP -> LoadBalancer
helm upgrade vsushko-chart .
```
get domain name:
```
kubectl get svc
```
What is HELM | Why We need HELM | Create HELM Chart?:
```
https://www.fosstechnix.com/what-is-helm-why-we-need-helm-helm-charts/
```
