# EC2
#### Instance types:
```
https://aws.amazon.com/ec2/instance-types
```
#### ssh to ec2 instances
ssh to ec2 instance with cert:
```
ssh -i cert.pem ec2-user@<instance-ip>
```
change cert permissions:
```
chmod 0400 cert.pem
```
putty download link:
```
https://www.chiark.greenend.org.uk/~sgtatham/putty/latest.html
```

## Troubleshooting:
user is not authorized to perform: ecr:GetAuthorizationToken on resource: * because no identity-based policy allows the ecr:GetAuthorizationToken action:
```
https://stackoverflow.com/questions/38587325/aws-ecr-getauthorizationtoken
```

`no basic auth credentials` Github to AWS ECR deployment on git events:
```
https://stackoverflow.com/questions/63262449/no-basic-auth-credentials-github-to-aws-ecr-deployment-on-git-events
```
