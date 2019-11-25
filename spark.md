#### useful commands:
returns spark version:
```
spark-submit --version
spark-shell --version
spark-sql --version
```

read parquet file:
```
parquetFile = spark.read.parquet("/user/me/part-0001.snappy.parquet")
```
