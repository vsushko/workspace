## Useful commands:
returns spark version:
```sh
spark-submit --version
spark-shell --version
spark-sql --version
```
environment variables (https://spark.apache.org/docs/latest/configuration.html#environment-variables): 
```
export PYSPARK_PYTHON=/usr/bin/python3
```
## Commands

read parquet file:
```python
parquetFile = spark.read.parquet("/user/me/part-0001.snappy.parquet")
```
find line in file:
```python
pythonLines = lines.filter(lambda line: "Python" in line)
```
```scala
val pythonLines = lines.filter(line => line.contains("Python"))
```
initializing spark context
```python
from pyspark import SparkConf, SparkContext
conf = SparkConf().setMaster("local").setAppName("My App")
sc = SparkContext(conf = conf)
```
```scala
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
val conf = new SparkConf().setMaster("local").setAppName("My App") val sc = new SparkContext(conf)
```

## Troubleshooting
environment variables PYSPARK_PYTHON and PYSPARK_DRIVER_PYTHON
add to ~/.bash_profile following lines
```
export PYSPARK_PYTHON="/usr/local/bin/python3"
export PYSPARK_DRIVER_PYTHON="/usr/local/bin/python3"
```

How to set spark.akka.frameSize in spark-shell?
Asked to send map output locations for shuffle
```
./bin/spark-submit --name "My app" --master local[4] --conf spark.akka.frameSize=100 --conf "spark.executor.extraJavaOptions=-XX:+PrintGCDetails -XX:+PrintGCTimeStamps" myApp.jar 
```
Stop displaying INFO messages:<br>
https://stackoverflow.com/questions/27781187/how-to-stop-info-messages-displaying-on-spark-console

Dynamic resource allocation
https://stackoverflow.com/questions/40200389/how-to-execute-spark-programs-with-dynamic-resource-allocation
