#### useful commands:
returns spark version:
```sh
spark-submit --version
spark-shell --version
spark-sql --version
```

read parquet file:
```python
parquetFile = spark.read.parquet("/user/me/part-0001.snappy.parquet")
```
