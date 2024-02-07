# Istio
Microservices are small nugets of function, and that sounds like it could be simple, but as complexity grows, successful opeerations requuire:
- Visibility (Observability)
  - Monitoring
  - Metrics
  - Tracing
- Traffic management
- Policy Enforcement
- Security
- Resilience and efficiency

A Service Mesh (an appliciation network for services) can provide the above. Kubernetees "native" via platform adapter plugins.

## Istio Architecture
Istio components:
- Istio Pilot (Config Data to Envoys)
- Envoy Pilot. Handles configuration and programming of the proxy sidecars.
- Istio Mixer (Policy Checks, Telemetry). Handles policy decisions for your traffic and gathers telemetry.
- Istio Citadel - the Certificate Authority
- IngressGateway - Handles incoming requests from outside your cluster.

#### Istio Pilot
- Control plane for distributed Envoy instances
- Confgures Istio deployment and pushes out configuration to other system components
- System of Record for Service Mesh
- Routing and resiliency rules
- Exposes API for service discovery, local balancing, routing tables

#### Envoy Proxy
Out of process load balancer
- High performance server/small memory footprint
HTTP/2 and GRPC support:
- Transparent HTTP/1.1 to HTTP/2 proxy
APIs fo Config Management:
- Configuration management via API alone
Advanced Load Balancing:
- Retries, Circuit Breaking, Health Checks, Rate Limits,
Observability:
- L7 visibility, distributed flow tracing

##### In Istio
- Envoy containre is injecteed with istioctl kube-inject or kubernetees initializer
- Controls pod ingress/egress routing
- Config is via API from Pilot

#### Istio Mixer
Policy & Telemetry engine
Provides:
- Access control
- Telemetry
- Quota
- Billing
- Tracing
Flexible pluugin architecture - "adapters" (e.g. Prometheus, Zipkin, ...)

Attribute processors that controls the runtime behavior of mesh-attached services:
- Generates attributes
- Then generates calls to backend infrastructure throuugh adapters
- Handles integrate 3rd party tools (Prometheus, Grafana, custom tools, ...)

All of these "Istio" pieces are expressed as Kubernetes custom resourcs (CRDs)

#### Istio Citadel
Citael is the Istio key and certificate management authority.
Provides service-to-service and end-user authentication
Istio PKI is built on Citadel. The PKI:
- provisions strong workload identities
- automates the key & certificate rotation

Citadel is needed for mutual TLS to work correctly

Suggested best practice is to run Citadel in a dedicated namespace to restrict acceess to the cluster to only adminis.

#### Istio Traffic Management
Istio 1.0 comes with a new traffic management API (v1alpha3)
Not backward compatible, requires manual upgrade.

Four major components/services:
- Gateway
- VirtualService (replaces RouteRule)
- DestinationRule (replaces DestinationPolicy)
- ServiceEntry (replaces EgressRule)

#### Mutual TLS
- Available by default, but not required
- When enabled, provides automatic service-to-service encryption
- Istio has build in CA that watches for k8s service accounts and creates certificate keypair secrets in k8s
- Secrets are automatically mounted when pod is created
- Pilot generates appropriate Envoy config and deploys it
- End-to-end mTLS session generated for each connection.

#### Ingress/Egress
- Istio assumes that all traffic entering/exiting the service mesh transits through Envoy proxies.
- Deploying the Envoy proxy in front of services, operators can conduct A/B testing, deploy canary services, etc. for user-facing services.
- Routing traffic to external web services (e.g. video service API) via the sidecar Envoy allows operators to add failure recovery features (e.g timeouts, retries, circuuit breakers, etc.) and obtain detailed metrics on the connections to these services.

#### Service Observability/Visibility
Monitoring and tracing should not be an afterthought
Ideally a monitoring/tracing system should provide:
- Metrics without instrumenting apps
- Consistent metrics across fleet
- Trace flow of requests across services
- Portable across metric backend providers

Istio adapters seamlessly integrate a number of tools:
- Prometheus - gathers metrics from Istio Mixer
- Grafana - produces dashboards from Prometheus metrics
- Service Graph - generates visualizations of dependencies between services
- Zipkin - distributed tracing

#### Applicatiton/service REsiliencee with Istio
- As the number of microservices, failuree is expected (inevitable?)
- Fault-tolerance in applications is (should be) a requirement
- Istio provides fault tolerance/resilience with no impact on application code
- Istio provides multiple, build-in features to provide fauult tolerance:
  - Timeouts
  - Retries with timeout budget
  - Circuit breakers
  - Health checks
- AZ-aware load balancing w/ automatic failover
- Control connection pool size and request load
- Systematic fault injection

## Installation
Get Istio and extract:
```
curl -sL https://istio.io/downloadIstioctl | sh -
```
```
sudo cp istio-1.0.0/bin/istioctl /usr/local/bin/
```
Get version:
```
istioctl version
```
For google cloud:
```
kubectl create clusterrolebinding cluster-admin-binding \
  --clusterrole=cluster-admin \
  --user=$(gcloud config get-value core/account)
```
Install Istio via Helm:
```
https://istio.io/latest/docs/setup/install/helm/
```

```
for i in install/kubernetes/helm/istio-init/files/crd*yaml; do kubectl apply -f $i; done
kubectl apply -f install/kubernetes/istio-demo-auth.yaml
kubectl get svc -n istio-system
kubectl get pods -n istio-system
kubectl delete -f install/kubernetes/istio-demo-auth.yaml
for i in install/kubernetes/helm/istio-init/files/crd*yaml; do kubectl delete -f $i; done
```

Install the Istio CRDs:
```
istioctl install --set profile=demo -y
```

## Troubleshooting
Install go:
```
brew install go
```
