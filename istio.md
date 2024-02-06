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
- Envoy Pilot 
- Istio Mixer (Policy Checks, Telemetry)
- Istio Citadel

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


