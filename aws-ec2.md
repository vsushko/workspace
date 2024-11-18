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
#### Install Stress Utility on Amazon Linux 2 and Amazon Linux 2023
stress.sh
```sh
sudo amazon-linux-extras install epel -y
sudo yum install stress -y
```
```
sudo yum install stress -y
```
will create a high CPU load by running 4 processes that continuously perform computations:
```
stress -c 4
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
install commands such as dig, nslookup
```
sudo yum install -y bind-utils
```

Configuring a profile:
```
aws configure --profile my-aws-account
cat config
aws s3 ls --profile my-aws-account
```
aws cli with MFA:
```
aws sts get-session-token --seriial-number arn:aws:iam::1414124124124:mfa/vasushko -- token-code 812412
aws configure --profile mfa
cat ~/.aws/credentials
aws s3 ls --profile mfa
```
