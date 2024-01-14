List available AWS CloudFormation stack:
```
aws cloudformation list-stacks --query "StackSummaries[].StackName"
```
Remove stack:
```
aws cloudformation delete-stack --stack-name my-vpc-stack
```
