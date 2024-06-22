# AWS

### Well Architected Framework General Guiding Principles
- Stop guessing your capacity needs
- Test systems at production scale
- Automate to make architectural experimentation easier
- Allow for evolutionary architectures
- - Design based on changing requirements
- Drive architectures using data
- Improve thrrough game days
- - Simulate applications for rflash scale days

### AWS Cloud Best Practices - Design Principles
- Scalability: vertical & horizontal
- Disposable Resources: servers should be disposable & easily configured
- Automation: Serverless, Infrastructure as a Service, Auto Scaling ...
- Loose Coupling:
- - Monolith are applications that do more and more over time, become bigger
  - Break it down int smaller, loosely coupled components
  - A change or a failure in one component should not cascade to other components
- Services, not Servers:
- Don't use just EC2
- Use managed services, databases, serverless, etc!

### Well Architected Framework. 6 Pillars
- Operational Excellence
- Security
- Rreliabilityy
- Performance Efficiency
- Cost Optimization
- Sustainability

#### Global infrastructure
```
https://aws.amazon.com/about-aws/global-infrastructure/regional-product-services/?p=ngi&loc=4
```
#### AWS Policy Generator
```
https://awspolicygen.s3.amazonaws.com/policygen.html
```
cli installation:
```
https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html
```
check installation:
```
which aws
/usr/local/bin/aws 
$ aws --version
aws-cli/2.15.36 Python/3.11.8 Darwin/22.6.0 exe/x86_64 prompt/off
```
list AWS IAM users:
```
aws iam list-users
# or
aws iam list-users --region me-central-1
```
create default vpc:
```
https://docs.aws.amazon.com/vpc/latest/userguide/default-vpc.html#create-default-vpc
```

#### Amazon S3 Transfer Acceleration. Speed Comparison
```
https://s3-accelerate-speedtest.s3-accelerate.amazonaws.com/en/accelerate-speed-comparsion.html
```
#### AWS Global Accelerator. Speed Comparison
```
https://speedtest.globalaccelerator.aws/
```
CIDR:
```
https://cidr.xyz/
```
AWS Free Tier:
```
https://aws.amazon.com/free/
```
7 Strategies for Migrating Applications to the Cloud, introducing AWS Mainframe Modernization and AWS Migration Hub Refactor Spaces:
```
https://aws.amazon.com/blogs/enterprise-strategy/new-possibilities-seven-strategies-to-accelerate-your-application-migration-to-aws/
```
