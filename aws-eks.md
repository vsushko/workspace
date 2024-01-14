### AWS EKS
How to Create Amazon EKS cluster using eksctl:
```
https://www.fosstechnix.com/how-to-create-amazon-eks-cluster-using-eksctl/
```
install eksctl:
```
brew tap weaveworks/tap
brew install weaveworks/tap/eksctl
eksctl version
```
Install AWS CLI (https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html):
```
curl "https://awscli.amazonaws.com/AWSCLIV2.pkg" -o "AWSCLIV2.pkg"
sudo installer -pkg AWSCLIV2.pkg -target /
which aws
aws --version
```
Create Create EKS Cluster in AWS using eksctl:
```
eksctl create cluster --name demo-ekscluster --region ap-south-1 --version 1.21 --nodegroup-name linux-nodes --node-type t2.micro --nodes 2
```
Check nodes in EKS cluster:
```
kubectl get nodes
kubectl get namespace
kubectl describe nodes ip-192-168-14-229.ap-south-1.compute.internal
```
