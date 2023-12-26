Spring native benefints:
- lower cpu usage
- lower memroy footprint
- smaller application image
- faster startup times
- reduced memory consumption
- more consistent response times
- instant peak performance
- polyglot programming and interoperability capabilities

Spring native is build on top of GraalVM and uses it:
- ahead of time compilation
- native image capability

Reduces resources (CPU, Memory, Storage, Traffic) usage -> reduces cloud costs

Microservices:
- scaoing up and down is much faster
- native image fast startuuup time and instant peak performance signifiicantlyy increases microservices scalability
- containers are smaller since created native image contains only used dependencies

drawbacks:
- building a nativie image is a complex prcess involving not only java toolset but also c++ toolset
- building a native image is slower than a regular compilation
- less mature
- additional configuration is required for reflection, resources, and dynamic proxies
- classpath is fixed at build time
- no lazy class loading, everytnig will be loaded in memory on startup

GraalVM Go / No-Go Decision
make sure to perform throught validation to understand the impact on your product:
- unit tests
- integration tests
- system tests
- performance tests
- destructive tests
- soak tests
- secuurity scans
- shadow testing in production

C1 – Client Compiler
- Less time spent compilation
- Less optimized code
- Better for desktop applications
- Avoids long pauses
C2 – Server Compiler
- A little more time spent on compilation
- Better-optimized code
- Better for long-running server applications
Tiered Compilation Strategy
- Both C1 and C2 are used
- Initially, C1 is used, when the number of calls
towards specific methods exceeds the threshold,
the method is recompiled with C2

pros and cons of jusut in time compilation:
Pros
- Compilation to bytecode is usually faster than compilation to machine code
- Allows for code optimizations during code execution based on statistical analysis
- Allows for runtime code evaluation and runtime dynamic features
- Allows for Cross-Platform capabilities without producing dedicated binaries per platform
Cons
- Increased Startup time
- Increased memory usage
- Overhead when analyzing, compiling, and validating code fragments during runtime
- May decrease overall performance if runtime code optimizations will not outweigh overhead caused by
executing compilation in runtime

Advanced Just in Time Compiler (JIT) Overview
- Advanced Just-in-Time (JIT) Compiler is the first runtime mode that allows for performance improvements
- Integrated with HotSpot VM using Java-Level JVM Compiler Interface (JVMCI)
- Best starting point for existing applications and classic workloads
- Provides performance improvements with minimized risk when compared to Ahead of Time Compilation (AOT)

Pros of Ahead Time Compilation (AOT)
- Lower CPU usage
- Lower Memory footprint
- Faster Startup Times
- More Consistent Response Times
- Instant Peak Performance
- Smaller Application Image
- Great for Microservices
- Great for Docker Images and Kubernetes
- Great for Auto-Scaling because of startup times

Cons of Ahead of Time Compilation (AOT)
Limitations
- Limited support for Reflection - requires special handling
- Limited support for Dynamic Proxies, Resources, JNI, ...
- Dynamic Class Loading is Unsupported
- JVM Agents are Unsupported (Profilers, Tracers, ...)
- JMX is Unsupported
- Security Manager Unsupported
- Some differences in behavior when compared to
- HotspotVM
- - Static init runs at build time
- - finalize() does not work
- May produce problems with common frameworks
- - Hibernate ORM
- Building a native image is a complex process involving
not only Java Toolset but also C++ Toolset
- Building a native image is slower than a regular
compilation
- May go against Dev/prod parity
(https://12factor.net/dev-prod-parity)
- Less mature than regular JVM 




