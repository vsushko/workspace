docs:
```
https://grpc.io/
https://grpc.io/docs/languages/java/
```

Examples:
```
https://github.com/grpc/grpc-java/tree/master/examples
```

API:
```
https://grpc.github.io/grpc-java/javadoc/
```

Enable server reflection:
```
https://github.com/grpc/grpc-java/blob/master/documentation/server-reflection-tutorial.md

```
evans:
```
evans --host localhost --port 50052 --reflection repl
show package
+-------------------------+
|         PACKAGE         |
+-------------------------+
| calculator              |
| grpc.reflection.v1alpha |
+-------------------------+
show service
+-------------------+--------+--------------+---------------+
|      SERVICE      |  RPC   | REQUEST TYPE | RESPONSE TYPE |
+-------------------+--------+--------------+---------------+
| CalculatorService | sum    | SumRequest   | SumResponse   |
| CalculatorService | primes | PrimeRequest | PrimeResponse |
| CalculatorService | avg    | AvgRequest   | AvgResponse   |
| CalculatorService | max    | MaxRequest   | MaxResponse   |
| CalculatorService | sqrt   | SqrtRequest  | SqrtResponse  |
+-------------------+--------+--------------+---------------+
show message
+---------------+
|    MESSAGE    |
+---------------+
| AvgRequest    |
| AvgResponse   |
| MaxRequest    |
|     ...       |
| SumRequest    |
| SumResponse   |
+---------------+
call <rpc_endpoint_name>
```
