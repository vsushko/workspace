## Useful commands:
returns spark version:
```sh
spark-submit --version
spark-shell --version
spark-sql --version
```

## Commands

read parquet file:
```python
parquetFile = spark.read.parquet("/user/me/part-0001.snappy.parquet")
```
find line in file:
```python
lines.filter(lambda line: "Python" in line)
```
```scala
val pythonLines = lines.filter(line => line.contains("Python"))
```

## Troubleshooting
environment variables PYSPARK_PYTHON and PYSPARK_DRIVER_PYTHON
add to ~/.bash_profile following lines
```
export PYSPARK_PYTHON="/usr/local/bin/python3"
export PYSPARK_DRIVER_PYTHON="/usr/local/bin/python3"
```
